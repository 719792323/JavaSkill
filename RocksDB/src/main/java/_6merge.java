import org.rocksdb.*;

/**
 * merge函数的作用是将一个新的值与一个已存在的键的值进行合并，得到一个新的值，然后将这个新的值存储在RocksDB中。
 * 合并的逻辑是由MergeOperator对象来定义的，你可以使用RocksDB提供的一些内置的MergeOperator，比如StringAppendOperator，它会将两个字符串拼接，
 * 也可以自己实现一个MergeOperator，。merge函数的好处是可以避免读取旧的值，直接进行合并操作，提高性能。
 */
public class _6merge {
    public static Options options = new Options().setCreateIfMissing(false);
    public static RocksDB db;

    static {
        RocksDB.loadLibrary();
        try {
            // 创建一个MergeOperator对象，用于定义合并逻辑
            MergeOperator mergeOperator = new StringAppendOperator();
            // 设置RocksDB的选项，使用MergeOperator
            Options options = new Options().setMergeOperator(mergeOperator);
            db = RocksDB.open(options, "RocksDB/data");
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RocksDBException {
        db.delete("merge".getBytes());
        db.put("merge".getBytes(), "hello ".getBytes());
        db.merge("merge".getBytes(), "world".getBytes());
        System.out.println(new String(db.get("merge".getBytes())));
        //注意，如果保留了使用merge的key，那么再此启动时必须指定merge_operator
        //否则无法启动rocksdb，所以这里删除此key，以免麻烦。
        db.delete("merge".getBytes());
        options.close();
        db.close();
    }
}
