public class StatusError extends Custom{

    public StatusError(String status) {
        super("ERROR: This device is already switched "+status+"!\n");
    }
}
