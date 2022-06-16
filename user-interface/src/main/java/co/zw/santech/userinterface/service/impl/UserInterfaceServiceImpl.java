package co.zw.santech.userinterface.service.impl;

import co.zw.santech.userinterface.model.Response;
import co.zw.santech.userinterface.model.UserDTO;
import co.zw.santech.userinterface.remote.RegistrationServiceCall;
import co.zw.santech.userinterface.service.InterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInterfaceServiceImpl implements InterfaceService {

    private final RegistrationServiceCall registrationService;

    public UserInterfaceServiceImpl(RegistrationServiceCall registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public Response registerUser(UserDTO userDTO) {
        return this.registrationService.registerUSer(userDTO);
    }
}
