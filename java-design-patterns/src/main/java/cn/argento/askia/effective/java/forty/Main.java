package cn.argento.askia.effective.java.forty;

/**
 * Effective Java中建议：compareTo()的实现要考虑3必须1建议：
 * 1. 自反性：x.compareTo(x) == 0, x非空
 *      延申(二元)：对于任何的非空的x和y, x.compareTo(y)的结果和y.compareTo(x)的结果是相反数
 * 2. 对称性：当且仅当x.compareTo(y) == 0时, y.compareTo(x) == 0
 *      延申(二元)：在确保x.compareTo(y) == 0的情况下，对于所有z都满足，x.compareTo(z) == y.compareTo(z)
 * 3. 基于对称性的二元延申，出现传递性：当x.compareTo(y) > 0, y.compareTo(z) > 0时, x.compareTo(z) > 0
 */
public class Main {
    public static void main(String[] args) {
        // 那么StudentError问题出在哪里？
        // 问题出在子类比较时，父类无法看到子类属性的问题，而子类在某些情况下又需要父类看到它：也就是对称性无法得到保障
        // 自反性测试：
        Person x = new Person("John Smith", 18);
        StudentError y = new StudentError("Michael Jason", 18, 3);
        int xVsY = x.compareTo(y);
        int yVsX = y.compareTo(x);
        System.out.println("x VS y = " + xVsY + ", y VS x = " + yVsX);
        if (xVsY == yVsX) {
            System.out.println("符合自反性");
        }
        else{
            System.out.println("不符合自反性");
        }

        // 引入第三个StudentError
        // 此时就会发现 x == y时 x == z, y != z
        StudentError z = new StudentError("Alex Jason", 18, 4);
        int xVsZ = x.compareTo(z);
        int yVsZ = y.compareTo(z);
        System.out.println("x VS y = " + xVsY + ", y VS z = " + yVsZ + ", x VS z = " + xVsZ);
        if (xVsY == 0){
            if (yVsZ == xVsY && xVsY == xVsZ){
                System.out.println("对称性通过");
            }
            else {
                System.out.println("对称性不通过");
            }
        }
        else{
            System.out.println("不符合自反性，因此无需验证对称性");
        }

        // 解决方法，建立独立的值类，然后提供view方法, 将对象比较拉回同一类型对象，然后使用聚合而非继承
        StudentRight y2 = new StudentRight("Michael Jason", 18, 3);
        StudentRight z2 = new StudentRight("Alex Jason", 18, 4);
        // 拉回同一类型
        Person personY2 = y2.asPerson();
        Person personZ2 = z2.asPerson();
        int i = x.compareTo(personZ2);
        int j = y.compareTo(personZ2);
        System.out.println("对称性测试结果：i == j == 0? " + ((i == j) && (i == xVsY) && (j == xVsY)));

    }
}
