package com.wilson.movie.library.service.impl.factories;

import com.wilson.movie.library.domain.TvShowEntity;

import javax.annotation.Nonnull;
import java.time.LocalDate;

/**
 * Provides methods for creating random TV show JPA entities.
 *
 * @author Zach Wilson
 */
public final class TvShowEntityFactory {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private TvShowEntityFactory() {
    }

    /**
     * Generates a new {@code TvShowEntity}.
     *
     * @return a new TV show.
     *
     * @see #generateRandomTvShowTitle()
     * @see #generateRandomNetwork()
     * @see RatingEntityFactory#generateRandomRating()
     * @see #generateRandomPlotSummary()
     */
    public static TvShowEntity generateRandomTvShow() {
        TvShowEntity tvShow = new TvShowEntity(generateRandomTvShowTitle(),
                                               LocalDate.now(),
                                               generateRandomNetwork(),
                                               RatingEntityFactory.generateRandomRating(),
                                               generateRandomPlotSummary(),
                                               RandomValueFactory.nextBoolean());
        tvShow.setId(RandomValueFactory.nextIntId());
        return tvShow;
    }

    /**
     * Generates a random string between 1 and 20 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomTvShowTitle() {
        return RandomValueFactory.generateRandomString(1, 20);
    }

    /**
     * Generates a random string between 1 and 100 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomNetwork() {
        return RandomValueFactory.generateRandomString(1, 100);
    }

    /**
     * Generates a random string between 1 and 1,024 characters, inclusive.
     *
     * @return a new string.
     */
    @Nonnull
    public static String generateRandomPlotSummary() {
        return RandomValueFactory.generateRandomString(0, RandomValueFactory.nextInt(1024));
    }

}
