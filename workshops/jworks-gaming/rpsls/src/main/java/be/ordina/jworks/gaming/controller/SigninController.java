package be.ordina.jworks.gaming.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/signin", method = RequestMethod.GET)
public class SigninController {

    @RequestMapping
    public String signin() {
        return "authentication/signin";
    }
}
