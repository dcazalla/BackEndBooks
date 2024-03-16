package com.pruebas.holamundo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pruebas.holamundo.bbdd.Book;
import com.pruebas.holamundo.bbdd.JdbcOperacionesCRUD;

@SpringBootApplication
public class StartApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcOperacionesCRUD")              // Test JdbcTemplate
   // @Qualifier("namedParameterJdbcOperacionesCRUD")  // Test NamedParameterJdbcTemplate
    private JdbcOperacionesCRUD jdbcOperacionesCRUD;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("StartApplication...");
        runJDBC();
    }

    void runJDBC() {

        log.info("Creating tables for testing...");

        jdbcTemplate.execute("DROP TABLE IF EXISTS books");
        jdbcTemplate.execute("CREATE TABLE books(" +
                "id SERIAL, name VARCHAR(255), price NUMERIC(15, 2))");

        List<Book> books = Arrays.asList(
                new Book("Thinking in Java", new BigDecimal("46.32")),
                new Book("Mkyong in Java", new BigDecimal("1.99")),
                new Book("Getting Clojure", new BigDecimal("37.3")),
                new Book("Head First Android Development", new BigDecimal("41.19"))
        );

        log.info("[SAVE]");
        books.forEach(book -> {
            log.info("Saving...{}", book.getName());
            jdbcOperacionesCRUD.save(book);
        });

        // count
        log.info("[COUNT] Total books: {}", jdbcOperacionesCRUD.count());

        // find all
        log.info("[FIND_ALL] {}", jdbcOperacionesCRUD.findAll());

        // find by id
        log.info("[FIND_BY_ID] :2L");
        Book book = jdbcOperacionesCRUD.findById(2L).orElseThrow(IllegalArgumentException::new);
        log.info("{}", book);

        // find by name (like) and price
        log.info("[FIND_BY_NAME_AND_PRICE] : like '%Java%' and price <= 10");
        log.info("{}", jdbcOperacionesCRUD.findByNameAndPrice("Java", new BigDecimal(10)));

        // get name (string) by id
        log.info("[GET_NAME_BY_ID] :1L = {}", jdbcOperacionesCRUD.getNameById(1L));

        // update
        log.info("[UPDATE] :2L :99.99");
        book.setPrice(new BigDecimal("99.99"));
        log.info("rows affected: {}", jdbcOperacionesCRUD.update(book));

        // delete
        log.info("[DELETE] :3L");
        log.info("rows affected: {}", jdbcOperacionesCRUD.deleteById(3L));

        // find all
        log.info("[FIND_ALL] {}", jdbcOperacionesCRUD.findAll());

    }

}
