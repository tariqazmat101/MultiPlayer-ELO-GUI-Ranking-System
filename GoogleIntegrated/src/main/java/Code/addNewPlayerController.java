package Code;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;


/**
 * Created by tariq on 9/7/2017.
 */
public class addNewPlayerController {
    @FXML
    private Text wins, losses, draws,textGreen;
    @FXML
    public CheckBox checkBox;
    @FXML
    private TextField lossesTextArea, winsTextArea, drawTextArea, ratingTextArea,nameField;
    @FXML
    private Button okButton;

    public void isCheckBoxClicked() {
        if (checkBox.isSelected()) {

            wins.setVisible(false);
            losses.setVisible(false);
            draws.setVisible(false);
            lossesTextArea.setVisible(false);
            winsTextArea.setVisible(false);
            drawTextArea.setVisible(false);

            //set the rating
            ratingTextArea.setText(Player.defaultRating + "");
            ratingTextArea.setDisable(true);
        } else {
            wins.setVisible(true);
            losses.setVisible(true);
            draws.setVisible(true);
            lossesTextArea.setVisible(true);
            winsTextArea.setVisible(true);
            drawTextArea.setVisible(true);

            ratingTextArea.setDisable(false);
            ratingTextArea.setText("");
        }
    }


    public void EnterButton() throws IOException, GeneralSecurityException {
    boolean stringCatch = false;
    String textBanner = "";
        for(Player player :League.getModel().getPlayers()){
            if(player.getName().equals(nameField.getText())){
                System.out.println("We hit somthing!");
                stringCatch = true;
                break;
            }
        }

        if(stringCatch){
            //Name is already taken
            textBanner = "Name is already taken";
        }
        else if(nameField.getText().equals("")){
            textBanner = "Name can't be blank!";
        }
        else if(!isNumeric(ratingTextArea.getText())){
            textBanner = "Rating is not a number";
        }
        else if((Integer.parseInt(ratingTextArea.getText()))<=500 || (Integer.parseInt(ratingTextArea.getText()))>3000) {
            textBanner = "Rating out of bounds";
        }
        else if(!isNumeric(winsTextArea.getText())){
            textBanner = "Wins is not a number ";
        }
        else if(!isNumeric(lossesTextArea.getText())){
            textBanner = "Losses is not a number";        }
        else if(!isNumeric(drawTextArea.getText())){
            textBanner = "Draws is not a number  ";        }
        else {
            if (checkBox.isSelected()) {

                Player newPlayer = new Player(nameField.getText());
                League.getModel().addPlayertoLeague(newPlayer);

                textBanner = nameField.getText() + " added!";
                nameField.setText("");
                winsTextArea.setText("0");
                lossesTextArea.setText("0");
                drawTextArea.setText("0");

                Integer id = google.CreatePlayerSheetandWritetoIt(newPlayer.getName());
                //create shhet
                newPlayer.setPlayerSheetId(id);

                //request all the merge cells request
                google.formatPlayerSheet(id);
                //finally write all the neccesary stuff

                System.out.println(newPlayer.getPlayerSheetId());

                ArrayList<String> playerstringData  = newPlayer.printData();
                google.WriteDatatoSheet(id,playerstringData);



                //addnewPlayer to Sheet
                //newPlayer.setSheetid(Response.getsheetid);

            } else {

                Player temp = (new Player(nameField.getText(), (Integer.parseInt(ratingTextArea.getText())),
                        Integer.parseInt(winsTextArea.getText()), Integer.parseInt(lossesTextArea.getText()), Integer.parseInt(drawTextArea.getText())));
                League.getModel().addPlayertoLeague(temp);


                Integer id = google.CreatePlayerSheetandWritetoIt(temp.getName());
                google.formatPlayerSheet(id);


                ArrayList<String> playerstringData  = temp.printData();

                google.WriteDatatoSheet(id,playerstringData);

                System.out.println("hi");
                textBanner = nameField.getText() + " added!";
                ResetTextFields();
            }
        }
        //Pop open a text message saing "X player has been added"
        // Clear the Data fields
       // textGreen.setVisible(true);
        textGreen.setText(textBanner);
        League.getModel().printPlayers();

        MainviewController.getModel().RefreshList();
        //error checking
        //If name is already regigstered, pop open a dialogue saying name is taken

    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    public void ResetTextFields(){
        nameField.setText("");
        ratingTextArea.setText("0");
        winsTextArea.setText("0");
        lossesTextArea.setText("0");
        drawTextArea.setText("0");

    }


    }

