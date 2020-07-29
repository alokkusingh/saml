package com.alok.security.saml.idp.configuration;

import com.alok.security.saml.idp.utils.CommonUtils;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.key.SimpleKey;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.identity.config.LocalIdentityProviderConfiguration;

@ConfigurationProperties(prefix = "saml2")
@Configuration
public class AppProperties extends SamlServerConfiguration {

    @SneakyThrows
    @Override
    public SamlServerConfiguration setIdentityProvider(LocalIdentityProviderConfiguration identityProvider) {

        SimpleKey simpleKey = identityProvider.getKeys().getActive();
        if (!simpleKey.getCertificate().startsWith("|") && !simpleKey.getCertificate().startsWith("-----BEGIN")) {
            simpleKey.setCertificate(CommonUtils.parsePEMFile(simpleKey.getCertificate()));
        }
        if (!simpleKey.getPrivateKey().startsWith("|") && !simpleKey.getPrivateKey().startsWith("-----BEGIN")) {
            simpleKey.setPrivateKey(CommonUtils.parsePEMFile(simpleKey.getPrivateKey()));
        }
        return super.setIdentityProvider(identityProvider);
    }
}

