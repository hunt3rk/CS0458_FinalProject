import java.util.NoSuchElementException;
import java.util.Arrays;

public class ArrayDeque<T> implements Deque<T> {

    private Object[] deque;
    private int size;
    private int front; //points to the first object in the deque
    private int back; //points to the null after the last item in the deque
    private int capacity;
    public static final int DEFAULT_CAPACITY = 16;

    public ArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayDeque(int capacity) {
        this.capacity = capacity;
        deque = new Object[capacity];
    }

    //add to the front.
    /**have to change front before doing deque[front] = obj so that front will point to the first item in the deque,
    *rather than the null before it.
    */
    public void addFirst(T obj) {
        if (size == capacity-1) {
            resizeArray(capacity*2);
        }
        front = (front-1)%capacity;
        if (front < 0) {
            front += capacity;
        }
        deque[front] = obj;
        size++;
    }

    //add to the back.
    /**
     * back will point to the null after the last item in the deque.
     * if done like addFirst, there will be a gap in the deque.
     */
    public void addLast(T obj) {
        if (size == capacity-1) {
            resizeArray(capacity*2);
        }
        deque[back] = obj;
        back = (back+1)%capacity;
        size++;
    }

    //same as addFirst() but returns false if it catches an exception.
    public boolean offerFirst(T obj) {
        try {
            addFirst(obj);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    //same as addLast() but returns false if it catches an exception
    public boolean offerLast(T obj) {
        try {
            addLast(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //remove the element at the front.
    /**
     * Since front points to the first element in the deque, this can be done.
     */
    @SuppressWarnings("unchecked")
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Empty Deque");
        }
        T obj = (T)deque[front];
        deque[front] = null;
        front = (front+1)%capacity;
        size--;
        return obj;
    }

    //remove the element at the back.
    /**
     * Since back points to the null after the last item, we have to decrement back before removing
     */
    @SuppressWarnings("unchecked")
    public T removeLast(){
        if (size == 0) {
            throw new NoSuchElementException("Empty deque");
        }
        back = (back-1)%capacity;
        if (back < 0) {
            back += capacity;
        }
        T obj = (T)deque[back];
        deque[back] = null;
        size--;
        return obj;
    }

    //same as removeFirst() but returns null instead of throwing an exception.
    public T pollFirst() {
        try {
            return removeFirst();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    //same as removeLast() but returns null instead of throwing an exception.
    public T pollLast() {
        try {
            return removeLast();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    //return the object at the front but do not remove it.
    @SuppressWarnings("unchecked")
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Empty deque");
        }
        return (T)deque[front];
    }

    //return the object at the back but do not remove it.
    @SuppressWarnings("unchecked")
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Empty deque");
        }
        int index = (back-1)%capacity;
        if (index < 0) {
            index += capacity;
        }
        return (T)deque[index];
    }

    //same as getFirst() but returns null instead of throwing an exception.
    public T peekFirst() {
        try {
            return getFirst();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }
    
    //same as getLast() but returns null instead of throwing an exception.
    public T peekLast() {
        try {
            return getLast();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /**
     * queue methods
    */

    //same as addLast(T obj).
    public void add(T obj) {
        addLast(obj);
    }

    //same as offerLast(T obj).
    public boolean offer(T obj) {
        return offerLast(obj);
    }

    //same as removeFirst().
    public T remove() {
        return removeFirst();
    }

    //same as pollFirst().
    public T poll() {
        return pollFirst();
    }

    //same as getFirst().
    public T element() {
        return getFirst();
    }

    //same as peekFirst().
    //(peek is both a queue and stack method)
    public T peek() {
        return peekFirst();
    }

    /**
     * stack methods
     */

    //same as addFirst(T obj).
    public void push(T obj) {
        addFirst(obj);
    }

    //same as removeFirst() and queue's remove().
    public T pop() {
        return removeFirst();
    }

    /**
     * other
     */

    //removes the first occurrence of obj.
    public boolean remove(T obj) {
        if (size == 0) {
            throw new NoSuchElementException("Empty Deque");
        }
        if (!contains(obj)) {
            return false;
        }
        int index = front;
        while (index != back) {
            if (deque[index].equals(obj)) {
                //shifting everything from front to index to the right to replace the obj we want to remove
                //done this way so no gaps exist within the deque
                while (index != front) {
                    index--;
                    if (index < 0) {
                        index += capacity;
                    }
                    deque[(index+1)%capacity] = deque[index];
                }
                deque[front] = null;
                size--;
                front = (front+1)%capacity;
                return true;
            }
            index = (index+1)%capacity;
        }
        return false;
    }

    //same as remove(T obj).
    public boolean removeFirstOccurrence(T obj) {
        return remove(obj);
    }

    //removes the last occurrence of obj.
    public boolean removeLastOccurrence(T obj) {
        if (size == 0) {
            throw new NoSuchElementException("Empty Deque");
        }
        if (!contains(obj)) {
            return false;
        }
        int index = back-1;
        while (index != front) {
            if (deque[index].equals(obj)) {
                //shifting everything from back to index to the left to replace the obj we want to remove
                //done this way so no gaps exist within the deque
                while (index != back) {
                    deque[index] = deque[(index+1)%capacity];
                    index++;
                }
                deque[back] = null;
                size--;
                return true;
            }
            index--;
            if (index < 0) {
                index += capacity;
            }
        }
        return false;
    }

    //returns true is the deque contains obj.
    public boolean contains(T obj) {
        int index = front;
        while (index != back) {
            if (deque[index].equals(obj)) {
                return true;
            }
            index = (index+1)%capacity;
        }
        return false;
    }

    //return the size.
    public int size() {
        return size;
    }

    //remove all elements.
    public void clear() {
        size = 0;
        front = 0;
        back = 0;
        deque = new Object[capacity];
    }

    //returns true if the deque is empty.
    public boolean isEmpty() {
        return size==0;
    }

    //resizes the array
    private void resizeArray(int newCapacity) {
        Object [] arr = new Object[newCapacity];
        int index = front;
        int i = 0;
        while (index != back) {
            arr[i] = deque[index];
            i++;
            index = (index + 1)%capacity;
        }
        front = 0;
        back = size;
        capacity = newCapacity;
        deque = arr;
    }

    //make an Object array from the deque.
    public Object[] toArray() {
        Object [] arr = new Object[size];
        int index = front;
        int i = 0;
        while (index != back) {
            arr[i] = deque[index];
            i++;
            index = (index + 1)%capacity;
        }
        return arr;
    }

    //make a T array from the deque.
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        return (T[])Arrays.copyOf(this.toArray(), size, a.getClass());
    }
}