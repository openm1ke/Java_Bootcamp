package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductsRepositoryJdbcImplTest {

    private ProductsRepository productsRepository;
    private EmbeddedDatabase dataSource;

    private final Product[] EXPECTED_FIND_ALL_PRODUCTS = {
            new Product(1, "Product 1", 10.00),
            new Product(2, "Product 2", 20.00),
            new Product(3, "Product 3", 30.00),
            new Product(4, "Product 4", 40.00),
            new Product(5, "Product 5", 50.00)
    };

    @BeforeEach
    public void init() throws SQLException {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                //.addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);

        // Clear the table before inserting test data
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM product");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource = new EmbeddedDatabaseBuilder().addScript("data.sql").build();
    }

    @AfterEach
    public void close() throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM product");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productsRepository = null;
        dataSource.shutdown();
    }

    @Test
    void findAll() {
        List<Product> products = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS.length, products.size());
        assertArrayEquals(EXPECTED_FIND_ALL_PRODUCTS, products.toArray());
    }

    @Test
    void findById() {
        Optional<Product> product = productsRepository.findById(1L);
        assertTrue(product.isPresent());
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS[0], product.get());
    }

    @Test
    void update() {
        Product product = EXPECTED_FIND_ALL_PRODUCTS[0];
        product.setName("Product 1 updated");
        product.setPrice(11.00);
        productsRepository.update(product);
        Optional<Product> updatedProduct = productsRepository.findById(product.getIdentifier());
        assertTrue(updatedProduct.isPresent());
        assertEquals(product, updatedProduct.get());
    }

    @Test
    void save() {
        Product product = new Product(6, "Product 6", 60.00);
        productsRepository.save(product);
        Optional<Product> savedProduct = productsRepository.findById(product.getIdentifier());
        assertTrue(savedProduct.isPresent());
        assertEquals(product, savedProduct.get());
    }

    @Test
    void delete() {
        productsRepository.delete(1L);
        Optional<Product> deletedProduct = productsRepository.findById(1L);
        assertFalse(deletedProduct.isPresent());
    }
}