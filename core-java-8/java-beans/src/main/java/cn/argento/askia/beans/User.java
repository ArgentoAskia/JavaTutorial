package cn.argento.askia.beans;

import java.beans.ExceptionListener;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;

public class User {
    
    // properties
    private String name;
    private int id;
    private String address;
    private InetAddress ip;
    private LocalDateTime registerTime;
    private List<String> partnerNames;
    private String[] references;
    private Level level;

    public User() {
        name = "default name";
        id = -1;
        address = "known address";
        ip = Inet4Address.getLoopbackAddress();
        registerTime = LocalDateTime.now();
        partnerNames = Collections.emptyList();
        references = new String[0];
        level = Level.NONE;
    }

    public User(String name,
                int id,
                String address,
                Inet4Address ip,
                LocalDateTime registerTime,
                LinkedList<String> partnerNames,
                String[] references, Level level) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.ip = ip;
        this.registerTime = registerTime;
        this.partnerNames = partnerNames;
        this.references = references;
        this.level = level;
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public List<String> getPartnerNames() {
        return partnerNames;
    }

    public String getPartnerNames(int index){
        return partnerNames.get(index);
    }

    public void setPartnerNames(List<String> partnerNames) {
        this.partnerNames = partnerNames;
    }

    public void setPartnerNames(int index, String parterName){
        if (index == -1){
            partnerNames.add(parterName);
        }else{
            partnerNames.set(index, parterName);
        }
    }

    public String[] getReferences() {
        return references;
    }

    public String getReferences(int index){
        return references[index];
    }

    public void setReferences(String[] references) {
        this.references = references;
    }

    public void setReferences(int index, String reference){
        this.references[index] = reference;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }


    // simple method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(address, user.address) &&
                Objects.equals(ip, user.ip) &&
                Objects.equals(registerTime, user.registerTime) &&
                Objects.equals(partnerNames, user.partnerNames) &&
                Arrays.equals(references, user.references) &&
                level == user.level;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, id, address, ip, registerTime, partnerNames, level);
        result = 31 * result + Arrays.hashCode(references);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("cn.argento.askia.beans.User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append(", address='").append(address).append('\'');
        sb.append(", ip=").append(ip);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", partnerNames=").append(partnerNames);
        sb.append(", references=").append(references == null ? "null" : Arrays.asList(references).toString());
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }


    // event listener add remove method

    public void addPropertyChangeListener(PropertyChangeListener listener){

    }

    public void addPropertyChangeListener(String propertyName ,PropertyChangeListener listener){

    }

    public PropertyChangeListener[] getPropertyChangeListeners(){
        return new PropertyChangeListener[0];
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName){
        return getPropertyChangeListeners();
    }


    public void removePropertyChangeListener(PropertyChangeListener listener){

    }

    public void removePropertyChangeListener(
            String propertyName,
            PropertyChangeListener listener){

    }

    public void addVetoableChangeListener(VetoableChangeListener listener){

    }

    public void removeVetoableChangeListener(VetoableChangeListener listener){

    }

    public VetoableChangeListener[] getVetoableChangeListeners(){
        return new VetoableChangeListener[0];
    }

    // 单播事件！
    public void addExceptionListener(ExceptionListener exceptionListener) throws TooManyListenersException{

    }

    public void removeExceptionListener(ExceptionListener exceptionListener){

    }


}
