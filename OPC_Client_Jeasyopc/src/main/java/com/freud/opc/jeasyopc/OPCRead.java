package main.java.com.freud.opc.jeasyopc;

import javafish.clients.opc.JEasyOpc;
import javafish.clients.opc.asynch.AsynchEvent;
import javafish.clients.opc.asynch.OpcAsynchGroupListener;
import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * OPCRead
 *
 * @author YangPeng
 * @date 2018/2/27
 * @time 15:16
 */
public class OPCRead implements OpcAsynchGroupListener {
    @Override
    public void getAsynchEvent(AsynchEvent event) {
        ArrayList<OpcItem> list = event.getOPCGroup().getItems();
        for (Iterator<OpcItem> it = list.iterator(); it.hasNext(); ) {
            OpcItem opcItem = it.next();
            String key = opcItem.getItemName().trim();
            String value = opcItem.getValue().toString().trim();
            String itemName = opcItem.getItemName();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("时间：" + df.format(new Date()) + "-->plc数据监听：" + key + "-->" + value);
        }
    }

    //执行
    public static void main(String[] args) throws Exception {
        OPCRead read = new OPCRead();
        //构造参数:IP OPCServer名称 任意
        JEasyOpc jEasyOpc = new JEasyOpc("", "", "");
        JEasyOpc.coInitialize();
        jEasyOpc.coInitialize();
        jEasyOpc.connect();
        //(用户组的标识名称)，开始活动的组(true 开始 false 不开始) 默认true 刷新组的时间 毫秒,默认0.0f
        OpcGroup group = new OpcGroup("", true, 3000, 0.0f);
        group.addItem(new OpcItem("", true, ""));
        jEasyOpc.addGroup(group);
        group.addAsynchListener(read); //添加监听
        jEasyOpc.run();
        jEasyOpc.terminate();
        JEasyOpc.coUninitialize();
    }
}
