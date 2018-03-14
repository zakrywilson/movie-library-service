package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.LanguageEntity;
import com.wilson.movie.library.repository.LanguageRepository;
import com.wilson.movie.library.service.LanguageService;
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
 * Language service.
 *
 * @author Zach Wilson
 */
@Service
@Transactional
@Slf4j
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository repository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository repository) {
        this.repository = repository;
    }

    @Override
    @Nonnull
    public LanguageEntity create(@Nonnull LanguageEntity language) {
        log.trace("Creating language: {}", language);

        LanguageEntity savedEntity = repository.save(language);
        log.debug("Persisted new language: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    public Optional<LanguageEntity> getById(@Nonnull Integer id) {
        log.trace("Getting language by ID: {}", id);

        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    @Nonnull
    public Optional<LanguageEntity> getByName(@Nonnull String name) {
        log.trace("Getting language by name: \"{}\"", name);

        return Optional.ofNullable(repository.findByName(name));
    }

    @Override
    @Nonnull
    public Collection<LanguageEntity> getAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Getting all language by IDs: {}", ids);

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    public Collection<LanguageEntity> getAll() {
        log.trace("Getting all language");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<LanguageEntity> update(@Nonnull Integer id, @Nonnull LanguageEntity language) {
        log.trace("Updating language with ID {}: {}", id, language);

        Optional<LanguageEntity> optionalSavedEntity = Optional.empty();

        Optional<LanguageEntity> optionalCurrentEntity = Optional.ofNullable(repository.findOne(id));
        if (optionalCurrentEntity.isPresent()) {
            LanguageEntity entity = optionalCurrentEntity.get();
            entity.setName(language.getName());
            entity.setDescription(language.getDescription());

            optionalSavedEntity = Optional.ofNullable(repository.save(entity));

            optionalSavedEntity.ifPresent((e) -> log.debug("Persisted update to language with ID {}: {}", id, e));
        } else {
            log.debug("No language exists with ID {}. Nothing to update", id);
        }

        return optionalSavedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<Integer> deleteById(@Nonnull Integer id) {
        log.trace("Deleting language by ID: {}", id);

        Optional<LanguageEntity> optionalEntity = Optional.ofNullable(repository.findOne(id));

        if (optionalEntity.isPresent()) {
            repository.delete(id);
            log.debug("Deleted language with ID {}: {}", id, optionalEntity.get());
            return Optional.of(id);
        } else {
            log.debug("No language exists with ID {}. Nothing to delete", id);
            return Optional.empty();
        }
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Deleting all language by IDs: {}", ids);

        Collection<LanguageEntity> entities = repository.findAllById(ids);
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(LanguageEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted {} language with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted {} language", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all language");

        List<LanguageEntity> entities = repository.findAll();
        repository.deleteInBatch(entities);

        Set<Integer> deletedEntityIds = entities.stream().map(LanguageEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted all {} language with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted all {} language", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if language exists with ID: {}", id);

        return repository.exists(id);
    }

    @Override
    public boolean exists(@Nonnull String name) {
        log.trace("Checking if language exists with title: \"{}\"", name);

        return Optional.ofNullable(repository.findByName(name)).isPresent();
    }

}
