package org.example.jdbctemplate.repositories;

import org.example.jdbctemplate.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> rowMapper = (rs, rowNum) -> {
        return new User(rs.getString("email"), rs.getLong("id"));
    };

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (email) VALUES (:email)";
        Map<String, Object> params = new HashMap<>();
        params.put("email", user.getEmail());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET email = :email WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("email", user.getEmail());
        params.put("id", user.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, rowMapper);
        return Optional.ofNullable(user);
    }

}
