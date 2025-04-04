package GameLogic_Client;


/**
 * A storage class used for representing the coordinates of tiles on the board.<br>
 * Provides some helper methods for basic mathematical operations for offsetting the coordinate.<br>
 * Notationally, the +X-axis moves from left to right, while the +Y-axis moves from down to up.
 */
final public class ivec2
{
    final public Integer x, y;
    public ivec2(int X, int Y)
    {
        x = X;
        y = Y;
    }


    /**
     * @return The Hash Code of this ivec2 object for hash-related operations.
     */
    @Override
    public int hashCode()
    {
        return x.hashCode() * y.hashCode();
    }


    /**
     * Compare 2 ivec2 instances together.
     * @param obj The other ivec2 object to compare with this one.
     * @return True if the coordinates of both ivec2 objects are the same; False otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ivec2 other)
        {
            return x.equals(other.x) && y.equals(other.y);
        }
        return false;
    }

    /**
     * @param steps
     * @return A copy of this vector with its y-coordinate shifted up by a given number of steps.
     */
    public ivec2 MoveUp(int steps)
    {
        return new ivec2(x, y + steps);
    }

    /**
     * @param steps
     * @return A copy of this vector with its y-coordinate shifted down by a given number of steps.
     */
    public ivec2 MoveDown(int steps)
    {
        return new ivec2(x, y - steps);
    }

    /**
     * @param steps
     * @return A copy of this vector with its x-coordinate shifted left by a given number of steps.
     */
    public ivec2 MoveLeft(int steps)
    {
        return new ivec2(x - steps, y);
    }

    /**
     * @param steps
     * @return A copy of this vector with its x-coordinate shifted right by a given number of steps.
     */
    public ivec2 MoveRight(int steps)
    {
        return new ivec2(x + steps, y);
    }

    /**
     * @param other
     * @return A copy of this vector offsetted by the other vector.
     */
    public ivec2 Add(final ivec2 other)
    {
        return new ivec2(x + other.x, y + other.y);
    }

    /**
     * @param offset
     * @return A copy of this vector with both of its component added to by the offset amount.
     */
    public ivec2 Add(int offset)
    {
        return new ivec2(x + offset, y + offset);
    }

    /**
     * @param other
     * @return A copy of this vector offsetted by the negation of the other vector.
     */
    public ivec2 Subtract(final ivec2 other)
    {
        return new ivec2(x - other.x, y - other.y);
    }

    /**
     * @param offset
     * @return A copy of this vector with both of its component subtracted from by the offset amount.
     */
    public ivec2 Subtract(int offset)
    {
        return new ivec2(x - offset, y - offset);
    }

    /**
     * @param other
     * @return A copy of this vector multiplied component-wise with the other vector.
     */
    public ivec2 Multiply(final ivec2 other)
    {
        return new ivec2(x * other.x, y * other.y);
    }

    /**
     * @param factor
     * @return A copy of this vector scaled by the given factor.
     */
    public ivec2 Multiply(int factor)
    {
        return new ivec2(x * factor, y * factor);
    }

    /**
     * @param other
     * @return A copy of this vector divided component-wise with the other vector.<br>
     * Note that this uses integer division, and therefore rounds the results towards 0.
     */
    public ivec2 Divide(final ivec2 other)
    {
        return new ivec2(x / other.x, y / other.y);
    }

    /**
     * @param denominator
     * @return A copy of this vector scaled by 1/denominator.<br>
     * Note that this uses integer division, and therefore rounds the results towards 0.
     */
    public ivec2 Divide(int denominator)
    {
        return new ivec2(x / denominator, y / denominator);
    }
}