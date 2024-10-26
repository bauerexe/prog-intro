package queue;

import java.util.function.Predicate;

public class TestArrayQueueADT {
    public static void fill(ArrayQueueADT queue, String prefix) {
        for (int i = 1; i <= 10; i++) {
            ArrayQueueADT.enqueue(queue, prefix + i);
        }
    }

    public static void fill1(ArrayQueueADT queue, String prefix) {
        for (int i = 1; i <= 10; i++) {
            ArrayQueueADT.push(queue, prefix + i);
        }
    }

    public static void dump1(ArrayQueueADT queue1, ArrayQueueADT queue2) {

        while (!ArrayQueueADT.isEmpty(queue1) && !ArrayQueueADT.isEmpty(queue2)) {
            System.out.println(ArrayQueueADT.size(queue1) + " " + ArrayQueueADT.peek(queue1)
                    + "\t\t" + ArrayQueueADT.size(queue2) + " " + ArrayQueueADT.peek(queue2));
            ArrayQueueADT.remove(queue1);
            ArrayQueueADT.remove(queue2);
        }

    }

    public static void dump(ArrayQueueADT queue1, ArrayQueueADT queue2) {

        while (!ArrayQueueADT.isEmpty(queue1) && !ArrayQueueADT.isEmpty(queue2)) {
            System.out.println(ArrayQueueADT.size(queue1) + " " + ArrayQueueADT.dequeue(queue1)
                    + "\t\t" + ArrayQueueADT.size(queue2) + " " + ArrayQueueADT.dequeue(queue2));
        }
    }




    public static void main(String[] args) {
        Predicate<Object> isString = n -> n instanceof String;
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        System.out.println("Count of String Object:" + ArrayQueueADT.countIf(queue1, isString));
        fill(queue1, "q1_");
        fill(queue2, "q2_");
        dump(queue1, queue2);
        System.out.println("------------------");
        fill1(queue1, "q1_");
        fill1(queue2, "q2_");
        System.out.println("Count of String Object:" + ArrayQueueADT.countIf(queue1, isString));
        dump1(queue1, queue2);

    }
}