package cl.smartjobandina.retotecnico.infrastucture.output.persistence;

import cl.smartjobandina.retotecnico.application.output.UserOutputPort;
import cl.smartjobandina.retotecnico.infrastucture.output.entity.UserEntity;
import cl.smartjobandina.retotecnico.infrastucture.output.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceAdapter implements UserOutputPort {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserEntity registerUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
