public class TestSynchronised {

    public synchronized void method1() throws InterruptedException {
        System.out.println("method1 begin at:" + System.currentTimeMillis());
        Thread.sleep(6000);
        System.out.println("method1 end at:" + System.currentTimeMillis());
    }

    public synchronized void method2() throws InterruptedException {
        while (true) {
            System.out.println("method2 running");
            Thread.sleep(200);
        }
    }

    static TestSynchronised instance = new TestSynchronised();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    instance.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 1; i < 4; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread1 still alive");
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    instance.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

    }

    public static int ef(int a[], int tag) {
        int first = 0;
        int end = a.length;
        for (int i = 0; i < a.length; i++) {
            int middle = (first + end) / 2;
            if (tag == a[middle]) {
                return middle;
            }
            if (tag > a[middle]) {
                first = middle + 1;
            }
            if (tag < a[middle]) {
                end = middle - 1;
            }
        }
        return 0;
    }

}
