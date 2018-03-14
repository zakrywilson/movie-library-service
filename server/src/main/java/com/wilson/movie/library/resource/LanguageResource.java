package com.wilson.movie.library.resource;

import com.wilson.movie.library.domain.LanguageEntity;
import com.wilson.movie.library.resource.model.Language;
import com.wilson.movie.library.service.LanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

import static com.wilson.movie.library.resource.utils.Adapters.toLanguage;
import static com.wilson.movie.library.resource.utils.Adapters.toLanguages;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Rest resource controller for languages.
 *
 * @author Zach Wilson
 */
@RequestMapping("languages")
@RestController
@Slf4j
public class LanguageResource {

    private final LanguageService service;

    @Autowired
    public LanguageResource(LanguageService service) {
        this.service = service;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody Language language) {
        log.trace("Received request to create language: {}", language);

        LanguageEntity createdLanguage = service.create(toLanguage(language));

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdLanguage.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = GET, value = "/{id}")
    public ResponseEntity<Language> getById(@PathVariable("id") Integer id) {
        log.trace("Received request to get language by ID: {}", id);

        Optional<LanguageEntity> optionalLanguage = service.getById(id);

        if (optionalLanguage.isPresent()) {
            return ResponseEntity.ok(toLanguage(optionalLanguage.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "ids")
    public ResponseEntity<Collection<Language>> getAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to get all languages with IDs: {}", ids);

        Collection<LanguageEntity> languages = service.getAllWithIds(ids);

        if (!languages.isEmpty()) {
            return ResponseEntity.ok(toLanguages(languages));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET, params = "name")
    public ResponseEntity<Language> getByName(@RequestParam("name") String name) {
        log.trace("Received request to get language by name: \"{}\"", name);

        Optional<LanguageEntity> optionalLanguage = service.getByName(name);

        if (optionalLanguage.isPresent()) {
            return ResponseEntity.ok(toLanguage(optionalLanguage.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<Language>> getAll() {
        log.trace("Received request tog et all languages");

        Collection<LanguageEntity> languages = service.getAll();

        if (!languages.isEmpty()) {
            return ResponseEntity.ok(toLanguages(languages));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Language language) {
        log.trace("Received request to update language with ID {}: {}", id, language);

        Optional<LanguageEntity> optionalUpdatedLanguage = service.update(id, toLanguage(language));

        if (optionalUpdatedLanguage.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.trace("Received request to delete language by ID: {}", id);

        Optional<Integer> optionalDeletedLanguage = service.deleteById(id);

        if (optionalDeletedLanguage.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE, params = "ids")
    public ResponseEntity<Void> deleteAllWithIds(@RequestParam("ids") Collection<Integer> ids) {
        log.trace("Received request to delete all languages with IDs: {}", ids);

        Collection<Integer> deletedLanguageIds = service.deleteAllWithIds(ids);

        if (!deletedLanguageIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Void> deleteAll() {
        log.trace("Received request to delete all languages");

        Collection<Integer> deletedLanguagesIds = service.deleteAll();

        if (!deletedLanguagesIds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
