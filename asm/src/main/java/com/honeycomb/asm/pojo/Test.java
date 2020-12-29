package com.honeycomb.asm.pojo;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

public class Test{

    // 远程传送javaagent
    public static void main(String[] args) throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("running JVM start ");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
//            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("Bean")) {
                System.out.println(vmd.displayName());
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("D:\\develop\\jar\\com\\honeycomb\\my-agent\\0.0.1-SNAPSHOT\\my-agent-0.0.1-SNAPSHOT.jar");
                virtualMachine.detach();
            }
        }
    }
}