package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final Connection connection;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3)));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE identifier = " + id);
            if (resultSet.next()) {
                return Optional.of(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3)));
            }
            statement.close();
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE product SET name = '" + product.getName() + "', price = " + product.getPrice() + " WHERE identifier = " + product.getIdentifier());
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO product VALUES (" + product.getIdentifier() + ", '" + product.getName() + "', " + product.getPrice() + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM product WHERE identifier = " + id);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
