package cl.smartjobandina.retotecnico.infrastucture.output.persistence.repository;

import cl.smartjobandina.retotecnico.infrastucture.output.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
}
