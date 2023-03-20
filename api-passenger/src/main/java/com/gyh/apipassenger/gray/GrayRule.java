package com.gyh.apipassenger.gray;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class GrayRule extends AbstractLoadBalancerRule {

    /**
     * 根据用户选出一个服务
     * @param iClientConfig
     * @return
     * */
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        return choose(getLoadBalancer(), o);
    }

    public Server choose(ILoadBalancer lb, Object key) {

        log.info("灰度 rule");
        Server server = null;
        while (server == null) {
            List<Server> reachableServers = lb.getReachableServers();
            // 获取 当前线程的参数，用户ID version=1
            Map<String, String> map = RibbonParameters.get();
            String version = "";
            if (map != null && map.containsKey("version")) {
                version = map.get("version");
            }
            log.info("当前rule version：" + version);
            // 根据用户选服务
            for (int i = 0; i < reachableServers.size(); i++) {
                 server = reachableServers.get(i);

                // 用户的version我知道了，服务的自定义meta我不知道
                // eureka:
                //   instance:
                //     metadata-map:
                //       version: v2
                // 进行强转
                Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
                String version1 = metadata.get("version");
                // 服务的meta和用户的version都有了
                if (version.trim().equals(version1)){
                    break;
                }
            }
        }
        // 怎么让server取到合适的值
        return server;
    }
}
