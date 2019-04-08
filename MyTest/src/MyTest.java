import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created on 6/4/2018.
 */
public class MyTest {

    public static void main(String args[]) throws IOException, InterruptedException {
        final ConcurrentLinkedHashMap<String, String> map = new ConcurrentLinkedHashMap.Builder<String, String>().maximumWeightedCapacity(1024).build();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                String v = System.nanoTime() + "-" + new Random(System.nanoTime()).nextInt();
                System.out.println("task->size=" + map.size());
                System.out.println("task->weightedSize=" + map.weightedSize());
                for (int i = 0; i < 10000; i++) {
                    map.put(i + "-" + v, v);
                }
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            service.execute(task);
        }
        service.shutdown();
        System.out.println(map.size());
        System.out.println(map.weightedSize());

        service.awaitTermination(1, TimeUnit.DAYS);

        System.out.println(map.size());
        System.out.println(map.weightedSize());
    }

}