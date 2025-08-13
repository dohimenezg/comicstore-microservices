package com.dohimenezg.auth.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dohimenezg.auth.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
