package axe170009;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class to simulate the Variable speeds algorithm
 */
public class Main {

    /**
     * method to randomly generate n numbers
     * @param min starting no. from where no's will be generated
     * @param max ending no. from where no's will be generated
     * @param n no. of numbers need to be generated
     * @return int array with size n and no's between min and max
     */
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

    /**
     * main method
     * @param args any arguments expects arg[0] to be input file
     * @throws FileNotFoundException when scanner does not find the file
     */
    public static void main(String[] args) throws FileNotFoundException {
        //read the input file
        Scanner sc = new Scanner(new File("src/axe170009/input.dat"));
        if(args.length >= 1){
            sc = new Scanner(new File(args[0]));
        }

        int n = sc.nextInt();
        int[] uids = new int[n];
        for(int i=0; i<n; i++){
            uids[i] = sc.nextInt();
        }

        /*Main mainObj = new Main();
        int[] temp = mainObj.randomArray(10, 30, 17);
        System.out.println("TEMP ::::"+ Arrays.toString(temp));
        MasterThread mt = new MasterThread(temp.length, temp);*/

        MasterThread mt = new MasterThread(n, uids);
        mt.start();
    }
}
