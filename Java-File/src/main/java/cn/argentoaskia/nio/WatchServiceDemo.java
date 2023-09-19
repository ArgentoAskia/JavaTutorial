package cn.argentoaskia.nio;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * 监听resources/WatchServiceDemo目录，所有在这个目录上的更改都会被以事件的形式打印出来！
 * 可以运行程序让后更改WatchServiceDemo目录的内容！！！
 */
public class WatchServiceDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Path path = Paths.get("Java-File/src/main/resources/WatchServiceDemo/");
        // 1.获取WatchService实现
        final WatchService watchService = FileSystems.getDefault().newWatchService();
        // 2.监听文件创建、删除、修改
        WatchEvent.Kind<?>[] kinds= {StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE};
        // 监听文件,返回WatchKey对象代表一个监听的目录
        final WatchKey register = path.register(watchService, kinds);
        System.out.println("register:" + register);
        System.out.println("register is valid:" + register.isValid());
        System.out.println("register events size:" + register.pollEvents().size());

        while(true){
            // 等待触发事件的监听目录对象
            // take方法阻塞，poll()方法不阻塞
            final WatchKey take = watchService.take();
            System.out.println("take == register?" + (register == take));
            if (take.isValid()){
                // 如果是一个有效的key，则获取基于这个key（目录）上的所有更改记录
                final List<WatchEvent<?>> watchEvents = take.pollEvents();
                for (WatchEvent<?> e :
                        watchEvents) {
                    // 该key上发生的具体的改变，如添加了目录，则调用context()返回添加的目录的路径将会作为context对象（一般是一个Path）
                    final Object context = e.context();
                    // 该事件是否是一个重复事件，大于1重复
                    final int count = e.count();
                    // 事件类型
                    final WatchEvent.Kind<?> kind = e.kind();
                    System.out.println("==============================");
                    System.out.println("发生在目录" + path + "上的事件:");
                    System.out.println("    事件类型：" + kind);
                    System.out.println("    事件内容：" + context);
                    System.out.println("    是否是重复事件：" + (count > 1));
                }
                // 重置key等待下一次轮询
                // 如果不重置，则只会监听一次，不会进入下一次轮询监听！
                final boolean reset = take.reset();
                // 如果重置失败则需要关闭watchservice
                if (!reset){
                    watchService.close();
                }
            }
            else{
                // 若 watch key 无效，则关闭watchservice
                watchService.close();
            }
        }
    }
}
