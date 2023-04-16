/**
 * Subclass for user defined exception class Custom
 */
public class PlugInError extends Custom{
    /**
     * Constructor for exception about if there is already item plugged in.
     */
    public PlugInError(){
        super("ERROR: There is already an item plugged in to that plug!\n");
    }
}