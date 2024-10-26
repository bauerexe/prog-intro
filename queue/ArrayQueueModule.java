package queue;

import java.util.function.Predicate;

// Model: elements[1]..elements[size]
// Inv: size >= 0 && for all i: elements[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueueModule {
    private static Object[] elements = new Object[10];
    private static int size = 0;
    private static int head = 0;
    private static int tail = 0;


    private static void addLen(int capacity) {
        if (capacity >= elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            System.arraycopy(elements, 0, newElements, elements.length - head, tail);
            elements = newElements;
            head = 0;
            tail = size;
        }
    }

    // Pre: element != null
    // Post: size' == size + 1 && a'[size'] = element && immutable(size)
    public static void enqueue(Object element) {
        addLen(size + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
    }

    // Pre: size > 0
    // Post: size' == size && R = elements[1] && immutable(size)
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pre: size > 0
    // Post: size' == size - 1 && R = elements[1] && immutable(size)
    public static Object dequeue() {
        assert size > 0;
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return result;
    }

    // Pre: true
    // Post: R = size && size' == size && immutable(size)
    public static int size() {
        return size;
    }

    // Pre: true
    // Post: R = (size == 0) && size' == size && immutable(size)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pre: true
    // Post: size' == 0 && for i=0..size: elements'[i] = null
    public static void clear() {
        elements = new Object[10];
        size = 0;
        head = 0;
        tail = 0;
    }

    // Pre: element != null
    // Post: size' = size + 1 && elements'[0] = element
    public static void push(Object element) {
        assert element != null;
        addLen(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    // Pre: size > 0
    // Post: R = elements[size] && size' = size
    public static Object peek() {
        assert size > 0;
        return elements[(tail - 1 + elements.length) % elements.length];
    }

    // Pre: size > 0
    // Post: R = elements[size] && size' = size - 1
    public static Object remove() {
        assert size > 0;
        tail = (tail - 1 + elements.length) % elements.length;
        Object result = elements[tail];
        elements[tail] = null;
        size--;
        return result;
    }

    // Pre: true
    // Post: R = sum(forall i in 0 ... size: 1 if predicate else 0) && size' = size
    public static int countIf(Predicate<Object> predicate) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            int index = (head + i) % elements.length;
            if (predicate.test(elements[index])) {
                count++;
            }
        }
        return count;
    }
}