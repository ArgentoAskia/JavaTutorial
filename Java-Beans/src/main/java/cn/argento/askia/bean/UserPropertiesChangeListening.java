package cn.argento.askia.bean;

import java.beans.*;
import java.lang.reflect.Field;

public class UserPropertiesChangeListening extends User {
    private PropertyChangeSupport propertyChangeSupport;

    public UserPropertiesChangeListening(){
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public UserPropertiesChangeListening(String name, Integer id, String address) {
        super(name, id, address);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    // 添加全局的属性监听（即所有属性的更改都会触发这个监听器！）
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    // 添加针对某个属性的监听器
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public boolean hasListeners(String propertyName){
        return this.propertyChangeSupport.hasListeners(propertyName);
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName){
        return this.propertyChangeSupport.getPropertyChangeListeners(propertyName);
    }
    public PropertyChangeListener[] getPropertyChangeListeners(){
        return this.propertyChangeSupport.getPropertyChangeListeners();
    }

    // 通用
    protected void setPropertyAndFirePropertyChange(String property, Object newValue){
        try {
            final Field field = getClass().getDeclaredField(property);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            final Object oldValue = field.get(this);
            field.set(this, newValue);
            this.propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setId(Integer id) {
        Integer oldValue = getId();
        // beforePropertyChange
        super.setId(id);
        this.propertyChangeSupport.firePropertyChange("id", oldValue, id);
    }

    @Override
    public void setAddress(String address) {
        String oldValue = getAddress();
        // beforePropertyChange
        super.setAddress(address);
        this.propertyChangeSupport.firePropertyChange("address", oldValue, address);
    }

    @Override
    public void setName(String name) {
        String oldValue = getName();
        // beforePropertyChange
        super.setName(name);
        this.propertyChangeSupport.firePropertyChange("name", oldValue, name);
    }

    @Override
    public UserPropertiesChangeListening getRandomUser() {
        final User randomUser = super.getRandomUser();
        return new UserPropertiesChangeListening(randomUser.getName(), randomUser.getId(), randomUser.getAddress());
    }
}


class Main {
    public static void main(String[] args) {
        UserPropertiesChangeListening userPropertiesChangeListening
                 = new UserPropertiesChangeListening();
        final GlobalPropertyChangeListener globalPropertyChangeListener = new GlobalPropertyChangeListener();
//        final GlobalVetoableChangeListener globalVetoableChangeListener = new GlobalVetoableChangeListener();
        // 添加全局监听器！
        userPropertiesChangeListening.addPropertyChangeListener(globalPropertyChangeListener);

        // 添加单独属性监听器！
        userPropertiesChangeListening.addPropertyChangeListener(AttributePropertyChangeListener.nameAttributePropertyChangeListener);
        userPropertiesChangeListening.addPropertyChangeListener(AttributePropertyChangeListener.idAttributePropertyChangeListener);
        userPropertiesChangeListening.addPropertyChangeListener(AttributePropertyChangeListener.addressAttributePropertyChangeListener);

        // 触发监听！
        userPropertiesChangeListening.setId(1);
        userPropertiesChangeListening.setName("Askia");
        userPropertiesChangeListening.setAddress("Askia");
    }
}
class GlobalPropertyChangeListener implements PropertyChangeListener{

    private final String statement = "全局属性监听器(PropertyChangeListener)";

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(statement);
        final String propertyName = evt.getPropertyName();
        final Object newValue = evt.getNewValue();
        final Object oldValue = evt.getOldValue();
        final Object source = evt.getSource();
        final Object propagationId = evt.getPropagationId();
        System.out.println("propertyName=" + propertyName);
        System.out.println("newValue=" + newValue);
        System.out.println("oldValue=" + oldValue);
        System.out.println("source=" + source);
        System.out.println("propagationId=" + propagationId);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}

class GlobalVetoableChangeListener implements VetoableChangeListener{

    private final String statement = "全局属性监听器(VetoableChangeListener)";

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(statement);
        final String propertyName = evt.getPropertyName();
        final Object newValue = evt.getNewValue();
        final Object oldValue = evt.getOldValue();
        final Object source = evt.getSource();
        final Object propagationId = evt.getPropagationId();
        System.out.println("propertyName=" + propertyName);
        System.out.println("newValue=" + newValue);
        System.out.println("oldValue=" + oldValue);
        System.out.println("source=" + source);
        System.out.println("propagationId=" + propagationId);
        try{
            throw new PropertyVetoException("抛出PropertyVetoException异常", evt);
        }catch (PropertyVetoException e){
            e.printStackTrace();
            System.err.println(e.getPropertyChangeEvent());
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}


class AttributePropertyChangeListener extends PropertyChangeListenerProxy{
    public static final AttributePropertyChangeListener idAttributePropertyChangeListener
            = new AttributePropertyChangeListener("id", event ->{
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println("id属性监听器");
        final String propertyName = event.getPropertyName();
        final Object newValue = event.getNewValue();
        final Object oldValue = event.getOldValue();
        final Object source = event.getSource();
        final Object propagationId = event.getPropagationId();
        System.out.println("propertyName=" + propertyName);
        System.out.println("newValue=" + newValue);
        System.out.println("oldValue=" + oldValue);
        System.out.println("source=" + source);
        System.out.println("propagationId=" + propagationId);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    });

    public static final AttributePropertyChangeListener nameAttributePropertyChangeListener
            = new AttributePropertyChangeListener("name", event ->{
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println("name属性监听器");
        final String propertyName = event.getPropertyName();
        final Object newValue = event.getNewValue();
        final Object oldValue = event.getOldValue();
        final Object source = event.getSource();
        final Object propagationId = event.getPropagationId();
        System.out.println("propertyName=" + propertyName);
        System.out.println("newValue=" + newValue);
        System.out.println("oldValue=" + oldValue);
        System.out.println("source=" + source);
        System.out.println("propagationId=" + propagationId);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    });

    public static final AttributePropertyChangeListener addressAttributePropertyChangeListener
            = new AttributePropertyChangeListener("address", event ->{
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println("address属性监听器");
        final String propertyName = event.getPropertyName();
        final Object newValue = event.getNewValue();
        final Object oldValue = event.getOldValue();
        final Object source = event.getSource();
        final Object propagationId = event.getPropagationId();
        System.out.println("propertyName=" + propertyName);
        System.out.println("newValue=" + newValue);
        System.out.println("oldValue=" + oldValue);
        System.out.println("source=" + source);
        System.out.println("propagationId=" + propagationId);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    });

    /**
     * Constructor which binds the {@code PropertyChangeListener}
     * to a specific property.
     *
     * @param propertyName the name of the property to listen on
     * @param listener     the listener object
     */
    public AttributePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        super(propertyName, listener);
    }
}


