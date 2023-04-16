import org.rocksdb.*;

public class _3内存限制 {

    static {
        RocksDB.loadLibrary();
    }

    public static void limitTable() throws RocksDBException {
        final long memTableSize = 512L * 1024L * 1024L; // 512MB
        final Options options = new Options().setCreateIfMissing(true).setWriteBufferSize(memTableSize);
        final RocksDB db = RocksDB.open(options, "path/to/db");
        // do something with the db
        db.close();
    }

}
