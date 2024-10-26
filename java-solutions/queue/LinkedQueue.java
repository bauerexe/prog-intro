package queue;

public class LinkedQueue extends AbstractQueue {
    private static class Node {
        Object value;
        Node next;

        Node(Object value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head = null;
    private Node tail = null;

    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    @Override
    public void enqueueImpl(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public Object dequeueImpl() {
        Object value = head.value;
        head = head.next;
        if (head == null)
            tail = null;
        return value;
    }

    @Override
    public Object element() {
        return head.value;
    }


    @Override
    protected Object elementImpl() {
        return head.value;
    }
}
