package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;
import java.util.Optional;
public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private static final MessagesRepositoryJdbcImpl instance = new MessagesRepositoryJdbcImpl();
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);

    }

    public void executeSqlFile(String fileName) {
        try (Connection connection = getConnection();
             InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + fileName);
            }

            StringBuilder sqlStatement = new StringBuilder();
            String line;
            try (Statement statement = connection.createStatement()) {
                while ((line = reader.readLine()) != null) {
                    // Пропускаем строки, начинающиеся с комментариев или пустые строки
                    if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                        sqlStatement.append(line);
                        // Если строка заканчивается на ';', выполним SQL-запрос
                        if (line.trim().endsWith(";")) {
                            statement.executeUpdate(sqlStatement.toString());
                            // Очистим StringBuilder для следующего SQL-запроса
                            sqlStatement.setLength(0);
                        }
                    }
                }
                // Выполним оставшийся SQL-запрос, если он есть
                if (sqlStatement.length() > 0) {
                    statement.executeUpdate(sqlStatement.toString());
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return instance.dataSource.getConnection();
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = MessagesRepositoryJdbcImpl.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat.message WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Long messageId = resultSet.getLong("id");
                User author = findByAuthorId(resultSet.getLong("author"));
                Chatroom chatroom = findByChatroomId(resultSet.getLong("room"));
                String text = resultSet.getString("text");
                Timestamp time = resultSet.getTimestamp("time");

                Message message = new Message(messageId, text, author, chatroom, time);
                return Optional.of(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User findByAuthorId(Long authorId) {
        try(Connection connection = MessagesRepositoryJdbcImpl.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat.user WHERE id = ?");
            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                List<Chatroom> createdChatrooms = null; //<>
                List<Chatroom> joinedChatrooms = null; //<>
                User user = new User(userId, login, password, createdChatrooms, joinedChatrooms);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Chatroom findByChatroomId(Long chatroomId) {
        try(Connection connection = MessagesRepositoryJdbcImpl.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat.chatroom WHERE id = ?");
            statement.setLong(1, chatroomId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Long chatroomId1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                User owner = null;
                List<Message> list_of_msg = null; //<>
                Chatroom chatroom = new Chatroom(chatroomId1, name, owner, list_of_msg);
                return chatroom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}