setCreateIfMissing(boolean)：当数据存储不存在时，创建新的数据存储。
setCreateMissingColumnFamilies(boolean)：创建缺少的列族。
setMaxOpenFiles(int)：最大文件打开数。
setUseFsync(boolean)：是否同步数据到磁盘。
setAllowMmapReads(boolean)：是否允许使用内存映射文件来读取数据。
setAllowMmapWrites(boolean)：是否允许使用内存映射文件来写入数据。
setIncreaseParallelism(int)：增加RocksDB内部的线程并行度。
setWriteBufferSize(long)：设置memtable的大小。
setMaxWriteBufferNumber(int)：设置最大memtable数量。
setMaxBackgroundCompactions(int)：设置最大后台压缩任务数量。
setLevelCompactionDynamicLevelBytes(boolean)：动态地调整每个层级的大小。
setCompactionStyle(CompactionStyle)：设置压缩策略，支持level-based和universal两种压缩方式。
setTargetFileSizeBase(long)：设置SST文件的目标大小。
setUseDirectIoForFlushAndCompaction(boolean)：是否使用Direct I/O来刷写和压缩数据。
setCompactionPriority(CompactionPriority)：设置压缩任务的优先级。