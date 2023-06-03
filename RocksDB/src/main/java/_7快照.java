import org.rocksdb.*;

/**
 * RocksDB的快照是一个用于获取RocksDB实例在某个时间点的数据视图的对象。
 * 快照的用途是可以保证数据的一致性和隔离性，即使在其他线程或进程对RocksDB进行修改。
 * RocksDB是线程安全的，但是它不保证读取操作的一致性和隔离性。
 * 也就是说，如果在一个线程中读取一个键的值，而在另一个线程中修改了这个键的值，可能会读到旧的值或新的值，或者两个值的混合。
 * 这样可能会导致数据的不一致或错误。如果你想保证读取操作的一致性和隔离性，可以使用快照。
 * 快照可以让你读取到RocksDB在某个时间点的数据视图，不受其他线程或进程的影响。
 * RocksDB的snapshot也是一个只读的数据视图。对snapshot进行修改不会生效，也不会影响原来的RocksDB。
 * <p>
 * ConcurrentHashMap保证了读取操作的一致性，但是不保证隔离性。
 * 也就是说，如果在一个线程中读取一个键的值，而在另一个线程中修改了这个键的值，可能会读到旧的值或新的值，但是不会读到两个值的混合。
 * 这样可以保证数据的一致性，但是不保证隔离性，因为你能会读到其他线程对ConcurrentHashMap的修改。
 * 如果想保证读取操作的隔离性，你可以使用ConcurrentHashMap的snapshot方法。
 * snapshot方法可以让你读取到ConcurrentHashMap在某个时间点的数据视图，不受其他线程的影响。
 * 注意：ConcurrentHashMap的snapshot修改不会生效。
 */
public class _7快照 {
    public static Options options = new Options().setCreateIfMissing(true);
    public static RocksDB db;

    static {
        RocksDB.loadLibrary();
        try {
            db = RocksDB.open(options, "RocksDB/data");
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RocksDBException, InterruptedException {
        System.out.println("---不使用快照---");
        //初始值是hello
        db.put("snapshot".getBytes(), "hello".getBytes());
        //不使用快照的情况
        //读取线程
        Thread a = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("不使用快照 a：" + new String(db.get("snapshot".getBytes())));
            } catch (RocksDBException e) {
                throw new RuntimeException(e);
            }
        });
        //修改线程
        Thread b = new Thread(() -> {
            try {
                db.put("snapshot".getBytes(), "world".getBytes());
            } catch (RocksDBException e) {
                throw new RuntimeException(e);
            }
        });
        a.start();
        b.start();
        Thread.sleep(3000);
        System.out.println("---使用快照---");
        db.put("snapshot".getBytes(), "hello".getBytes());
        // 获取一个快照对象
        Snapshot snapshot = db.getSnapshot();
        // 创建一个读取选项对象，使用快照
        ReadOptions readOptions = new ReadOptions().setSnapshot(snapshot);
        a = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("使用快照 a：" + new String(db.get(readOptions, "snapshot".getBytes())));
            } catch (RocksDBException e) {
                throw new RuntimeException(e);
            }

        });
        b = new Thread(() -> {
            try {
                db.put("snapshot".getBytes(), "world".getBytes());
            } catch (RocksDBException e) {
                throw new RuntimeException(e);
            }
        });
        a.start();
        b.start();
        Thread.sleep(3000);
        // 释放快照对象
        db.releaseSnapshot(snapshot);
        // 关闭RocksDB实例
        db.close();
    }
}
