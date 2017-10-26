package Code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tariq on 9/26/2017.
 */

public class MatchStatsController implements Initializable {
    @FXML
    private Text p1, p2, p3, p4, p5;
    @FXML
    private ComboBox c1, c2, c3, c4, c5;
    @FXML
    private Button Donebutton;

    private ELOMatch matchData;
    public ObservableList<Integer> comboList = FXCollections.observableArrayList();
    private Player name1, name2, name3, name4, name5;

    private static int extraPlayers;

    public MatchStatsController(ELOMatch matchData) {
        this.matchData = matchData;
        //Will always be called
        name1 = matchData.getPlayers().get(0);
        name2 = matchData.getPlayers().get(1);

        //set Play standings
        for (int i = 0; i < this.matchData.getPlayers().size(); i++) {
            System.out.println(i);
            comboList.add(i + 1);
        }

        if (this.matchData.getPlayers().size() == 2) {
            extraPlayers = 0;
        } else if (this.matchData.getPlayers().size() == 3) {
            name3 = matchData.getPlayers().get(2);
            extraPlayers = 3;
        } else if (this.matchData.getPlayers().size() == 4) {
            name3 = matchData.getPlayers().get(2);
            name4 = matchData.getPlayers().get(3);
            extraPlayers = 4;
        } else if (this.matchData.getPlayers().size() == 5) {
            name3 = matchData.getPlayers().get(2);
            name4 = matchData.getPlayers().get(3);
            name5 = matchData.getPlayers().get(4);
            extraPlayers = 5;
        } else {

        }

        for (int i = 0; i < matchData.getPlayers().size(); i++) {
            matchData.getPlayers().get(i).addMatch(matchData);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //sett extra components to invisble
        setInvisible();

        p1.setText(name1.getName());
        p2.setText(name2.getName());

        c1.setItems(comboList);
        c2.setItems(comboList);

        if (extraPlayers == 3) {
            p3.setVisible(true);
            c3.setVisible(true);

            p3.setText(name3.getName());
            c3.setItems(comboList);

        } else if (extraPlayers == 4) {

            p3.setVisible(true);
            p4.setVisible(true);

            p3.setText(name3.getName());
            p4.setText(name4.getName());

            c3.setVisible(true);
            c4.setVisible(true);


            c3.setItems(comboList);
            c4.setItems(comboList);


        } else if (extraPlayers == 5) {

            p3.setVisible(true);
            p4.setVisible(true);
            p5.setVisible(true);

            p3.setText(name3.getName());
            p4.setText(name4.getName());
            p5.setText(name5.getName());

            c3.setVisible(true);
            c4.setVisible(true);
            c5.setVisible(true);

            c3.setItems(comboList);
            c4.setItems(comboList);
            c5.setItems(comboList);
        } else {
            //error?
        }

    }

    public void setInvisible() {

        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);

        c3.setVisible(false);
        c4.setVisible(false);
        c5.setVisible(false);
    }

    public void handleDoneRequestion() {
        System.out.println("hELLO");
        //reset fields
        for (Player t : matchData.getPlayers()) {
            t.resetFields();
        }
        //setPlayer Standings
        int temp = (int) c1.getSelectionModel().getSelectedItem();
        name1.setPlace(temp);
        temp = (int) c2.getSelectionModel().getSelectedItem();
        name2.setPlace(temp);

        if (extraPlayers == 3) {
            name3.setPlace((int) c3.getSelectionModel().getSelectedItem());
        } else if (extraPlayers == 4) {
            name3.setPlace((int) c3.getSelectionModel().getSelectedItem());
            name4.setPlace((int) c4.getSelectionModel().getSelectedItem());
        } else if (extraPlayers == 5) {
            name3.setPlace((int) c3.getSelectionModel().getSelectedItem());
            name4.setPlace((int) c4.getSelectionModel().getSelectedItem());
            name5.setPlace((int) c5.getSelectionModel().getSelectedItem());
        }
        for (Player t : matchData.getPlayers()) {
            //System.out.println(t.getName() + " " + t.getPlace());
            System.out.println("Rating before" + t.getName() + " " + t.getRating());
        }
        matchData.calculateELOs();
        MainviewController.getModel().RefreshList();

        for (Player t : matchData.getPlayers()) {
            System.out.println("Rating after" + t.getName() + " " + t.getRating());
        }
    }



    public void getData(ELOMatch data) {
        matchData = data;
    }
}
