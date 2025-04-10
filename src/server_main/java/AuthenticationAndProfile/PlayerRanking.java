package AuthenticationAndProfile;

import player.PlayerManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class PlayerRanking {
    private int id;
    private double[] winLossRatio = new double[3];
    private int[] rating = new int[3];
    private String[] rank = new String[3];
    private int[] wins = new int[3];
    private int[] losses = new int[3];
    private int[] total = new int[3];

    public static final int TTT_INDEX = 0;
    public static final int CONNECT4_INDEX = 1;
    public static final int CHECKERS_INDEX = 2;

    public PlayerRanking(int id, double[] winLossRatio, int[] rating, String[] rank, int[] wins, int[] losses, int[] total) {
        this.id = id;
        this.winLossRatio = winLossRatio;
        this.rating = rating;
        this.rank = rank;
        this.wins = wins;
        this.losses = losses;
        this.total = total;
    }

    public PlayerRanking() {
    }

    public void endOfMatchMethod(int playerID, int gameNumber, int result) throws SQLException {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            int currentRating = Integer.parseInt(PlayerManager.getAttribute(playerID, "rating_" + gameName));
            int currentWins = Integer.parseInt(PlayerManager.getAttribute(playerID, "wins_" + gameName));
            int currentLosses = Integer.parseInt(PlayerManager.getAttribute(playerID, "losses_" + gameName));
            double currentWinLossRatio = Double.parseDouble(PlayerManager.getAttribute(playerID, "win_loss_ratio_" + gameName));
            int currentGamesPlayed = Integer.parseInt(PlayerManager.getAttribute(playerID, "total_" + gameName));

            if (result == 1) {
                currentRating += 50;
                currentWins += 1;
                currentGamesPlayed += 1;

                currentWinLossRatio = Math.round(((double) currentWins / currentGamesPlayed) * 100.0 / 100.0);
            } else if (result == 0) {
                currentRating -= 50;
                if (currentRating < 0) {
                    currentRating = 0;
                }
                currentLosses += 1;
                currentGamesPlayed += 1;
                currentWinLossRatio = Math.round(((double) currentWins / currentGamesPlayed) * 100.0 / 100.0);
            }

            String newRank = PlayerRanking.getRank(currentRating);

            PlayerManager.updateAttribute(playerID, "rating_" + gameName, currentRating);
            PlayerManager.updateAttribute(playerID, "losses_" + gameName, currentLosses);
            PlayerManager.updateAttribute(playerID, "total_" + gameName, currentGamesPlayed);
            PlayerManager.updateAttribute(playerID, "wins_" + gameName, currentWinLossRatio);
            PlayerManager.updateAttribute(playerID, "win_loss_ratio_" + gameName, currentWinLossRatio);
            PlayerManager.updateAttribute(playerID, "rank_" + gameName, newRank);

            Profile profile = ProfileDatabaseAccess.obtainProfile(id);

            HashMap<String, Double> achievementProgress = profile.getGameHistory().getAchievementProgress();

            if (achievementProgress != null) {
                String winAchievement = "10 Wins " + gameName;
                String gamesPlayedAchievement = "50 Games Played " + gameName;

                if (achievementProgress.containsKey(winAchievement)) {
                    double progress = Math.min(currentWins / 10.0, 1.0);
                    achievementProgress.put(winAchievement, progress);
                }
                if (achievementProgress.containsKey(gamesPlayedAchievement)) {
                    double progress = Math.min(currentGamesPlayed / 50.0, 1.0);
                    achievementProgress.put(gamesPlayedAchievement, progress);
                }
                profile.getGameHistory().setAchievementProgress(achievementProgress);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getWinLossRatio(int gameNumber) {
        return winLossRatio[gameNumber];
    }

    public static int getRating(int playerID, int gameNumber) throws SQLException {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            return Integer.parseInt(PlayerManager.getAttribute(playerID, "rating_" + gameName));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    public static String getRank(int rating) {
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


    public int getWins(int gameNumber) throws SQLException {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            return Integer.parseInt(PlayerManager.getAttribute(id, "wins_" + gameName));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    //    public void setWinLossRatio(int gameNumber, double ratio) throws SQLException {
//        try {
//            String gameName = "";
//            if (gameNumber == TTT_INDEX) {
//                gameName = "ttt";
//            } else if (gameNumber == CONNECT4_INDEX) {
//                gameName = "connect4";
//            } else if (gameNumber == CHECKERS_INDEX) {
//                gameName = "checkers";
//            }
//            String attribute = String.format("win_loss_ratio_%s", gameName);
//            PlayerManager.updateAttribute(id, attribute, ratio);
//            winLossRatio[gameNumber] = ratio;
//        } catch (SQLException s) {
//            throw new SQLException(s.getMessage());
//        }
//    }

    public static void setGameRating(int playerID, int gameNumber, int rating) throws SQLException {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            PlayerManager.updateAttribute(playerID, "rating_" + gameName, rating);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

//    public void setRank(String[] rank) {
//        this.rank = rank;
//    }
//
//    public void setWins(int[] wins) {
//        this.wins = wins;
//    }
//
//    public void setLosses(int[] losses) {
//        this.losses = losses;
//    }
//
//    public void setTotal(int[] total) {
//        this.total = total;
//    }

    public int getTotal(int gameNumber) throws SQLException{
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            return Integer.parseInt(PlayerManager.getAttribute(id, "total_" + gameName));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }
}
