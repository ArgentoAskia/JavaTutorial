package cn.argento.askia.chain.type2;

/**
 * ConcreteHandler
 */
public class WebHandler1 extends Handler {

    @Override
    protected void handlerRequest(int days, Handler handler) {
        System.out.println("do WebHandler1...");
        handler.handlerRequest(days);
        System.out.println("redo WebHandler1...");
    }
}
