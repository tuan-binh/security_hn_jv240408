package com.ra.binhpt.repository;

import com.ra.binhpt.constants.RoleName;
import com.ra.binhpt.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Roles, Long>
{
	Optional<Roles> findByRoleName(RoleName roleName);
}
