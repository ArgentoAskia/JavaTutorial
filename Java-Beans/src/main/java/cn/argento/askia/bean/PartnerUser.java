package cn.argento.askia.bean;

import java.util.LinkedList;

public class PartnerUser extends VipUser{
    private LinkedList<String> partnerNames;
    private String[] references;

    public PartnerUser() {
        references = new String[100];
    }

    public PartnerUser(String name, Integer id, String address, VipLevel vipLevel, LinkedList<String> partnerNames, String[] references) {
        super(name, id, address, vipLevel);
        this.partnerNames = partnerNames;
        this.references = references;
    }

    public PartnerUser(VipLevel vipLevel, LinkedList<String> partnerNames, String[] references) {
        super(vipLevel);
        this.partnerNames = partnerNames;
        this.references = references;
    }

    public PartnerUser(LinkedList<String> partnerNames, String[] references) {
        this.partnerNames = partnerNames;
        this.references = references;
    }


    public LinkedList<String> getPartnerNames() {
        return partnerNames;
    }

    public void setPartnerNames(LinkedList<String> partnerNames) {
        this.partnerNames = partnerNames;
    }

    public String getPartnerNames(int index) {
        return partnerNames.get(index);
    }

    public void setPartnerNames(int index, String s) {
        if (index == -1){
            partnerNames.add(s);
        }else{
            partnerNames.set(index, s);
        }
    }

    public String[] getReferences() {
        return references;
    }

    public void setReferences(String[] references) {
        this.references = references;
    }

    public void setReferences(int index, String s){
        references[index] = s;
    }
    public String getReferences(int index){
        return references[index];
    }

}
