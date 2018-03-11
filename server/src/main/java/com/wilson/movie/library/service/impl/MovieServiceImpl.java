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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    public MovieEntity create(@Nonnull MovieEntity movie) {
        log.trace("Creating movie: {}", movie);

        MovieEntity savedEntity = repository.save(movie);
        log.debug("Persisted new movie: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    public Optional<MovieEntity> getById(@Nonnull Integer id) {
        log.trace("Getting movie by ID: {}", id);

        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    @Nonnull
    public Optional<MovieEntity> getByTitle(@Nonnull String title) {
        log.trace("Getting movie by title: \"{}\"", title);

        return Optional.ofNullable(repository.findByTitle(title));
    }

    @Override
    @Nonnull
    public Collection<MovieEntity> getAllByReleaseDate(@Nonnull LocalDate releaseDate) {
        log.trace("Getting all movies by release date: {}", releaseDate);

        return repository.findAllByReleaseDate(releaseDate);
    }

    @Override
    @Nonnull
    public Collection<MovieEntity> getAllByReleaseDate(@Nonnull Integer releaseDateEpochDay) {
        LocalDate releaseDate = LocalDate.ofEpochDay(releaseDateEpochDay);

        log.trace("Getting all movies by release date: {} ({})", releaseDateEpochDay, releaseDate);

        return repository.findAllByReleaseDate(releaseDate);
    }

    @Override
    @Nonnull
    public Collection<MovieEntity> getAllByStudio(@Nonnull String studio) {
        log.trace("Getting all movies by studio: \"{}\"", studio);

        return repository.findAllByStudio(studio);
    }

    @Override
    @Nonnull
    public Collection<MovieEntity> getAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Getting all movies by IDs: {}", ids);

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    public Collection<MovieEntity> getAll() {
        log.trace("Getting all movies");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<MovieEntity> update(@Nonnull Integer id, @Nonnull MovieEntity movie) {
        log.trace("Updating movie with ID {}: {}", id, movie);

        Optional<MovieEntity> optionalSavedEntity = Optional.empty();

        Optional<MovieEntity> optionalCurrentEntity = Optional.ofNullable(repository.findOne(id));
        if (optionalCurrentEntity.isPresent()) {
            MovieEntity entity = optionalCurrentEntity.get();
            entity.setTitle(movie.getTitle());
            entity.setStudio(movie.getStudio());
            entity.setReleaseDate(movie.getReleaseDate());
            entity.setPlotSummary(movie.getPlotSummary());
            entity.setNotes(movie.getNotes());

            optionalSavedEntity = Optional.ofNullable(repository.save(entity));

            optionalSavedEntity.ifPresent((e) -> log.debug("Persisted update to movie with ID {}: {}", id, e));
        } else {
            log.debug("No movie exists with ID: {}. Nothing to update", id);
        }

        return optionalSavedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<Integer> deleteById(@Nonnull Integer id) {
        log.trace("Deleting movie by ID: {}", id);

        Optional<MovieEntity> optionalEntity = Optional.ofNullable(repository.findOne(id));

        if (optionalEntity.isPresent()) {
            repository.delete(id);
            log.debug("Deleted movie with ID {}: {}", id, optionalEntity.get());
            return Optional.of(id);
        } else {
            log.debug("No movie exists with ID {}. Nothing to delete", id);
            return Optional.empty();
        }
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Deleting all movies by IDs: {}", ids);

        Collection<MovieEntity> entities = repository.findAllById(ids);
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(MovieEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted {} movies with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted {} movies", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all movies");

        List<MovieEntity> entities = repository.findAll();
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(MovieEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted all {} movies with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted all {} movies", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if movie exists with ID: {}", id);

        return repository.exists(id);
    }

    @Override
    public boolean exists(@Nonnull String title) {
        log.trace("Checking if movie exists with title: \"{}\"", title);

        return Optional.ofNullable(repository.findByTitle(title)).isPresent();
    }

}
