package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "math")
//localhost:8082/math   --> mein Rechner
public class StartController
{
    //localhost:8082/math/add/3/5  //URL ca. = Path
    @GetMapping("/add/{number1}/{number2}")
    @ResponseBody
    public int addPV(@PathVariable int number1,
                     @PathVariable int number2)
    {
        return number1 + number2;
    }

    //localhost:8082/math/add?number1=3&number2=5
    @GetMapping("/add")
    @ResponseBody
    public  int addRP(@RequestParam int number1,
                      @RequestParam int number2)
    {
        return number1+ number2;
    }
}
