/* *****************************************************************************
 *  Name: Abhishek Vaid
 *  Date: 6th August
 *  Description:
 **************************************************************************** */

/*
Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
 */

import java.util.Arrays;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Object[] buffer;
    private int begin, end;

    // construct an empty deque
    public Deque() {
        this.buffer = new Object[1 << 1];
        this.begin = 0;
        this.end = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return end == begin ;
    }

    // return the number of items on the deque
    public int size() {
        return end - begin;
    }

    private void expandBuffer() {

        Object[] bufferTemp = new Object[buffer.length*2];
        int offset = bufferTemp.length / 4 ;
        int size = size();
        for (int i = 0; i < size; i++) {
            bufferTemp[offset+i] = buffer[begin+i];
        }
        buffer = bufferTemp;
        begin = offset;
        end = begin + size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (begin == 0) {
            expandBuffer();
        }
        buffer[--begin] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (end == buffer.length){
            expandBuffer();
        }
        buffer[end++] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item res = (Item) buffer[begin];
        buffer[begin++] = null;
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item res = (Item) buffer[--end];
        buffer[end] = null;
        return res;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return (Iterator<Item>) Arrays.stream(Arrays.copyOfRange(buffer, begin, end)).iterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addLast(4);
        d.addLast(5);
        d.addLast(6);
        for (Integer integer : d) System.out.println(integer);
    }

}
