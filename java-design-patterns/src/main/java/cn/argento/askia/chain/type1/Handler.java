package cn.argento.askia.chain.type1;

/**
 * Handler（抽象处理者）
 */
public abstract class Handler {
    protected Handler successor;

    public void setSuccessor(Handler handler){
        this.successor = handler;
    }

    public abstract void handlerRequest(int days);
}
