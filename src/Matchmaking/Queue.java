package Matchmaking;

public class Queue {
    private List<Player> players;

    public QueueSystem() {
        this.players = new ArrayList<>();
    }

    public void addToQueue(Player player) {
        players.add(player);
    }

    public void removeFromQueue(Player player) {
        players.remove(player);
    }

    public List<Player> sortQueue() {
        players.sort(Comparator.comparingInt(p -> p.ranking.rating));
        return players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
