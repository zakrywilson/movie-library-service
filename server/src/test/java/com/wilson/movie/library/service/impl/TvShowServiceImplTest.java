package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.TvShowEntity;
import com.wilson.movie.library.repository.TvShowRepository;
import com.wilson.movie.library.service.impl.factories.RandomValueFactory;
import com.wilson.movie.library.service.impl.factories.TvShowEntityFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link TvShowServiceImpl}.
 *
 * @author Zach Wilson
 */
@RunWith(MockitoJUnitRunner.class)
public class TvShowServiceImplTest {

    @InjectMocks
    private TvShowServiceImpl service;

    @Mock
    private TvShowRepository repository;

    /**
     * Tests {@link TvShowServiceImpl#create(TvShowEntity)}.
     */
    @Test
    public void create() {
        TvShowEntity expected = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        TvShowEntity actual = service.create(expected);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Tests {@link TvShowServiceImpl#create(TvShowEntity)}.
     */
    @Test
    public void getById() {
        TvShowEntity expected = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<TvShowEntity> optionalEntity = service.getById(expected.getId());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertTvShow(actual, expected));
    }

    /**
     * Tests {@link TvShowServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById_whereTvShowDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<TvShowEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link TvShowServiceImpl#getByTitle(String)}.
     */
    @Test
    public void getByTitle() {
        TvShowEntity expected = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.findByTitle(expected.getTitle())).thenReturn(expected);

        Optional<TvShowEntity> optionalEntity = service.getByTitle(expected.getTitle());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertTvShow(actual, expected));
    }

    /**
     * Tests {@link TvShowServiceImpl#getByTitle(String)}.
     */
    @Test
    public void getByTitle_whereTvShowDoesNotExist() {
        String title = TvShowEntityFactory.generateRandomTvShowTitle();

        Mockito.when(repository.findByTitle(title)).thenReturn(null);

        Optional<TvShowEntity> optionalEntity = service.getByTitle(title);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllByDateAired(LocalDate)}.
     */
    @Test
    public void getAllByDateAired_withLocalDate() {
        LocalDate dateAired = LocalDate.now();
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();
            entity.setDateAired(dateAired);
            expectedEntities.add(entity);
        }

        Mockito.when(repository.findAllByDateAired(dateAired)).thenReturn(expectedEntities);

        List<TvShowEntity> actualEntities = new ArrayList<>(service.getAllByDateAired(dateAired));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertTvShow(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllByDateAired(LocalDate)}.
     */
    @Test
    public void getAllByDateAired_withLocalDate_whereTvShowsDoNotExist() {
        LocalDate dateAired = LocalDate.now();

        Mockito.when(repository.findAllByDateAired(dateAired)).thenReturn(new ArrayList<>());

        Collection<TvShowEntity> actualEntities = service.getAllByDateAired(dateAired);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllByDateAired(Integer)}.
     */
    @Test
    public void getAllByDateAired_withIntEpochDay() {
        LocalDate dateAired = LocalDate.now();
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();
            entity.setDateAired(LocalDate.ofEpochDay(dateAired.toEpochDay()));
            expectedEntities.add(entity);
        }

        Mockito.when(repository.findAllByDateAired(dateAired)).thenReturn(expectedEntities);

        List<TvShowEntity> actualEntities = new ArrayList<>(service.getAllByDateAired((int) dateAired.toEpochDay()));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertTvShow(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllByDateAired(Integer)}.
     */
    @Test
    public void getAllByDateAired_withIntEpochDay_whereTvShowsDoNotExist() {
        LocalDate dateAired = LocalDate.now();

        Mockito.when(repository.findAllByDateAired(dateAired)).thenReturn(new ArrayList<>());

        Collection<TvShowEntity> actualEntities = service.getAllByDateAired((int) dateAired.toEpochDay());

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllByNetwork(String)}.
     */
    @Test
    public void getAllByNetwork() {
        String network = TvShowEntityFactory.generateRandomNetwork();
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();
            entity.setNetwork(network);
            expectedEntities.add(entity);
        }

        Mockito.when(repository.findAllByNetwork(network)).thenReturn(expectedEntities);

        List<TvShowEntity> actualEntities = new ArrayList<>(service.getAllByNetwork(network));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertTvShow(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllByNetwork(String)}.
     */
    @Test
    public void getAllByNetwork_whereTvShowDoNotExit() {
        String network = TvShowEntityFactory.generateRandomNetwork();

        Mockito.when(repository.findAllByNetwork(network)).thenReturn(new ArrayList<>());

        Collection<TvShowEntity> actualEntities = service.getAllByNetwork(network);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedEntities);

        List<TvShowEntity> actualEntities = new ArrayList<>(service.getAllWithIds(ids));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertTvShow(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link TvShowServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds_whereTvShowDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        Collection<TvShowEntity> actualEntities = service.getAllWithIds(ids);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#getAll()}.
     */
    @Test
    public void getAll() {
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            expectedEntities.add(TvShowEntityFactory.generateRandomTvShow());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedEntities);

        List<TvShowEntity> actualEntities = new ArrayList<>(service.getAll());

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertTvShow(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link TvShowServiceImpl#getAll()}.
     */
    @Test
    public void getAll_whereTvShowDoNotExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Collection<TvShowEntity> actualEntities = service.getAll();

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#update(Integer, TvShowEntity)}.
     */
    @Test
    public void update() {
        TvShowEntity expected = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        Optional<TvShowEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), expected);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertTvShow(actual, expected));
    }

    /**
     * Tests {@link TvShowServiceImpl#update(Integer, TvShowEntity)}.
     */
    @Test
    public void update_whereTvShowDoesNotExist() {
        TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.save(entity)).thenReturn(null);

        Optional<TvShowEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), entity);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link TvShowServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        TvShowEntity expected = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<Integer> optionalId = service.deleteById(expected.getId());

        assertThat(optionalId.isPresent());
        optionalId.ifPresent((actualId) -> assertThat(actualId).isEqualTo(expected.getId()));
    }

    /**
     * Tests {@link TvShowServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById_whereTvShowDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<Integer> optionalId = service.deleteById(id);

        assertThat(optionalId.isPresent()).isFalse();
    }

    /**
     * Tests {@link TvShowServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();
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
     * Tests {@link TvShowServiceImpl#deleteAllWithIds(Collection)}.
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
     * Tests {@link TvShowServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll() {
        List<Integer> ids = new ArrayList<>();
        List<TvShowEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();
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
     * Tests {@link TvShowServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll_whereNoTvShowExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_exists() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(true);

        assertThat(service.exists(id)).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_doesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(false);

        assertThat(service.exists(id)).isFalse();
    }

    /**
     * Tests {@link TvShowServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_exists() {
        TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.findByTitle(entity.getTitle())).thenReturn(entity);

        assertThat(service.exists(entity.getTitle())).isTrue();
    }

    /**
     * Tests {@link TvShowServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_doesNotExist() {
        TvShowEntity entity = TvShowEntityFactory.generateRandomTvShow();

        Mockito.when(repository.findByTitle(entity.getTitle())).thenReturn(null);

        assertThat(service.exists(entity.getTitle())).isFalse();
    }

    /**
     * Asserts that the fields of a given <i>actual</i> {@link TvShowEntity} match the fields of the
     * <i>expected</i> {@link TvShowEntity}.
     *
     * @param actual the <i>actual</i> TV show to be compared to the expected TV show.
     * @param expected the <i>expected</i> TV show to be compared to the actual TV show.
     */
    private static void assertTvShow(TvShowEntity actual, TvShowEntity expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getDateAired()).isEqualTo(expected.getDateAired());
        assertThat(actual.getNetwork()).isEqualTo(expected.getNetwork());
        assertThat(actual.getRating()).isEqualTo(expected.getRating());
        assertThat(actual.getPlotSummary()).isEqualTo(expected.getPlotSummary());
        assertThat(actual.isSeries()).isEqualTo(expected.isSeries());
    }

}
