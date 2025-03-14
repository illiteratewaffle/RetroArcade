package gamelogic.Connect4;

public class C4Controller {
    private C4GameLogic c4GameLogic;
    void start() {
        System.out.println("A new game of connect four has started");
        c4GameLogic = new C4GameLogic();
        printBoard();
    }
    void handleInput() {
    }
    void printBoard() {
        System.out.println(c4GameLogic);
    }
}