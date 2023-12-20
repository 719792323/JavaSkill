package guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import common.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class _1_基本使用 {
    public static Map<String, User> userMap = new HashMap<String, User>() {
        {
            put("zjf", new User("zjf", 10));
            put("djq", new User("djq", 10));
        }
    };

    public static void main(String[] args) throws Exception {
        //创建LoadingCache
        LoadingCache<String, User> userCache = CacheBuilder.newBuilder().maximumSize(100) // 设置最大只能缓存100条数据
                .expireAfterAccess(30, TimeUnit.MINUTES) // 设置数据将在访问后的30分钟内过期
                .build(
                        //创建LoadingCache能够通过CacheLoader自发的加载缓存，当获取缓存中数据不存在时，会通过CacheLoader的load方法自动加载到缓存中
                        new CacheLoader<String, User>() {
                            @Override
                            public User load(String s) throws Exception {
                                return userMap.get(s);
                            }
                        });
        //主动存入
        userCache.put("zjf", userMap.get("zjf"));
        //获取
        System.out.println(userCache.get("zjf"));
        //触发load
        System.out.println(userCache.get("djq"));
    }
}
