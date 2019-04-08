import java.io.InputStream;

/**
 * Created on 3/9/2018.
 */
public class Tester {

    public static void main(String args[]) {
        InputStream in = Object.class.getClassLoader().getResourceAsStream("index_area_v2\\denali\\my19\\IndexArea.xml");
        System.out.println("123123");
    }
}
