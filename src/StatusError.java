/**
 * Subclass for user defined exception class Custom
 * @see Custom
 */
public class StatusError extends Custom{
    /**
     * Constructor for exception class about switching
     * @param status
     */

    public StatusError(String status) {
        super("ERROR: This device is already switched "+status.toLowerCase()+"!\n");
    }
}
