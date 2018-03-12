package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.RatingEntity;
import com.wilson.movie.library.resource.model.Rating;
import com.wilson.movie.library.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

import static com.wilson.movie.library.resource.utils.Adapters.toRating;
import static com.wilson.movie.library.resource.utils.Adapters.toRatings;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Rest resource controller for ratings.
 *
 * @author Zach Wilson
 */
@RequestMapping("ratings")
@RestController
@Slf4j
public class RatingResource {

    private final RatingService service;

    @Autowired
    public RatingResource(RatingService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody Rating rating) {
        log.trace("Received request to create rating: {}", rating);

        RatingEntity createdRating = service.create(toRating(rating));

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdRating.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = GET, value = "/{id}")
    public ResponseEntity<Rating> getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get rating by ID: {}", id);

        Optional<RatingEntity> optionalRating = service.getById(id);

        if (optionalRating.isPresent()) {
            return ResponseEntity.ok(toRating(optionalRating.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<Rating>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all ratings with IDs: {}", ids);

        Collection<RatingEntity> ratings = service.getAllWithIds(ids);

        if (!ratings.isEmpty()) {
            return ResponseEntity.ok(toRatings(ratings));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "name")
    public ResponseEntity<Rating> getByName(@RequestParam("name") String name) {
        log.trace("Received request to get rating by name: \"{}\"", name);

        Optional<RatingEntity> optionalRating = service.getByName(name);

        if (optionalRating.isPresent()) {
            return ResponseEntity.ok(toRating(optionalRating.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<Rating>> getAll() {
        log.trace("Received request tog et all ratings");

        Collection<RatingEntity> ratings = service.getAll();

        if (!ratings.isEmpty()) {
            return ResponseEntity.ok(toRatings(ratings));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Rating rating) {
        log.trace("Received request to update rating with ID {}: {}", id, rating);

        Optional<RatingEntity> optionalUpdatedRating = service.update(id, toRating(rating));

        if (optionalUpdatedRating.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete rating by ID: {}", id);

        Optional<Integer> optionalDeletedRating = service.deleteById(id);

        if (optionalDeletedRating.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all ratings with IDs: {}", ids);

        Collection<Integer> deletedRatingIds = service.deleteAllWithIds(ids);

        if (!deletedRatingIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all ratings");

        Collection<Integer> deletedRatingsIds = service.deleteAll();

        if (!deletedRatingsIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
