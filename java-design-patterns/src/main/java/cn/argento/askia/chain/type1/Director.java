package cn.argento.askia.chain.type1;

/**
 * ConcreteHandler
 */
public class Director extends Handler{

    @Override
    public void handlerRequest(int days) {
        if (days <= 3){
            System.out.println("主管批准了" + days + "天的请假申请");
        }
        else if (successor != null){
            successor.handlerRequest(days);
        }
    }
}
