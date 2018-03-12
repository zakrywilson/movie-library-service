package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.GenreEntity;
import com.wilson.movie.library.repository.GenreRepository;
import com.wilson.movie.library.service.GenreService;
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
 * Genre service.
 *
 * @author Zach Wilson
 */
@Service
@Transactional
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    @Nonnull
    public GenreEntity create(@Nonnull GenreEntity genre) {
        log.trace("Creating genre: {}", genre);

        GenreEntity savedEntity = repository.save(genre);
        log.debug("Persisted new genre: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    public Optional<GenreEntity> getById(@Nonnull Integer id) {
        log.trace("Getting genre by ID: {}", id);

        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    @Nonnull
    public Optional<GenreEntity> getByName(@Nonnull String name) {
        log.trace("Getting genre by name: \"{}\"", name);

        return Optional.ofNullable(repository.findByName(name));
    }

    @Override
    @Nonnull
    public Collection<GenreEntity> getAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Getting all genre by IDs: {}", ids);

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    public Collection<GenreEntity> getAll() {
        log.trace("Getting all genre");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<GenreEntity> update(@Nonnull Integer id, @Nonnull GenreEntity genre) {
        log.trace("Updating genre with ID {}: {}", id, genre);

        Optional<GenreEntity> optionalSavedEntity = Optional.empty();

        Optional<GenreEntity> optionalCurrentEntity = Optional.ofNullable(repository.findOne(id));
        if (optionalCurrentEntity.isPresent()) {
            GenreEntity entity = optionalCurrentEntity.get();
            entity.setName(genre.getName());
            entity.setDescription(genre.getDescription());

            optionalSavedEntity = Optional.ofNullable(repository.save(entity));

            optionalSavedEntity.ifPresent((e) -> log.debug("Persisted update to genre with ID {}: {}", id, e));
        } else {
            log.debug("No genre exists with ID {}. Nothing to update", id);
        }

        return optionalSavedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<Integer> deleteById(@Nonnull Integer id) {
        log.trace("Deleting genre by ID: {}", id);

        Optional<GenreEntity> optionalEntity = Optional.ofNullable(repository.findOne(id));

        if (optionalEntity.isPresent()) {
            repository.delete(id);
            log.debug("Deleted genre with ID {}: {}", id, optionalEntity.get());
            return Optional.of(id);
        } else {
            log.debug("No genre exists with ID {}. Nothing to delete", id);
            return Optional.empty();
        }
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Deleting all genre by IDs: {}", ids);

        Collection<GenreEntity> entities = repository.findAllById(ids);
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(GenreEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted {} genre with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted {} genre", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all genre");

        List<GenreEntity> entities = repository.findAll();
        repository.deleteInBatch(entities);

        Set<Integer> deletedEntityIds = entities.stream().map(GenreEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted all {} genre with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted all {} genre", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if genre exists with ID: {}", id);

        return repository.exists(id);
    }

    @Override
    public boolean exists(@Nonnull String name) {
        log.trace("Checking if genre exists with title: \"{}\"", name);

        return Optional.ofNullable(repository.findByName(name)).isPresent();
    }

}
