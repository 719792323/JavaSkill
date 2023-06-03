import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

public class _3EqualsAndHashCode {
    public static void main(String[] args) {
        User2 user1 = new User2(1, "xx","male");
        User2 user2 = new User2(1, "xx","female");
        System.out.println(user1 == user2);
        System.out.println(user1.equals(user2));
        System.out.println(user1.hashCode() == user2.hashCode());
    }
}

/**
 * 重点关注：
 * 1.比较是基于值比较，对于属性是对象的情况调用其equals方法进行比较
 * 2.hashcode的值生成也是基于值生成
 * 3.如果某些变量不想加以判断或者生成hashcode，可以通过exclude进行排除
 *
 */
@EqualsAndHashCode(exclude = "sex")
@AllArgsConstructor
class User2 {
    int id;
    String name;
    String sex;
    // 生成的比较代码
    /**
     *     public boolean equals(Object o) {
     *         if (o == this) {
     *             return true;
     *         } else if (!(o instanceof User2)) {
     *             return false;
     *         } else {
     *             User2 other = (User2)o;
     *             if (!other.canEqual(this)) {
     *                 return false;
     *             } else if (this.id != other.id) {
     *                 return false;
     *             } else {
     *                 Object this$name = this.name;
     *                 Object other$name = other.name;
     *                 if (this$name == null) {
     *                     if (other$name != null) {
     *                         return false;
     *                     }
     *                 } else if (!this$name.equals(other$name)) {
     *                     return false;
     *                 }
     *                 return true;
     *             }
     *         }
     *     }
     *
     *     protected boolean canEqual(Object other) {
     *         return other instanceof User2;
     *     }
     *
     *     public int hashCode() {
     *         int PRIME = true;
     *         int result = 1;
     *         result = result * 59 + this.id;
     *         Object $name = this.name;
     *         result = result * 59 + ($name == null ? 43 : $name.hashCode());
     *         return result;
     *     }
     */
}