package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.resource.model.Movie;
import com.wilson.movie.library.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;

import static com.wilson.movie.library.resource.utils.Adapters.*;
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

        MovieEntity createdMovie = service.create(toMovieEntity(movie));

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdMovie.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = GET, value = "/{id}")
    public Movie getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get movie by ID: {}", id);

        return toMovie(service.getById(id));
    }

    @RequestMapping(method = GET, params = "ids")
    public Collection<Movie> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all movies with IDs: {}", ids);

        return toMovies(service.getAllWithIds(ids));
    }

    @RequestMapping(method = GET, params = "title")
    public Movie getByTitle(@RequestParam("title") String title) {
        log.trace("Received request to get movie by title: \"{}\"", title);

        return toMovie(service.getByTitle(title));
    }

    @RequestMapping(method = GET, params = "release-date")
    public Collection<Movie> getAllByReleaseDate(@RequestParam("release-date") Integer releaseDateEpochDay) {
        if (log.isTraceEnabled()) {
            log.trace("Received request to get all movies by release date: {}",
                      releaseDateEpochDay != null ? LocalDate.ofEpochDay(releaseDateEpochDay) : null);
        }

        return toMovies(service.getAllByReleaseDate(releaseDateEpochDay));
    }

    @RequestMapping(method = GET, params = "studio")
    public Collection<Movie> getAllByStudio(@RequestParam("studio") String studio) {
        log.trace("Received request to get all movies by studio: \"{}\"", studio);

        return toMovies(service.getAllByStudio(studio));
    }

    @RequestMapping(method = GET)
    public Collection<Movie> getAll() {
        log.trace("Received request to get all movies");

        return toMovies(service.getAll());
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Movie movie) {
        log.trace("Received request to update movie with ID {}: {}", id, movie);

        if (!service.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        service.update(id, toMovieEntity(movie));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete movie by ID: {}", id);

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all movies with IDs: {}", ids);

        service.deleteAllWithIds(ids);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all movies");

        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
