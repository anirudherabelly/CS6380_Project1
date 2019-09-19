package axe170009;

/**
 * Class to hold details about a token message generated or received
 */
public class Message {

    //round in which message received
    private int round;
    //uid of the process that generates it initially
    private int uId;
    //boolean to know whether the message is received from neighbour or generated at it's own
    private boolean isReceived;

    /**
     * Constructor for message
     * @param _round round from which it is at the corresponding process
     * @param _uId ID of the process that sends this message token
     * @param _isReceived Boolean to set whether the message is recieved from adjacent or initial message
     */
    public Message(int _round, int _uId, boolean _isReceived){
        this.round = _round;
        this.uId = _uId;
        this.isReceived = _isReceived;
    }

    /**
     * method to get round
     * @return round
     */
    public int getRound() {
        return round;
    }

    /**
     * method to set round
     * @param _round assign new round
     */
    public void setRound(int _round) {
        this.round = _round;
    }

    /**
     * method to get Id
     * @return uid
     */
    public int getUId() {
        return uId;
    }

    /**
     * method to set uid
     * @param _uId assign new uid
     */
    public void setUId(int _uId) {
        this.uId = _uId;
    }

    /**
     * method to get isReceived
     * @return isReceived
     */
    public boolean isReceived() {
        return isReceived;
    }
}
