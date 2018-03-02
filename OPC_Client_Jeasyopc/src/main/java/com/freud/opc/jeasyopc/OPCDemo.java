package main.java.com.freud.opc.jeasyopc;


import javafish.clients.opc.JOpc;
import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.exception.*;
import javafish.clients.opc.variant.Variant;

/**
 * OPCTest
 *
 * @author YangPeng
 * @date 2018/2/6
 * @time 17:16
 */
public class OPCDemo {

    public static void main(String[] args) {
//第一步，初始化
//把配置文件javafish/clients/opc/JCustomOpc.properties、放到classpath
        JOpc.coInitialize();
//第二步，建立一个JOpc对象，三个参数，分别是OpcServer的IP，Server的name，还有JOpc的name
        JOpc jopc = new JOpc("192.168.0.3", "Matrikon.OPC.Simulation.1", "JOPC1");
//第三步，建立连接
        try {
            jopc.connect();
        } catch (ConnectivityException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
//第四步，新建一个OPC的group和item，并把item加到group中
        OpcGroup group = new OpcGroup("TestGroup", true, 500, 0.0f);
        OpcItem item = new OpcItem("Bucket Brigade.Int1", true, "");
        group.addItem(item);
//第五步，注册group，item
        jopc.addGroup(group);
        try {
            jopc.registerGroup(group);
        } catch (UnableAddGroupException e) {
            e.printStackTrace();
        }
        try {
            jopc.registerItem(group, item);
        } catch (UnableAddItemException e) {
            e.printStackTrace();
        }
//读
        OpcItem responseItem;
        try {
            responseItem = jopc.synchReadItem(group, item);
            System.out.println(responseItem);
            System.out.println(Variant.getVariantName(responseItem.getDataType()) + ": " + responseItem.getValue());
        } catch (SynchReadException e1) {
            e1.printStackTrace();
        }

//第六步赋值，并同步至服务器
        System.out.println("v:" + item.getValue().getVariantType() + "..");
        item.setValue(new Variant(400));
        try {
            jopc.synchWriteItem(group, item);
        } catch (SynchWriteException e) {
            e.printStackTrace();
        }
        System.out.println(1);

        System.out.println(2);
//释放资源
        try {
            jopc.unregisterItem(group, item);
        } catch (UnableRemoveItemException e) {
            e.printStackTrace();
        }
        try {
            jopc.unregisterGroup(group);
        } catch (UnableRemoveGroupException e) {
            e.printStackTrace();
        }
        JOpc.coUninitialize();
    }
}
