import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class DiskTest {

    static int length = 0xFFFFFFF;

    public static void main(String[] args) throws Exception {
        MappedByteBuffer out = new RandomAccessFile("/Users/zfshi/Workspace/disktest", "rw").getChannel().map(
                FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i = 0; i < length; i++) {
            out.put((byte) 'x');
        }
        System.out.println("Finished writing!");
    }

}
