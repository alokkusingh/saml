package com.alok.security.saml.idp.utils;

import com.alok.security.saml.idp.model.IDPUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.saml.saml2.attribute.Attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserUtils {
    private UserUtils() {
    }

    public static List<IDPUserDetails> getAllUserLoginDetails() {
        /*
        HERE HERE: add any user with any attribute as you need in your
        Note: the userName (IDPUserDetails.userName) should be unique
         */
        return Arrays.asList(
                getSampleUser("alok.ku.singh@email", "ADMIN", "Alok", "Singh"),
                getSampleUser("rachna2589@email", "USER", "Rachna", "Singh")
        );

    }

    private static IDPUserDetails getSampleUser(String principal, String roleAttr, String firstNameAttr, String lastNameAttr) {

        List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute().setName("firstName").setValues(Collections.<Object>singletonList(firstNameAttr)));
        attrs.add(new Attribute().setName("lastName").setValues(Collections.<Object>singletonList(lastNameAttr)));
        attrs.add(new Attribute().setName("role").setValues(Collections.<Object>singletonList(roleAttr)));

        return new IDPUserDetails(principal, roleAttr, attrs);
    }


    public static IDPUserDetails getCurrentUserDetails(Authentication authentication) {
        IDPUserDetails userDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof IDPUserDetails) {
            userDetails = (IDPUserDetails) authentication.getPrincipal();
        }
        return userDetails;
    }

}
