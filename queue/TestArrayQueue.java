package queue;

import java.util.function.Predicate;

public class TestArrayQueue {
    public static void fill(ArrayQueue queue, String prefix) {
        for (int i = 1; i <= 10; i++) {
            queue.enqueue(prefix + i);
        }
    }

    public static void fill1(ArrayQueue queue, String prefix) {
        for (int i = 1; i <= 10; i++) {
            queue.push(prefix + i);
        }
    }

    public static void dump(ArrayQueue queue1, ArrayQueue queue2) {

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            System.out.println(queue1.size() + " " + queue1.dequeue()
                    + "\t\t" + queue2.size() + " " + queue2.dequeue());
        }
    }

    public static void dump1(ArrayQueue queue1, ArrayQueue queue2) {

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            System.out.println(queue1.size() + " " + queue1.peek()
                    + "\t\t" + queue2.size() + " " + queue2.peek());
            queue1.remove();
            queue2.remove();
        }
    }

    public static void main(String[] args) {
        Predicate<Object> isString = n -> n instanceof String;
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        System.out.println("Count of String Object:" + queue1.countIf(isString));
        fill(queue1, "q1_");
        fill(queue2, "q2_");
        dump(queue1, queue2);
        System.out.println("------------------");
        fill1(queue1, "q1_");
        System.out.println("Count of String Object:" + queue1.countIf(isString));
        fill1(queue2, "q2_");
        dump1(queue1, queue2);
    }
}
