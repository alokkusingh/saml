package com.alok.security.saml.idp.configuration;

import com.alok.security.saml.idp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.key.SimpleKey;
import org.springframework.security.saml.provider.identity.config.SamlIdentityProviderSecurityConfiguration;

import java.io.IOException;

import static org.springframework.security.saml.provider.identity.config.SamlIdentityProviderSecurityDsl.identityProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(1)
    @Slf4j
    public static class SamlSecurity extends SamlIdentityProviderSecurityConfiguration {

        private final AppProperties appProperties;
        private final SAMLConfig samlConfig;

        public SamlSecurity(SAMLConfig samlConfig, @Qualifier("appProperties") AppProperties appProperties) throws IOException {
            super("/saml/idp/", samlConfig);
            this.appProperties = appProperties;
            SimpleKey simpleKey = appProperties.getIdentityProvider().getKeys().getActive();
            if (simpleKey.getCertificate() == null) {
                simpleKey.setCertificate(CommonUtils.parsePEMFile(appProperties.getCertificateFile()));
            }
            if (simpleKey.getPrivateKey() == null) {
                simpleKey.setPrivateKey(CommonUtils.parsePEMFile(appProperties.getPrivateKeyFile()));
            }

            this.samlConfig = samlConfig;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http.
                    userDetailsService(samlConfig.userDetailsService())
                    .formLogin();

            http.
                    apply(identityProvider())
                    .configure(appProperties);
        }
    }

    @Configuration
    @Order(2)
    public static class AppSecurity extends WebSecurityConfigurerAdapter {

        private final SAMLConfig samlConfig;

        public AppSecurity(SAMLConfig samlConfig) {
            this.samlConfig = samlConfig;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
                    .and()
                    .userDetailsService(samlConfig.userDetailsService()).formLogin()
            ;
        }
    }


}
