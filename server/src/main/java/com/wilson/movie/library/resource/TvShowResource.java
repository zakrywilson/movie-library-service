package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.GenreEntity;
import com.wilson.movie.library.domain.LanguageEntity;
import com.wilson.movie.library.domain.RatingEntity;
import com.wilson.movie.library.domain.TvShowEntity;
import com.wilson.movie.library.resource.model.TvShow;
import com.wilson.movie.library.service.GenreService;
import com.wilson.movie.library.service.LanguageService;
import com.wilson.movie.library.service.RatingService;
import com.wilson.movie.library.service.TvShowService;
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
 * Rest resource controller for TV shows.
 *
 * @author Zach Wilson
 */
@RequestMapping("tv-shows")
@RestController
@Slf4j
public class TvShowResource {

    private final TvShowService tvShowService;
    private final RatingService ratingService;
    private final GenreService genreService;
    private final LanguageService languageService;

    @Autowired
    public TvShowResource(TvShowService tvShowService, RatingService ratingService,
            GenreService genreService, LanguageService languageService) {
        this.tvShowService = tvShowService;
        this.ratingService = ratingService;
        this.genreService = genreService;
        this.languageService = languageService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody TvShow tvShow) {
        log.trace("Received request to create TV show: {}", tvShow);

        // Get the rating
        Optional<RatingEntity> rating = ratingService.getByName(tvShow.getRating());
        if (!rating.isPresent()) {
            log.debug("Cannot create TV show: provided rating does not exist: \"{}\"", tvShow.getRating());
            return ResponseEntity.badRequest().build();
        }

        // Get the genre
        Optional<GenreEntity> genre = genreService.getByName(tvShow.getGenre());
        if (!genre.isPresent()) {
            log.debug("Cannot create TV show: provided genre does not exist: \"{}\"", tvShow.getGenre());
            return ResponseEntity.badRequest().build();
        }

        // Get the language
        Optional<LanguageEntity> language = languageService.getByName(tvShow.getLanguage());
        if (!language.isPresent()) {
            log.debug("Cannot create TV show: provided language does not exist: \"{}\"", tvShow.getLanguage());
            return ResponseEntity.badRequest().build();
        }

        TvShowEntity createdTvShow = tvShowService.create(toTvShow(tvShow,
                                                                   toRating(rating.get()),
                                                                   toGenre(genre.get()),
                                                                   toLanguage(language.get())));

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

        Optional<TvShowEntity> optionalTvShow = tvShowService.getById(id);

        if (optionalTvShow.isPresent()) {
            return ResponseEntity.ok(toTvShow(optionalTvShow.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<TvShow>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all TV shows with IDs: {}", ids);

        Collection<TvShowEntity> tvShows = tvShowService.getAllWithIds(ids);

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "title")
    public ResponseEntity<TvShow> getByTitle(@RequestParam("title") String title) {
        log.trace("Received request to get TV show by title: \"{}\"", title);

        Optional<TvShowEntity> optionalTvShow = tvShowService.getByTitle(title);

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

        Collection<TvShowEntity> tvShows = tvShowService.getAllByDateAired(dateAiredEpochDay);

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "network")
    public ResponseEntity<Collection<TvShow>> getAllByNetwork(@RequestParam("network") String network) {
        log.trace("Received request to get all TV shows by network: \"{}\"", network);

        Collection<TvShowEntity> tvShows = tvShowService.getAllByNetwork(network);

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<TvShow>> getAll() {
        log.trace("Received request to get all TV shows");

        Collection<TvShowEntity> tvShows = tvShowService.getAll();

        if (!tvShows.isEmpty()) {
            return ResponseEntity.ok(toTvShows(tvShows));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody TvShow tvShow) {
        log.trace("Received request to update TV show with ID {}: {}", id, tvShow);

        // Get the rating
        Optional<RatingEntity> rating = ratingService.getByName(tvShow.getRating());
        if (!rating.isPresent()) {
            log.debug("Cannot update TV show: provided rating does not exist: \"{}\"", tvShow.getRating());
            return ResponseEntity.badRequest().build();
        }

        // Get the genre
        Optional<GenreEntity> genre = genreService.getByName(tvShow.getGenre());
        if (!genre.isPresent()) {
            log.debug("Cannot update TV show: provided genre does not exist: \"{}\"", tvShow.getGenre());
            return ResponseEntity.badRequest().build();
        }

        // Get the language
        Optional<LanguageEntity> language = languageService.getByName(tvShow.getLanguage());
        if (!language.isPresent()) {
            log.debug("Cannot update TV show: provided language does not exist: \"{}\"", tvShow.getLanguage());
            return ResponseEntity.badRequest().build();
        }

        Optional<TvShowEntity> optionalUpdatedTvShow =
                tvShowService.update(id, toTvShow(tvShow,
                                                  toRating(rating.get()),
                                                  toGenre(genre.get()),
                                                  toLanguage(language.get())));

        if (optionalUpdatedTvShow.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete TV show by ID: {}", id);

        Optional<Integer> optionalDeletedTvShowId = tvShowService.deleteById(id);

        if (optionalDeletedTvShowId.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all TV shows with IDs: {}", ids);

        Collection<Integer> deletedTvShowIds = tvShowService.deleteAllWithIds(ids);

        if (!deletedTvShowIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all TV shows");

        Collection<Integer> deletedTvShowIds = tvShowService.deleteAll();

        if (!deletedTvShowIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
