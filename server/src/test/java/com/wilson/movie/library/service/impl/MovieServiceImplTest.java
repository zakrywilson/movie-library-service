package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.repository.MovieRepository;
import com.wilson.movie.library.service.impl.factories.MovieEntityFactory;
import com.wilson.movie.library.service.impl.factories.RandomValueFactory;
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
 * Tests {@link MovieServiceImpl}.
 *
 * @author Zach Wilson
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl service;

    @Mock
    private MovieRepository repository;

    /**
     * Tests {@link MovieServiceImpl#create(MovieEntity)}.
     */
    @Test
    public void create() {
        MovieEntity expected = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        MovieEntity actual = service.create(expected);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Tests {@link MovieServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById() {
        MovieEntity expected = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<MovieEntity> optionalEntity = service.getById(expected.getId());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertMovie(actual, expected));
    }

    /**
     * Tests {@link MovieServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById_whereMovieDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<MovieEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#getByTitle(String)}.
     */
    @Test
    public void getByTitle() {
        MovieEntity expected = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.findByTitle(expected.getTitle())).thenReturn(expected);

        Optional<MovieEntity> optionalEntity = service.getByTitle(expected.getTitle());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertMovie(actual, expected));
    }

    /**
     * Tests {@link MovieServiceImpl#getByTitle(String)}.
     */
    @Test
    public void getByTitle_whereMovieDoesNotExist() {
        String title = MovieEntityFactory.generateRandomMovieTitle();

        Mockito.when(repository.findByTitle(title)).thenReturn(null);

        Optional<MovieEntity> optionalEntity = service.getByTitle(title);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(LocalDate)}.
     */
    @Test
    public void getAllByReleaseDate_withLocalDate() {
        LocalDate releaseDate = LocalDate.now();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            MovieEntity entity = MovieEntityFactory.generateRandomMovie();
            entity.setReleaseDate(releaseDate);
            expectedEntities.add(entity);
        }

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(expectedEntities);

        List<MovieEntity> actualEntities = new ArrayList<>(service.getAllByReleaseDate(releaseDate));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertMovie(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(LocalDate)}.
     */
    @Test
    public void getAllByReleaseDate_withLocalDate_whereMoviesDoNotExist() {
        LocalDate releaseDate = LocalDate.now();

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualEntities = service.getAllByReleaseDate(releaseDate);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(Integer)}.
     */
    @Test
    public void getAllByReleaseDate_withIntEpochDay() {
        LocalDate releaseDate = LocalDate.now();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            MovieEntity entity = MovieEntityFactory.generateRandomMovie();
            entity.setReleaseDate(LocalDate.ofEpochDay(releaseDate.toEpochDay()));
            expectedEntities.add(entity);
        }

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(expectedEntities);

        List<MovieEntity> actualEntities = new ArrayList<>(service.getAllByReleaseDate((int) releaseDate.toEpochDay()));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertMovie(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(Integer)}.
     */
    @Test
    public void getAllByReleaseDate_withIntEpochDay_whereMoviesDoNotExist() {
        LocalDate releaseDate = LocalDate.now();

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualEntities = service.getAllByReleaseDate((int) releaseDate.toEpochDay());

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByStudio(String)}.
     */
    @Test
    public void getAllByStudio() {
        String studio = MovieEntityFactory.generateRandomStudio();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            MovieEntity entity = MovieEntityFactory.generateRandomMovie();
            entity.setStudio(studio);
            expectedEntities.add(entity);
        }

        Mockito.when(repository.findAllByStudio(studio)).thenReturn(expectedEntities);

        List<MovieEntity> actualEntities = new ArrayList<>(service.getAllByStudio(studio));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertMovie(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByStudio(String)}.
     */
    @Test
    public void getAllByStudio_whereMoviesDoNotExit() {
        String studio = MovieEntityFactory.generateRandomStudio();

        Mockito.when(repository.findAllByStudio(studio)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualEntities = service.getAllByStudio(studio);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            MovieEntity entity = MovieEntityFactory.generateRandomMovie();
            expectedEntities.add(entity);
            ids.add(entity.getId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedEntities);

        List<MovieEntity> actualEntities = new ArrayList<>(service.getAllWithIds(ids));

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertMovie(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllWithIds(Collection)}.
     */
    @Test
    public void getAllWithIds_whereMoviesDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualEntities = service.getAllWithIds(ids);

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAll()}.
     */
    @Test
    public void getAll() {
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            expectedEntities.add(MovieEntityFactory.generateRandomMovie());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedEntities);

        List<MovieEntity> actualEntities = new ArrayList<>(service.getAll());

        assertThat(actualEntities.size()).isEqualTo(expectedEntities.size());
        for (int i = 0; i < actualEntities.size(); i++) {
            assertMovie(actualEntities.get(i), expectedEntities.get(i));
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAll()}.
     */
    @Test
    public void getAll_whereMoviesDoNotExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualEntities = service.getAll();

        assertThat(actualEntities.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#update(Integer, MovieEntity)}.
     */
    @Test
    public void update() {
        MovieEntity expected = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        Optional<MovieEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), expected);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertMovie(actual, expected));
    }

    /**
     * Tests {@link MovieServiceImpl#update(Integer, MovieEntity)}.
     */
    @Test
    public void update_whereMovieDoesNotExist() {
        MovieEntity entity = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.save(entity)).thenReturn(null);

        Optional<MovieEntity> optionalEntity = service.update(RandomValueFactory.nextIntId(), entity);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        MovieEntity expected = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.findOne(expected.getId())).thenReturn(expected);

        Optional<Integer> optionalId = service.deleteById(expected.getId());

        assertThat(optionalId.isPresent());
        optionalId.ifPresent((actualId) -> assertThat(actualId).isEqualTo(expected.getId()));
    }

    /**
     * Tests {@link MovieServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById_whereMovieDoesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<Integer> optionalId = service.deleteById(id);

        assertThat(optionalId.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds() {
        List<Integer> ids = new ArrayList<>();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            MovieEntity entity = MovieEntityFactory.generateRandomMovie();
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
     * Tests {@link MovieServiceImpl#deleteAllWithIds(Collection)}.
     */
    @Test
    public void deleteAllWithIds_whereMoviesDoNotExist() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < RandomValueFactory.nextInt(20) + 1; i++) {
            ids.add(RandomValueFactory.nextIntId());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAllWithIds(ids));

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll() {
        List<Integer> ids = new ArrayList<>();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MovieEntity entity = MovieEntityFactory.generateRandomMovie();
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
     * Tests {@link MovieServiceImpl#deleteAll()}.
     */
    @Test
    public void deleteAll_whereNoMoviesExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        assertThat(actualIds.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_exists() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(true);

        assertThat(service.exists(id)).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_doesNotExist() {
        int id = RandomValueFactory.nextIntId();

        Mockito.when(repository.exists(id)).thenReturn(false);

        assertThat(service.exists(id)).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_exists() {
        MovieEntity entity = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.findByTitle(entity.getTitle())).thenReturn(entity);

        assertThat(service.exists(entity.getTitle())).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_doesNotExist() {
        MovieEntity entity = MovieEntityFactory.generateRandomMovie();

        Mockito.when(repository.findByTitle(entity.getTitle())).thenReturn(null);

        assertThat(service.exists(entity.getTitle())).isFalse();
    }

    /**
     * Asserts that the fields of a given <i>actual</i> {@link MovieEntity} match the fields of the
     * <i>expected</i> {@link MovieEntity}.
     *
     * @param actual the <i>actual</i> movie to be compared to the expected movie.
     * @param expected the <i>expected</i> movie to be compared to the actual movie.
     */
    private static void assertMovie(MovieEntity actual, MovieEntity expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getReleaseDate()).isEqualTo(expected.getReleaseDate());
        assertThat(actual.getStudio()).isEqualTo(expected.getStudio());
        assertThat(actual.getPlotSummary()).isEqualTo(expected.getPlotSummary());
        assertThat(actual.getNotes()).isEqualTo(expected.getNotes());
    }

}
