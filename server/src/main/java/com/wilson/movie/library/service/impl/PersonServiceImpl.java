package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.PersonEntity;
import com.wilson.movie.library.repository.PersonRepository;
import com.wilson.movie.library.service.PersonService;
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
 * Person service.
 *
 * @author Zach Wilson
 */
@Service
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    @Nonnull
    public PersonEntity create(@Nonnull PersonEntity person) {
        log.trace("Creating person: {}", person);

        PersonEntity savedEntity = repository.save(person);
        log.debug("Persisted new person: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    public Optional<PersonEntity> getById(@Nonnull Integer id) {
        log.trace("Getting person by ID: {}", id);

        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByFirstName(@Nonnull String firstName) {
        log.trace("Getting persons by first name: \"{}\"", firstName);

        return repository.findAllByFirstName(firstName);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByMiddleName(@Nonnull String middleName) {
        log.trace("Getting persons by middle name: \"{}\"", middleName);

        return repository.findAllByMiddleName(middleName);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByLastName(@Nonnull String lastName) {
        log.trace("Getting persons by last name: \"{}\"", lastName);

        return repository.findAllByLastName(lastName);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByName(@Nonnull String firstName, @Nonnull String lastName) {
        log.trace("Getting persons by first name \"{}\" and last name \"{}\"", firstName, lastName);

        return repository.findAllByName(firstName, lastName);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByName(@Nonnull String firstName, @Nonnull String middleName,
            @Nonnull String lastName) {
        log.trace("Getting persons by first name \"{}\", middle name \"{}\", and last name \"{}\"",
                  firstName, middleName, lastName);

        return repository.findAllByName(firstName, middleName, lastName);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByDateOfBirth(@Nonnull LocalDate dateOfBirth) {
        log.trace("Getting all persons by date of birth: {}", dateOfBirth);

        return repository.findAllByDateOfBirth(dateOfBirth);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByDateOfBirth(@Nonnull Integer dateOfBirthEpochDay) {
        LocalDate dateOfBirth = LocalDate.ofEpochDay(dateOfBirthEpochDay);

        log.trace("Getting all persons by date of birth: {} ({})", dateOfBirthEpochDay, dateOfBirth);

        return repository.findAllByDateOfBirth(dateOfBirth);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByDateOfDeath(@Nonnull LocalDate dateOfDeath) {
        log.trace("Getting all persons by date of death: {}", dateOfDeath);

        return repository.findAllByDateOfDeath(dateOfDeath);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllByDateOfDeath(@Nonnull Integer dateOfDeathEpochDay) {
        LocalDate dateOfDeath = LocalDate.ofEpochDay(dateOfDeathEpochDay);

        log.trace("Getting all persons by date of death: {} ({})", dateOfDeathEpochDay, dateOfDeath);

        return repository.findAllByDateOfDeath(dateOfDeath);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Getting all persons by IDs: {}", ids);

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    public Collection<PersonEntity> getAll() {
        log.trace("Getting all persons");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<PersonEntity> update(@Nonnull Integer id, @Nonnull PersonEntity person) {
        log.trace("Updating person with ID {}: {}", id, person);

        Optional<PersonEntity> optionalSavedEntity = Optional.empty();

        Optional<PersonEntity> optionalCurrentEntity = Optional.ofNullable(repository.findOne(id));
        if (optionalCurrentEntity.isPresent()) {
            PersonEntity entity = optionalCurrentEntity.get();
            entity.setFirstName(person.getFirstName());
            entity.setMiddleName(person.getMiddleName());
            entity.setLastName(person.getLastName());
            entity.setDateOfBirth(person.getDateOfBirth());
            entity.setDateOfDeath(person.getDateOfDeath());

            optionalSavedEntity = Optional.ofNullable(repository.save(entity));

            optionalSavedEntity.ifPresent((e) -> log.debug("Persisted update to person with ID {}:{}", id, e));
        }

        return optionalSavedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<Integer> deleteById(@Nonnull Integer id) {
        log.trace("Deleting person by ID: {}", id);

        Optional<PersonEntity> optionalEntity = Optional.ofNullable(repository.findOne(id));

        if (optionalEntity.isPresent()) {
            repository.delete(id);
            log.debug("Deleted person with ID {}: {}", id, optionalEntity.get());
            return Optional.of(id);
        } else {
            log.debug("No person exists with ID {}. Nothing to delete", id);
            return Optional.empty();
        }
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Deleting all persons by IDs: {}", ids);

        Collection<PersonEntity> entities = repository.findAllById(ids);
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(PersonEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted {} persons with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted {} persons", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all persons");

        List<PersonEntity> entities = repository.findAll();
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(PersonEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted all {} persons with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted all {} persons", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if person exists with ID: {}", id);

        return repository.exists(id);
    }

}
