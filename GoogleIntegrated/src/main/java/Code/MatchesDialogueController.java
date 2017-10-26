package Code;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.AutoCompletionBinding.ISuggestionRequest;

/**
 * Created by tariq on 9/7/2017.
 */
public class MatchesDialogueController implements Initializable {
    @FXML
    private TextField p1, p2, p3, p4, p5;
    @FXML
    private Text p3text, p4text, p5text;
    @FXML
    private RadioButton bulletPoint, blitzPoint, classicalPoint;

    //Create Referece variable
    ELOMatch newMatch = null;

    private int EXTRA_PLAYERS = 0;
    private int MATCH_TYPE = 1;

    @FXML
    private Text text;
    @FXML
    private Button startMatchButton;


    private String textString;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       ArrayList<String> NAMES =  new ArrayList<>();

        for (Player t : League.getModel().getPlayers()) {
            NAMES.add(t.getName());
        }


        TextFields.bindAutoCompletion(p1, NAMES);
        TextFields.bindAutoCompletion(p2, NAMES);
        TextFields.bindAutoCompletion(p3, NAMES);
        TextFields.bindAutoCompletion(p4, NAMES);
        TextFields.bindAutoCompletion(p5, NAMES);
    }

    public void Display0players() {
        ClearandSetVisible(p3);
        ClearandSetVisible(p4);
        ClearandSetVisible(p5);
        p3text.setVisible(false);
        p4text.setVisible(false);
        p5text.setVisible(false);
        EXTRA_PLAYERS = 0;


    }

    public void Display1players() {
        ClearandSetVisible(p4);
        ClearandSetVisible(p5);
        p3text.setVisible(true);
        p3.setVisible(true);
        p4text.setVisible(false);
        p5text.setVisible(false);
        EXTRA_PLAYERS = 1;

    }

    public void Display2players() {
        p3text.setVisible(true);
        p3.setVisible(true);
        p4text.setVisible(true);
        p4.setVisible(true);

        ClearandSetVisible(p5);
        p5text.setVisible(false);
        EXTRA_PLAYERS = 2;

    }

    public void Display3players() {
        p3text.setVisible(true);
        p3.setVisible(true);
        p4text.setVisible(true);
        p4.setVisible(true);

        p5text.setVisible(true);
        p5.setVisible(true);
        EXTRA_PLAYERS = 3;

    }

    public void setBulletPoint() {
        MATCH_TYPE = 0;
    }

    public void setBlitzPoint() {
        MATCH_TYPE = 1;
    }

    public void setClassicalPoint() {
        MATCH_TYPE = 2;
    }


    public void ClearandSetVisible(TextField input) {
        input.setText("");
        input.setVisible(false);

    }

    public void SubmitMatchRequest() {
        if ((!(League.getModel().isPlayerExists(p1.getText()) || League.getModel().isPlayerExists(p2.getText()))) || p1.getText().equals(p2.getText())) {
            textString = "Players do not exist or can't have name input twice";
        } else if (EXTRA_PLAYERS == 0) {
            //CREATE MATCH OBJECT!!
             newMatch = new ELOMatch(MATCH_TYPE);
            Player temp = League.getModel().returnPlayer(p1.getText());
            newMatch.addPlayer(temp);
            temp = League.getModel().returnPlayer(p2.getText());
            newMatch.addPlayer(temp);

        } else if (EXTRA_PLAYERS == 1) {
            if (!(League.getModel().isPlayerExists(p3.getText()))) {
                textString = "First extra player does not exist";
            } else if (p3.getText().equals(p1.getText()) || (p3.getText().equals(p2.getText()))) {
                textString = "Duplicate name detected";
            } else {
                //CREATE MATCH OBJECT!!
                newMatch = new ELOMatch(MATCH_TYPE);
                Player temp = League.getModel().returnPlayer(p1.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p2.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p3.getText());
                newMatch.addPlayer(temp);
            }
        } else if (EXTRA_PLAYERS == 2) {
            if (!(League.getModel().isPlayerExists(p3.getText()))) {
                textString = "3rd player does not exist";
            }
            else if(!(League.getModel().isPlayerExists(p4.getText()))){
                textString = "4rth player does not exist";
            }
            else if (p3.getText().equals(p1.getText()) || p4.getText().equals(p1.getText()) || p3.getText().equals(p2.getText()) || p4.getText().equals(p2.getText())) {
                textString = "Duplicate name detected";
            }
            else if(p3.getText().equals(p4.getText())){
                textString = "Player 3 and player 4 are duplicates";
            }
            else {
                textString = "We have made it";
                newMatch = new ELOMatch(MATCH_TYPE);
                Player temp = League.getModel().returnPlayer(p1.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p2.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p3.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p4.getText());
                newMatch.addPlayer(temp);

            }
        } else if (EXTRA_PLAYERS == 3) {
            if (!(League.getModel().isPlayerExists(p3.getText()) || (League.getModel().isPlayerExists(p4.getText())) || League.getModel().isPlayerExists(p5.getText()))) {
                textString = "First,second, and/or third extra player do not exist";
            } else if (p3.getText().equals(p1.getText()) || p4.getText().equals(p1.getText()) || p5.getText().equals(p1.getText()) || p3.getText().equals(p2.getText())
                    || p4.getText().equals(p2.getText()) || p5.getText().equals(p2.getText()) || p3.getText().equals(p4.getText())) {
                textString = "Duplicate name detected";
            }
            else {
                 newMatch = new ELOMatch(MATCH_TYPE);
                Player temp = League.getModel().returnPlayer(p1.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p2.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p3.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p4.getText());
                newMatch.addPlayer(temp);
                temp = League.getModel().returnPlayer(p5.getText());
                newMatch.addPlayer(temp);

            }
        }
        else {
            textString = "Contact the developer, weird error has come about";
        }


        //ENDING OF THE SUBMIT MATCH REQUEST
        text.setText(textString);
        if (newMatch != null) {
            League.getModel().getLeagueMatches().add(newMatch);
           Stage stage = (Stage) startMatchButton.getScene().getWindow();
//            // do what you have to do
            stage.close();

        }
    }
}


