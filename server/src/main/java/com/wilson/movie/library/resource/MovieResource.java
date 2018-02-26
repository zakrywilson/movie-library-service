package com.wilson.movie.library.resource;

import com.wilson.movie.library.resource.model.Movie;
import com.wilson.movie.library.resource.utils.Adapters;
import com.wilson.movie.library.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Rest resource controller for movies.
 *
 * @author Zach Wilson
 */
@RequestMapping("/movies")
@RestController
@Slf4j
public class MovieResource {

    private final MovieService service;

    @Autowired
    public MovieResource(MovieService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody Movie movie) {
        log.trace("Received request to create movie: {}", movie);

        return new ResponseEntity<>(service.create(Adapters.toMovie(movie)), HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, params = "title")
    public ResponseEntity<?> getByTitle(@RequestParam("title") String title) {
        log.trace("Received request to get movie by title: \"{}\"", title);

        return new ResponseEntity<>(service.getByTitle(title), HttpStatus.OK);
    }

    @RequestMapping(method = GET, params = "release-date")
    public ResponseEntity<?> getAllByReleaseDate(@RequestParam("release-date") Integer releaseDateEpochDay) {
        if (log.isTraceEnabled()) {
            log.trace("Received request to get all movies by release date: {}",
                      releaseDateEpochDay != null ? LocalDate.ofEpochDay(releaseDateEpochDay) : null);
        }

        return new ResponseEntity<>(service.getAllByReleaseDate(releaseDateEpochDay), HttpStatus.OK);
    }

    @RequestMapping(method = GET, params = "studio")
    public ResponseEntity<?> getAllByStudio(@RequestParam("studio") String studio) {
        log.trace("Received request to get all movies by studio: \"{}\"", studio);

        return new ResponseEntity<>(service.getAllByStudio(studio), HttpStatus.OK);
    }

    @RequestMapping(method = GET)
    public ResponseEntity<?> getAll() {
        log.trace("Received request to get all movies");

        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = PUT, params = "id")
    public ResponseEntity<?> update(@RequestParam("id") Integer id, @RequestBody Movie movie) {
        log.trace("Received request to update movie with ID {}: {}", id, movie);

        return new ResponseEntity<>(service.update(id, Adapters.toMovie(movie)), HttpStatus.OK);
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<?> deleteAll() {
        log.trace("Received request to delete all movies");

        service.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = GET, params = "title-exists")
    public ResponseEntity<?> exists(@RequestParam("title-exists") String title) {
        log.trace("Received request to check if movie exists with title: \"{}\"", title);

        return new ResponseEntity<>(service.exists(title) ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

}
