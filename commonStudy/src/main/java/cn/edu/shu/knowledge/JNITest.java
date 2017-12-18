package cn.edu.shu.knowledge;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.tools.jdi.SocketAttachingConnector;

import java.util.List;
import java.util.Map;

/**
 * Created by jxxiangwen
 * Time: 17-8-25 下午3:28.
 */
public class JNITest {

    private static final String CLASS_NAME = "com.ymm.uc.app.rest.PersonController";
    private static final int LINE = 100;
    private static final String VAR_NAME = "userDetailCodeRequest";
    public static void main(String[] args) {
        // 获取虚拟机管理器（VirtualMachineManager）,虚拟机管理器将在第一次被调用时初始化可用的链接器。一般地，调试器会默认地采用启动型链接器进行链接
        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        // 获取默认的链接器（Connector）,调试器调用链接器的 launch () 来启动目标程序，并完成调试器与目标虚拟机的链接，
        List<Connector> connectors = virtualMachineManager.allConnectors();
        // 启动目标程序，连接调试器（Debuuger）与目标虚拟机（VirtualMachine）
//        VirtualMachine targetVM = defaultConnector.launch(arguments);
        try {
            SocketAttachingConnector sac = null;
            for (Connector connector : connectors) {
                if(connector instanceof SocketAttachingConnector) {
                    sac = (SocketAttachingConnector)connector;
                }
            }

            Map<String, Connector.Argument> defaultArguments = sac.defaultArguments();
            Connector.Argument hostArg = defaultArguments.get("hostname"); // SocketAttachingConnector#ARG_HOST
            Connector.Argument portArg = defaultArguments.get("port"); // SocketAttachingConnector#ARG_PORT
            hostArg.setValue("localhost");
            portArg.setValue("8080");
            // 启动目标程序，连接调试器（Debuuger）与目标虚拟机（VirtualMachine）
            VirtualMachine vm = sac.attach(defaultArguments);
            // process = vm.process();
            if (vm == null) {
                return;
            }

            // 2. 发送请求告诉目标VM我们需要关心哪些事件
            EventRequestManager requestManager = vm.eventRequestManager();
            List<ReferenceType> referenceTypes = vm.classesByName(CLASS_NAME);
            List<Location> locations = referenceTypes.get(0).locationsOfLine(LINE);
            BreakpointRequest breakpointRequest =
                    requestManager.createBreakpointRequest(locations.get(0));
            breakpointRequest.enable();

            // 3. 事件监听以及处理
            EventQueue eventQueue = vm.eventQueue();
            boolean disconnected = false;
            while (true) {
                if (disconnected) break;
                EventSet eventSet = eventQueue.remove();
                EventIterator eventIterator = eventSet.eventIterator();
                while (eventIterator.hasNext()) {
                    Event event = eventIterator.nextEvent();
                    if (event instanceof BreakpointEvent) {
                        System.out.println("Reach line " + LINE + " of " + CLASS_NAME);
                        BreakpointEvent breakpointEvent = (BreakpointEvent) event;
                        ThreadReference threadReference = breakpointEvent.thread();
                        StackFrame stackFrame = threadReference.frame(0);
                        LocalVariable localVariable = stackFrame
                                .visibleVariableByName(VAR_NAME);
                        Value value = stackFrame.getValue(localVariable);
                        int i = ((IntegerValue) value).value();
                        System.out.println("The local variable " + VAR_NAME + " is " + i);
                        eventSet.resume();
                    } else if (event instanceof VMDisconnectEvent) {
                        System.out.println("VM disconnected.");
                        disconnected = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
