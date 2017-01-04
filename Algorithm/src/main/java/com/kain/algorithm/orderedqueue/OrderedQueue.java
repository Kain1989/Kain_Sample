package com.kain.algorithm.orderedqueue;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderedQueue<T> {

    private ConcurrentLinkedQueue<T> queue;

    private List<OrderedObject<T>> unordered = new ArrayList<OrderedObject<T>>();

    private volatile AtomicInteger addOrdinal = new AtomicInteger(0);

    private volatile AtomicInteger pollOrdinal = new AtomicInteger(0);

    private volatile List<TerminationSignal> terminationSignals = new ArrayList<TerminationSignal>();

    public OrderedQueue(ConcurrentLinkedQueue<T> queue) {
        this.queue = queue;
    }

    public OrderedQueue() {
        this.queue = new ConcurrentLinkedQueue<T>();
        this.addOrdinal.addAndGet(queue.size());
    }

    public void add(OrderedObject<T> e) {
        flush();
        if (e.getOrdinal() != addOrdinal.get() + 1) {
            synchronized (unordered) {
                unordered.add(e);
            }
        } else {
            queue.add(e.getValue());
            addOrdinal.incrementAndGet();
        }
    }

    private void flush() {
        synchronized (unordered) {
            if (CollectionUtils.isEmpty(unordered)) {
                return;
            }
            Collections.sort(unordered, new Comparator<OrderedObject<T>>() {

                @Override
                public int compare(OrderedObject<T> o1, OrderedObject<T> o2) {
                    return o1.getOrdinal() - o2.getOrdinal();
                }

            });
            for (Iterator<OrderedObject<T>> i = unordered.iterator(); i.hasNext();) {
                OrderedObject<T> o = i.next();
                if (o.getOrdinal() == addOrdinal.get() + 1) {
                    queue.add(o.getValue());
                    addOrdinal.incrementAndGet();
                    i.remove();
                } else {
                    break;
                }
            }
        }
    }

    public OrderedObject<T> poll() {
        T t = queue.poll();

        if (t == null) {
            if (isExhausted()) {
                return null;
            } else {
                flush();
                return new OrderedObject<T>(null, pollOrdinal.get());
            }

        }

        return new OrderedObject<T>(t, pollOrdinal.incrementAndGet());
    }

    public void terminate(TerminationSignal terminationSignal) {
        synchronized (terminationSignals) {
            terminationSignals.add(terminationSignal);
        }
    }

    public ConcurrentLinkedQueue<T> getQueue() {
        return queue;
    }

    public List<OrderedObject<T>> getUnordered() {
        return unordered;
    }

    public AtomicInteger getAddOrdinal() {
        return addOrdinal;
    }

    public AtomicInteger getPollOrdinal() {
        return pollOrdinal;
    }

    public boolean isExhausted() {
        synchronized (terminationSignals) {
            if (terminationSignals.isEmpty()) {
                return false;
            }
            for (TerminationSignal signal : terminationSignals) {
                if (signal instanceof NoMoreSignal) {
                    // wait for the last element to be fetched
                    return ((NoMoreSignal) signal).totalNums <= this.pollOrdinal.get();
                } else {
                    // for other signal, all jobs should be terminated
                    // immediately
                    return true;
                }
            }
            return false;
        }
    }

    public int getSize() {
        if (!isExhausted()) {
            return Integer.MAX_VALUE;
        } else {
            int min = Integer.MAX_VALUE;
            for (TerminationSignal signal : terminationSignals) {
                if (signal instanceof NoMoreSignal) {
                    if (min > ((NoMoreSignal) signal).totalNums) {
                        min = ((NoMoreSignal) signal).totalNums;
                    }
                }
            }
            return min;
        }
    }

}
