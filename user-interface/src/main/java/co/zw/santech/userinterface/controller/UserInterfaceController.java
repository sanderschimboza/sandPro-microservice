package co.zw.santech.userinterface.controller;

import co.zw.santech.userinterface.model.Response;
import co.zw.santech.userinterface.model.UserDTO;
import co.zw.santech.userinterface.service.InterfaceService;
import co.zw.santech.userinterface.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserInterfaceController {

    private final InterfaceService service;

    public UserInterfaceController(InterfaceService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO,
                               RedirectAttributes redirectAttributes) {

        Response registerResponse = this.service.registerUser(userDTO);

        if (registerResponse.getResponseCode().equals("00")) {
            redirectAttributes.addFlashAttribute(Constants.REGISTERED_INDICATOR,
                    Constants.RESPONSE_CODE_OK);
            return "redirect:/resendTokenView";

        } else if (registerResponse.getResponseCode().equals("05")) {
            redirectAttributes.addFlashAttribute(Constants.REGISTERED_INDICATOR,
                    Constants.RESPONSE_CODE_ERR);
            return "redirect:/registerView";
        }
        redirectAttributes.addFlashAttribute(Constants.REGISTERED_INDICATOR,
                Constants.RESPONSE_CODE_FALSE);
        return "redirect:/registerView";
    }
}
