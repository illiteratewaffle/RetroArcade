package GameLogic_Client.Checkers;

/**
 * enum for the type of highlight with 1 being piece, 2 being a selected piece, 3 being a capture, and 4 being a tile
 */
public enum CheckersHighlightType
{
    // enum values
    PIECE(1),
    SELECTED_PIECE(2),
    CAPTURE(3),
    TILE(4);


    // The internal value of this enum.
    private final int value;


    /**
     * Generate a CheckersHighlightType enum with the given value.
     * @param value The value for the generated enum. No 2 enums should have the same value.
     */
    CheckersHighlightType(int value)
    {
        this.value = value;
    }


    /**
     * @return The integer value of this enum.
     */
    public int getValue()
    {
        return value;
    }
}