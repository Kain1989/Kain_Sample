import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTest2 {

    static String format1 = "yyyy-MM-dd'T'HH:mm:ss";

    static String format2 = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static void main(String args[]) {

        new Thread() {

            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    new Thread() {

                        @Override
                        public void run() {
                            Date date = new Date();
                            DateFormat df = new SimpleDateFormat(format2);
                            String out = df.format(date);
                            if (out.length() != 23) {
                                System.out.println(out);
                            }
                        }
                    }.start();
                }
            }

        }.start();

        new Thread() {

            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    new Thread() {

                        @Override
                        public void run() {
                            Date date = new Date();
                            DateFormat df = new SimpleDateFormat(format1);
                            df.format(date);
//                            System.out.println(df.format(date));
                        }
                    }.start();
                }
            }

        }.start();

    }

}
