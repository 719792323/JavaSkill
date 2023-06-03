import org.rocksdb.*;

public class _8事务 {
    static {
        RocksDB.loadLibrary();
    }

    public static void main(final String[] args) {
        final String db_path = "RocksDB/data";

        Options options = new Options();
        options.setCreateIfMissing(true);

        TransactionDBOptions txn_db_options = new TransactionDBOptions();

        try (final TransactionDB txnDb = TransactionDB.open(options, txn_db_options, db_path)) {

            // Put
            byte[] key1 = "hello".getBytes();
            byte[] value1 = "world".getBytes();
            txnDb.put(key1, value1);

            // Transaction
            WriteOptions write_options = new WriteOptions();
            TransactionOptions txn_options = new TransactionOptions();
            txn_options.setSetSnapshot(true);

            Transaction txn = txnDb.beginTransaction(write_options, txn_options);
            try {
                byte[] key2 = "hello".getBytes();
                byte[] value2 = "kitty".getBytes();
                txn.put(key2, value2);
                // Commit
                txn.commit();
            } catch (RocksDBException e) {
                // Rollback
                txn.rollback();
            } finally {
                // Release the transaction
                txn.close();
            }
            // Get
            byte[] value = txnDb.get(key1);
            System.out.println(new String(value));

        } catch (RocksDBException e) {
            System.out.format("Caught the expected exception -- %s\n", e);
        }
    }
}
