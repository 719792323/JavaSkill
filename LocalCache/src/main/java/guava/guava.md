# 资料
1. [资料1](https://www.cnblogs.com/MrRightZhao/p/11800776.html)
2. [资料2](https://www.cnblogs.com/MrRightZhao/p/11800776.html)
# guava cache分类
guava cache 提供了2种类型：
1. Cache：创建1个缓存.
2. LoadingCache：它能够通过CacheLoader自发的加载缓存，当获取缓存中数据不存在时，会通过CacheLoader的load方法自动加载到缓存中(后面会进步说明)