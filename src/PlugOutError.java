/**
 * Subclass for user defined exception class Custom
 * @see Custom
 */
public class PlugOutError extends Custom{
    /**
     * Constructor of the exception if a plug has no device to plug out
     */
    public PlugOutError(){
        super("ERROR: This plug has no item to plug out from that plug!\n");
    }
}