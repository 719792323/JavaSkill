import org.rocksdb.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注意range和字符串排序密切相关，所以要设计好Key的格式，以及进行区间操作时要确保区间的正确性
 */
public class _4批操作 {

    public static Options options = new Options().setCreateIfMissing(false);
    public static RocksDB db;

    static {
        RocksDB.loadLibrary();
        try {
            db = RocksDB.open(options, "RocksDB/data");
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写入一些数据方便演示范围操作（随机写入）
     */
    public static void writeSomeData() throws RocksDBException {
        WriteOptions writeOptions = new WriteOptions();
        db.put(writeOptions, "priceIndex:10#1".getBytes(), "1".getBytes());
        db.put(writeOptions, "priceIndex:20#2".getBytes(), "2".getBytes());
        db.put(writeOptions, "priceIndex:30#3".getBytes(), "3".getBytes());
        db.put(writeOptions, "priceIndex:40#4".getBytes(), "4".getBytes());
        db.put(writeOptions, "priceIndex:50#5".getBytes(), "5".getBytes());
        writeOptions.close();
    }

    /**
     * 批写入
     * 使用WriteBatch方法的优势是可以保证多个键值对的原子性写入，提高写入效率，避免数据不一致的问题。
     * 同时，WriteBatch也支持在内存中构建一个可查询的索引，方便用户在写入之前对数据进行排序、过滤、合并等操作。
     * 使用WriteBatch方法的缺点是可能会增加内存的占用，因为需要缓存所有的更新操作。
     * 另外，WriteBatch也不是线程安全的，如果多个线程同时操作同一个WriteBatch对象，可能会导致数据混乱或丢失。
     * 因此，建议您在使用WriteBatch时注意控制内存使用量和线程同步问题。
     *
     */
    public static void writeBatchData() throws RocksDBException {
        WriteBatch writeBatch = new WriteBatch();
        WriteOptions writeOptions = new WriteOptions();
        writeBatch.put("priceIndex:10#1".getBytes(), "1".getBytes());
        writeBatch.put("priceIndex:20#2".getBytes(), "2".getBytes());
        writeBatch.put("priceIndex:30#3".getBytes(), "3".getBytes());
        writeBatch.put("priceIndex:40#4".getBytes(), "4".getBytes());
        writeBatch.put("priceIndex:50#5".getBytes(), "5".getBytes());
        db.write(writeOptions,writeBatch);
        writeOptions.close();
        writeBatch.close();
    }

    /**
     * 扫描区间数据
     */
    public static void scanRangeData(String start, String end) {
        // 创建一个迭代器
        ReadOptions readOptions = new ReadOptions();
        // 设置价格范围的上界
        // 范围设置是左闭右开原则
        readOptions.setIterateUpperBound(new Slice(end));
        RocksIterator iterator = db.newIterator(readOptions);
        // 定位到价格范围的下界
        iterator.seek(start.getBytes());
        // 循环遍历迭代器，打印键值对
        while (iterator.isValid()) {
            System.out.println(new String(iterator.key()) + " => " + new String(iterator.value()));
            iterator.next();
        }
        // 关闭资源
        iterator.close();
        readOptions.close();

    }

    /**
     * 区间删除数据
     * 因为使用了WriteBatch，所以该操作也是线程不安全的
     * 如果要清空整个表的数据，用删除CF，而不是deleteRange
     */
    public static void deleteRangeData(String start, String end) throws RocksDBException {
        // 创建一个写入批次
        WriteBatch writeBatch = new WriteBatch();
        // 设置要删除的区间的起始和结束键
        // 调用DeleteRange方法
        writeBatch.deleteRange(start.getBytes(), end.getBytes());
        // 将批次写入数据库
        WriteOptions writeOptions = new WriteOptions();
        db.write(writeOptions, writeBatch);
        // 关闭资源
        writeBatch.close();
        writeOptions.close();
    }

    /**
     * 一次性获得多个数据
     */
    public static void multiGet(String... keys) throws RocksDBException {
        List<byte[]> collect = Arrays.stream(keys).map(key -> key.getBytes()).collect(Collectors.toList());
        Map<byte[], byte[]> map = db.multiGet(collect);
        for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
            System.out.println(new String(entry.getKey()) + " => " + new String(entry.getValue()));
        }
    }

    public static void main(String[] args) throws RocksDBException {
        //写入一些数据
        writeBatchData();
        //获取一些指定的key
        multiGet("priceIndex:10#1", "priceIndex:30#3", "priceIndex:50#5");
        System.out.println("------------");
        //全表扫描
        //假设不知道最后一条数据.可以根据字符串的设计指定一个最大的字符串
        scanRangeData("priceIndex:0", "priceIndex:99999999");
        System.out.println("------------");
        //范围扫描[10,40),因为是左闭又开原则所以结果是10-30
        scanRangeData("priceIndex:10", "priceIndex:40");
        System.out.println("------------");
        //删除范围[10,30]
        deleteRangeData("priceIndex:10", "priceIndex:40");
        scanRangeData("priceIndex:10", "priceIndex:99999999");
        //删除全部数据
        deleteRangeData("priceIndex:0", "priceIndex:99999999");
        scanRangeData("priceIndex:0", "priceIndex:99999999");
        System.out.println("------------");
        //关闭资源
        options.close();
        db.close();
    }
}
