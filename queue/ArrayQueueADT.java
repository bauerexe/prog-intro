package queue;

import java.util.function.Predicate;

// Model: elements[1]..elements[size]
// Inv: size >= 0 && for all i: elements[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueueADT {
    private Object[] elements = new Object[10];

    private int size;
    private int head;
    private int tail;

    // Pre: True
    // Post: size = 0
    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[10];
        queue.size = 0;
        queue.head = 0;
        queue.tail = 0;
        return queue;
    }


    private static void addLen(ArrayQueueADT queue, int capacity) {
        if (capacity >= queue.elements.length) {
            Object[] newElements = new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, newElements, queue.elements.length - queue.head, queue.tail);
            queue.elements = newElements;
            queue.head = 0;
            queue.tail = queue.size;
        }
    }

    // Pre: element != null
    // Post: size' == size + 1 && a'[size'] = element && immutable(size)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        addLen(queue, queue.size + 1);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
        queue.size++;
    }

    // Pre: size > 0
    // Post: size' == size && R = elements[1] && immutable(size)
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    // Pre: size > 0
    // Post: size' == size - 1 && R = elements[1] && immutable(size)
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object result = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return result;
    }

    // Pre: true
    // Post: R = size && size' == size && immutable(size)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pre: true
    // Post: R = (size == 0) && size' == size && immutable(size)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pre: true
    // Post: size' == 0 && for i=0..size: elements'[i] = null
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[10];
        queue.size = 0;
        queue.head = 0;
        queue.tail = 0;
    }

    // Pre: element != null
    // Post: size' = size + 1 && elements'[0] = element
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;
        addLen(queue, queue.size + 1);
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = element;
        queue.size++;
    }

    // Pre: size > 0
    // Post: R = elements[size] && size' = size
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.tail - 1 + queue.elements.length) % queue.elements.length];
    }

    // Pre: size > 0
    // Post: R = elements[size] && size' = size - 1
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
        Object result = queue.elements[queue.tail];
        queue.elements[queue.tail] = null;
        queue.size--;
        return result;
    }

    // Pre: true
    // Post: R = sum(forall i in 0 ... size: 1 if predicate else 0) && size' = size
    public static int countIf(ArrayQueueADT queue, Predicate<Object> predicate) {
        int count = 0;
        for (int i = 0; i < queue.size; i++) {
            int index = (queue.head + i) % queue.elements.length;
            if (predicate.test(queue.elements[index])) {
                count++;
            }
        }
        return count;
    }
}