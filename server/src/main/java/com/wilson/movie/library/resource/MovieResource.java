package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.resource.model.Movie;
import com.wilson.movie.library.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

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
    public ResponseEntity<Movie> getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get movie by ID: {}", id);

        Optional<MovieEntity> optionalMovie = service.getById(id);

        if (optionalMovie.isPresent()) {
            return ResponseEntity.ok(toMovie(optionalMovie.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<Movie>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all movies with IDs: {}", ids);

        Collection<MovieEntity> movies = service.getAllWithIds(ids);

        if (!movies.isEmpty()) {
            return ResponseEntity.ok(toMovies(movies));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "title")
    public ResponseEntity<Movie> getByTitle(@RequestParam("title") String title) {
        log.trace("Received request to get movie by title: \"{}\"", title);

        Optional<MovieEntity> optionalMovie = service.getByTitle(title);

        if (optionalMovie.isPresent()) {
            return ResponseEntity.ok(toMovie(optionalMovie.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "release-date")
    public ResponseEntity<Collection<Movie>> getAllByReleaseDate(@RequestParam("release-date") Integer releaseDateEpochDay) {
        if (log.isTraceEnabled()) {
            log.trace("Received request to get all movies by release date: {}",
                      releaseDateEpochDay != null ? LocalDate.ofEpochDay(releaseDateEpochDay) : null);
        }

        Collection<MovieEntity> movies = service.getAllByReleaseDate(releaseDateEpochDay);

        if (!movies.isEmpty()) {
            return ResponseEntity.ok(toMovies(movies));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "studio")
    public ResponseEntity<Collection<Movie>> getAllByStudio(@RequestParam("studio") String studio) {
        log.trace("Received request to get all movies by studio: \"{}\"", studio);

        Collection<MovieEntity> movies = service.getAllByStudio(studio);

        if (!movies.isEmpty()) {
            return ResponseEntity.ok(toMovies(movies));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<Movie>> getAll() {
        log.trace("Received request to get all movies");

        Collection<MovieEntity> movies = service.getAll();

        if (!movies.isEmpty()) {
            return ResponseEntity.ok(toMovies(movies));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Movie movie) {
        log.trace("Received request to update movie with ID {}: {}", id, movie);

        Optional<MovieEntity> optionalUpdatedMovie = service.update(id, toMovieEntity(movie));

        if (optionalUpdatedMovie.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete movie by ID: {}", id);

        Optional<Integer> optionalDeletedMovieId = service.deleteById(id);

        if (optionalDeletedMovieId.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all movies with IDs: {}", ids);

        Collection<Integer> deletedMovieIds = service.deleteAllWithIds(ids);

        if (!deletedMovieIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all movies");

        Collection<Integer> deletedMovieIds = service.deleteAll();

        if (!deletedMovieIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
