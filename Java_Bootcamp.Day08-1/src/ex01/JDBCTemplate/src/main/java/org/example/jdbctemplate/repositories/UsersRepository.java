package org.example.jdbctemplate.repositories;

import org.example.jdbctemplate.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {

    Optional<User> findByEmail(String email);
}
