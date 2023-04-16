/**
 * Subclass for user defined exception class Custom
 */
public class NameError extends Custom{
    /**
     *
     * Constructor of the exception that ensures every device has a unique name
     */
    public NameError() {
            super("ERROR: There is already a smart device with same name!\n");
        }

}


