package com.longholidayfinder;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.longholidayfinder.DateCalculation.*;

public class CalendarQuickstart {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime oneYearFromNow = yearsFromNow(1);

        Events events = service.events().list("en.indian#holiday@group.v.calendar.google.com")
                .setTimeMin(now)
                .setTimeMax(oneYearFromNow)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();


        List<Event> holidays = events.getItems();
        List<LongHoliday> longHolidays = new ArrayList<>();

        if (holidays.isEmpty()) {
            System.out.println("No upcoming holidays found.");
        } else {
            System.out.println("Upcoming holidays");
            for (Event event : holidays) {
                DateTime eventStartDate = event.getStart().getDate();

                if (isFridayOrMonday(event)) {

                    if (onAFriday(event)) {
                        LongHoliday some = new LongHoliday(eventStartDate, daysAfterDate(2, eventStartDate));
                        some.addHoliday(event.getSummary());
                        longHolidays.add(some);
                    }

                    if (onAMonday(event)) {
                        LongHoliday some = new LongHoliday(daysBeforeDate(2, eventStartDate), eventStartDate);
                        some.addHoliday(event.getSummary());
                        longHolidays.add(some);
                    }
                } else if (isOneDayAwayFromWeekend(event)) {

                    if (onAThursday(event)) {
                        LongHoliday some = new LongHoliday(eventStartDate, daysAfterDate(3, eventStartDate));
                        some.addHoliday(event.getSummary());
                        longHolidays.add(some);
                    }

                    if (onATuesday(event)) {
                        LongHoliday some = new LongHoliday(daysBeforeDate(3, eventStartDate), eventStartDate);
                        some.addHoliday(event.getSummary());
                        longHolidays.add(some);
                    }
                }
            }
        }

        for (LongHoliday some : longHolidays) {
            String calendarId = "primary";
            Event someHoliday = service.events().insert(calendarId, some.getEventReminder()).execute();
            System.out.printf("Event created: %s\n", someHoliday.getHtmlLink());

        }
    }
}