package cn.argento.askia.collections.list;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LinkedListDemo {
    private static LinkedList<String> stack = new LinkedList<>();
    private static LinkedList<String> queue = new LinkedList<>();
    public static void main(String[] args) {
        playAsStack();
        System.out.println("=========================================");
        playAsQueue();
    }

    private static String randomString(){
        return UUID.randomUUID().toString();
    }
    /**
     * 当作Stack来使用！
     */

    private static void printStack(LinkedList<String> stack) {
        System.out.println("|                 ↑ ↓                  |");
        for (int i = 0; i < stack.size(); i++) {
            String s = stack.get(i);
            System.out.println("| " + s + " |");
        }
        System.out.println("|--------------------------------------|");
    }
    private static void playAsStack(){
        for (int i = 0; i < 10; i++) {
            stack.push(randomString());
        }
        printStack(stack);
        System.out.println();

        // 1.压栈
        System.out.println("Hello World!入栈！！");
        stack.push("Hello World！");
        System.out.println();

        // 2.peek element
        String peek = stack.peek();
        String s = stack.peekLast();
        String s1 = stack.peekFirst();
        System.out.println("peek的结果："  + peek);
        System.out.println("peek栈首元素：" + s1);
        System.out.println("peek栈底元素：" + s);
        System.out.println();

        // 3.弹栈
        for (int i = 0; i < 5; i++) {
            String pop = stack.pop();
            System.out.println("第" + i + "个元素（栈自顶向下）：" + pop);
        }
    }

    private static void printQueue(LinkedList<String> queue) {
        System.out.println("|                 ↑                    |");
        for (int i = 0; i < queue.size(); i++) {
            String s = queue.get(i);
            System.out.println("| " + s + " |");
        }
        System.out.println("|                 ↑                    |");
    }

    private static void playAsQueue(){
        for (int i = 0; i < 10; i++) {
            queue.offer(randomString());
        }
        printQueue(queue);
        System.out.println();

        // 1.入队
        System.out.println("Hello World！入队！！");
        queue.offer("Hello World!");
        String s = randomString();
        System.out.println(s + "插队到队首！");
        queue.offerFirst(s);
        System.out.println("Hello World 2！入队！！");
        queue.offerLast("Hello World 2!");
        System.out.println();

        // 2.出队
        for (int i = 0; i < 13; i++) {
            String poll = queue.poll();
            System.out.println("第" + i+ "个出队的是：" + poll);
        }

    }


}
