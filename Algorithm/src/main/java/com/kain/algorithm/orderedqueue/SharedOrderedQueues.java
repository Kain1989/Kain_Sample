package com.kain.algorithm.orderedqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedOrderedQueues<T> {

    private List<OrderedQueue<T>> queues = new ArrayList<OrderedQueue<T>>();

    private List<OrderedQueue<T>> fetched = new ArrayList<OrderedQueue<T>>();

    private ThreadLocal<OrderedQueue<T>> localQueue = new ThreadLocal<OrderedQueue<T>>();

    private int nThreads;

    private volatile AtomicInteger closedTimes = new AtomicInteger();

    public SharedOrderedQueues(int nThreads) {
        this.nThreads = nThreads;
    }

    public class Channel {

        private OrderedQueue<T> queue;

        private OrderedQueue<?> fromQueue;

        public void offer(OrderedObject<T> obj) {
            queue.add(obj);
        }

        public void close(TerminationSignal terminationSignal) {
            queue.terminate(terminationSignal);
        }

        public OrderedQueue<?> fromQueue() {
            return fromQueue;
        }

    }

    public class Output {

        private OrderedObject<T> orderedObject;

        private OrderedQueue<T> from;

        public OrderedObject<T> getOrderedObject() {
            return orderedObject;
        }

        public OrderedQueue<T> getFrom() {
            return from;
        }

    }

    public Channel createQueue() {
        return createQueue(null);
    }

    public synchronized Channel createQueue(OrderedQueue<?> fromQueue) {
        if (isClosed()) {
            // TODO throw exception
            return null;
        }
        OrderedQueue<T> queue = new OrderedQueue<T>();
        Channel channel = new Channel();
        channel.queue = queue;
        channel.fromQueue = fromQueue;
        queues.add(queue);
        return channel;
    }

    public synchronized OrderedObject<T> consumeExclusively() {
        OrderedQueue<T> queue = localQueue.get();
        if (queue != null) {
            OrderedObject<T> obj = queue.poll();
            if (obj != null && obj.getValue() != null) {
                return obj;
            }
        } else {
            for (OrderedQueue<T> q : queues) {
                if (fetched.contains(q)) {
                    continue;
                }
                OrderedObject<T> obj = q.poll();
                if (obj != null && obj.getValue() != null) {
                    localQueue.set(q);
                    fetched.add(q);
                    return obj;
                }
            }
        }

        return null;
    }

    public void returnQueue() {
        OrderedQueue<T> queue = localQueue.get();
        if (queue != null) {
            localQueue.remove();
            synchronized (fetched) {
                fetched.remove(queue);
            }
        }
    }

    public synchronized Output consume() {
        for (OrderedQueue<T> q : queues) {
            OrderedObject<T> obj = q.poll();
            if (obj != null && obj.getValue() != null) {
                Output output = new Output();
                output.orderedObject = obj;
                output.from = q;
                return output;
            }
        }
        return null;
    }

    public synchronized boolean isExhausted() {
        for (OrderedQueue<T> queue : queues) {
            if (!queue.isExhausted()) {
                return false;
            }
        }
        return true;
    }

    public void close() {
        closedTimes.incrementAndGet();
    }

    public boolean isClosed() {
        return closedTimes.get() >= nThreads;
    }

    public synchronized int size() {
        int size = 0;
        for (OrderedQueue<T> queue : queues) {
            size += queue.getQueue().size();
        }
        return size;
    }

}
