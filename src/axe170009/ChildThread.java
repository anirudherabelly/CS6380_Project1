package axe170009;

public class ChildThread extends Thread {
    int childUid;
    int itsNeightbour;
    public ChildThread(int childUid, int itsNeightbour) {
        this.childUid = childUid;
        this.itsNeightbour = itsNeightbour;
    }
    public void run() {
        System.out.println("childThread Running ::: "+childUid+" ::: "+itsNeightbour);
    }
}
