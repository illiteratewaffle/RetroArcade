package leaderboard;

public class PlayerRanking {
    private double[] winLossRatio = new double[3];
    private int[] rating = new int[3];
    private String[] rank = new String[3];
    private int[] wins = new int[3];

    public static final int TTT_INDEX = 0;
    public static final int CONNECT4_INDEX = 1;
    public static final int CHECKERS_INDEX = 2;

    public PlayerRanking(double[] winLossRatio, int[] rating, String[] rank, int[] wins) {
        this.winLossRatio = winLossRatio;
        this.rating = rating;
        this.rank = rank;
        this.wins = wins;
    }

    public PlayerRanking(){}

    public double getWinLossRatio(int gameNumber) {
        return winLossRatio[gameNumber];
    }

    public void setWinLossRatio(double winLossRatio, int gameNumber) {
        this.winLossRatio[gameNumber] = winLossRatio;
    }

    public int getRating(int gameNumber) {
        return rating[gameNumber];
    }

    public void setRating(int rating, int gameNumber) {
        this.rating[gameNumber] = rating;
    }

    public String getRank(int gameNumber) {
        return rank[gameNumber];
    }

    public void setRank(String rank, int gameNumber) {
        this.rank[gameNumber] = rank;
    }

    public int getWins(int gameNumber) {
        return wins[gameNumber];
    }

    public void setWins(int wins, int gameNumber) {
        this.wins[gameNumber] = wins;
    }
}

//Abstract version test:
//public abstract class PlayerRanking {
//    protected double winLossRatio;
//    protected int rating;
//    protected String rank;
//    protected int wins;
//
//    public PlayerRanking(double winLossRatio, int rating, String rank, int wins) {
//        this.winLossRatio = winLossRatio;
//        this.rating = rating;
//        this.rank = rank;
//        this.wins = wins;
//    }
//
//    public double getWinLossRatio() {
//        return winLossRatio;
//    }
//
//    public void setWinLossRatio(double winLossRatio) {
//        this.winLossRatio = winLossRatio;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public String getRank() {
//        return rank;
//    }
//
//    public void setRank(String rank) {
//        this.rank = rank;
//    }
//
//    public int getWins() {
//        return wins;
//    }
//
//    public void setWins(int wins) {
//        this.wins = wins;
//    }
//}
