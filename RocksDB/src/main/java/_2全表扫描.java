import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 全表扫描数据
 */
public class _2全表扫描 {
    static {
        RocksDB.loadLibrary();
    }

    public static void insertData(RocksDB db, List<byte[]> keys, List<byte[]> values) throws RocksDBException {
        for (int i = 0; i < keys.size(); i++) {
            db.put(keys.get(i), values.get(i));
        }
    }
    public static void scanData(RocksDB db){
        RocksIterator iterator = db.newIterator();
        for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
            byte[] key = iterator.key();
            byte[] value = iterator.value();
            System.out.println(new String(key) + " : " + new String(value));
        }
    }

    public static void main(String[] args) {
        try (final Options options = new Options().setCreateIfMissing(false)) {//数据库文件目录不存在则创建
            try (final RocksDB db = RocksDB.open(options, "D:/Code/Java/JavaSkill/RocksDB/data")) {
                List<byte[]> keys = Arrays.asList("key1", "key2", "key3").stream().map(x -> x.getBytes()).collect(Collectors.toList());
                List<byte[]> values = Arrays.asList("value1", "value2", "value3").stream().map(x -> x.getBytes()).collect(Collectors.toList());
                insertData(db, keys, values);
                scanData(db);
            } catch (RocksDBException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}