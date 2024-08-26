package com.ra.binhpt.repository;

import com.ra.binhpt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Long>
{
	Optional<Users> findByEmail(String email);
}
