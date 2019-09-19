package axe170009;

public class Message {

    private int round;
    private int uId;
    private boolean myself;

    public Message(int _round, int _uId, boolean _myself){
        this.round = _round;
        this.uId = _uId;
        this.myself = _myself;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int _round) {
        this.round = _round;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int _uId) {
        this.uId = _uId;
    }

    public boolean isMyself() {
        return myself;
    }

    public void setMyself(boolean myself) {
        this.myself = myself;
    }
}
