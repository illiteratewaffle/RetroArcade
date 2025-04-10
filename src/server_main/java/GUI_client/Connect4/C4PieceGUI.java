package GUI_client.Connect4;
import javafx.scene.paint.Color;

public enum C4PieceGUI {
    RED(Color.RED), BLUE(Color.BLUE), BLANK(Color.TRANSPARENT);

    private final Color color;

    C4PieceGUI(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
