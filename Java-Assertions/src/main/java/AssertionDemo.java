import cn.hutool.core.lang.Assert;

import java.util.Random;

// 默认情况下assert被禁用，需要加入-ea参数！
public class AssertionDemo {
    public static void main(String[] args) {
        Random random = new Random();
        int a = random.nextInt();
        System.out.println(a);
        if (a % 3 == 0){

        } else if (a % 3 == 1){

        } else {
            assert a % 3 == 2 : "a的值是" + a;
        }

    }
}
