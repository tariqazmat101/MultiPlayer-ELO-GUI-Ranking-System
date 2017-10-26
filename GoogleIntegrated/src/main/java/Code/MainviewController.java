package Code;

import com.google.api.client.auth.oauth2.Credential;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainviewController implements Initializable {
    @FXML
    Button button;
    @FXML
    private TableView<Player> playersTableView;
    @FXML
    private TableColumn<Player, String> Users;
    @FXML
    private TableColumn<Player, Integer> Rating;
    @FXML
    private TableColumn<Player, Integer> Wins;
    @FXML
    private TableColumn<Player, String> Streak;
    @FXML
    private TableColumn<Player, Integer> Loss;
    @FXML
    private TableColumn<Player, Number> Rank;

    @FXML
    private ListView matchesListView;


    public static ObservableList<Player> oPlayers = FXCollections.observableArrayList();
    private ObservableList<String> oStreak = FXCollections.observableArrayList();

    private ObservableList<ELOMatch> matches = League.getModel().getLeagueMatches();

    // Reference to the main application
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    Credential credential;
    //Singleton class
    private static MainviewController Controllermodel = new MainviewController();

    public static MainviewController getModel() {
        return Controllermodel;
    }


    @FXML
    public void printHello() {
        System.out.println("hello");
    }

    //Comparator for String, by Day
    private Comparator<? super Player> sortbyRating = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p2.getRating() - p1.getRating();
        }
    };


    public void RefreshList() {

      // MainviewController.getModel().credential
        oPlayers.clear();

        for (Player t : League.getModel().getPlayers()) {
            oPlayers.add(t);
        }
        oPlayers.sort(sortbyRating);

        //COME BACK TO THIS METHOD
        for(Player t: oPlayers){
            if(t.getStreak() > 0 ) //add a special placeicon
            oStreak.add(" " +t.getStreak());
        }


        // playersTableView.setItems(oPlayers);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        //Autohrize first;
        try {
            credential =  google.authorize();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //authorize google credentials possibly in a new
        //





        //load XML Data
        writePlayer();
        MainviewController.getModel().RefreshList();


        //TODO
        //updateLeaderBoard();
        Users.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        Rating.setCellValueFactory(new PropertyValueFactory<Player, Integer>("rating"));
        Wins.setCellValueFactory(new PropertyValueFactory<Player, Integer>("wins"));
        Loss.setCellValueFactory(new PropertyValueFactory<Player, Integer>("losses"));
       // Streak.
        // Streak.setCellValueFactory(new PropertyValueFactory<Player, String>("streak"));


        // TableColumn<Player, Number> Rank = new TableColumn<Person, Number>("#");
        Rank.setSortable(false);
        Rank.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(playersTableView.getItems().indexOf(column.getValue()) + 1));

        playersTableView.setItems(oPlayers);
        matchesListView.setItems(matches);


        matchesListView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent click) {
                if (click.getClickCount() == 2) {
                    int currentItemSelected = matchesListView.getSelectionModel().getSelectedIndex();
                    ELOMatch currentELOMatch = (ELOMatch) matchesListView.getSelectionModel().getSelectedItem();
                    if (currentItemSelected != -1) {
                        System.out.println(currentELOMatch.getPlayers().size());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/XML/matchstats.fxml"));

                        // Create a controller instance
                        MatchStatsController controller = new MatchStatsController(currentELOMatch);
                        // Set it in the FXMLLoader
                        loader.setController(controller);
                        AnchorPane anchorPane = null;
                        try {
                            anchorPane = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(anchorPane, 150, 239);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
        });


    }

    public void changetoAddNewPlayersDialogue(ActionEvent event) throws IOException {
        Parent Dialogue = FXMLLoader.load(getClass().getResource("/XML/addNewPlayer.fxml"));
        Scene scene = new Scene(Dialogue);

        Stage Stage = new Stage();
        Stage.setTitle("New Player");
        Stage.setScene(scene);
        Stage.show();
    }

    @FXML
    private void handleNew() {
        League.getModel().getPlayers().clear();
        mainApp.setPersonFilePath(null);
        RefreshList();

    }

    public void handleOpen() {
        System.out.println("Hello world");
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        System.out.println("DID I WORKKKKKKK");
        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
            RefreshList();
        }
    }
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }



    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

        alert.showAndWait();
    }

    public void changetoCreateMatch(ActionEvent event) throws IOException {
        Parent Dialogue = FXMLLoader.load(getClass().getResource("/XML/MatchesDialogue.fxml"));
        Scene scene = new Scene(Dialogue);

        Stage Stage = new Stage();
        Stage.setTitle("Create New Match");
        Stage.setScene(scene);

        Stage.show();
    }

    public void changetoSearchPlayer(ActionEvent event) throws IOException {
        Parent Dialogue = FXMLLoader.load(getClass().getResource("/XML/searchPlayer.fxml"));
        Scene scene = new Scene(Dialogue);

        Stage Stage = new Stage();
        Stage.setTitle("Create New Match");
        Stage.setScene(scene);
        Stage.show();
    }


    public void writePlayer(){
        League.getModel().addPlayertoLeague(new Player("a",2000,0,0,0));
        League.getModel().addPlayertoLeague(new Player("b",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("c",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("e",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("d",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("g",2000,0,0,0));
        League.getModel().addPlayertoLeague(new Player("gg",2000,0,0,0));
        League.getModel().addPlayertoLeague(new Player("magnus",2500,0,0,0));
        League.getModel().addPlayertoLeague(new Player("carlson",1800,0,0,0));  League.getModel().addPlayertoLeague(new Player("a",2000,0,0,0));
        League.getModel().addPlayertoLeague(new Player("black",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("cat",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("lifee",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("dick",2000,0,0,0));

        League.getModel().addPlayertoLeague(new Player("go",2000,0,0,0));
        League.getModel().addPlayertoLeague(new Player("gama",2000,0,0,0));
        League.getModel().addPlayertoLeague(new Player("monster",2500,0,0,0));
        League.getModel().addPlayertoLeague(new Player("carpet",1800,0,0,0));
    }
}
