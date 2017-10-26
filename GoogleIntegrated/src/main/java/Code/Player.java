package Code;

import java.util.ArrayList;

/**
 * Created by tariq on 9/3/2017.
 */
public class Player {

    ArrayList<Pencils> pencils;
    public static final int defaultRating = 1000;
    private String name;
    private int rating;
    private int wins;
    private int losses;
    private int draws;
    private int streak;
    private int ties;


    private ArrayList<String> printData;
    private ArrayList<ELOMatch> playerHistory;
    private Integer playerSheetId;

    boolean isStreak = false;

    //ELO FIELDS
    private int place = 0;
    private int preELO = rating;
    private int postELO = 0;
    private int eloChange = 0;


    public ArrayList<Pencils> getPencils() {
        return pencils;
    }

    public void setPencils(ArrayList<Pencils> pencils) {
        this.pencils = pencils;
    }

    public Player(String nameParam, int ratingParam, int winsParam, int lossParam, int drawParam) {
        name = nameParam;
        rating = ratingParam;
        wins = winsParam;
        losses = lossParam;
        draws = drawParam;
        preELO = ratingParam;

        pencils = new ArrayList<>();
        pencils.add(new Pencils("blue", 18, "Pentel"));
        pencils.add(new Pencils("orange", 2, "Staples"));

    }

    //if player is new
    public Player(String nameParam) {
        name = nameParam;
        rating = defaultRating;
        preELO = defaultRating;
    }


    public Player() {
        this(null);
    }

    public ArrayList<ELOMatch> getPlayerHistory() {
        return playerHistory;
    }

    public void setPlayerHistory(ArrayList<ELOMatch> playerHistory) {
        this.playerHistory = playerHistory;
    }

    public void addMatch(ELOMatch match){
        playerHistory.add(match);

    }

    public String toString() {
        return this.name;
    }

    // GETTER/SETTER METHODS
    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public ArrayList<String> getPrintData() {
        return printData;
    }

    public void setPrintData(ArrayList<String> printData) {
        this.printData = printData;
    }

    public boolean isStreak() {
        return isStreak;
    }

    public void setStreak(boolean streak) {
        isStreak = streak;
    }

    public static int getDefaultRating() {
        return defaultRating;
    }

    public int getTotalGamesPlayedByPlayer() {
        return (losses + wins + draws);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getEloChange() {
        return eloChange;
    }

    public int getPreELO() {
        return preELO;
    }

    public void setPostELO(int postELO) {
        rating = postELO;
    }

    public int getPostELO() {
        return postELO;
    }

    public void setEloChange(int eloChanges) {
        eloChange = eloChange + eloChanges;
        // System.out.println("ELO CHANGES" + this.eloChange);
    }

    public void incrementWins() {
        wins++;
        if (streak < 0) streak = 0;
        streak++;
    }

    public void DecrementLoss() {
        losses++;
        if (streak > 0) streak = 0;
        --streak;
        System.out.println("STREAK" + streak);
        System.out.println("Do i get called??????");

    }

    public int getStreak(){
        return streak;
    }


    public void setPreELO(int preELO) {
        this.preELO = preELO;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

    public void resetFields() {
        eloChange = 0;
        setPreELO(getRating());
    }

    public int getTies() {
        return ties;
    }

    public void incrementTies(){
        ties++;
    }

    public Integer getPlayerSheetId() {
        return playerSheetId;
    }

    public void setPlayerSheetId(Integer playerSheetId) {
        this.playerSheetId = playerSheetId;
    }


    public ArrayList<String> printData() {
        printData = new ArrayList<>();

        printData.add(getName());
        printData.add("Current Rating: " + getRating());
        printData.add("Wins: " + getWins());
        printData.add("LOsses: " + getLosses());
        printData.add("Ties" + getTies());


        return  printData;
    }
    }


