package sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list"; // redirect:URL => URL로 페이지를 리다이렉트하게 해주는 명령어
    }
}
