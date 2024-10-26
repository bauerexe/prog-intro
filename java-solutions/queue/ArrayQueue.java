package queue;

import java.util.function.Predicate;

// :NOTE: contract До сих пор не черех модель
public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[10];
    private int head = 0;
    private int tail = 0;

    // Pre: true
    // Post: size' = size && tail' = size && immutable(size) && head' = 0
    private void addLen(int capacity) {
        if (capacity >= elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            System.arraycopy(elements, 0, newElements, elements.length - head, tail);
            elements = newElements;
            head = 0;
            tail = size;
        }
    }


    @Override
    public void enqueueImpl(Object element) {
        addLen(size + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    // Pre: size > 0
    // Post: R = elements[head] && size' = size && immutable(size)
    public Object element() {
        assert size > 0;
        return elements[head];
    }

    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    @Override
    public Object dequeueImpl() {
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return result;
    }

    @Override
    public void clearImpl() {
        elements = new Object[10];
        head = 0;
        tail = 0;
    }

    // Pre: element != null
    // Post: size' = size + 1 && elements'[head'] = element && head' = (head - 1 + elements.length) % elements.length
    public void push(Object element) {
        assert element != null;
        addLen(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    // Pre: size > 0
    // Post: R = elements[(tail - 1 + elements.length) % elements.length] && size' = size
    public Object peek() {
        assert size > 0;
        return elements[(tail - 1 + elements.length) % elements.length];
    }

    // Pre: size > 0
    // Post: R = elements[tail] && size' = size - 1 && elements'[tail'] = null, tail' = (tail - 1 + elements.length) % elements.length
    public Object remove() {
        assert size > 0;
        tail = (tail - 1 + elements.length) % elements.length;
        Object result = elements[tail];
        elements[tail] = null;
        size--;
        return result;
    }

    // :NOTE: описание формальное что происхожит
    // Pre: true
    // Post: R = sum(forall i in 0 ... size: 1 if predicate else 0) && size' = size
    public int countIf(Predicate<Object> predicate) {
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