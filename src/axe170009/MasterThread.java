package axe170009;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MasterThread extends Thread {
    List<ChildThread> childThreadsList = new ArrayList<>();
    public void run() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("src/input/input.dat"));
            int totalProcesses = Integer.parseInt(sc.nextLine());
            int[] uidsArr = new int[totalProcesses];
            int i=0;
            for(String eachUid: sc.nextLine().split(" ")) {
                uidsArr[i] = Integer.parseInt(eachUid);
                childThreadsList.add(new ChildThread(uidsArr[i], (i+1)%totalProcesses));
                ChildThread ct = childThreadsList.get(i);
                ct.run();
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
