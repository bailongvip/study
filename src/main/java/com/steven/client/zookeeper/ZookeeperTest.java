package com.steven.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Zookeeper原生API测试
 */
public class ZookeeperTest {
    private static ZooKeeper zooKeeper;
    final static String scheme = "digest";
    final static String auth = "test";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String server = "192.168.50.50:2181,192.168.50.50:2182,192.168.50.50:2183";
        //连接服务器(无权限设置)
        zooKeeper = new ZooKeeper(server, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("服务器连接时"+event);
            }
        });

        //设置连接权限ACL
        zooKeeper.addAuthInfo(scheme,auth.getBytes());

        //创建持久化节点
        createPersistent("/test","2020");

        //创建有权限验证持久化节点
        //createPersistentAcl("/test","2020");

        Stat stat = new Stat();
        //getData可以注册watcher
        //其它可以注册watcher的方法：exists,getChildren
        //watcher事件是一个一次性触发器，回调一次后被移除
        //可以触发watcher的方法：create,delete,setData
        String s = new String(zooKeeper.getData("/test", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(Event.EventType.NodeDataChanged.equals(event.getType())){
                    System.out.println("数据发生了改变"+event);
                }
            }
        },stat));

        System.in.read();
    }

    /**
     * 创建持久化节点
     * @param path 节点路径
     * @param data 节点数据
     */
    public static void createPersistent(String path,String data){
        try {
            zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建临时节点
     * @param path 节点路径
     * @param data 节点数据
     */
    public static void createEphemeral(String path,String data){
        try {
            zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建带权限验证持久化节点
     * @param path 节点路径
     * @param data 节点数据
     */
    public static void createPersistentAcl(String path,String data){
        try {
            zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
