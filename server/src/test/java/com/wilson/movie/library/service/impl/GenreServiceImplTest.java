package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.GenreEntity;
import com.wilson.movie.library.repository.GenreRepository;
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
 * Tests {@link GenreServiceImpl}.
 *
 * @author Zach Wilson
 */
@RunWith(MockitoJUnitRunner.class)
public class GenreServiceImplTest {

    @InjectMocks
    private GenreServiceImpl service;

    @Mock
    private GenreRepository repository;

    /**
     * Tests {@link GenreServiceImpl#create(GenreEntity)}.
     */
    @Test
    public void create() {
        GenreEntity expected = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        GenreEntity actual = service.create(expected);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Tests {@link GenreServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById() {
        GenreEntity expected = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<GenreEntity> optionalEntity = service.getById(expected.getId());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertGenre(actual, expected));
    }

    /**
     * Tests {@link GenreServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById_whereGenreDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<GenreEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link GenreServiceImpl#getByName(String)}.
     */
    @Test
    public void getByName() {
        GenreEntity expected = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.findByName(expected.getName())).thenReturn(expected);

        Optional<GenreEntity> optionalEntity = service.getByName(expected.getName());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertGenre(actual, expected));
    }

    /**
     * Tests {@link GenreServiceImpl#getByName(String)}.
     */
    @Test
    public void getByName_whereGenreDoesNotExist() {
        String name = IdentityEntityFactory.generateRandomIdentityName();

        Mockito.when(repository.findByName(name)).thenReturn(null);

        Optional<GenreEntity> optionalEntity = service.getByName(name);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link GenreServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<GenreEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            GenreEntity entity = IdentityEntityFactory.generateRandomGenre();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedEntities);

        List<GenreEntity> actualEntities = new ArrayList<>(service.getAllWithIds(ids));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertGenre(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link GenreServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds_whereGenresDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        Collection<GenreEntity> actualEntities = service.getAllWithIds(ids);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link GenreServiceImpl#getAll()}.
     */
    @Test
    public void getAll() {
        List<GenreEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            expectedEntities.add(IdentityEntityFactory.generateRandomGenre());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedEntities);

        List<GenreEntity> actualEntities = new ArrayList<>(service.getAll());

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertGenre(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link GenreServiceImpl#getAll()}.
     */
    @Test
    public void getAll_whereGenresDoNotExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Collection<GenreEntity> actualEntities = service.getAll();

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link GenreServiceImpl#update(Integer, GenreEntity)}.
     */
    @Test
    public void update() {
        GenreEntity expected = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        Optional<GenreEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), expected);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertGenre(actual, expected));
    }

    /**
     * Tests {@link GenreServiceImpl#update(Integer, GenreEntity)}.
     */
    @Test
    public void update_whereGenreDoesNotExist() {
        GenreEntity entity = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.save(entity)).thenReturn(null);

        Optional<GenreEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), entity);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link GenreServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        GenreEntity expected = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<Integer> optionalId = service.deleteById(expected.getId());

        assertThat(optionalId.isPresent());
        optionalId.ifPresent((actualId) -> assertThat(actualId).isEqualTo(expected.getId()));
    }

    /**
     * Tests {@link GenreServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById_whereTvShowDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<Integer> optionalId = service.deleteById(id);

        assertThat(optionalId.isPresent()).isFalse();
    }

    /**
     * Tests {@link GenreServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<GenreEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            GenreEntity entity = IdentityEntityFactory.generateRandomGenre();
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
     * Tests {@link GenreServiceImpl#deleteAllWithIds(Collection)}.
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
     * Tests {@link GenreServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll() {
        List<Integer> ids = new ArrayList<>();
        List<GenreEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GenreEntity entity = IdentityEntityFactory.generateRandomGenre();
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
     * Tests {@link GenreServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll_whereNoTvShowExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link GenreServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_exists() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(true);

        assertThat(service.exists(id)).isTrue();
    }

    /**
     * Tests {@link GenreServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_doesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(false);

        assertThat(service.exists(id)).isFalse();
    }

    /**
     * Tests {@link GenreServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withName_exists() {
        GenreEntity entity = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.findByName(entity.getName())).thenReturn(entity);

        assertThat(service.exists(entity.getName())).isTrue();
    }

    /**
     * Tests {@link GenreServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withName_doesNotExist() {
        GenreEntity entity = IdentityEntityFactory.generateRandomGenre();

        Mockito.when(repository.findByName(entity.getName())).thenReturn(null);

        assertThat(service.exists(entity.getName())).isFalse();
    }

    /**
     * Asserts that the fields of a given <i>actual</i> {@link GenreEntity} match the fields of the
     * <i>expected</i> {@link GenreEntity}.
     *
     * @param actual the <i>actual</i> genre to be compared to the expected genre.
     * @param expected the <i>expected</i> genre to be compared to the actual genre.
     */
    private static void assertGenre(GenreEntity actual, GenreEntity expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

}
