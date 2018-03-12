package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.RatingEntity;
import com.wilson.movie.library.repository.RatingRepository;
import com.wilson.movie.library.service.impl.factories.RandomValueFactory;
import com.wilson.movie.library.service.impl.factories.RatingEntityFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link RatingServiceImpl}.
 *
 * @author Zach Wilson
 */
@RunWith(MockitoJUnitRunner.class)
public class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl service;

    @Mock
    private RatingRepository repository;

    /**
     * Tests {@link RatingServiceImpl#create(RatingEntity)}.
     */
    @Test
    public void create() {
        RatingEntity expected = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        RatingEntity actual = service.create(expected);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Tests {@link RatingServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById() {
        RatingEntity expected = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<RatingEntity> optionalEntity = service.getById(expected.getId());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertRating(actual, expected));
    }

    /**
     * Tests {@link RatingServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById_whereRatingDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<RatingEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link RatingServiceImpl#getByName(String)}.
     */
    @Test
    public void getByName() {
        RatingEntity expected = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.findByName(expected.getName())).thenReturn(expected);

        Optional<RatingEntity> optionalEntity = service.getByName(expected.getName());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertRating(actual, expected));
    }

    /**
     * Tests {@link RatingServiceImpl#getByName(String)}.
     */
    @Test
    public void getByName_whereRatingDoesNotExist() {
        String name = RatingEntityFactory.generateRandomRatingName();

        Mockito.when(repository.findByName(name)).thenReturn(null);

        Optional<RatingEntity> optionalEntity = service.getByName(name);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link RatingServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<RatingEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            RatingEntity entity = RatingEntityFactory.generateRandomRating();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedEntities);

        List<RatingEntity> actualEntities = new ArrayList<>(service.getAllWithIds(ids));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertRating(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link RatingServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds_whereRatingsDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        Collection<RatingEntity> actualEntities = service.getAllWithIds(ids);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link RatingServiceImpl#getAll()}.
     */
    @Test
    public void getAll() {
        List<RatingEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            expectedEntities.add(RatingEntityFactory.generateRandomRating());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedEntities);

        List<RatingEntity> actualEntities = new ArrayList<>(service.getAll());

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertRating(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link RatingServiceImpl#getAll()}.
     */
    @Test
    public void getAll_whereRatingsDoNotExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Collection<RatingEntity> actualEntities = service.getAll();

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link RatingServiceImpl#update(Integer, RatingEntity)}.
     */
    @Test
    public void update() {
        RatingEntity expected = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        Optional<RatingEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), expected);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertRating(actual, expected));
    }

    /**
     * Tests {@link RatingServiceImpl#update(Integer, RatingEntity)}.
     */
    @Test
    public void update_whereRatingDoesNotExist() {
        RatingEntity entity = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.save(entity)).thenReturn(null);

        Optional<RatingEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), entity);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link RatingServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        RatingEntity expected = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<Integer> optionalId = service.deleteById(expected.getId());

        assertThat(optionalId.isPresent());
        optionalId.ifPresent((actualId) -> assertThat(actualId).isEqualTo(expected.getId()));
    }

    /**
     * Tests {@link RatingServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById_whereTvShowDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<Integer> optionalId = service.deleteById(id);

        assertThat(optionalId.isPresent()).isFalse();
    }

    /**
     * Tests {@link RatingServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<RatingEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            RatingEntity entity = RatingEntityFactory.generateRandomRating();
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
     * Tests {@link RatingServiceImpl#deleteAllWithIds(Collection)}.
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
     * Tests {@link RatingServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll() {
        List<Integer> ids = new ArrayList<>();
        List<RatingEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RatingEntity entity = RatingEntityFactory.generateRandomRating();
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
     * Tests {@link RatingServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll_whereNoTvShowExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link RatingServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_exists() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(true);

        assertThat(service.exists(id)).isTrue();
    }

    /**
     * Tests {@link RatingServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_doesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(false);

        assertThat(service.exists(id)).isFalse();
    }

    /**
     * Tests {@link RatingServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withName_exists() {
        RatingEntity entity = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.findByName(entity.getName())).thenReturn(entity);

        assertThat(service.exists(entity.getName())).isTrue();
    }

    /**
     * Tests {@link RatingServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withName_doesNotExist() {
        RatingEntity entity = RatingEntityFactory.generateRandomRating();

        Mockito.when(repository.findByName(entity.getName())).thenReturn(null);

        assertThat(service.exists(entity.getName())).isFalse();
    }

    /**
     * Asserts that the fields of a given <i>actual</i> {@link RatingEntity} match the fields of the
     * <i>expected</i> {@link RatingEntity}.
     *
     * @param actual the <i>actual</i> rating to be compared to the expected rating.
     * @param expected the <i>expected</i> rating to be compared to the actual rating.
     */
    private static void assertRating(RatingEntity actual, RatingEntity expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

}
