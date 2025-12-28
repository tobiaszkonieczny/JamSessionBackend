package com.sap.jamsession.repositories;

import com.sap.jamsession.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findById(int id);

  Optional<User> findByEmail(String email);

  List<User> findAll();

  User save(User user);

  void deleteById(int id);

  boolean existsByEmail(String email);
}
