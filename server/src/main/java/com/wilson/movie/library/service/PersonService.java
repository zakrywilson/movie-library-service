package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.PersonEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Outlines the implementation of a person service.
 *
 * @author Zach Wilson
 */
public interface PersonService {

    PersonEntity create(PersonEntity person);

    Optional<PersonEntity> getById(Integer id);
    Collection<PersonEntity> getAllByFirstName(String firstName);
    Collection<PersonEntity> getAllByMiddleName(String middleName);
    Collection<PersonEntity> getAllByLastName(String lastName);
    Collection<PersonEntity> getAllByName(String firstName, String lastName);
    Collection<PersonEntity> getAllByName(String firstName, String middleName, String lastName);
    Collection<PersonEntity> getAllByDateOfBirth(LocalDate dateOfBirth);
    Collection<PersonEntity> getAllByDateOfBirth(Integer dateOfBirthEpochDay);
    Collection<PersonEntity> getAllByDateOfDeath(LocalDate dateOfDeath);
    Collection<PersonEntity> getAllByDateOfDeath(Integer dateOfDeathEpochDay);
    Collection<PersonEntity> getAllWithIds(Collection<Integer> ids);
    Collection<PersonEntity> getAll();

    Optional<PersonEntity> update(Integer id, PersonEntity person);

    Optional<Integer> deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);

}
