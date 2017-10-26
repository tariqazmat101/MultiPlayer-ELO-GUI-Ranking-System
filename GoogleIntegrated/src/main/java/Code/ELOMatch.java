package Code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by tariq on 9/24/2017.
 */
class ELOMatch
{
    private ArrayList<Player> players = new ArrayList<Player>();
    private int Matchtype;


    //CONSTRUCTOR
    public ELOMatch(int match){
       Matchtype = match;
    }

    public void addPlayer(Player t){
        players.add(t);
    }


    @Override
    public String toString() {
        String text = "";
        for (int i = 0 ; i < players.size() -1  ; i++) {
            text +=  players.get(i).toString() + " vs. ";
        }

        text += players.get(players.size() - 1);
        if(Matchtype == 0){
            text += " TYPE:Bullet";
        } else if (Matchtype == 1) {
            text += " TYPE:Blitz";
        }
        else {
            text += " TYPE: Classical";
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String date = (dtf.format(localDate)); //2016/11/16
        return text + " " + date;
     }

    public int getELOChange(String name)
    {
        for (Player p : players)
        {
            if (p.getName() == name)
                return p.getEloChange();
        }
        return 0;
    }
    public void printELOplayer(){
        for (Player t : players) {
            System.out.println(t);
        }
    }

    public int getELO(String name)
    {
        for (Player p : players)
        {
            if (p.getName() == name)
                return p.getPostELO();
        }
        return 1500;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //Calculations

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getMatchtype() {
        return Matchtype;
    }

    public void setMatchtype(int matchtype) {
        Matchtype = matchtype;
    }

    public void calculateELOs()
    {
        int n = players.size();
        //
         float K = 32 / (float)(n - 1);
           //float K = 32;

        for (int i = 0; i < n; i++)
        {
            Player currPlayer = players.get(i);
            int curPlace = players.get(i).getPlace();
            int curELO   = players.get(i).getPreELO();


            for (int j = n - 1; j >= 0; j--)
            {
                if (i != j)
                {
                    int opponentPlace = players.get(j).getPlace();
                    int opponentELO   = players.get(j).getPreELO();

                    Player Opponent = players.get(j);
                    //calculate placement
                    if(curPlace > opponentPlace){
                        currPlayer.DecrementLoss();

                    }
                    else if( curPlace < opponentPlace) {
                        currPlayer.incrementWins();
                    }
                    else currPlayer.incrementTies();

                    //work out S
                    float S;
                    if (curPlace < opponentPlace)
                        S = 1.0F;
                    else if (curPlace == opponentPlace)
                        S = 0.5F;
                    else
                        S = 0.0F;

                    //work out EA
                    float EA = 1 / (1.0f + (float)Math.pow(10.0f, (opponentELO - curELO) / 400.0f));

                    //calculate ELO change vs this one opponent, add it to our change bucket
                    //I currently round at this point, this keeps rounding changes symetrical between EA and EB, but changes K more than it should
                    System.out.println("ELOEACHE" + (K * (S - EA)));
                    players.get(i).setEloChange(Math.round((K * (S - EA))));

                    System.out.println(  players.get(i).getName() + "Rating" + players.get(i).getEloChange());

                }
            }


            System.out.println("Carpet ELO" + players.get(i).getRating());
            int total = (players.get(i).getPreELO() + players.get(i).getEloChange());
            System.out.println("TOTAL" + total);

            players.get(i).setPostELO(total);
            players.get(i).resetFields();
        }
    }
}