public class ArrayDequeDriver {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>(10);

        deque.addFirst("E");
        deque.addFirst("D");
        deque.addFirst("C");
        deque.addFirst("B");
        deque.addFirst("A");
        deque.addLast("F");
        deque.addLast("G");
        deque.addLast("H");
        deque.addLast("I");
        deque.addLast("J");

        System.out.println(deque.removeLastOccurrence("G"));

        while(!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
        }
    }
}