package jdi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ClassType;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.StackFrame;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

public class JDIExampleDebugger {

    private static VirtualMachine vm;
    private static Process process;
    private static EventRequestManager eventRequestManager;
    private static EventQueue eventQueue;
    private static EventSet events;
    private static boolean vmExit = false;


    public static void main(String[] args) throws VMStartException, IllegalConnectorArgumentsException, IOException {
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, Connector.Argument> defaultArguments = launchingConnector.defaultArguments();
        Connector.Argument mainArg = defaultArguments.get("main");
        Connector.Argument suspendArg = defaultArguments.get("suspend");
        mainArg.setValue("jdi.JDIExampleDebuggee");
        suspendArg.setValue("true");
        vm = launchingConnector.launch(defaultArguments);

        process = vm.process();

        eventRequestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter("jdi.JDIExampleDebuggee");
        classPrepareRequest.addCountFilter(1);
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();


    }

}