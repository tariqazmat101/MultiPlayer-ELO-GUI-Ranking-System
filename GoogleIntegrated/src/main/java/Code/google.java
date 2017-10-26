package Code;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class google {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    //Don't set this value, let the user paste the sheetid via textbox;
    public static String SHEET_ID = "1BjxLCVBygxzjG2rQ7QC06sIvXzsvYREfL8-3ClN9yJI";

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                google.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void   WriteExample() throws IOException {
        Sheets service = getSheetsService();
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();

        List<ValueRange> valueRanges = new ArrayList<>();
        //valueRanges.add(new ValueRange().setValues(practice.newOne));



        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue("Donkey World!")));
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(0)
                                .setRowIndex(5)
                                .setColumnIndex(5))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));





        BatchUpdateValuesRequest batchRequest = new BatchUpdateValuesRequest();
        batchRequest.setValueInputOption("RAW");
        batchRequest.setData(valueRanges);

        BatchUpdateSpreadsheetRequest batchUpdateRequest
                = new BatchUpdateSpreadsheetRequest();


        BatchUpdateSpreadsheetResponse response
                = service.spreadsheets()
                .batchUpdate("1DlnWmdOajjjU1-Td9zxFiWaDiEbk-yiIIW2e9dePQ3M", batchUpdateRequest)
                .execute();


        // Spreadsheet response = requests.execute();

        //  System.out.printf("%d replacements made.", findReplaceResponse.getOccurrencesChanged());
        // TODO: Change code below to process the `response` object:
        System.out.println(response);
    }

    public static Sheets createSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // TODO: Change placeholder below to generate authentication credentials. See
        // https://developers.google.com/sheets/quickstart/java#step_3_set_up_the_sample
        //
        // Authorize using one of the following scopes:
        //   "https://www.googleapis.com/auth/drive"
        //   "https://www.googleapis.com/auth/drive.file"
        //   "https://www.googleapis.com/auth/spreadsheets"
        Credential credential = authorize();

        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google-SheetsSample/0.1")
                .build();
    }

    public static Integer CreatePlayerSheetandWritetoIt(String Name ) throws IOException, GeneralSecurityException {
        List<Request> requests = new ArrayList<>();

        Request temp = new Request();
        SheetProperties newProperty  = new SheetProperties();

        temp.setAddSheet(new AddSheetRequest().setProperties(newProperty.setTitle(Name)));
        requests.add(temp);
        BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
        requestBody.setRequests(requests);

        Sheets sheetsService = createSheetsService();

        Sheets.Spreadsheets.BatchUpdate request =
                sheetsService.spreadsheets().batchUpdate(SHEET_ID, requestBody);
        BatchUpdateSpreadsheetResponse response = request.execute();
        Integer playerSheetID = response.getReplies().get(0).getAddSheet().getProperties().getSheetId();

        System.out.println(response.toPrettyString());

        return playerSheetID;
    }

    public static void formatPlayerSheet(Integer sheeid) throws IOException {
        List<Request> requests = new ArrayList<>();

        Request request = new Request();

        List<CellData> values = new ArrayList<>();
        requests.add(new Request().setMergeCells(new MergeCellsRequest().setMergeType("MERGE_ALL").setRange(new GridRange().setSheetId(sheeid)
                .setStartColumnIndex(0).setEndRowIndex(1)
                .setStartRowIndex(0).setEndColumnIndex(12))));

        requests.add(new Request().setMergeCells(new MergeCellsRequest().setMergeType("MERGE_ALL").setRange(new GridRange().setSheetId(sheeid)
                .setStartColumnIndex(0).setEndRowIndex(2)
                .setStartRowIndex(1).setEndColumnIndex(12))));

        for(int i = 3; i < 8; i++) {
            requests.add(new Request().setMergeCells(new MergeCellsRequest().setMergeType("MERGE_ALL").setRange(new GridRange().setSheetId(sheeid)
                    .setStartColumnIndex(0).setEndRowIndex(i + 1)
                    .setStartRowIndex(i).setEndColumnIndex(5))));
        }


        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);

        Sheets service = getSheetsService();
        service.spreadsheets().batchUpdate(SHEET_ID, batchUpdateRequest)
                .execute();
    }

    public static void WriteDatatoSheet(Integer playerID,ArrayList<String> data) throws IOException {
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();

        CellData cellData = new CellData();
        CellFormat myFormatOne = new CellFormat();

        values.add(cellData
                .setUserEnteredValue(new ExtendedValue()
                        //
                        .setStringValue(data.get(0))));
        myFormatOne.setBackgroundColor(new Color().setRed(0f).setBlue(100f)); // red background
        myFormatOne.setTextFormat(new TextFormat().setFontSize(20).setBold(true)); // 16pt font
        myFormatOne.setHorizontalAlignment("CENTER");

        cellData.setUserEnteredFormat(myFormatOne);

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(playerID)
                                .setRowIndex(0 )
                                .setColumnIndex(0))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("*")));


//--------------------------------------------------------------------------------------------------------------------------
        List<CellData> values1 = new ArrayList<>();

        cellData = new CellData();
        values1.add(cellData
                .setUserEnteredValue(new ExtendedValue()
                        //
                        .setStringValue(data.get(1))));
         myFormatOne = new CellFormat();
        myFormatOne.setBackgroundColor(new Color().setRed(100f).setBlue(100f)); // red background
        myFormatOne.setTextFormat(new TextFormat().setFontSize(17).setBold(true)); // 16pt font
        myFormatOne.setHorizontalAlignment("CENTER");


        cellData.setUserEnteredFormat(myFormatOne);

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(playerID)
                                .setRowIndex(1 )
                                .setColumnIndex(0))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values1)))
                        .setFields("*")));





        for(int i = 2;  i < data.size(); i++) {
            cellData = new CellData();
            values.add(cellData
                    .setUserEnteredValue(new ExtendedValue()
                            //
                            .setStringValue(data.get(i))));

             myFormatOne = new CellFormat();
            myFormatOne.setBackgroundColor(new Color().setRed(0f).setBlue(100f)); // red background
            myFormatOne.setTextFormat(new TextFormat().setFontSize(12)); // 16pt font
           // myFormatOne.setHorizontalAlignment("CENTER");


            cellData.setUserEnteredFormat(myFormatOne);

            ArrayList<CellData> celldatas = new ArrayList<>();
            celldatas.add(cellData);
            requests.add(new Request()
                    .setUpdateCells(new UpdateCellsRequest()
                            .setStart(new GridCoordinate()
                                    .setSheetId(playerID)
                                    .setRowIndex(2 + i)
                                    .setColumnIndex(0))
                            .setRows(Arrays.asList(
                                    new RowData().setValues(celldatas)))
                            .setFields("*")));
        }




//




        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);

        Sheets service = getSheetsService();
        service.spreadsheets().batchUpdate(SHEET_ID, batchUpdateRequest)
                .execute();



    }

}