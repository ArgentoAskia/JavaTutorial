package cn.argento.askia.chain.type2;


/**
 * ConcreteHandler（具体处理者）
 */
public class WenHandler3 extends Handler {

    @Override
    protected void handlerRequest(int days, Handler handler) {
        System.out.println("do WebHandler3...");
        handler.handlerRequest(days);
        System.out.println("redo WebHandler3...");
    }
}
