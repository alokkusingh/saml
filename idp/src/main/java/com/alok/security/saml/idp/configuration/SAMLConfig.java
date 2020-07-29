package com.alok.security.saml.idp.configuration;

import com.alok.security.saml.idp.filter.CustomIDPAuthenticationRequestFilter;
import com.alok.security.saml.idp.filter.CustomIDPInitiatedLoginFilter;
import com.alok.security.saml.idp.model.IDPUserDetails;
import com.alok.security.saml.idp.utils.CommonUtils;
import com.alok.security.saml.idp.utils.UserUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.saml.key.SimpleKey;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.identity.IdpInitiatedLoginFilter;
import org.springframework.security.saml.provider.identity.config.SamlIdentityProviderServerBeanConfiguration;

import javax.servlet.Filter;
import java.util.Collection;

@Configuration
@Slf4j
public class SAMLConfig extends SamlIdentityProviderServerBeanConfiguration {
    private final AppProperties config;

    public SAMLConfig(@Qualifier("appProperties") AppProperties config) {
        this.config = config;
    }

    @SneakyThrows
    @Override
    protected SamlServerConfiguration getDefaultHostSamlServerConfiguration() {

        SimpleKey simpleKey = config.getIdentityProvider().getKeys().getActive();
        if (simpleKey.getCertificate() == null) {
            simpleKey.setCertificate(CommonUtils.parsePEMFile(config.getCertificateFile()));
        }
        if (simpleKey.getPrivateKey() == null) {
            simpleKey.setPrivateKey(CommonUtils.parsePEMFile(config.getPrivateKeyFile()));
        }

        return config;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        Collection<IDPUserDetails> allUsers = UserUtils.getAllUserLoginDetails();
        return new IDPInMemoryUserDetailsManager(allUsers);
    }

    @Override
    public Filter idpInitatedLoginFilter() {
        return new CustomIDPInitiatedLoginFilter(getSamlProvisioning(), samlAssertionStore());
    }


    @Override
    public Filter idpAuthnRequestFilter() {
        return new CustomIDPAuthenticationRequestFilter(getSamlProvisioning(), samlAssertionStore());
    }

}
