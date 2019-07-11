import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

    public static List<List<Object>> initialCall(String range) throws IOException, GeneralSecurityException{
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1wIaasPTWmgaKb37Y2dJbCGjjNwUssHP9Y4Nop6gUGdc";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void getDriverList(List<Driver> driverList) throws IOException, GeneralSecurityException {
        List<List<Object>> values = initialCall("Drivers");
        values.remove(0);
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                if (!row.get(0).toString().equals("")) {
                    Driver newDriver = new Driver(row.get(0).toString(), row.get(1).toString(), row.get(3).toString(), row.get(4).toString());
                    newDriver.setAttributes(Integer.parseInt(row.get(5).toString()), Integer.parseInt(row.get(6).toString()),
                            Integer.parseInt(row.get(7).toString()), Integer.parseInt(row.get(8).toString()),
                            Integer.parseInt(row.get(9).toString()), Integer.parseInt(row.get(10).toString()),
                            Integer.parseInt(row.get(11).toString()), Integer.parseInt(row.get(12).toString()),
                            Integer.parseInt(row.get(13).toString()), Integer.parseInt(row.get(14).toString()));
                    driverList.add(newDriver);
                }
            }
        }
    }

    public static void getTrackList(List<Track> trackList) throws IOException, GeneralSecurityException {
        List<List<Object>> values = initialCall("Tracks");
        values.remove(0);
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            //System.out.println("Name, Major");
            for (List row : values) {
                if(!row.get(0).toString().equals("")) {
                    Track newTrack = new Track(row.get(0).toString(), TrackType.valueOf(row.get(2).toString()), Double.parseDouble(row.get(3).toString()),
                            Integer.parseInt(row.get(4).toString()), row.get(5).toString(), row.get(6).toString(), row.get(7).toString());
                    trackList.add(newTrack);
                }
            }
        }
    }

    public static List<Engine> getEngineList() {
        List<Engine> engineList = new ArrayList<Engine>();
        List<List<Object>> values = null;
        try {
            values = initialCall("Engines");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        values.remove(0);
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                if (!row.get(0).toString().equals("")) {
                    engineList.add(new Engine(Integer.parseInt(row.get(0).toString()), row.get(1).toString()));
                }
            }
        }
        return engineList;
    }

    public static List<Engine> setEngineStatsByYear(List<Engine> engineList, int year) {
        List<Engine> activeEngineList =  List.copyOf(engineList);
        List<List<Object>> values = null;
        try {
            values = initialCall("Engines " + year);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        values.remove(0);
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                if (!row.get(0).toString().equals("")) {
                    if (row.get(2).toString().equals("TRUE")){
                        engineList.get(Integer.parseInt(row.get(0).toString())).setAttributes(true,
                                Integer.parseInt(row.get(3).toString()), Integer.parseInt(row.get(4).toString()),
                                Integer.parseInt(row.get(5).toString()));
                    }
                    else {
                        activeEngineList.get(Integer.parseInt(row.get(0).toString())).setAttributes(false, 0, 0, 0);
                    }
                }
            }
        }
        return activeEngineList;
    }


    public static List<Team> getTeamList(){
        List<Team> teamList = new ArrayList<Team>();
        List<List<Object>> values = null;
        try {
            values = initialCall("Teams");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        values.remove(0);
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                if (!row.get(0).toString().equals("")) {
                    teamList.add(new Team(row.get(0).toString(), row.get(2).toString()));
                }
            }
        }
        return teamList;
    }

    public static void setTeamStatsByYear(List<Team> teamList, int year, List<Engine> engineList) {
        List<List<Object>> values = null;
        try {
            values = initialCall("Teams " + year);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        values.remove(0);
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                if (!row.get(0).toString().equals("")) {
                    if (row.get(3).toString().equals("TRUE")){
                        Engine theEngine = engineList.stream()
                                .filter(engine -> row.get(4).toString().equals(engine.getName()))
                                .findAny()
                                .orElse(null);
                        teamList.get(Integer.parseInt(row.get(1).toString())).setAttributes(true,
                                theEngine, Integer.parseInt(row.get(5).toString()),
                                Integer.parseInt(row.get(6).toString()), Integer.parseInt(row.get(7).toString()),
                                Integer.parseInt(row.get(8).toString()), Integer.parseInt(row.get(9).toString()),
                                Integer.parseInt(row.get(10).toString()));
                    }
                    else {
                        teamList.get(Integer.parseInt(row.get(1).toString())).setAttributes(false, null, 0, 0, 0, 0, 0, 0);
                    }
                }
            }
        }
    }
}