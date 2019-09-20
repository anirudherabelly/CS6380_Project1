package axe170009;

import java.util.concurrent.CyclicBarrier;

/**
 * Class to simulate the behaviour of master thread in variable speeds algorithm
 */
public class MasterThread extends Thread {

    //array to hold the reference of all the n child threads
    private ChildThread[] childThreads;

    //current round count
    private int currentRound;

    //total number of rounds required to elect the leader
    private int totalRounds;

    //total no. of processes in the ring
    private int totalProcesses;

    //this flag is used to terminate the code when the leader is elected
    private volatile boolean isLeaderElected;

    //CyclicBarrier to implement thread synchronization
    private CyclicBarrier synchronousBarrier;

    /**
     * default constructor
     * @param _totalProcesses - total no of processes on which the simulation should be done
     * @param _uids - uids of all the processes
     */
    public MasterThread(int _totalProcesses, int[] _uids){
        this.totalProcesses = _totalProcesses;
        this.totalRounds = this.totalProcesses * waitingTime(_uids);
        this.synchronousBarrier = new CyclicBarrier(this.totalProcesses, () -> this.executeAfterEachRound());
        this.currentRound = 1;
        this.isLeaderElected = false;
        this.setChildThreads(_uids, this.synchronousBarrier);
    }

    /**
     * Method that is executed concurrently, when MasterThread.start() is called.
     */
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

    /**
     * Method that is executed after each round is executed
     */
    private void executeAfterEachRound(){
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
        System.out.println("current round - "+ currentRound+" isLeaderElected - "+isLeaderElected);
    }

    /**
     * Method to calculate waiting time for token with min uid at each hop
     * @param uids array of ids of all processes
     * @return waiting time
     */
    private int waitingTime(int[] uids) {
        int min = Integer.MAX_VALUE;
        for(int id : uids){
            min = Math.min(min, id);
        }
        return (int)Math.pow(2, min);
    }

    /**
     * Method to create child threads
     * @param _uids array of process uids
     * @param _barrier cyclic barrier that keeps thread waiting till each round is executed
     */
    public void setChildThreads(int[] _uids, CyclicBarrier _barrier) {
        this.childThreads = new ChildThread[this.totalProcesses];
        for(int i=0; i<this.totalProcesses; i++){
            childThreads[i] = new ChildThread(_uids[i], _barrier);
        }
        for(int i=0; i<this.totalProcesses; i++){
            childThreads[i].setNeighbour(childThreads[(i+1)%this.totalProcesses]);
        }
    }
}
