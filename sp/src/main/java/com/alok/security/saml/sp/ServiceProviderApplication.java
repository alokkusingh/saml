package com.alok.security.saml.sp;

import com.alok.security.saml.sp.configuration.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
@Slf4j
public class ServiceProviderApplication {

    public static void main(String[] args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(ServiceProviderApplication.class);

        Environment env = app.run(args).getEnvironment();

        log.info("Service Provider: Access URLs:\n" +
                        "----------------------------------------------------------\n" +
                        "App URL: \t\t\thttp://localhost:{}{}\n" +
                        "Meta URL: \t\t\thttp://localhost:{}{}{}\n" +
                        "----------------------------------------------------------\n",
                env.getProperty("server.port"), env.getProperty("server.servlet.context-path"),
                env.getProperty("server.port"), env.getProperty("server.servlet.context-path"), "/saml/sp/metadata");
    }

}