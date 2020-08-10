[![Build Status](https://travis-ci.org/alokkusingh/saml.svg?branch=master)](https://travis-ci.org/github/alokkusingh/saml)
[![GitHub issues](https://img.shields.io/github/issues/alokkusingh/saml.svg)](https://github.com/alokkusingh/saml/issues)
[![GitHub issues closed](https://img.shields.io/github/issues-closed-raw/alokkusingh/saml.svg?maxAge=2592000)]()

# SAML Identity Provider and Service Provider Services
## IdP and SP both the services provide SAML meta data URL to negotiate the service:
        - http://localhost:8081/alok-idp/saml/idp/metadata
        - http://localhost:8082/hello-sp/saml/sp/metadata
        
## It supports SAML authentication triggered by:
- `SPS` - SPS sends **SML Request** to IdP, IdP authenticate the user and responds with **SAMl Response**.
- `IdP` - IdP directly sends **SAML Reponse** to SPS. No **SAML Request** generated.

## both the service sign and encrypt metadata data and SAML Request and SAML Response

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
