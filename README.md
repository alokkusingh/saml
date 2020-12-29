[![Build Status](https://travis-ci.org/alokkusingh/saml.svg?branch=master)](https://travis-ci.org/github/alokkusingh/saml)
[![GitHub issues](https://img.shields.io/github/issues/alokkusingh/saml.svg)](https://github.com/alokkusingh/saml/issues)
[![GitHub issues closed](https://img.shields.io/github/issues-closed-raw/alokkusingh/saml.svg?maxAge=2592000)](https://github.com/alokkusingh/saml/issues?q=is%3Aissue+is%3Aclosed)

# SAML - Identity Provider and Service Provider Services
## IdP and SPS both the services provide SAML meta data URL to negotiate the service:
        - http://localhost:8081/alok-idp/saml/idp/metadata
        - http://localhost:8082/hello-sp/saml/sp/metadata
        
## It supports SAML authentication triggered by:
- `SPS` - SPS sends **SML Request** to IdP, IdP authenticate the user and responds with **SAMl Response**.

        - Usecase: 
- `IdP` - IdP directly sends **SAML Reponse** to SPS. No **SAML Request** generated.

        - Usecase: **Signle Sign-on** - if we want to embed a secure web page provided by guest application (will act as SPS service) as iFrame to the web page provided by host application (will act as IdP service). The Guest server validates the user credentials as part of login process and the same time generates **SAML Response** for guest application. When loading guest application web page to the Web page (as iFrame) the **SAML Reponse** shall be sent to guest application. Guest application shall use **SAML Response** to Authenticate/Authorize user without asking additinal credentials from user. Seemless single sign-on takes place.

### Both the services sign and encrypt Metadata/SAML Request/SAML Response

## IdP RSA Private Key and Cert Generation

    -- Generate Private Key and CSR Request
    openssl req -new -newkey rsa:4096 -nodes -keyout IdP_pkcs8.key -out IdP.csr
        -- no pwd

    -- to pkcs1
    openssl rsa -in IdP_pkcs8.key -out IdP_pkcs1.key

    -- Sign Certificate
    openssl x509 -req -CA rootCA_Alok.crt -CAkey rootCA_Alok.key -in IdP.csr -out IdP.crt -days 365 -CAcreateserial

## SPS RSA Private Key and Cert Generation
    -- Generate Private Key and CSR Request
    openssl req -new -newkey rsa:4096 -nodes -keyout saml_spcs_pkcs8.key -out saml_spcs.csr
        -- no pwd

    -- to pkcs1
    openssl rsa -in saml_spcs_pkcs8.key -out saml_spcs_pkcs1.key

    -- Sign certtificate
    openssl x509 -req -CA rootCA_Alok.crt -CAkey rootCA_Alok.key -in saml_spcs.csr -out saml_spcs.crt -days 365 -CAcreateserial
