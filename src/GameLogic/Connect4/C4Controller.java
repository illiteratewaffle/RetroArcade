package gamelogic.Connect4;

public class C4Controller {
    private C4GameLogic c4GameLogic;
    void start() {
        c4GameLogic = new C4GameLogic();
        System.out.println("A new game of connect four has started");
    }
    void handleInput() {
    }
}