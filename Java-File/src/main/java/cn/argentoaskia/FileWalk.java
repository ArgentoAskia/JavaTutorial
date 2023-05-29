package cn.argentoaskia;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileWalk {
    public static void main(String[] args) throws IOException {
        try{
        Files.walk(Paths.get("E:/"), FileVisitOption.FOLLOW_LINKS).filter(new Predicate<Path>() {
            @Override
            public boolean test(Path path) {
                return !path.toString().contains("$") && !path.toString().contains(".");
            }
        }).forEach(System.out::println);
        }catch (AccessDeniedException e){

        }
//        Stream<Path> walk = null;
//        try {
//            walk = Files.walk(Paths.get("C:/"), FileVisitOption.FOLLOW_LINKS);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert walk != null;
//        walk.filter(new Predicate<Path>() {
//            @Override
//            public boolean test(Path path) {
//                if (path.toString().contains("$Recycle.Bin")){
//                    return false;
//                }
//                return true;
//            }
//        }).forEach(System.out::println);
//        walk.close();
    }
}
