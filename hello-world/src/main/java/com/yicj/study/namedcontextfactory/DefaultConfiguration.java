package com.yicj.study.namedcontextfactory;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DefaultConfiguration {
    @Value("${myRibbon.client.name}") // 服务名称
    //@RibbonClientName
    private String name = "client";
	// 每个服务会有不同的配置

    @Bean
    public IClientConfig config() {
        DefaultClientConfigImpl config = new DefaultClientConfigImpl("myRibbon");
        config.loadProperties(this.name);
        config.set(CommonClientConfigKey.ConnectTimeout, 1000);
        config.set(CommonClientConfigKey.ReadTimeout, 1000);
        config.set(CommonClientConfigKey.GZipPayload, true);
        return config;
    }
	// 使用ConfigurationBasedServerList，是通过读取配置文件的方式获取服务实例列表的
    @Bean
    @ConditionalOnMissingBean
    public ServerList<Server> ribbonServerList(IClientConfig config) {
        ConfigurationBasedServerList serverList = new ConfigurationBasedServerList();
        serverList.initWithNiwsConfig(config);
        return serverList;
    }
}