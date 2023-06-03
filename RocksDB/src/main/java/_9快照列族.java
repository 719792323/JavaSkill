import org.rocksdb.*;

import java.util.ArrayList;
import java.util.List;

public class _9快照列族 {

    static RocksDB db;
    static DBOptions dbOptions = new DBOptions();
    static ColumnFamilyOptions testOpts = new ColumnFamilyOptions();
    static List<ColumnFamilyHandle> cfHandles = new ArrayList<>();
    static List<ColumnFamilyDescriptor> cfDescriptors = new ArrayList<>();

    static Snapshot snapshot;

    static {
        dbOptions.setCreateIfMissing(true);
        dbOptions.setCreateMissingColumnFamilies(true);
        cfDescriptors.add(new ColumnFamilyDescriptor("test".getBytes(), testOpts));
        cfDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY));
        try {
            db = RocksDB.open(dbOptions, "RocksDB/data", cfDescriptors, cfHandles);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RocksDBException {
        db.put(cfHandles.get(0), "hello".getBytes(), "world".getBytes());
        System.out.println(new String(db.get(cfHandles.get(0), "hello".getBytes())));
        //启用快照后删除，还能读到元素
        snapshot = db.getSnapshot();
        db.delete(cfHandles.get(0), "hello".getBytes());
        System.out.println(db.get(cfHandles.get(0), "hello".getBytes()) == null);//null
        ReadOptions readOptions = new ReadOptions();
        readOptions.setSnapshot(snapshot);
        System.out.println(new String(db.get(cfHandles.get(0), readOptions, "hello".getBytes())));

        //删除列族后，需要保存旧handler才能读到快照数据
        ColumnFamilyHandle oldHandler = cfHandles.get(0);
        db.dropColumnFamily(cfHandles.get(0));
        cfHandles.get(0).close();
        cfHandles.set(0, db.createColumnFamily(new ColumnFamilyDescriptor("test".getBytes(), testOpts)));
        //旧handler
        System.out.println(new String(db.get(oldHandler, readOptions, "hello".getBytes())));
        //新handler
        System.out.println(db.get(cfHandles.get(0), readOptions, "hello".getBytes()));
        oldHandler.close();
        snapshot.close();
        db.releaseSnapshot(snapshot);
        db.close();
    }
}
