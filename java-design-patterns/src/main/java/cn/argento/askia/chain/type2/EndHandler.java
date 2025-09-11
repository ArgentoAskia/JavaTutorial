package cn.argento.askia.chain.type2;

public class EndHandler extends Handler{


    //  EndHandler一定要重写两个方法，否则EndHandler会无限循环下去！
    @Override
    public void handlerRequest(int days) {
        handlerRequest(days, null);
    }

    protected void handlerRequest(int days, Handler successor) {
        System.out.println("执行Servlet代码...");
    }
}
