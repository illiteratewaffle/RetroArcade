package leaderboard;
import AuthenticationAndProfile.Profile;

public class UpdatePlayerStats {


    /**
     * Applies a strike to the player for leaving a match early.
     * Resets the number of games the player must complete to reduce a strike.
     *
     * @param player The player who left the game early.
     */
    public void applyStrike(Profile player) {
        int currentStrikes = player.getStrikes();
        player.setStrikes(currentStrikes + 1);
        player.setGamesToReduceStrikes(5); // reset to a certain number of games to clear next strike
    }

    /**
     * Call this method after a player completes a game.
     * Decrements the player's games-to-reduce-strikes counter.
     * If applicable, updates rating and rank.
     *
     * @param player   The player who completed the game.
     * @param gameType The game type (e.g., "TTT", "Connect4", "Checkers").
     */
    public void recordGameCompletion(Profile player, String gameType) {
        int gamesLeft = player.getGamesToReduceStrikes();
        if (gamesLeft > 0) {
            player.setGamesToReduceStrikes(gamesLeft - 1);
        }
        updateStats(player, gameType);
    }

    /**
     * Updates a player's ranking score based on the game they completed,
     * their current strike level, and the game type.
     * Rewards vary by game:
     * Checkers: 50 points
     * Connect4: 35 points
     * Tic Tac Toe: 15 points
     * @param player   The player to update.
     * @param gameType The game type ("TTT", "Connect4", or "Checkers").
     */
    private void updateStats(Profile player, String gameType) {
        // Check if player has completed enough games to reduce a strike
        int rewardPoint;
        int gameIndex;

        if (gameType.equals("TTT")){
            gameIndex = PlayerRanking.TTT_INDEX;
        } else if (gameType.equals("Connect4"))  {
            gameIndex = PlayerRanking.CONNECT4_INDEX;
        } else{
            gameIndex = PlayerRanking.CHECKERS_INDEX;
        }

        if (gameIndex == 2){
            rewardPoint = 50;
        } else if (gameIndex == 1) {
            rewardPoint = 35;
        } else{
            rewardPoint = 15;
        }

        int currRanking = Integer.parseInt(player.getPlayerRanking().getRank(gameIndex));
        if (player.getGamesToReduceStrikes <= 5) {
            PlayerRanking.setRanking(player, currRanking + rewardPoint);
        } else if (player.getGamesToReduceStrikes <= 10) {
            PlayerRanking.setRanking(player, currRanking + (rewardPoint - 10));
        } else if (player.getGamesToReduceStrikes <= 15) {
            PlayerRanking.setRanking(player, currRanking + (rewardPoint - 15));
        } else {
            PlayerRanking.setRanking(player, currRanking);
        }

    }

}
