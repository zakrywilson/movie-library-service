package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.LanguageEntity;
import com.wilson.movie.library.repository.LanguageRepository;
import com.wilson.movie.library.service.impl.factories.IdentityEntityFactory;
import com.wilson.movie.library.service.impl.factories.RandomValueFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link LanguageServiceImpl}.
 *
 * @author Zach Wilson
 */
@RunWith(MockitoJUnitRunner.class)
public class LanguageServiceImplTest {

    @InjectMocks
    private LanguageServiceImpl service;

    @Mock
    private LanguageRepository repository;

    /**
     * Tests {@link LanguageServiceImpl#create(LanguageEntity)}.
     */
    @Test
    public void create() {
        LanguageEntity expected = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        LanguageEntity actual = service.create(expected);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Tests {@link LanguageServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById() {
        LanguageEntity expected = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<LanguageEntity> optionalEntity = service.getById(expected.getId());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertLanguage(actual, expected));
    }

    /**
     * Tests {@link LanguageServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById_whereLanguageDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<LanguageEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link LanguageServiceImpl#getByName(String)}.
     */
    @Test
    public void getByName() {
        LanguageEntity expected = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.findByName(expected.getName())).thenReturn(expected);

        Optional<LanguageEntity> optionalEntity = service.getByName(expected.getName());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertLanguage(actual, expected));
    }

    /**
     * Tests {@link LanguageServiceImpl#getByName(String)}.
     */
    @Test
    public void getByName_whereLanguageDoesNotExist() {
        String name = IdentityEntityFactory.generateRandomIdentityName();

        Mockito.when(repository.findByName(name)).thenReturn(null);

        Optional<LanguageEntity> optionalEntity = service.getByName(name);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link LanguageServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<LanguageEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            LanguageEntity entity = IdentityEntityFactory.generateRandomLanguage();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedEntities);

        List<LanguageEntity> actualEntities = new ArrayList<>(service.getAllWithIds(ids));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertLanguage(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link LanguageServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds_whereLanguagesDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        Collection<LanguageEntity> actualEntities = service.getAllWithIds(ids);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link LanguageServiceImpl#getAll()}.
     */
    @Test
    public void getAll() {
        List<LanguageEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            expectedEntities.add(IdentityEntityFactory.generateRandomLanguage());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedEntities);

        List<LanguageEntity> actualEntities = new ArrayList<>(service.getAll());

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertLanguage(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link LanguageServiceImpl#getAll()}.
     */
    @Test
    public void getAll_whereLanguagesDoNotExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Collection<LanguageEntity> actualEntities = service.getAll();

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link LanguageServiceImpl#update(Integer, LanguageEntity)}.
     */
    @Test
    public void update() {
        LanguageEntity expected = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        Optional<LanguageEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), expected);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertLanguage(actual, expected));
    }

    /**
     * Tests {@link LanguageServiceImpl#update(Integer, LanguageEntity)}.
     */
    @Test
    public void update_whereLanguageDoesNotExist() {
        LanguageEntity entity = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.save(entity)).thenReturn(null);

        Optional<LanguageEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), entity);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link LanguageServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        LanguageEntity expected = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<Integer> optionalId = service.deleteById(expected.getId());

        assertThat(optionalId.isPresent());
        optionalId.ifPresent((actualId) -> assertThat(actualId).isEqualTo(expected.getId()));
    }

    /**
     * Tests {@link LanguageServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById_whereTvShowDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<Integer> optionalId = service.deleteById(id);

        assertThat(optionalId.isPresent()).isFalse();
    }

    /**
     * Tests {@link LanguageServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<LanguageEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            LanguageEntity entity = IdentityEntityFactory.generateRandomLanguage();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedEntities);

        List<Integer> actualIds = new ArrayList<>(service.deleteAllWithIds(ids));

        // Sort the collections so that the proceeding loop will compare IDs in the right order.
        Collections.sort(ids);
        Collections.sort(actualIds);

        assertThat(actualIds.isEmpty()).isFalse();
        for (int i = 0; i < actualIds.size(); i++) {
            assertThat(actualIds.get(i)).isEqualTo(ids.get(i));
        }
    }

    /**
     * Tests {@link LanguageServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds_whereTvShowDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAllWithIds(ids));

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link LanguageServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll() {
        List<Integer> ids = new ArrayList<>();
        List<LanguageEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            LanguageEntity entity = IdentityEntityFactory.generateRandomLanguage();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedEntities);

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        // Sort the collections so that the proceeding loop will compare IDs in the right order.
        Collections.sort(ids);
        Collections.sort(actualIds);

        assertThat(actualIds.isEmpty()).isFalse();
        for (int i = 0; i < actualIds.size(); i++) {
            assertThat(actualIds.get(i)).isEqualTo(ids.get(i));
        }
    }

    /**
     * Tests {@link LanguageServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll_whereNoTvShowExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link LanguageServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_exists() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(true);

        assertThat(service.exists(id)).isTrue();
    }

    /**
     * Tests {@link LanguageServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_doesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(false);

        assertThat(service.exists(id)).isFalse();
    }

    /**
     * Tests {@link LanguageServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withName_exists() {
        LanguageEntity entity = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.findByName(entity.getName())).thenReturn(entity);

        assertThat(service.exists(entity.getName())).isTrue();
    }

    /**
     * Tests {@link LanguageServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withName_doesNotExist() {
        LanguageEntity entity = IdentityEntityFactory.generateRandomLanguage();

        Mockito.when(repository.findByName(entity.getName())).thenReturn(null);

        assertThat(service.exists(entity.getName())).isFalse();
    }

    /**
     * Asserts that the fields of a given <i>actual</i> {@link LanguageEntity} match the fields of
     * the <i>expected</i> {@link LanguageEntity}.
     *
     * @param actual the <i>actual</i> language to be compared to the expected language.
     * @param expected the <i>expected</i> language to be compared to the actual language.
     */
    private static void assertLanguage(LanguageEntity actual, LanguageEntity expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

}
