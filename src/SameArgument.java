/**
 * Subclass for user defined exception class Custom
 * @see Custom
 */
public class SameArgument extends Custom{

    /**
     * Constructor of exception thrown when both device names are same in changing names
     */
    public SameArgument() {
        super("ERROR: Both of the names are the same, nothing changed!\n");
    }
}
