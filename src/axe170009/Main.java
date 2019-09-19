package axe170009;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

//    function to randomly generate n numbers
    public static int[] randomArray(int min, int max, int n) {
        Random rand = new Random();
        List<Integer> list = new ArrayList<Integer>();
        int[] randArr = new int[n];
        int counter = 0;
        while (counter < n) {
            int num = min + rand.nextInt(max);
            if (list.contains(num)) continue;
            list.add(num);
            counter++;
        }
        counter = 0;
        for (Integer num: list) randArr[counter++] = num;
        return randArr;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //read the input file
        Scanner sc = new Scanner(new File("src/input/input.dat"));
        int n = sc.nextInt();
        int[] uids = new int[n];
        for(int i=0; i<n; i++){
            uids[i] = sc.nextInt();
        }
        Main mainObj = new Main();
        int[] temp = mainObj.randomArray(10, 30, 17);
        System.out.println("TEMP ::::"+ Arrays.toString(temp));
        //MasterThread mt = new MasterThread(n, uids);
        MasterThread mt = new MasterThread(temp.length, temp);
        mt.start();
    }
}
