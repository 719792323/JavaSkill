# 单机版

## docker

```shell
docker run \
-p 6379:6379 \
--name redis \
-v /home/sj/Redis/standalone/redis.conf:/etc/redis/redis.conf \
-v /home/sj/Redis/standalone/data:/data \
-d redis /etc/redis/redis.conf

# 注意需要在配置文件中进行如下配置
* protected-mode no
* bind 0.0.0.0
```

# redis主从

* 主节点的配置普通模式一样，注意修改protected-mode和bind
* 从节点在主节点的基础上配置 replicateof 主节点host ip参数。
* 如果master配置了密码和用户名，从节点要填写master的这些内容
* 启动成功后通过info replication命令查看是否配置成功

# redis哨兵

哨兵配置，切记一定要把主节点和从节点的密码设置为一样的，因为sentinel不能分别为master和slave设置不同的密码，因此master和slave的密码应该设置相同

```text
# sentinel.conf主要进行如下配置
#端口默认为26379。
port 26379
#关闭保护模式，可以外部访问。
protected-mode no
#设置为后台启动。
daemonize yes
#日志文件。
logfile ./sentinel.log
#指定服务器IP地址和端口，并且指定当有2台哨兵认为主机挂了，则对主机进行容灾切换。  注意:三台哨兵这里的ip配置均为主节点ip 和端口
sentinel monitor mymaster 192.168.10.6 6379 2
#当在Redis实例中开启了requirepass，这里就需要提供密码。
sentinel auth-pass mymaster psw66
#这里设置了主机多少秒无响应，则认为挂了。
sentinel down-after-milliseconds mymaster 3000
#主备切换时，最多有多少个slave同时对新的master进行同步，这里设置为默认的snetinel parallel-syncs mymaster 1
#故障转移的超时时间，这里设置为三分钟。
sentinel failover-timeout mymaster 180000
```
启动sentinel
redis-sentinel redisConfig/sentinel.conf
或者
./redis-server.sh sentinel.conf --sentinel
连接sentinel查看集群概况
```text
#连接客户端
[root@localhost bin]# redis-cli -p 26379
#查看哨兵信息
127.0.0.1:26379> info sentinel
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
#哨兵已经监听到主节点IP端口和运行状态，并且有2个从节点，3个哨兵。
master0:name=mymaster,status=ok,address=192.168.10.6:6379,slaves=2,sentinels=3
```

# redis-cluster