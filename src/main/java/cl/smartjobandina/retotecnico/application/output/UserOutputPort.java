package cl.smartjobandina.retotecnico.application.output;

import cl.smartjobandina.retotecnico.infrastucture.output.entity.UserEntity;

public interface UserOutputPort {
    UserEntity registerUser(UserEntity user);
    void deleteUser(UserEntity user);
    UserEntity findByEmail(String email);
}
