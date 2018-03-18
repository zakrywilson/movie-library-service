package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.PersonEntity;
import com.wilson.movie.library.resource.model.Person;
import com.wilson.movie.library.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static com.wilson.movie.library.resource.utils.Adapters.toPerson;
import static com.wilson.movie.library.resource.utils.Adapters.toPersons;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Rest resource controller for persons.
 *
 * @author Zach Wilson
 */
@RequestMapping("/persons")
@RestController
@Slf4j
public class PersonResource {

    private final PersonService service;

    @Autowired
    public PersonResource(PersonService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody Person person) {
        log.trace("Received request to create person: {}", person);

        PersonEntity createdPerson = service.create(toPerson(person));

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdPerson.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = GET, value = "/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get person by ID: {}", id);

        Optional<PersonEntity> optionalPerson = service.getById(id);

        if (optionalPerson.isPresent()) {
            return ResponseEntity.ok(toPerson(optionalPerson.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<Person>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all persons with IDs: {}", ids);

        Collection<PersonEntity> persons = service.getAllWithIds(ids);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "first-name")
    public ResponseEntity<Collection<Person>> getByFirstName(@RequestParam("first-name") String firstName) {
        log.trace("Received request to get person by first name: \"{}\"", firstName);

        Collection<PersonEntity> persons = service.getAllByFirstName(firstName);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "middle-name")
    public ResponseEntity<Collection<Person>> getByMiddleName(@RequestParam("middle-name") String middleName) {
        log.trace("Received request to get person by middle name: \"{}\"", middleName);

        Collection<PersonEntity> persons = service.getAllByMiddleName(middleName);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "last-name")
    public ResponseEntity<Collection<Person>> getByLastName(@RequestParam("last-name") String lastName) {
        log.trace("Received request to get person by last name: \"{}\"", lastName);

        Collection<PersonEntity> persons = service.getAllByLastName(lastName);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = {"first-name, middle-name, last-name"})
    public ResponseEntity<Collection<Person>> getByFullName(
            @RequestParam(value = "first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name") String lastName) {
        if (log.isTraceEnabled()) {
            if (middleName == null) {
                log.trace("Received request to get person by first name \"{}\" and last name \"{}\"",
                          firstName, lastName);
            } else {
                log.trace("Received request to get person by first name \"{}\", middle name \"{}\", and last name \"{}\"",
                          firstName, middleName, lastName);
            }
        }

        Collection<PersonEntity> persons =
                middleName == null
                        ? service.getAllByName(firstName, lastName)
                        : service.getAllByName(firstName, middleName, lastName);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "date-of-birth")
    public ResponseEntity<Collection<Person>> getAllByDateOfBirth(
            @RequestParam("date-of-birth") Integer dateDateOfBirthEpochDay) {
        if (log.isTraceEnabled()) {
            log.trace("Received request to get all persons by date of birth: {}",
                      dateDateOfBirthEpochDay != null ? LocalDate.ofEpochDay(dateDateOfBirthEpochDay) : null);
        }

        Collection<PersonEntity> persons = service.getAllByDateOfBirth(dateDateOfBirthEpochDay);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "date-of-death")
    public ResponseEntity<Collection<Person>> getAllByDateOfDeath(
            @RequestParam("date-of-death") Integer dateDateOfDeathEpochDay) {
        if (log.isTraceEnabled()) {
            log.trace("Received request to get all persons by date of death: {}",
                      dateDateOfDeathEpochDay != null ? LocalDate.ofEpochDay(dateDateOfDeathEpochDay) : null);
        }

        Collection<PersonEntity> persons = service.getAllByDateOfDeath(dateDateOfDeathEpochDay);

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<Person>> getAll() {
        log.trace("Received request to get all persons");

        Collection<PersonEntity> persons = service.getAll();

        if (!persons.isEmpty()) {
            return ResponseEntity.ok(toPersons(persons));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Person person) {
        log.trace("Received request to update person with ID {}: {}", id, person);

        Optional<PersonEntity> optionalUpdatedPerson = service.update(id, toPerson(person));

        if (optionalUpdatedPerson.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete person by ID: {}", id);

        Optional<Integer> optionalDeletedPersonId = service.deleteById(id);

        if (optionalDeletedPersonId.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all persons with IDs: {}", ids);

        Collection<Integer> deletedPersonIds = service.deleteAllWithIds(ids);

        if (!deletedPersonIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all persons");

        Collection<Integer> deletedPersonIds = service.deleteAll();

        if (!deletedPersonIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
