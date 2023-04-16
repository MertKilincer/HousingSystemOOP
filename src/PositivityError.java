/**
 * Subclass for user defined exception class Custom
 * @see Custom
 */
public  class PositivityError extends Custom{
    /**
     * Constructor of the exception thrown if some fields are not positive
     * @param Type field name
     */
    public PositivityError(String Type) {
        super("ERROR: "+Type+" value must be a positive number!\n");
    }
}
