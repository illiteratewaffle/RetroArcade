package server.GameLogic_Client.Checkers;

public enum CheckersHighlightType
{
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
    private CheckersHighlightType(int value)
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