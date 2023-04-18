import org.rocksdb.*;

import java.util.ArrayList;
import java.util.List;
/**
 * ColumnFamily的作用：
 * ColumnFamily是RocksDB提供的一种逻辑分区的方式，它可以让您对不同的数据集使用不同的设置和操作。一些常见的使用场景有：
 * 使用不同的压缩算法，合并操作，比较器，压缩过滤器等来优化不同的数据集的性能和空间占用。
 * 快速删除一个ColumnFamily来清除其数据，而不影响其他ColumnFamily。
 * 使用一个ColumnFamily来存储元数据，另一个ColumnFamily来存储实际的数据。
 * 使用多个ColumnFamily来实现多版本控制，时间序列分析等功能。
 */

/**
 * ColumnFamily与Table比较：
 * RocksDB的ColumnFamily和MySQL的表有一些相似之处，但也有一些区别。
 * 相似之处有：
 * 都可以用来存储不同的数据集，并对其进行增删改查操作。
 * 都可以用来实现一对多或多对多的关系，比如一个ColumnFamily或表可以存储用户信息，另一个ColumnFamily或表可以存储用户的订单信息。
 * 都可以用来定义不同的索引，比如主键索引，二级索引等。
 * 区别有：
 * RocksDB的ColumnFamily是基于键值对的存储，而MySQL的表是基于行列的存储。这意味着RocksDB的ColumnFamily可以存储任意类型和长度的键值对，而MySQL的表需要预先定义每一列的类型和长度。
 * RocksDB的ColumnFamily可以使用不同的设置和操作来优化不同的数据集，比如压缩算法，合并操作，比较器，压缩过滤器等。而MySQL的表通常使用相同的设置和操作来处理所有的数据集。
 * RocksDB的ColumnFamily可以快速删除一个ColumnFamily来清除其数据，而不影响其他ColumnFamily。而MySQL的表删除一个表需要花费更多的时间和资源，并可能影响其他表。
 */
public class _5表操作 {
    public static RocksDB db;
    // 创建一个DBOptions对象
    public static DBOptions dbOptions = new DBOptions();
    // 创建一个ColumnFamilyOptions对象
    public static ColumnFamilyOptions cfOpts = new ColumnFamilyOptions();
    // 创建一个ColumnFamilyDescriptor对象，并指定ColumnFamily的名称和选项
    // ColumnFamilyHandle的列表,创建数据库后RocksDB会向里写入Handler，需要靠这些handler操作不同的CF
    public static List<ColumnFamilyHandle> cfHandles = new ArrayList<>();
    public static List<ColumnFamilyDescriptor> cfDescriptors = new ArrayList<>();

    static {
        RocksDB.loadLibrary();
        dbOptions.setCreateIfMissing(true);
        dbOptions.setCreateMissingColumnFamilies(true);
        //设置压缩算法
//        cfOpts.setCompressionType(CompressionType.SNAPPY_COMPRESSION);
        //配置加载的columFamily
        cfDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY));//加载default
        cfDescriptors.add(new ColumnFamilyDescriptor("my_cf".getBytes(), cfOpts));//自定义的columnFamily
        try {
            db = RocksDB.open(dbOptions, "RocksDB/data", cfDescriptors, cfHandles);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RocksDBException {
        //操作default
        RocksIterator iterator = db.newIterator(cfHandles.get(0));
        for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
            byte[] key = iterator.key();
            byte[] value = iterator.value();
            System.out.println(new String(key) + " : " + new String(value));
        }
        System.out.println("---------------");
        //操作my_cf
        db.put(cfHandles.get(1), "key2".getBytes(), "value2".getBytes());
        System.out.println("key2" + " : " + new String(db.get(cfHandles.get(1), "key2".getBytes())));
        System.out.println("---------------");
        //删除my_cf，该cf下的数据都会被删除，是清除数据最快的方式
        db.dropColumnFamily(cfHandles.get(1));
        cfHandles.get(1).close();
        //重新创建该cf
        cfHandles.set(1,db.createColumnFamily(new ColumnFamilyDescriptor("my_cf".getBytes(), cfOpts)));
        //遍历该cf，看数据是否还存在
        iterator = db.newIterator(cfHandles.get(1));
        for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
            byte[] key = iterator.key();
            byte[] value = iterator.value();
            System.out.println(new String(key) + " : " + new String(value));
        }
        // 关闭数据库，并释放所有的句柄
        db.close();
        for (ColumnFamilyHandle handle : cfHandles) {
            handle.close();
        }
        dbOptions.close();
        cfOpts.close();
    }
}
