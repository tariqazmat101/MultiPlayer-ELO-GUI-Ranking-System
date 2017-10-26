package Code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tariq on 9/10/2017.
 */

public class searchPlayerController implements Initializable {
    @FXML
    private TextField searchBAR;
    @FXML
    private ListView list;

    @FXML
    private TextField listext;


    ObservableList<Player> entries = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Player p : League.getModel().getPlayers()) {
            entries.add(p);
        }


}
    }