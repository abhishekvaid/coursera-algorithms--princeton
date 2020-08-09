/* *****************************************************************************
 *  Name: Abhishek Vaid
 *  Date: 6th August 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty())
            queue.enqueue(StdIn.readString());
        Iterator<String> itor = queue.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(itor.next());
        }
    }
}
