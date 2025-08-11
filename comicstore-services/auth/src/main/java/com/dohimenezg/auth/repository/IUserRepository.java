package com.dohimenezg.auth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dohimenezg.auth.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    
}
