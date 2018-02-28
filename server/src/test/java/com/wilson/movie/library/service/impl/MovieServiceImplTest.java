package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.repository.MovieRepository;
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
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.findOne(id)).thenReturn(movie);

        Optional<MovieEntity> optionalEntity = service.getById(id);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((m) -> assertMovie(m, movie.getTitle(), movie.getReleaseDate(), movie.getStudio()));
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
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.findByTitle(movie.getTitle())).thenReturn(movie);

        Optional<MovieEntity> optionalEntity = service.getByTitle(movie.getTitle());

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((m) -> assertMovie(m, movie.getTitle(), movie.getReleaseDate(), movie.getStudio()));
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
        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            movie.setReleaseDate(releaseDate);
            expectedMovies.add(movie);
        }

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(expectedMovies);

        List<MovieEntity> actualMovies = new ArrayList<>(service.getAllByReleaseDate(releaseDate));

        assertThat(actualMovies.size()).isEqualTo(expectedMovies.size());
        for (int i = 0; i < actualMovies.size(); i++) {
            MovieEntity expected = expectedMovies.get(i);
            assertMovie(actualMovies.get(i), expected.getTitle(), expected.getReleaseDate(), expected.getStudio());
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(LocalDate)}.
     */
    @Test
    public void getAllByReleaseDate_withLocalDate_whereMoviesDoNotExist() {
        LocalDate releaseDate = LocalDate.now();

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualMovies = service.getAllByReleaseDate(releaseDate);

        assertThat(actualMovies.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(Integer)}.
     */
    @Test
    public void getAllByReleaseDate_withIntEpochDay() {
        LocalDate releaseDate = LocalDate.now();
        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            movie.setReleaseDate(LocalDate.ofEpochDay(releaseDate.toEpochDay()));
            expectedMovies.add(movie);
        }

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(expectedMovies);

        List<MovieEntity> actualMovies = new ArrayList<>(service.getAllByReleaseDate((int) releaseDate.toEpochDay()));

        assertThat(actualMovies.size()).isEqualTo(expectedMovies.size());
        for (int i = 0; i < actualMovies.size(); i++) {
            MovieEntity expected = expectedMovies.get(i);
            assertMovie(actualMovies.get(i), expected.getTitle(), expected.getReleaseDate(), expected.getStudio());
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByReleaseDate(Integer)}.
     */
    @Test
    public void getAllByReleaseDate_withIntEpochDay_whereMoviesDoNotExist() {
        LocalDate releaseDate = LocalDate.now();

        Mockito.when(repository.findAllByReleaseDate(releaseDate)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualMovies = service.getAllByReleaseDate((int) releaseDate.toEpochDay());

        assertThat(actualMovies.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByStudio(String)}.
     */
    @Test
    public void getAllByStudio() {
        String studio = generateRandomStudio();
        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            movie.setStudio(studio);
            expectedMovies.add(movie);
        }

        Mockito.when(repository.findAllByStudio(studio)).thenReturn(expectedMovies);

        List<MovieEntity> actualMovies = new ArrayList<>(service.getAllByStudio(studio));

        assertThat(actualMovies.size()).isEqualTo(expectedMovies.size());
        for (int i = 0; i < actualMovies.size(); i++) {
            MovieEntity expected = expectedMovies.get(i);
            assertMovie(actualMovies.get(i), expected.getTitle(), expected.getReleaseDate(), expected.getStudio());
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAllByStudio(String)}.
     */
    @Test
    public void getAllByStudio_whereMoviesDoNotExit() {
        String studio = generateRandomStudio();

        Mockito.when(repository.findAllByStudio(studio)).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualMovies = service.getAllByStudio(studio);

        assertThat(actualMovies.isEmpty()).isTrue();
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

        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            MovieEntity movie = generateRandomMovie();
            expectedMovies.add(movie);
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedMovies);

        List<MovieEntity> actualMovies = new ArrayList<>(service.getAllWithIds(ids));

        assertThat(actualMovies.size()).isEqualTo(expectedMovies.size());
        for (int i = 0; i < actualMovies.size(); i++) {
            MovieEntity expected = expectedMovies.get(i);
            assertMovie(actualMovies.get(i), expected.getTitle(), expected.getReleaseDate(), expected.getStudio());
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

        Collection<MovieEntity> actualMovies = service.getAllWithIds(ids);

        assertThat(actualMovies.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#getAll()}.
     */
    @Test
    public void getAll() {
        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            MovieEntity movie = generateRandomMovie();
            expectedMovies.add(movie);
        }

        Mockito.when(repository.findAll()).thenReturn(expectedMovies);

        List<MovieEntity> actualMovies = new ArrayList<>(service.getAll());

        assertThat(actualMovies.size()).isEqualTo(expectedMovies.size());
        for (int i = 0; i < actualMovies.size(); i++) {
            MovieEntity expected = expectedMovies.get(i);
            assertMovie(actualMovies.get(i), expected.getTitle(), expected.getReleaseDate(), expected.getStudio());
        }
    }

    /**
     * Tests {@link MovieServiceImpl#getAll()}.
     */
    @Test
    public void getAll_whereMoviesDoNotExist() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Collection<MovieEntity> actualMovies = service.getAll();

        assertThat(actualMovies.isEmpty()).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#update(Integer, MovieEntity)}.
     */
    @Test
    public void update() {
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.save(movie)).thenReturn(movie);

        Optional<MovieEntity> optionalEntity = service.update(generateRandomId(), movie);

        assertThat(optionalEntity.isPresent());
        optionalEntity.ifPresent((m) -> assertMovie(m, movie.getTitle(), movie.getReleaseDate(), movie.getStudio()));
    }

    /**
     * Tests {@link MovieServiceImpl#update(Integer, MovieEntity)}.
     */
    @Test
    public void update_whereMovieDoesNotExist() {
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.save(movie)).thenReturn(null);

        Optional<MovieEntity> optionalEntity = service.update(generateRandomId(), movie);

        assertThat(optionalEntity.isPresent()).isFalse();
    }

    /**
     * Tests {@link MovieServiceImpl#deleteById(Integer)}.
     */
    @Test
    public void deleteById() {
        int id = generateRandomId();
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.findOne(id)).thenReturn(movie);

        Optional<Integer> optionalId = service.deleteById(id);

        assertThat(optionalId.isPresent());
        optionalId.ifPresent((actualId) -> assertThat(actualId).isEqualTo(id));
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
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            ids.add(generateRandomId());
        }

        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            expectedMovies.add(generateRandomMovie());
        }

        Mockito.when(repository.findAllById(ids)).thenReturn(expectedMovies);

        List<Integer> actualIds = new ArrayList<>(service.deleteAllWithIds(ids));

        assertThat(actualIds.isEmpty()).isFalse();
        for (int i = 0; i < actualIds.size(); i++) {
            assertThat(actualIds.get(i)).isEqualTo(expectedMovies.get(i).getId());
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
        List<MovieEntity> expectedMovies = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(20) + 1; i++) {
            expectedMovies.add(generateRandomMovie());
        }

        Mockito.when(repository.findAll()).thenReturn(expectedMovies);

        List<Integer> actualIds = new ArrayList<>(service.deleteAll());

        assertThat(actualIds.isEmpty()).isFalse();
        for (int i = 0; i < actualIds.size(); i++) {
            assertThat(actualIds.get(i)).isEqualTo(expectedMovies.get(i).getId());
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
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.findByTitle(movie.getTitle())).thenReturn(movie);

        assertThat(service.exists(movie.getTitle())).isTrue();
    }

    /**
     * Tests {@link MovieServiceImpl#exists(String)}.
     */
    @Test
    public void exists_withTitle_doesNotExist() {
        MovieEntity movie = generateRandomMovie();

        Mockito.when(repository.findByTitle(movie.getTitle())).thenReturn(null);

        assertThat(service.exists(movie.getTitle())).isFalse();
    }

    /**
     * Asserts that the fields of a given {@link MovieEntity} match the specified fields.
     *
     * @param movie the movie to be compared to the other specified values.
     * @param title the title to compare to the title of {@code movie}.
     * @param releaseDate the release date to compare to the release date of {@code movie}.
     * @param studio the studio to compare to the studio of {@code movie}.
     */
    private static void assertMovie(MovieEntity movie, String title, LocalDate releaseDate, String studio) {
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getTitle()).isEqualTo(title);
        assertThat(movie.getReleaseDate()).isEqualTo(releaseDate);
        assertThat(movie.getStudio()).isEqualTo(studio);
        assertThat(movie.getNotes()).isNull();
        assertThat(movie.getPlotSummary()).isNull();
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
        return new MovieEntity(generateRandomMovieTitle(), LocalDate.now(), generateRandomStudio());
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