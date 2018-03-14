package com.wilson.movie.library.service.impl.factories;

import com.wilson.movie.library.domain.GenreEntity;
import com.wilson.movie.library.domain.LanguageEntity;
import com.wilson.movie.library.domain.RatingEntity;

/**
 * Factory for creating random <i>identity</i> entities.
 *
 * @author Zach Wilson
 */
public final class IdentityEntityFactory {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private IdentityEntityFactory() {
    }

    /**
     * Generates a random string between 1 and 100 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomIdentityName() {
        return RandomValueFactory.generateRandomString(1, 100);
    }

    /**
     * Generates a random string between 1 and 200 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomIdentityDescription() {
        return RandomValueFactory.generateRandomString(1, 200);
    }

    /**
     * Generates a random {@link LanguageEntity}.
     *
     * @return a new language.
     */
    public static LanguageEntity generateRandomLanguage() {
        LanguageEntity genre = new LanguageEntity(
                RandomValueFactory.generateRandomString(1, 100),
                RandomValueFactory.generateRandomString(1, 200));
        genre.setId(RandomValueFactory.nextIntId());
        return genre;
    }

    /**
     * Generates a random {@link GenreEntity}.
     *
     * @return a new genre.
     */
    public static GenreEntity generateRandomGenre() {
        GenreEntity genre = new GenreEntity(
                RandomValueFactory.generateRandomString(1, 100),
                RandomValueFactory.generateRandomString(1, 200));
        genre.setId(RandomValueFactory.nextIntId());
        return genre;
    }

    /**
     * Generates a random {@link RatingEntity}.
     *
     * @return a new rating.
     */
    public static RatingEntity generateRandomRating() {
        RatingEntity rating = new RatingEntity(
                RandomValueFactory.generateRandomString(1, 100),
                RandomValueFactory.generateRandomString(1, 200));
        rating.setId(RandomValueFactory.nextIntId());
        return rating;
    }

}
