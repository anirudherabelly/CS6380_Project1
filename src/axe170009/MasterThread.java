package axe170009;

import java.util.concurrent.CyclicBarrier;

public class MasterThread extends Thread {

    //array holds the references of all the n child threads
    private ChildThread[] childThreads;
    private int currentRound;           //info about the current round
    private int totalRounds;            //mininum number of rounds required to elect the leader
    private int totalProcesses;
    private volatile boolean isLeaderElected;   //this flag is used to terminate the code when the leader is elected
    private CyclicBarrier synchronousBarrier;   //CyclicBarrier to implement thread synchronization

    public MasterThread(int _totalProcesses, int[] _uids){
        this.totalProcesses = _totalProcesses;
        this.totalRounds = this.totalProcesses * waitingTime(_uids);
        this.synchronousBarrier = new CyclicBarrier(this.totalProcesses, () -> this.executeAfterEachRound());
        this.currentRound = 1;
        this.isLeaderElected = false;
        this.setChildThreads(_uids, this.synchronousBarrier);
    }

    @Override
    public void run() {
        for(ChildThread child : childThreads){
            child.start();
        }
        while(!this.isLeaderElected){
        }
        for(ChildThread thread : this.childThreads){
            System.out.println("thread_ID---isLeader---leaderID");
            System.out.println(thread.getUid()+"            "+thread.isLeader()+"          "+thread.minMessageUID());
        }
    }

    private void executeAfterEachRound(){
        System.out.println("current round - "+ currentRound+" isLeader - "+isLeaderElected);
        this.currentRound += 1;
        for(ChildThread thread : this.childThreads){
            if(thread.isLeader()){
                this.isLeaderElected = true;
            }
        }
        for(ChildThread thread : this.childThreads){
            thread.setCurrentRound(this.currentRound);
            if(isLeaderElected)thread.setLeaderElected(true);
        }
    }

    private int waitingTime(int[] uids) {
        int min = Integer.MAX_VALUE;
        for(int id : uids){
            min = Math.min(min, id);
        }
        return (int)Math.pow(2, min);
    }

    public ChildThread[] getChildThreads() {
        return childThreads;
    }

    public void setChildThreads(int[] _uids, CyclicBarrier barrier) {
        this.childThreads = new ChildThread[this.totalProcesses];
        for(int i=0; i<this.totalProcesses; i++){
            childThreads[i] = new ChildThread(_uids[i], barrier);
        }
        for(int i=0; i<this.totalProcesses; i++){
            childThreads[i].setNeighbour(childThreads[(i+1)%this.totalProcesses]);
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }
}
