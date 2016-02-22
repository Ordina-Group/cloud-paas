package be.ordina.jworks.gaming.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class HomeController {

    @RequestMapping
    public String home() {
        return "index";
    }
}
