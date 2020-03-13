package com.steven.client.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;

public class ZkClientCrud<T> {
    ZkClient zkClient;
    private String server = "192.168.50.50:2181,192.168.50.50:2182,192.168.50.50:2183";

    public ZkClientCrud() {
        //增加序列化对对象的支持
        //this.zkClient = new ZkClient(server, 5000, 5000,new SerializableSerializer());
        this.zkClient = new ZkClient(server, 5000, 5000, new ZkSerializer() {
            @Override
            public byte[] serialize(Object o) throws ZkMarshallingError {
                return new byte[0];
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                return null;
            }
        });
    }

    /**
     * 创建持久化节点
     * @param path
     * @param data
     */
    public void createPersistent(String path,Object data){
        zkClient.createPersistent(path,data);
    }

    /**
     * 读取数据
     * @param path
     * @return
     */

    public T readData(String path){
        return zkClient.readData(path);
    }

    /**
     * 删除持久化节点
     * @param path
     */
    public void deletePersistent(String path){
        zkClient.delete(path);
    }

    /**
     * watcher测试
     */
    public void listener(String path){
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("变更的节点："+s+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("删除的节点为："+s);
            }
        });
    }
}
