package Code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by tariq on 9/3/2017.
 */
public class League {
    //Singleton class
    private static League model = new League();

    public static League getModel(){
        return model;
    }
    private ArrayList<Player> players;
    public  ObservableList<ELOMatch> matches = FXCollections.observableArrayList();


    // Score constants
    public final static double WIN = 1.0;
    public final static double DRAW = 0.5;
    public final static double LOSS = 0.0;

    public League(){
        players = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public ObservableList<ELOMatch> getLeagueMatches(){
        return matches;
    }

    public void addPlayertoLeague(Player p1){
        players.add(p1);
    }

    public void printPlayers(){
        for(Player temp: players){
            System.out.println(temp.toString());
        }
    }

    public Player returnPlayer(String name){
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    public boolean isPlayerExists(String name ){
        for (Player p : players) {
            if(p.getName().equals(name))
                return true;
        }
        return false;
    }

}
