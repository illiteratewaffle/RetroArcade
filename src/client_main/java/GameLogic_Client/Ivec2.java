package client_main.java.GameLogic_Client;


/**
 * A storage class used for representing the coordinates of tiles on the board.<br>
 * Provides some helper methods for basic mathematical operations for offsetting the coordinate.<br>
 * Notationally, the +X-axis moves from left to right, while the +Y-axis moves from down to up.
 */
final public class Ivec2
{
    // Store the components of this vector's coordinates.
    final public Integer x, y;


    public Ivec2(int x, int y)
    {
        this.x = x;
        this.y = y;
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
     * Compare 2 instances of <code>Ivec2</code> together.
     * @param obj The other <code>Ivec2</code> object to compare with this one.
     * @return
     * <code>True</code> if the coordinates of both <code>Ivec2</code> objects are the same;
     * <code>False</code> otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        // Only compare this with other Ivec2 instances.
        if (obj instanceof Ivec2 other)
        {
            return x.equals(other.x) && y.equals(other.y);
        }
        return false;
    }

    /**
     * @param steps
     * @return A copy of this vector with its y-coordinate shifted up by a given number of steps.
     */
    public Ivec2 moveUp(int steps)
    {
        return new Ivec2(x, y + steps);
    }

    /**
     * @param steps
     * @return A copy of this vector with its y-coordinate shifted down by a given number of steps.
     */
    public Ivec2 moveDown(int steps)
    {
        return new Ivec2(x, y - steps);
    }

    /**
     * @param steps
     * @return A copy of this vector with its x-coordinate shifted left by a given number of steps.
     */
    public Ivec2 moveLeft(int steps)
    {
        return new Ivec2(x - steps, y);
    }

    /**
     * @param steps
     * @return A copy of this vector with its x-coordinate shifted right by a given number of steps.
     */
    public Ivec2 moveRight(int steps)
    {
        return new Ivec2(x + steps, y);
    }

    /**
     * @param other
     * @return A copy of this vector offsetted by the other vector.
     */
    public Ivec2 add(final Ivec2 other)
    {
        return new Ivec2(x + other.x, y + other.y);
    }

    /**
     * @param offset
     * @return A copy of this vector with both of its component added to by the offset amount.
     */
    public Ivec2 add(int offset)
    {
        return new Ivec2(x + offset, y + offset);
    }

    /**
     * @param other
     * @return A copy of this vector offsetted by the negation of the other vector.
     */
    public Ivec2 subtract(final Ivec2 other)
    {
        return new Ivec2(x - other.x, y - other.y);
    }

    /**
     * @param offset
     * @return A copy of this vector with both of its component subtracted from by the offset amount.
     */
    public Ivec2 subtract(int offset)
    {
        return new Ivec2(x - offset, y - offset);
    }

    /**
     * @param other
     * @return A copy of this vector multiplied component-wise with the other vector.
     */
    public Ivec2 multiply(final Ivec2 other)
    {
        return new Ivec2(x * other.x, y * other.y);
    }

    /**
     * @param factor
     * @return A copy of this vector scaled by the given factor.
     */
    public Ivec2 multiply(int factor)
    {
        return new Ivec2(x * factor, y * factor);
    }

    /**
     * @param other
     * @return A copy of this vector divided component-wise with the other vector.<br>
     * Note that this uses integer division, and therefore rounds the results towards 0.
     */
    public Ivec2 divide(final Ivec2 other)
    {
        return new Ivec2(x / other.x, y / other.y);
    }

    /**
     * @param denominator
     * @return A copy of this vector scaled by 1/denominator.<br>
     * Note that this uses integer division, and therefore rounds the results towards 0.
     */
    public Ivec2 divide(int denominator)
    {
        return new Ivec2(x / denominator, y / denominator);
    }
}