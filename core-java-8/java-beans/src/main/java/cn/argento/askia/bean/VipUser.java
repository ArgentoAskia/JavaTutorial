package cn.argento.askia.bean;

public class VipUser extends User{
    private VipLevel vipLevel;

    public VipUser(String name, Integer id, String address, VipLevel vipLevel) {
        super(name, id, address);
        this.vipLevel = vipLevel;
    }

    public VipUser(VipLevel vipLevel) {
        this.vipLevel = vipLevel;
    }

    public VipUser() {
    }

    public VipLevel getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(VipLevel vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("cn.argento.askia.bean.VipUser{" + super.toString());
        sb.append(", vipLevel=").append(vipLevel);
        sb.append('}');
        return sb.toString();
    }
}
