package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleId(int id);

    Optional<Role> findByName(String name);

}
