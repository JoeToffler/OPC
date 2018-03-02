package main.java.com.freud.opc.jeasyopc;

import javafish.clients.opc.JOpc;
import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.variant.Variant;

/**
 * OPCWrite
 *
 * @author YangPeng
 * @date 2018/2/27
 * @time 15:25
 */
public class OPCWrite {
    public static void main(String[] args) throws Exception {
        //第一步，初始化
        JOpc.coInitialize();
        //第二步，建立一个JOpc对象，三个参数，分别是OpcServer的IP，Server的name，JOpc的name
        JOpc jopc = new JOpc("", "", "");
        //第三步，建立连接
        jopc.connect();
        //第四步，新建一个OPC的group和item，并把item加到group中
        OpcGroup group = new OpcGroup("", true, 500, 0.0f);
        //前提 opc的服务点，必须有该点位
        OpcItem item = new OpcItem("", true, "");
        group.addItem(item);
        //第五步
        jopc.addGroup(group);
        jopc.registerGroup(group);
        jopc.registerItem(group, item);
        //第六步，写入数据，此处以float类型的0.04f，写入f.f.f地址
        //写入的数据都需要填入Variant类的构造参数中
        item.setValue(new Variant(0.04f));
        jopc.synchWriteItem(group, item);
        jopc.unregisterGroup(group);
        jopc.coUninitialize();
    }

    public static void run() throws Exception {
        //第一步，初始化
        JOpc.coInitialize();
        //第二步，建立一个JOpc对象，三个参数，分别是OpcServer的IP，Server的name，还有JOpc的name
        JOpc jOpc = new JOpc("", "", "");
        //第三步，建立连接
        jOpc.connect();
        //第四步，新建一个OPC的group和item，并把item加到group中
        OpcGroup group = new OpcGroup("group", true, 500, 0.0f);
        OpcItem item = new OpcItem("f.f.f", true, "");
        group.addItem(item);
        //第五步
        jOpc.addGroup(group);
        jOpc.registerGroup(group);
        jOpc.registerItem(group, item);
        jOpc.registerItem(group, item);
        //第六步，写入数据，此处以float类型的0.04f,写入f.f.f地址
        //写入的数据都需要填入Variant类型的构造参数中
        item.setValue(new Variant(0.04f));
        jOpc.synchWriteItem(group, item);
        //释放资源
        jOpc.unregisterItem(group, item);
        jOpc.unregisterGroup(group);
        JOpc.coUninitialize();
    }
}
