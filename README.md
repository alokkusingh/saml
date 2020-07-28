# SAML IdP and SP Services
## IdP and SP both the service provides SAML meta data URL to negotiate the service:
        - http://localhost:8081/alok-idp/saml/idp/metadata
        - http://localhost:8082/hello-sp/saml/sp/metadata
        
## It supports SAML authentication request by any one - IdP or SPS

## both the service sign and encrypt metata data and SAML Request and SAML Response

## IdP RSA Private Key and Cert Generation

    -- Generate Private Key and CSR Request
    openssl req -new -newkey rsa:4096 -nodes -keyout IdP_pkcs8.key -out IdP.csr
        -- no pwd

    -- to pkcs1
    openssl rsa -in IdP_pkcs8.key -out IdP_pkcs1.key

    -- Sign Vertificate
    openssl x509 -req -CA rootCA_Alok.crt -CAkey rootCA_Alok.key -in IdP.csr -out IdP.crt -days 365 -CAcreateserial

## SPS RSA Private Key and Cert Generation
    -- Generate Private Key and CSR Request
    openssl req -new -newkey rsa:4096 -nodes -keyout saml_spcs_pkcs8.key -out saml_spcs.csr
        -- no pwd

    -- to pkcs1
    openssl rsa -in saml_spcs_pkcs8.key -out saml_spcs_pkcs1.key

    -- Sign certtificate
    openssl x509 -req -CA rootCA_Alok.crt -CAkey rootCA_Alok.key -in saml_spcs.csr -out saml_spcs.crt -days 365 -CAcreateserial
