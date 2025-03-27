package leaderboard;

public class PlayerRanking {
    private double winLossRatio;
    private int rating;
    private int rank;
    private int wins;

    public PlayerRanking(double winLossRatio, int rating, int rank, int wins) {
        this.winLossRatio = winLossRatio;
        this.rating = rating;
        this.rank = rank;
        this.wins = wins;
    }

    public double getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(double winLossRatio) {
        this.winLossRatio = winLossRatio;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
