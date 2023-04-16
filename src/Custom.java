/**
 * User defined exception class that will be caught in certain
 * condition in the system to print appropriate error messages
 * @see Exception
 */
public class Custom extends Exception{
    /**
     * Constructor that uses
     * @param message message that will be printed when it is thrown
     */
    public Custom(String message){
        super(message);
    }



}
