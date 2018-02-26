package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.repository.MovieRepository;
import com.wilson.movie.library.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Movie service.
 *
 * @author Zach Wilson
 */
@Service
@Transactional
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Autowired
    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    @Nonnull
    @Transactional
    public MovieEntity create(@Nonnull MovieEntity movie) {
        log.trace("Creating movie: {}", movie);

        MovieEntity savedEntity = repository.save(movie);
        log.debug("Persisted new movie: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public MovieEntity getById(@Nonnull Integer id) {
        log.trace("Getting movie by ID: {}", id);

        return repository.findOne(id);
    }

    @Override
    @Nonnull
    @Transactional
    public MovieEntity getByTitle(@Nonnull String title) {
        log.trace("Getting movie by title: \"{}\"", title);

        return repository.findByTitleIsLike(title);
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<MovieEntity> getAllByReleaseDate(@Nonnull LocalDate releaseDate) {
        log.trace("Getting all movies by release date: {}", releaseDate);

        return repository.findAllByReleaseDate(releaseDate);
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<MovieEntity> getAllByReleaseDate(@Nonnull Integer releaseDateEpochDay) {
        LocalDate releaseDate = LocalDate.ofEpochDay(releaseDateEpochDay);

        log.trace("Getting all movies by release date: {} ({})", releaseDateEpochDay, releaseDate);

        return repository.findAllByReleaseDate(releaseDate);
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<MovieEntity> getAllByStudio(@Nonnull String studio) {
        log.trace("Getting all movies by studio: \"{}\"", studio);

        return repository.findAllByStudio(studio);
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<MovieEntity> getAllById(Collection<Integer> ids) {
        if (log.isTraceEnabled()) {
            log.trace("Getting all movies by IDs: {}", Arrays.toString(ids.toArray()));
        }

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<MovieEntity> getAll() {
        log.trace("Getting all movies");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public MovieEntity update(@Nonnull Integer id, @Nonnull MovieEntity movie) {
        log.trace("Updating movie with ID {}: {}", id, movie);

        if (!repository.exists(id)) {
            throw new IllegalStateException("Movie not found with ID: " + id);
        }

        MovieEntity currentEntity = repository.findOne(id);
        currentEntity.setTitle(movie.getTitle());
        currentEntity.setStudio(movie.getStudio());
        currentEntity.setReleaseDate(movie.getReleaseDate());
        currentEntity.setPlotSummary(movie.getPlotSummary());
        currentEntity.setNotes(movie.getNotes());

        MovieEntity savedEntity = repository.save(currentEntity);
        log.debug("Persisted update to movie with ID {}: {}", id, savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Integer deleteById(@Nonnull Integer id) {
        log.trace("Deleting movie by ID: {}", id);

        MovieEntity entity = getById(id); // Gets entity, but also verifies that it exists.

        repository.delete(id);
        log.debug("Deleted movie with ID {}: {}", id, entity);

        return id;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllById(Collection<Integer> ids) {
        if (log.isTraceEnabled()) {
            log.trace("Deleting all movies by IDs: {}", Arrays.toString(ids.toArray()));
        }

        Set<Integer> deletedEntityIds = repository.findAllById(ids)
                .stream()
                .map(MovieEntity::getId)
                .collect(Collectors.toSet());

        repository.deleteAllById(deletedEntityIds);
        if (log.isDebugEnabled()) {
            log.debug("Deleted movies with IDs: {}", Arrays.toString(deletedEntityIds.toArray()));
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all movies");

        Set<Integer> deletedMovieIds = repository.findAll()
                .stream()
                .map(MovieEntity::getId)
                .collect(Collectors.toSet());

        repository.deleteAllById(deletedMovieIds);
        if (log.isDebugEnabled()) {
            log.debug("Deleted all movies with IDs: {}", Arrays.toString(deletedMovieIds.toArray()));
        }

        return deletedMovieIds;
    }

    @Override
    @Transactional
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if movie exists with ID: {}", id);

        return repository.exists(id);
    }

    @Override
    @Transactional
    public boolean exists(@Nonnull String title) {
        log.trace("Checking if movie exists with title: \"{}\"", title);

        return repository.exists(getByTitle(title).getId());
    }

}
