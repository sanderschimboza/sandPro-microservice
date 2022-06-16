package co.zw.santech.userinterface.service;

import co.zw.santech.userinterface.model.Response;
import co.zw.santech.userinterface.model.UserDTO;

public interface InterfaceService {

    Response registerUser(UserDTO userDTO);
}
