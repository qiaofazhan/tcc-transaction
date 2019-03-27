package org.mengyun.tcctransaction.sample.http.capital;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Fazhan.Qiao
 * @Date: 2019/3/26 19:54
 * @Description:
 */
@Controller
public class HelloController {
    @RequestMapping("/")
    public String hello() {
        return "index";
    }
}
