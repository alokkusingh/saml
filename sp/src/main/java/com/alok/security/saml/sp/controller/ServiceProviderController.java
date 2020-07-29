package com.alok.security.saml.sp.controller;

import com.alok.security.saml.sp.configuration.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.saml.provider.config.ExternalProviderConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ServiceProviderController {

    final AppProperties appProperties;

    @GetMapping("/idps")
    public String listIDPs(Model model) {

        List<String> collect = appProperties.getServiceProvider().getProviders().stream()
                .map(ExternalProviderConfiguration::getLinktext)
                .collect(toList());

        model.addAttribute("idps", collect);
        return "idp-list";
    }

    @RequestMapping(value = {"/", "/index", "/logged-in"})
    public String home(Principal principal, Model model) {
        log.info("Sample SP Application - You are logged in! - {}",
                principal.getName());
        model.addAttribute("username", principal.getName());
        return "logged-in";
    }

}
