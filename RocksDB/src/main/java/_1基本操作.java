import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.Random;

/**
 * 数据库配置配置与连接
 * 数据库的插入、删除、查询
 */
public class _1基本操作 {
    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) {
        //db的配置类Options
        try (final Options options = new Options().setCreateIfMissing(true)) {//数据库文件目录不存在则创建
            //db的操作类
            try (final RocksDB db = RocksDB.open(options, "D:/Code/Java/JavaSkill/RocksDB/data")) {
                byte[] key = "hello".getBytes();
                //查找key
                byte[] value = db.get(key);
                if (value == null) {
                    System.out.println(String.format("key:%s is missing", new String(key)));
                    //存入key
                    db.put(key, "world".getBytes());
                } else {
                    System.out.println(String.format("key:%s,value:%s", new String(key), new String(value)));
                    Random random = new Random();
                    if (random.nextInt() % 2 == 0) {
                        //删除key
                        db.delete(key);
                    }
                }
            } catch (RocksDBException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}