package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.TvShowEntity;
import com.wilson.movie.library.resource.model.TvShow;
import com.wilson.movie.library.service.TvShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static com.wilson.movie.library.resource.utils.Adapters.toTvShow;
import static com.wilson.movie.library.resource.utils.Adapters.toTvShowEntity;
import static com.wilson.movie.library.resource.utils.Adapters.toTvShows;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Rest resource controller for TV shows.
 *
 * @author Zach Wilson
 */
@RequestMapping("tv-shows")
@RestController
@Slf4j
public class TvShowResource {

    private final TvShowService service;

    @Autowired
    public TvShowResource(TvShowService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody TvShow tvShow) {
        log.trace("Received request to create TV show: {}", tvShow);

        TvShowEntity createdTvShow = service.create(toTvShowEntity(tvShow));

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdTvShow.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = GET, value = "/{id}")
    public ResponseEntity<TvShow> getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get TV show by ID: {}", id);

        Optional<TvShowEntity> optionalTvShow = service.getById(id);

        if (optionalTvShow.isPresent()) {
            return ResponseEntity.ok(toTvShow(optionalTvShow.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<TvShow>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all TV shows with IDs: {}", ids);

        Collection<TvShowEntity> tvShows = service.getAllWithIds(ids);

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "title")
    public ResponseEntity<TvShow> getByTitle(@RequestParam("title") String title) {
        log.trace("Received request to get TV show by title: \"{}\"", title);

        Optional<TvShowEntity> optionalTvShow = service.getByTitle(title);

        if (optionalTvShow.isPresent()) {
            return ResponseEntity.ok(toTvShow(optionalTvShow.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "date-aired")
    public ResponseEntity<Collection<TvShow>> getAllByDateAired(@RequestParam("date-aired") Integer dateAiredEpochDay) {
        if (log.isTraceEnabled()) {
            log.trace("Received request to get all TV shows by date aired: {}",
                      dateAiredEpochDay != null ? LocalDate.ofEpochDay(dateAiredEpochDay) : null);
        }

        Collection<TvShowEntity> tvShows = service.getAllByDateAired(dateAiredEpochDay);

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "network")
    public ResponseEntity<Collection<TvShow>> getAllByNetwork(@RequestParam("network") String network) {
        log.trace("Received request to get all TV shows by network: \"{}\"", network);

        Collection<TvShowEntity> tvShows = service.getAllByNetwork(network);

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<TvShow>> getAll() {
        log.trace("Received request to get all TV shows");

        Collection<TvShowEntity> tvShows = service.getAll();

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody TvShow tvShow) {
        log.trace("Received request to update TV show with ID {}: {}", id, tvShow);

        Optional<TvShowEntity> optionalUpdatedTvShow = service.update(id, toTvShowEntity(tvShow));

        if (optionalUpdatedTvShow.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete TV show by ID: {}", id);

        Optional<Integer> optionalDeletedTvShowId = service.deleteById(id);

        if (optionalDeletedTvShowId.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all TV shows with IDs: {}", ids);

        Collection<Integer> deletedTvShowIds = service.deleteAllWithIds(ids);

        if (!deletedTvShowIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all TV shows");

        Collection<Integer> deletedTvShowIds = service.deleteAll();

        if (!deletedTvShowIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
