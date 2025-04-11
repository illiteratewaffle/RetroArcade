package AuthenticationAndProfile;

import java.sql.SQLException;

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

    public void endOfMatchMethod(int gameNumber, int result) throws SQLException {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            int currentRating = Integer.parseInt(PlayerManager.getAttribute(id, "rating_" + gameName));
            int currentWins = Integer.parseInt(PlayerManager.getAttribute(id, "wins_" + gameName));
            int currentLosses = Integer.parseInt(PlayerManager.getAttribute(id, "losses_" + gameName));
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
                if (currentRating < 0) {
                    currentRating = 0;
                }
                currentLosses += 1;
                currentGamesPlayed += 1;
                currentWinLossRatio = Math.round(((double) currentWins / currentGamesPlayed) * 100.0 / 100.0);
            }

            String newRank = getRank(currentRating);

            if (gameNumber == 0 && result == 0) {
                PlayerManager.updateAttribute(id, "rating_ttt", currentRating);
                PlayerManager.updateAttribute(id, "losses_ttt", currentLosses);
                PlayerManager.updateAttribute(id, "win_loss_ratio_ttt", currentWinLossRatio);
                PlayerManager.updateAttribute(id, "rank_ttt", newRank);
            } else if (gameNumber == 1 && result == 0) {
                PlayerManager.updateAttribute(id, "rating_connect4", currentRating);
                PlayerManager.updateAttribute(id, "losses_connect4", currentLosses);
                PlayerManager.updateAttribute(id, "win_loss_ratio_connect4", currentWinLossRatio);
                PlayerManager.updateAttribute(id, "rank_connect4", newRank);
            } else if (gameNumber == 2 && result == 0) {
                PlayerManager.updateAttribute(id, "rating_checkers", currentRating);
                PlayerManager.updateAttribute(id, "losses_checkers", currentLosses);
                PlayerManager.updateAttribute(id, "win_loss_ratio_checkers", currentWinLossRatio);
                PlayerManager.updateAttribute(id, "rank_checkers", newRank);
            } else if (gameNumber == 0 && result == 1) {
                PlayerManager.updateAttribute(id, "rating_ttt", currentRating);
                PlayerManager.updateAttribute(id, "wins_ttt", currentWins);
                PlayerManager.updateAttribute(id, "win_loss_ratio_ttt", currentWinLossRatio);
                PlayerManager.updateAttribute(id, "rank_ttt", newRank);
            } else if (gameNumber == 1 && result == 1) {
                PlayerManager.updateAttribute(id, "rating_connect4", currentRating);
                PlayerManager.updateAttribute(id, "wins_connect4", currentWins);
                PlayerManager.updateAttribute(id, "win_loss_ratio_connect4", currentWinLossRatio);
                PlayerManager.updateAttribute(id, "rank_connect4", newRank);
            } else if (gameNumber == 2 && result == 1) {
                PlayerManager.updateAttribute(id, "rating_checkers", currentRating);
                PlayerManager.updateAttribute(id, "wins_checkers", currentWins);
                PlayerManager.updateAttribute(id, "win_loss_ratio_checkers", currentWinLossRatio);
                PlayerManager.updateAttribute(id, "rank_checkers", newRank);
            }
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    public double getWinLossRatio(int gameNumber) {
        return winLossRatio[gameNumber];
    }

    public int getRating(int gameNumber) throws SQLException {
        String gameName = null;
        if (gameNumber == TTT_INDEX) {
            gameName = "ttt";
        } else if (gameNumber == CONNECT4_INDEX) {
            gameName = "connect4";
        } else if (gameNumber == CHECKERS_INDEX) {
            gameName = "checkers";
        }
        try {
            return Integer.parseInt(PlayerManager.getAttribute(id, "rating_" + gameName));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
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
            return Integer.parseInt(PlayerManager.getAttribute(id, "wins" + gameName));
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
//
//    public void setRating(int[] rating) {
//        this.rating = rating;
//    }
//
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
}
