package com.example.springtesting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return  "Hola, mundo";
    }


    @GetMapping("/add")
    public Object add (
          @RequestParam(value = "a", defaultValue = "0") Float a,
          @RequestParam(value = "b", defaultValue = "0") Float b
    ) {
        if(a==0){
            return a;
        }
        return a+b;
    }

    @GetMapping("/subtract")
    public Object subtract (
            @RequestParam(value = "a", defaultValue = "0") Float a,
            @RequestParam(value = "b", defaultValue = "0") Float b
    ) {
        return a - b;
    }

    @GetMapping("/multiply")
    public Object multiply (
           @RequestParam(value = "a", defaultValue = "0") Float a,
                    @RequestParam(value = "b", defaultValue = "0") Float b
    ) {
       return a * b;
    }

    @GetMapping("/divide")
    public Object divide (
            @RequestParam(value = "a", defaultValue = "0") Float a,
            @RequestParam(value = "b", defaultValue = "0") Float b
    ) {
        return a / b;
    }
}
