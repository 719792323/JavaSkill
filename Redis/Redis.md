# 使用缓存需要解决问题

* 缓存与数据库不一致

* 缓存雪崩、缓存穿透、缓存击穿

* 缓存并发竞争


# Redis的数据结构

* 数据类型有哪些？适合用在什么场景

# Redis支持的集群模式有哪些、怎么搭建

* redis主从模式
* redis哨兵模式
* redis cluster模式
  * [三种集群模式介绍、cluster搭建参考](https://www.modb.pro/db/102339)
  * [cluster模式详解](https://zhuanlan.zhihu.com/p/347125538)

# Redis线程模型(进阶47p)

* 模型大致结构是什么
* 为什么单线程模型可以效率高

* 6.0后为什么引入多线程模型(哪里改成了多线程模型)

# Redis过期策略

* 定期删除策略
* 惰性删除策略
* 内存淘汰机制
* 过期和淘汰会有什么问题

# Redis高并发和高可用

* Redis主从架构（复制模式、过期key处理）

* Redis哨兵模式

* Redis集群模式(Redis 的 key 是如何寻址的？分布式寻址都有哪些算法？)

  * 一致性hash算法

  * hash slot算法（hashslot是怎么分配的？）

    [hash slot分配示例](https://pythonjishu.com/ehbkmzkiqjbynfo/)

# Redis持久化

* 持久化有几种方式
* 各种持久化方式的优缺点
* 持久化机制底层原理

# Redis常见问题与解决方案

* 缓存雪崩
* 缓存穿透
* 缓存击穿
* 缓存不一致问题

# Redis CAS

* 基于zk的分布式锁方案