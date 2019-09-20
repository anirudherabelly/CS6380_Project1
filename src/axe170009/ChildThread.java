package axe170009;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Class to simulate the behaviour of child thread in variable speeds algorithm
 */
public class ChildThread extends Thread {
    // UID of this thread
    private int uid;
    //current round of this thread
    private int currentRound;
    //boolean to check if this thread is the leader
    volatile boolean isLeaderElected;
    //Message object containing details of the token received from previous thread
    private Message receivedMessage;
    //This message abject contains the token details of the minimum UID seen so far
    private Message minMessage;
    //Reference to this thread's neighbour
    private ChildThread neighbour;
    //Cyclyic barrier object - used to call await method
    private CyclicBarrier synchronousBarrier;

    /**
     * constructor to initialize the varialbles
     * @param _uid
     * @param _barrier
     */
    public ChildThread(int _uid, CyclicBarrier _barrier) {
        this.uid = _uid;
        this.currentRound = 1;
        this.isLeaderElected = false;
        this.synchronousBarrier = _barrier;
        this.minMessage = new Message(this.currentRound, _uid, true);
        this.receivedMessage = new Message(this.currentRound, this.uid, true);
    }

    @Override
    public void run() {
        try {
            while(!this.isLeaderElected) {
                System.out.println("threadID ::: "+this.uid+"  minMessage ::: "+minMessage.getUId()+"    receivedMessage ::: "+receivedMessage.getUId()+"   "+receivedMessage.isReceived());
                if(minMessage.getUId() > receivedMessage.getUId()){
                    minMessage.setUId(receivedMessage.getUId());
                    minMessage.setRound(receivedMessage.getRound());
                }
                if (this.canSendMessage()) {
                    System.out.println("Yes can send msg ID::: "+uid+"   round ::: "+currentRound);
                    this.sendMessageToNeighbour();
                }
                synchronousBarrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /*
    determines if the arrived message have waited sufficient time to send the message to next thread
     */
    private boolean canSendMessage(){
        int roundsWaited = minMessage.getRound() + (int)Math.pow(2, minMessage.getUId()) - 1;
        return this.currentRound == roundsWaited;
    }

    /*
    updates the message object of the next thread
     */
    private void sendMessageToNeighbour(){
        Message messageToNeighbour = new Message(this.currentRound+1, minMessage.getUId(), false);
        neighbour.setReceivedMessage(messageToNeighbour);
    }

    /*
    determines if this thread is the leader based on the incoming message UID
     */
    public boolean isLeader() {
        return this.uid == this.receivedMessage.getUId() && !this.receivedMessage.isReceived();
    }

    /*
    get's the current thread's UID
     */
    public int getUid() {
        return uid;
    }

    /**
     * Sets the neighbour of the current thread
     * @param _neighbour
     */
    public void setNeighbour(ChildThread _neighbour) {
        this.neighbour = _neighbour;
    }

    /**
     * Sets the current round of this child thread by the master thread
     * @param currentRound
     */
    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    /**
     * After all the rounds, this boolean variable will be set to true if this is the leader thread
     * @param leaderElected
     */
    public void setLeaderElected(boolean leaderElected) {
        isLeaderElected = leaderElected;
    }

    public void setReceivedMessage(Message receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public int minMessageUID() {
        return minMessage.getUId();
    }
}
