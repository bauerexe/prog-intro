package queue;

import java.util.function.Predicate;

public class TestArrayQueueModule {
    public static void fill() {
        for (int i = 1; i <= 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void fill1() {
        for (int i = 1; i <= 10; i++) {
            ArrayQueueModule.push(String.valueOf(i));
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
        }
    }

    public static void dump1() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.peek());
            ArrayQueueModule.remove();
        }
    }

    public static void main(String[] args) {
        Predicate<Object> isString = n -> n instanceof String;
        fill();
        System.out.println("Count of String Object:" + ArrayQueueModule.countIf(isString));
        dump();
        System.out.println("------------------");
        fill1();
        System.out.println("Count of String Object:" + ArrayQueueModule.countIf(isString));
        dump1();
        fill1();
    }
}
