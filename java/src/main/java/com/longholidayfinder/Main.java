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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.longholidayfinder.DateCalculation.yearsAfter;
import static com.longholidayfinder.WeekendCalculation.processHoliday;

public class Main {
    private static final String APPLICATION_NAME = "Long Holiday Finder";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        Calendar myCalendar = getCalendar();

        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime oneYearFromNow = yearsAfter(now,1);

        List<Event> holidays = getEvents(myCalendar, now, oneYearFromNow);
        List<LongHoliday> longHolidays = new ArrayList<>();

        if (holidays.isEmpty()) {
            System.out.println("No upcoming holidays found.");
        } else {
            System.out.println("Upcoming holidays");
            for (Event event : holidays) {
                if(!longHolidays.isEmpty()){
                    LongHoliday mostRecent = longHolidays.get(longHolidays.size()-1);
                    mostRecent.extendHoliday(event);
                }

                LongHoliday myLongHoliday = processHoliday(event);
                if(myLongHoliday != null){
                    longHolidays.add(myLongHoliday);
                }
            }
        }

        for (LongHoliday some : longHolidays) {
            String calendarId = "primary";
            Event someHoliday = myCalendar.events().insert(calendarId, some.getEventReminder()).execute();
            System.out.printf("Event created: %s\n", someHoliday.getHtmlLink());
        }
    }

    private static List<Event> getEvents(Calendar service, DateTime now, DateTime oneYearFromNow) throws IOException {
        return service.events().list("en.indian#holiday@group.v.calendar.google.com")
                    .setTimeMin(now)
                    .setTimeMax(oneYearFromNow)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute()
                    .getItems();
    }

    private static Calendar getCalendar() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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
}