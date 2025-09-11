package cn.argento.askia.chain.type1;

public class Manager extends Handler{

    @Override
    public void handlerRequest(int days) {
        if (days <= 7){
            System.out.println("经理批准了" + days + "天的请假申请");
        }
        else if (successor != null){
            successor.handlerRequest(days);
        }
    }
}
