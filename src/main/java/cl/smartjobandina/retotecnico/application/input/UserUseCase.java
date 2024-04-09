package cl.smartjobandina.retotecnico.application.input;

import cl.smartjobandina.retotecnico.domain.dto.ResponseUser;
import cl.smartjobandina.retotecnico.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserUseCase {
    ResponseUser registerUser(UserDto user) throws Exception ;
    ResponseUser updaterUser(UserDto user) throws Exception ;
    void deleteUser(String email) throws Exception;
    ResponseUser findUserByEmail(String email) throws Exception ;
    UserDetails loadUserByUsername(String username) throws Exception;
}
