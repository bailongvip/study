package com.steven.client.zookeeper;

import java.io.IOException;

/**
 * zkclient客户端测试
 * 基于原生API的封装
 */
public class ZkClientTest {
    public static void main(String[] args) throws IOException {
        ZkClientCrud zkClientCrud = new ZkClientCrud();
        String path = "/test";
       /*
        创建节点，写入对象数据
        User user = new User();
        user.setUserName("jason");
        user.setAge(18);
        zkClientCrud.createPersistent("/test",user);*/
        zkClientCrud.deletePersistent(path);
        zkClientCrud.listener(path);
        zkClientCrud.createPersistent(path,"2020");

        //System.out.println(zkClientCrud.readData("/test"));
        System.in.read();
    }
}
