package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.GenreEntity;
import com.wilson.movie.library.resource.model.Genre;
import com.wilson.movie.library.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

import static com.wilson.movie.library.resource.utils.Adapters.toGenre;
import static com.wilson.movie.library.resource.utils.Adapters.toGenres;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Rest resource controller for genres.
 *
 * @author Zach Wilson
 */
@RequestMapping("genres")
@RestController
@Slf4j
public class GenreResource {

    private final GenreService service;

    @Autowired
    public GenreResource(GenreService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody Genre genre) {
        log.trace("Received request to create genre: {}", genre);

        GenreEntity createdGenre = service.create(toGenre(genre));

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdGenre.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = GET, value = "/{id}")
    public ResponseEntity<Genre> getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get genre by ID: {}", id);

        Optional<GenreEntity> optionalGenre = service.getById(id);

        if (optionalGenre.isPresent()) {
            return ResponseEntity.ok(toGenre(optionalGenre.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<Genre>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all genres with IDs: {}", ids);

        Collection<GenreEntity> genres = service.getAllWithIds(ids);

        if (!genres.isEmpty()) {
            return ResponseEntity.ok(toGenres(genres));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "name")
    public ResponseEntity<Genre> getByName(@RequestParam("name") String name) {
        log.trace("Received request to get genre by name: \"{}\"", name);

        Optional<GenreEntity> optionalGenre = service.getByName(name);

        if (optionalGenre.isPresent()) {
            return ResponseEntity.ok(toGenre(optionalGenre.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<Genre>> getAll() {
        log.trace("Received request tog et all genres");

        Collection<GenreEntity> genres = service.getAll();

        if (!genres.isEmpty()) {
            return ResponseEntity.ok(toGenres(genres));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Genre genre) {
        log.trace("Received request to update genre with ID {}: {}", id, genre);

        Optional<GenreEntity> optionalUpdatedGenre = service.update(id, toGenre(genre));

        if (optionalUpdatedGenre.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete genre by ID: {}", id);

        Optional<Integer> optionalDeletedGenre = service.deleteById(id);

        if (optionalDeletedGenre.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all genres with IDs: {}", ids);

        Collection<Integer> deletedGenreIds = service.deleteAllWithIds(ids);

        if (!deletedGenreIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all genres");

        Collection<Integer> deletedGenresIds = service.deleteAll();

        if (!deletedGenresIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
