package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void clearImpl();

    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void enqueueImpl(Object element);

    @Override
    public void enqueue(Object element) {
        enqueueImpl(element);
        size++;
    }

    protected abstract Object dequeueImpl();

    @Override
    public Object dequeue() {
        assert size > 0;
        size--;
        return dequeueImpl();
    }


    // :NOTE: недостаточно абстрактный класс
    @Override
    public void dedup() {
        if (isEmpty()) return;
        AbstractQueue tempQueue = new LinkedQueue();
        Object current = null;
        while (!isEmpty()){
            Object next = dequeue();
            if (tempQueue.size() == 0 || !Objects.equals(current, next)){
                tempQueue.enqueue(next);
                current = next;
            }
        }
        clear();
        while (!tempQueue.isEmpty()) {
            enqueue(tempQueue.dequeue());
        }
    }

    protected abstract Object elementImpl();

    @Override
    public Object element() {
        return elementImpl();
    }
}
