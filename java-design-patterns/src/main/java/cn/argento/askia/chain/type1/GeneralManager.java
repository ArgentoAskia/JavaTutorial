package cn.argento.askia.chain.type1;


/**
 * ConcreteHandler（具体处理者）
 */
public class GeneralManager extends Handler{

    @Override
    public void handlerRequest(int days) {
        System.out.println("总监批准了" + days + "天的请假申请。");
    }
}
