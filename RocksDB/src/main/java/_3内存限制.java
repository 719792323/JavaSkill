import org.rocksdb.*;

/**
 * 通过设置write_buffer_size和max_write_buffer_number来限制RocksDB的MemTable的大小。
 * write_buffer_size是每个MemTable的大小限制，max_write_buffer_number是每个ColumnFamily可以存在的最大MemTable数量。
 * 当MemTable的数量超过这个限制时，前台写入会被阻塞，直到有MemTable被刷新到磁盘。
 */
public class _3内存限制 {

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws RocksDBException {
        // open DB
        final String dbPath = "RocksDB/data";
        final Options options = new Options();
        options.setCreateIfMissing(true);
        // set write buffer size to 64 MB
        options.setWriteBufferSize(64 * 1024 * 1024);
        // set max write buffer number to 3
        options.setMaxWriteBufferNumber(3);
        RocksDB db = RocksDB.open(options, dbPath);
    }
}
