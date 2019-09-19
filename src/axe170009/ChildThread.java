package axe170009;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ChildThread extends Thread {

    private int uid;
    private int currentRound;

    volatile boolean isLeaderElected;

    private Message receivedMessage;
    private Message minMessage;

    private ChildThread neighbour;

    private CyclicBarrier synchronousBarrier;



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
                System.out.println("threadID ::: "+this.uid+"  minMessage ::: "+minMessage.getUId()+"    receivedMessage ::: "+receivedMessage.getUId()+"   "+receivedMessage.isMyself());
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

    private boolean canSendMessage(){
        int roundsWaited = minMessage.getRound() + (int)Math.pow(2, minMessage.getUId()) - 1;
        return this.currentRound == roundsWaited;
    }

    private void sendMessageToNeighbour(){
        Message messageToNeighbour = new Message(this.currentRound+1, minMessage.getUId(), false);
        neighbour.setReceivedMessage(messageToNeighbour);
    }

    public boolean isLeader() {
        return this.uid == this.receivedMessage.getUId() && !this.receivedMessage.isMyself();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int _uid) {
        this.uid = _uid;
    }

    public ChildThread getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(ChildThread _neighbour) {
        this.neighbour = _neighbour;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public boolean isLeaderElected() {
        return isLeaderElected;
    }

    public void setLeaderElected(boolean leaderElected) {
        isLeaderElected = leaderElected;
    }

    public Message getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(Message receivedMessage) {
        this.receivedMessage = receivedMessage;
    }
}
