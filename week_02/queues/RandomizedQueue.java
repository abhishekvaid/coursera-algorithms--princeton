/* *****************************************************************************
 *  Name: Abhishek Vaid
 *  Date: 6th August
 *  Description:
 **************************************************************************** */


/*
Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] buffer;
    private int begin, end;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.buffer = new Object[1 << 2];
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (end == buffer.length) {
            expandBuffer();
        }
        buffer[end++] = item;
    }

    private void expandBuffer() {

        Object[] bufferTemp = new Object[buffer.length * 2];
        int size = size();
        int offset = bufferTemp.length / 4;
        for (int i = 0; i < size; i++) {
            bufferTemp[offset + i] = buffer[begin+i];
        }
        buffer = bufferTemp;
        begin = offset;
        end = begin + size;
    }

    // return the number of items on the randomized queue
    public int size() {
        return end - begin;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(begin, end);
        Item res = (Item) buffer[index];
        buffer[index] = buffer[--end];
        buffer[end] = null;
        return res;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return end == begin;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return (Item) buffer[StdRandom.uniform(begin, end)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        Object[] buffer_ = Arrays.copyOfRange(buffer, begin, end);
        StdRandom.shuffle(buffer_);
        return (Iterator<Item>) Arrays.stream(buffer_).iterator();
    }

}
