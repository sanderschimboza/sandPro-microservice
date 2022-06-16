package co.zw.santech.registration.repo;

import co.zw.santech.registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
