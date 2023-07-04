package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserLoginAndActiveTrue(String userLogin);

    Optional<User> findByUserLogin(String userLogin);

    List<User> findByRoleRoleIdInAndActiveTrue(List<Integer> rolesId);

    Optional<User> findByUserId(int id);


    Boolean existsByUserLogin(String login);

}
