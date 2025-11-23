package cn.argento.askia.beans;

import java.beans.*;

public class UserBeanInfo extends SimpleBeanInfo {

    private BeanDescriptor beanDescriptor;
    private MethodDescriptor[] methodDescriptors;
    private EventSetDescriptor[] eventSetDescriptors;
    private PropertyDescriptor[] propertyDescriptors;
    private IndexedPropertyDescriptor[] indexedPropertyDescriptors;


    public UserBeanInfo() {
        super();

    }

    private void analyzeBeanDescriptor(){

    }

    private void analyzeMethodDescriptors(){

    }

    private void analyzeEventSetDescriptors(){

    }

    private void analyzePropertyDescriptors(){
        
    }

    @Override
    public BeanDescriptor getBeanDescriptor() {
        return super.getBeanDescriptor();
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
