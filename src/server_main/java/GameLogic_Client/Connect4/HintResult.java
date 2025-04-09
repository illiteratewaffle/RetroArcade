package GameLogic_Client.Connect4;

public class HintResult {
    public int col;
    public String type; // "WIN", "BLOCK", or "NONE"

    public HintResult(int col, String type) {
        this.col = col;
        this.type = type;
    }
}
