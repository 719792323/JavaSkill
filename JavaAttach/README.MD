https://www.cnblogs.com/qlqwjy/p/15639710.html

```java
public interface Instrumentation {
/**
     * 加入一个转换器Transformer，之后的所有的类加载都会被Transformer拦截。
     * ClassFileTransformer类是一个接口，使用时需要实现它，该类只有一个方法，该方法传递类的信息，返回值是转换后的类的字节码文件。
     */
 	void addTransformer(ClassFileTransformer transformer, boolean canRetransform);    

 /**
     * 对JVM已经加载的类重新触发类加载。使用的就是上面注册的Transformer。
     * 该方法可以修改方法体、常量池和属性值，但不能新增、删除、重命名属性或方法，也不能修改方法的签名
     */
    void retransformClasses(Class<?>... classes) throws UnmodifiableClassException;
    
/**
   *此方法用于替换类的定义，而不引用现有的类文件字节，就像从源代码重新编译以进行修复和继续调试时所做的那样。
   *在要转换现有类文件字节的地方(例如在字节码插装中)，应该使用retransformClasses。
   *该方法可以修改方法体、常量池和属性值，但不能新增、删除、重命名属性或方法，也不能修改方法的签名
   */
	void redefineClasses(ClassDefinition... definitions)throws  ClassNotFoundException, UnmodifiableClassException;

    /**
     * 获取一个对象的大小
     */
    long getObjectSize(Object objectToSize);
    
    /**
     * 将一个jar加入到bootstrap classloader的 classpath里
     */
    void appendToBootstrapClassLoaderSearch(JarFile jarfile);
    
    /**
     * 获取当前被JVM加载的所有类对象
     */
    Class[] getAllLoadedClasses();
}
```