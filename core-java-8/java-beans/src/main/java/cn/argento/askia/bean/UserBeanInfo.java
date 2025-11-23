package cn.argento.askia.bean;

import java.awt.*;
import java.beans.*;

public class UserBeanInfo extends SimpleBeanInfo {

    private BeanDescriptor beanDescriptor;
    private PropertyDescriptor[] propertyDescriptors;
    private EventSetDescriptor[] eventSetDescriptors;
    private MethodDescriptor[] methodDescriptors;
    private BeanInfo[] beanInfos;
    private Image image;

    public UserBeanInfo() {
        System.out.println("使用UserBeanInfo作为User类的BeanInfo分析类!!");
        System.out.println("UserBeanInfo所属类：cn.argento.askia.bean.User");
        beanDescriptor = new BeanDescriptor(User.class);
    }

    // Properties是以Getter方法来判断的而不是Field字段！
    private void initProperties(){
        System.out.println("初始化Properties...");


    }

    public UserBeanInfo(Customizer customizerBean){
        beanDescriptor = new BeanDescriptor(User.class, customizerBean.getClass());
    }

    @Override
    public BeanDescriptor getBeanDescriptor() {
        return beanDescriptor;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return super.getPropertyDescriptors();
    }

    @Override
    public int getDefaultPropertyIndex() {
        return super.getDefaultPropertyIndex();
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return super.getEventSetDescriptors();
    }

    @Override
    public int getDefaultEventIndex() {
        return super.getDefaultEventIndex();
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return super.getMethodDescriptors();
    }

    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        return super.getAdditionalBeanInfo();
    }

    @Override
    public Image getIcon(int iconKind) {
        return super.getIcon(iconKind);
    }

    @Override
    public Image loadImage(String resourceName) {
        return super.loadImage(resourceName);
    }
}
