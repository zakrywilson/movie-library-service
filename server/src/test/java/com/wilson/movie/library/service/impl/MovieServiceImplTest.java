package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.Nullable;
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
        MovieEntity expected = generateRandomMovie();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        MovieEntity actual = service.create(expected);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Tests {@link MovieServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById() {
        int id = generateRandomId();
        MovieEntity expected = generateRandomMovie();

        Mockito.when(repository.findOne(id)).thenReturn(expected);

        Optional<MovieEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertMovie(actual, expected));
    }

    /**
     * Tests {@link MovieServiceImpl#getById(Integer)}.
     */
    @Test
    public void getById_whereMovieDoesNotExist() {
        int id = generateRandomId();

        Mockito.when(repository.findOne(id)).thenReturn(null);

        Optional<MovieEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#getByTitle(String)}.
     */
    @Test
    public void getByTitle() {
        MovieEntity expected = generateRandomMovie();

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
        String title = generateRandomMovieTitle();

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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            movie.setReleaseDate(releaseDate);
            expectedEntities.add(movie);
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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            movie.setReleaseDate(LocalDate.ofEpochDay(releaseDate.toEpochDay()));
            expectedEntities.add(movie);
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
        String studio = generateRandomStudio();
        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            movie.setStudio(studio);
            expectedEntities.add(movie);
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
        String studio = generateRandomStudio();

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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            ids.add(generateRandomId());
        }

        List<MovieEntity> expectedEntities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            MovieEntity movie = generateRandomMovie();
            expectedEntities.add(movie);
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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            ids.add(generateRandomId());
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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            expectedEntities.add(movie);
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
        MovieEntity expected = generateRandomMovie();

        Mockito.when(repository.save(expected)).thenReturn(expected);

        Optional<MovieEntity> optionalEntity = service.update(generateRandomId(), expected);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((actual) -> assertMovie(actual, expected));
    }

    /**
     * Tests {@link MovieServiceImpl#update(Integer, MovieEntity)}.
     */
    @Test
    public void update_whereMovieDoesNotExist() {
        MovieEntity entity = generateRandomMovie();

        Mockito.when(repository.save(entity)).thenReturn(null);

        Optional<MovieEntity> optionalEntity = service.update(generateRandomId(), entity);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        MovieEntity expected = generateRandomMovie();

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
        int id = generateRandomId();

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
        for (int i = 0; i < 20; i++) {
            MovieEntity entity = generateRandomMovie();
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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            ids.add(generateRandomId());
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
            MovieEntity entity = generateRandomMovie();
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
        int id = generateRandomId();

        Mockito.when(repository.exists(id)).thenReturn(true);

        assertThat(service.exists(id)).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(Integer)}.
     */
    @Test
    public void exists_withId_doesNotExist() {
        int id = generateRandomId();

        Mockito.when(repository.exists(id)).thenReturn(false);

        assertThat(service.exists(id)).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_exists() {
        MovieEntity entity = generateRandomMovie();

        Mockito.when(repository.findByTitle(entity.getTitle())).thenReturn(entity);

        assertThat(service.exists(entity.getTitle())).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_doesNotExist() {
        MovieEntity entity = generateRandomMovie();

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

    /**
     * Generates a new {@code MovieEntity}.
     *
     * @return a new movie.
     *
     * @see #generateRandomMovieTitle()
     * @see #generateRandomStudio()
     */
    private static MovieEntity generateRandomMovie() {
        MovieEntity movie = new MovieEntity(generateRandomMovieTitle(),
                                            LocalDate.now(),
                                            generateRandomStudio(),
                                            generateRandomPlotSummary(),
                                            generateRandomNotes());
        movie.setId(generateRandomId());
        return movie;
    }

    /**
     * Generates a new integer between 1 and 10,000.
     *
     * @return a new integer.
     */
    private static int generateRandomId() {
        return rand.nextInt(10_000) + 1; // Offset by +1 since Random#nextInt(int) is exclusive.
    }

    /**
     * Generates a random string between 1 and 20 characters, inclusive.
     *
     * @return a new string.
     */
    private static String generateRandomMovieTitle() {
        return generateRandomString(1, 20);
    }

    /**
     * Generates a random string between 1 and 1,000 characters, inclusive.
     *
     * @return a new string.
     */
    private static String generateRandomStudio() {
        return generateRandomString(1, 1_000);
    }

    /**
     * Return either null or generates a random string between 1 and 1,024 characters, inclusive.
     * <p>
     * A random boolean is generated to determine whether a null should be returned or a string
     * should be generated.
     *
     * @return a new string or null.
     */
    @Nullable
    private static String generateRandomPlotSummary() {
        return rand.nextBoolean() ? generateRandomString(0, rand.nextInt(1024)) : null;
    }

    /**
     * Return either null or generates a random string between 1 and 4,096 characters, inclusive.
     * <p>
     * A random boolean is generated to determine whether a null should be returned or a string
     * should be generated.
     *
     * @return a new string or null.
     */
    @Nullable
    private static String generateRandomNotes() {
        return rand.nextBoolean() ? generateRandomString(0, rand.nextInt(4096)) : null;
    }

    /**
     * Generates a random string between {@code maxCharacters} and {@code minCharacters}.
     *
     * @param minCharacters the minimum number of characters in the returned string, inclusive.
     * @param maxCharacters the maximum number of characters in the returned string, inclusive.
     * @return a random string of characters between {@code minCharacters} and {@code maxCharacters}.
     */
    private static String generateRandomString(int minCharacters, int maxCharacters) {
        // Offset by +1 since Random#nextInt(int) is exclusive on the upper bound and this method
        // is inclusive.
        int characterCount = rand.nextInt(maxCharacters - minCharacters + 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < characterCount; i++) {
            sb.append(getRandomCharacter());
        }

        return sb.toString();
    }

    /**
     * Returns a random alphanumeric character (plus some punctuation characters).
     *
     * @return a random character.
     */
    private static char getRandomCharacter() {
        return acceptableChars[rand.nextInt(acceptableChars.length)];
    }

    private static final Random rand = new Random(System.nanoTime());
    private static final char[] acceptableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 .,:;-_".toCharArray();

}