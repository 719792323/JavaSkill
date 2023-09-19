import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class AttachTest {
    public static void main(String[] args) throws Exception{
        //列出正在运行的jvm程序
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        System.out.println(list);
        for (VirtualMachineDescriptor vm : list) {
            if (vm.displayName().contains("RunningProgram")) {
                System.out.println(vm.displayName());
                System.out.println(vm.id());
                VirtualMachine attach = VirtualMachine.attach(vm.id());
                attach.loadAgent("D:\\Code\\Java\\JavaSkill\\JavaAttach\\target\\JavaAttach-1.0-SNAPSHOT.jar");

            }
        }
    }
}
