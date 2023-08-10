package cn.argentoaskia;

import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

public class FilePermissionDemo {

    private static String READ_ACTION = "READ";
    private static String WRITE_ACTION = "WRITE";
    private static String EXECUTE_ACTION = "EXECUTE";
    private static String DELETE_ACTION = "DELETE";
    private static String READLINK_ACTION = "READLINK";
    public static void main(String[] args) throws IOException {

        Path pathDemoPath = Paths.get("C:\\a.txt");
        FilePermission filePermission = new FilePermission(pathDemoPath.toString(), DELETE_ACTION + "," + WRITE_ACTION + "," + READ_ACTION + "," +EXECUTE_ACTION);
        FilePermission filePermission2 = new FilePermission("C:\\-", READLINK_ACTION);
        String name = filePermission.getName();
        System.out.println(name);
        String actions = filePermission.getActions();
        System.out.println(actions);
        boolean implies = filePermission2.implies(filePermission);
        System.out.println(implies);

//        boolean delete = pathDemoPath.toFile().delete();
//        System.out.println(delete);
        Files.delete(pathDemoPath);
    }
}
