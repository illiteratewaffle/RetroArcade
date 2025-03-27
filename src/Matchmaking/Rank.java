package Matchmaking;

public class Rank {
    int rankNumber;
    String rankName;
    int ratingLowerBound;
    int ratingUpperBound;

    public Rank(int rankNumber, String rankName, int lower, int upper) {
        this.rankNumber = rankNumber;
        this.rankName = rankName;
        this.ratingLowerBound = lower;
        this.ratingUpperBound = upper;
    }

    public void updateRank(Player player, int rating) {
        player.ranking.rating = rating;
    }
}
