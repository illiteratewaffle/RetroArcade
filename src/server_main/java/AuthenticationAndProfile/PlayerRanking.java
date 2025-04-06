package AuthenticationAndProfile;

import player.PlayerManager;

public class PlayerRanking {
    private final int id;
    private double[] winLossRatio = new double[3];
    private int[] rating = new int[3];
    private String[] rank = new String[3];
    private int[] wins = new int[3];

    public static final int TTT_INDEX = 0;
    public static final int CONNECT4_INDEX = 1;
    public static final int CHECKERS_INDEX = 2;

    public PlayerRanking(int id, double[] winLossRatio, int[] rating, String[] rank, int[] wins) {
        this.id = id;
        this.winLossRatio = winLossRatio;
        this.rating = rating;
        this.rank = rank;
        this.wins = wins;
    }

    public void endOfMatchMethod(int gameNumber, int result) {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        int currentRating = Integer.parseInt(PlayerManager.getAttribute(id, "rating_" + gameName));
        int currentWins = Integer.parseInt(PlayerManager.getAttribute(id, "wins_" + gameName));
        double currentWinLossRatio = Double.parseDouble(PlayerManager.getAttribute(id, "win_loss_ratio_" + gameName));
        int currentGamesPlayed = Integer.parseInt(PlayerManager.getAttribute(id, "total_" + gameName));

        if (result == 1) {
            currentRating += 50;
            currentWins += 1;
            currentGamesPlayed += 1;

            // Rounding method looked up from StackOverflow:
            // https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
            currentWinLossRatio = Math.round(((double) currentWins / currentGamesPlayed) * 100.0 / 100.0);
        } else if (result == 0) {
            currentRating -= 50;
            currentGamesPlayed += 1;
            currentWinLossRatio = Math.round(((double) currentWins / currentGamesPlayed) * 100.0 / 100.0);
        }

        String newRank = getRank(currentRating);

        if (gameNumber == 0) {
            PlayerManager.updateAttribute(id, "rating_ttt", currentRating);
            PlayerManager.updateAttribute(id, "wins_ttt", currentWins);
            PlayerManager.updateAttribute(id, "win_loss_ratio_ttt", currentWinLossRatio);
            PlayerManager.updateAttribute(id, "rank_ttt", newRank);
        } else if (gameNumber == 1) {
            PlayerManager.updateAttribute(id, "rating_connect4", currentRating);
            PlayerManager.updateAttribute(id, "wins_connect4", currentWins);
            PlayerManager.updateAttribute(id, "win_loss_ratio_connect4", currentWinLossRatio);
            PlayerManager.updateAttribute(id, "rank_connect4", newRank);
        } else if (gameNumber == 2) {
            PlayerManager.updateAttribute(id, "rating_checkers", currentRating);
            PlayerManager.updateAttribute(id, "wins_checkers", currentWins);
            PlayerManager.updateAttribute(id, "win_loss_ratio_checkers", currentWinLossRatio);
            PlayerManager.updateAttribute(id, "rank_checkers", newRank);
        }
    }
    public double getWinLossRatio(int gameNumber) {
        return winLossRatio[gameNumber];
    }

    public void setWinLossRatio(double winLossRatio, int gameNumber) {
        this.winLossRatio[gameNumber] = winLossRatio;
        if (gameNumber == 0) {
            PlayerManager.updateAttribute(id, "win_loss_ratio_ttt", winLossRatio);
        } else if (gameNumber == 1) {
            PlayerManager.updateAttribute(id, "win_loss_ratio_connect4", winLossRatio);
        } else if (gameNumber == 2) {
            PlayerManager.updateAttribute(id, "win_loss_ratio_checkers", winLossRatio);
        }
    }

    public int getRating(int gameNumber) {
        return rating[gameNumber];
    }

    public void setRating(int rating, int gameNumber) {
        this.rating[gameNumber] = rating;
        if (gameNumber == 0) {
            PlayerManager.updateAttribute(id, "rating_ttt", rating);
        } else if (gameNumber == 1) {
            PlayerManager.updateAttribute(id, "rating_connect4", rating);
        } else if (gameNumber == 2) {
            PlayerManager.updateAttribute(id, "rating_checkers", rating);
        }
    }


    public String getRank(int rating) {
        if (rating < 500) {
            return "Bronze";
        } else if (rating < 1000) {
            return "Silver";
        } else if (rating < 1500) {
            return "Gold";
        } else if (rating < 2000) {
            return "Platinum";
        } else {
            return "Diamond";
        }
    }


    public void setRank(String rank, int gameNumber) {
        this.rank[gameNumber] = rank;
        if (gameNumber == 0) {
            PlayerManager.updateAttribute(id, "rank_ttt", rank);
        } else if (gameNumber == 1) {
            PlayerManager.updateAttribute(id, "rank_connect4", rank);
        } else if (gameNumber == 2) {
            PlayerManager.updateAttribute(id, "rank_checkers", rank);
        }
    }

    public int getWins(int id, int gameNumber) {
        return wins[gameNumber];
    }



    public void setWins(int wins, int gameNumber) {
        this.wins[gameNumber] = wins;
        if (gameNumber == 0) {
            PlayerManager.updateAttribute(id, "wins_ttt", wins);
        } else if (gameNumber == 1) {
            PlayerManager.updateAttribute(id, "wins_connect4", wins);
        } else if (gameNumber == 2) {
            PlayerManager.updateAttribute(id, "wins_checkers", wins);
        }
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
