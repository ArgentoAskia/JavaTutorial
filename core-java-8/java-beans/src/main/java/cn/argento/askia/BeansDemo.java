package cn.argento.askia;

import cn.argento.askia.bean.PartnerUser;
import cn.argento.askia.bean.User;
import cn.argento.askia.bean.VipUser;
import com.sun.beans.infos.ComponentBeanInfo;

import javax.swing.*;
import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BeansDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IntrospectionException {
        String userClassName = "cn.argento.askia.bean.User";
        String vipUserClassName = "cn.argento.askia.bean.VipUser";
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        final Object userInstance = Beans.instantiate(systemClassLoader, userClassName);
        final Object vipUserInstance = Beans.instantiate(systemClassLoader, vipUserClassName);
        System.out.println(userInstance);
        System.out.println(vipUserInstance);

        // 判断是否是类或及其子类的实例
        final boolean instanceOf = Beans.isInstanceOf(vipUserInstance, VipUser.class);
        final boolean instanceOf1 = Beans.isInstanceOf(userInstance, User.class);
        System.out.println("vipUserInstance 是否是 VipUser类的实例：" + instanceOf);
        System.out.println("vipUserInstance 是否是 User类的实例：" + instanceOf1);

        final String[] beanInfoSearchPath = Introspector.getBeanInfoSearchPath();
//        String[] path = new String[]{beanInfoSearchPath[0], "cn.argento.askia.bean"};
//        Introspector.setBeanInfoSearchPath(path);
        final BeanInfo beanInfo = Introspector.getBeanInfo(PartnerUser.class, null, Introspector.IGNORE_IMMEDIATE_BEANINFO);
        System.out.println(beanInfo);


    }
}
