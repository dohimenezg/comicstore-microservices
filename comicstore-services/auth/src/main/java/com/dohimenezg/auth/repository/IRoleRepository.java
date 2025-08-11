package com.dohimenezg.auth.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dohimenezg.auth.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    
    public Optional<Role> findByName(String name);

}
