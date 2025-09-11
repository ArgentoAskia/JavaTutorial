package cn.argento.askia.chain.type2;

public class WebHandler2 extends Handler {

    @Override
    protected void handlerRequest(int days, Handler handler) {
        System.out.println("do WebHandler2...");
        handler.handlerRequest(days);
        System.out.println("redo WebHandler2...");
    }
}
