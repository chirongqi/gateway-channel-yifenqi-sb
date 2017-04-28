package com.namibank.gateway.channel.yifenqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 *
 * @author CliveYuan
 * @date Apr 20, 2017 2:32:54 PM
 */
@SpringBootApplication
@ComponentScan("com.namibank")
// @EnableAutoConfiguration
// @PropertySource("classpath:config/gateway-channel-yifenqi.properties")
@PropertySources(value = { @PropertySource("classpath:application.properties") })
// @ImportResource(locations = {"classpath:spring/*.xml"})
@EnableTransactionManagement
public class NbApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbApplication.class, args);
	}
}
