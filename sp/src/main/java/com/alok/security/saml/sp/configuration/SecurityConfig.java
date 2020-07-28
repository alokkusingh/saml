package com.alok.security.saml.sp.configuration;

import com.alok.security.saml.sp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.key.SimpleKey;
import org.springframework.security.saml.provider.service.config.SamlServiceProviderSecurityConfiguration;

import java.io.IOException;

import static org.springframework.security.saml.provider.service.config.SamlServiceProviderSecurityDsl.serviceProvider;

@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class SamlSecurity extends SamlServiceProviderSecurityConfiguration {

        private final AppProperties appProperties;

        public SamlSecurity(SAMLConfig SAMLConfig, @Qualifier("appProperties") AppProperties appConfig) throws IOException {
            super("/saml/sp/", SAMLConfig);
            this.appProperties = appConfig;

            SimpleKey simpleKey = appProperties.getServiceProvider().getKeys().getActive();
            if (simpleKey.getCertificate() == null) {
                simpleKey.setCertificate(CommonUtils.parsePEMFile(appProperties.getCertificateFile()));
            }
            if (simpleKey.getPrivateKey() == null) {
                simpleKey.setPrivateKey(CommonUtils.parsePEMFile(appProperties.getPrivateKeyFile()));
            }
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);

            http.apply(serviceProvider())
                    .configure(appProperties);
        }
    }

    @Configuration
    @Order(2)
    public static class AppSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
                    .and()
                    .formLogin().loginPage("/saml/sp/select")
            ;
        }
    }

}
