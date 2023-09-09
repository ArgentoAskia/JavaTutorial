package cn.argento.askia.annotation.warnings;

public class SuppressWarningShow {


    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public static void main(String[] args) {

        hello(2,3);
    }


    @SuppressWarnings({"SameParameterValue","UnusedParameters", "unused", "UnusedReturnValue"})
    private static String hello(int a, int b){
        int ab = 2;
        switch (a){
            case 2:
            case 3:
        }
        return "";
    }
}
