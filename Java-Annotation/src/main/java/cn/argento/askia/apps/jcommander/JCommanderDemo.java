package cn.argento.askia.apps.jcommander;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * JCommander官方Demo
 */
public class JCommanderDemo {

    public static void main(String[] args) {


        Args args1 = new Args();
        String[] argv = {"arg1", "arg2", "arg3", "arg4" ,"-log", "2", "-groups", "unit" , "-mode", "26", "-debug", "true"};
        JCommander.newBuilder()
                .addObject(args1)
                .build()
                .parse(argv);
        System.out.println(args1);

    }
}


class Args {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
    private Integer verbose = 1;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;

    @Parameter(names = "-mode", description = "mode number")
    private int mode = 2;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Args{");
        sb.append("parameters=").append(parameters);
        sb.append(", verbose=").append(verbose);
        sb.append(", groups='").append(groups).append('\'');
        sb.append(", debug=").append(debug);
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}

