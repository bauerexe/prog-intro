package queue;

// Model: elements[1]..elements[size]
// Inv: size >= 0 && for all i: elements[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public interface Queue {
    // Pre: element != null
    // Post: size' == size + 1 && a'[size'] = element && immutable(size)
    void enqueue(Object element);

    // Pre: size > 0
    // Post: size' == size - 1 && R = elements[1] && immutable(size)
    Object dequeue();

    // Pre: size > 0
    // Post: size' == size && R = elements[1] && immutable(size)
    Object element();

    // Pre: true
    // Post: R = size && size' == size && immutable(size)
    int size();

    // Pre: true
    // Post: R = (size == 0) && size' == size && immutable(size)
    boolean isEmpty();

    // Pre: true
    // Post: size' == 0 && for i=0..size: elements'[i] = null
    void clear();

    // Pre: True
    // Post: (count = 0 forall i = 2...length: if (elements[i] != elements[i - 1]) -> elements'[count++] = elements[i]) &&
    //        count = size' <= size && forall i=1..size': elements'[i] in elements &&
    //        forall i=2..size': elements'[i] != elements'[i-1]
    void dedup();
}
