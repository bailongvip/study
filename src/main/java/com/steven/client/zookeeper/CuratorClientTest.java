package com.steven.client.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;

public class CuratorClientTest {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.50.50:2181", new RetryNTimes(3, 1000));
        client.start();
        //client.create().forPath("/zookeeper","111".getBytes());
        String path = "/zookeeper";
        NodeCache nodeCache = new NodeCache(client,path);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("修改zookeeper");
            }
        });
        System.in.read();
    }
}
