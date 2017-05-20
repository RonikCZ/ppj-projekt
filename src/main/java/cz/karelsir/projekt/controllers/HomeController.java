package cz.karelsir.projekt.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ronik on 20. 5. 2017.
 */

@RestController
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    String showHome() {
        return "home";
    }
}