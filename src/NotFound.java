/**
 * Subclass for user defined exception class Custom
 * @see Custom
 */
public class NotFound extends Custom{
    /**
     * Constructor of the exception thrown is system does not have the device
     */
    public NotFound(){
        super("ERROR: There is not such a device!\n");
    }
}
