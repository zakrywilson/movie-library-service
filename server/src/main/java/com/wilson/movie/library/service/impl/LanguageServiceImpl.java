package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.RatingEntity;
import com.wilson.movie.library.repository.RatingRepository;
import com.wilson.movie.library.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rating service.
 *
 * @author Zach Wilson
 */
@Service
@Transactional
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository repository;

    @Autowired
    public RatingServiceImpl(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    @Nonnull
    public RatingEntity create(@Nonnull RatingEntity rating) {
        log.trace("Creating rating: {}", rating);

        RatingEntity savedEntity = repository.save(rating);
        log.debug("Persisted new rating: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    public Optional<RatingEntity> getById(@Nonnull Integer id) {
        log.trace("Getting rating by ID: {}", id);

        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    @Nonnull
    public Optional<RatingEntity> getByName(@Nonnull String name) {
        log.trace("Getting rating by name: \"{}\"", name);

        return Optional.ofNullable(repository.findByName(name));
    }

    @Override
    @Nonnull
    public Collection<RatingEntity> getAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Getting all rating by IDs: {}", ids);

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    public Collection<RatingEntity> getAll() {
        log.trace("Getting all rating");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<RatingEntity> update(@Nonnull Integer id, @Nonnull RatingEntity rating) {
        log.trace("Updating rating with ID {}: {}", id, rating);

        Optional<RatingEntity> optionalSavedEntity = Optional.empty();

        Optional<RatingEntity> optionalCurrentEntity = Optional.ofNullable(repository.findOne(id));
        if (optionalCurrentEntity.isPresent()) {
            RatingEntity entity = optionalCurrentEntity.get();
            entity.setName(rating.getName());
            entity.setDescription(rating.getDescription());

            optionalSavedEntity = Optional.ofNullable(repository.save(entity));

            optionalSavedEntity.ifPresent((e) -> log.debug("Persisted update to rating with ID {}: {}", id, e));
        } else {
            log.debug("No rating exists with ID {}. Nothing to update", id);
        }

        return optionalSavedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<Integer> deleteById(@Nonnull Integer id) {
        log.trace("Deleting rating by ID: {}", id);

        Optional<RatingEntity> optionalEntity = Optional.ofNullable(repository.findOne(id));

        if (optionalEntity.isPresent()) {
            repository.delete(id);
            log.debug("Deleted rating with ID {}: {}", id, optionalEntity.get());
            return Optional.of(id);
        } else {
            log.debug("No rating exists with ID {}. Nothing to delete", id);
            return Optional.empty();
        }
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Deleting all rating by IDs: {}", ids);

        Collection<RatingEntity> entities = repository.findAllById(ids);
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(RatingEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted {} rating with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted {} rating", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all rating");

        List<RatingEntity> entities = repository.findAll();
        repository.deleteInBatch(entities);

        Set<Integer> deletedEntityIds = entities.stream().map(RatingEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted all {} rating with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted all {} rating", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if rating exists with ID: {}", id);

        return repository.exists(id);
    }

    @Override
    public boolean exists(@Nonnull String name) {
        log.trace("Checking if rating exists with title: \"{}\"", name);

        return Optional.ofNullable(repository.findByName(name)).isPresent();
    }

}
