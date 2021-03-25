public interface Deque<T> {
    
    //add to the front.
    public void addFirst(T obj);

    //add to the back.
    public void addLast(T obj);

    //same as addFirst() but returns false if it catches an exception.
    public boolean offerFirst(T obj);

    //same as addLast() but returns false if it catches an exception
    public boolean offerLast(T obj);

    //remove the element at the front.
    public T removeFirst();

    //remove the element at the back.
    public T removeLast();

    //same as removeFirst() but returns null instead of throwing an exception.
    public T pollFirst();

    //same as removeLast() but returns null instead of throwing an exception.
    public T pollLast();

    //return the object at the front but do not remove it.
    public T getFirst();

    //return the object at the back but do not remove it.
    public T getLast();

    //same as getFirst() but returns null instead of throwing an exception.
    public T peekFirst();
    
    //same as getLast() but returns null instead of throwing an exception.
    public T peekLast();

    /**
     * queue methods
    */

    //same as addLast(T obj).
    public void add(T obj);

    //same as offerLast(T obj).
    public boolean offer(T obj);

    //same as removeFirst().
    public T remove();

    //same as pollFirst().
    public T poll();

    //same as getFirst().
    public T element();

    //same as peekFirst().
    //(peek is both a queue and stack method)
    public T peek();

    /**
     * stack methods
     */

    //same as addFirst(T obj).
    public void push(T obj);

    //same as removeFirst() and queue's remove().
    public T pop();

    /**
     * other
     */

    //removes the first occurrence of obj.
    public boolean remove(T obj);

    //same as remove(T obj).
    public boolean removeFirstOccurrence(T obj);

    //removes the last occurrence of obj.
    public boolean removeLastOccurrence(T obj);

    //returns true is the deque contains obj.
    public boolean contains(T obj);

    //return the size.
    public int size();

    //remove all elements.
    public void clear();

    //returns true if the deque is empty.
    public boolean isEmpty();

    //make an Object array from the deque.
    public Object[] toArray();

    //make a T array from the deque.
    public T[] toArray(T[] a);
}