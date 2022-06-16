package co.zw.santech.userinterface.controller;

import co.zw.santech.userinterface.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

    @GetMapping
    public String routeHomePage() {
        return "login";
    }

    @GetMapping("/registerView")
    public String register(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register-2";
    }
    @GetMapping("/resendTokenView")
    public String resendTokenView() {
        return "resend-token";
    }
}
