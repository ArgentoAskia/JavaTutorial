package cn.argento.askia.apps.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.BooleanConverter;

import java.util.Arrays;

public class JCommanderMainDemo {
    @Parameter(names={"--length", "-l"})
    private int length;
    @Parameter(names={"--pattern", "-p"})
    private int pattern;
    @Parameter(names = "-debug")
    private boolean debug;
    public static void main(String ... argv) {
        JCommanderMainDemo main = new JCommanderMainDemo();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        System.out.println(main.toString());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JCommanderMainDemo{");
        sb.append("length=").append(length);
        sb.append(", pattern=").append(pattern);
        sb.append(", debug=").append(debug);
        sb.append('}');
        return sb.toString();
    }
}
