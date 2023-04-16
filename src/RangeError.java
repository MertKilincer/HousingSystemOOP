/**
 * Subclass for user defined exception class Custom
 */
public class RangeError extends Custom{
    /**
     * Constructor about exception that thrown when a value not in wanted range
     * @param rangeType range type of Error
     * @param range range value of the Error
     */
    public RangeError(String rangeType,String range) {
        super(rangeType+"must be in range of "+range);
    }
}
