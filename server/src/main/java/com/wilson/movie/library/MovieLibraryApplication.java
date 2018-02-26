package com.wilson.movie.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Launches application.
 *
 * @author Zach Wilson
 */
@SpringBootApplication
@EnableTransactionManagement
public class MovieLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieLibraryApplication.class, args);
    }

}
