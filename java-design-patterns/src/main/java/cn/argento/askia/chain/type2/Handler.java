package cn.argento.askia.chain.type2;

/**
 * Handler（抽象处理者）
 * 另外一种写法，这种写法的好处是能够判断是否到了责任链的链尾部，JavaWeb的中的Filter就是采用这种写法
 */
public abstract class Handler {
    protected Handler successor;

    public void setSuccessor(Handler handler){
        this.successor = handler;
    }

    public void handlerRequest(int days){
        if (successor == null){
            // 到达最后一个过滤器啦！
            // 注意，处理完最后的过滤器一定一定要退出当前方法！！
            handlerRequest(days, new EndHandler());
            return;
        }
        handlerRequest(days, successor);
    }
    protected abstract void handlerRequest(int days, Handler successor);
    // 另外这种类似于WebFilter的写法

}
