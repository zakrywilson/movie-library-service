package com.wilson.movie.library.resource.utils;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.domain.RatingEntity;
import com.wilson.movie.library.domain.TvShowEntity;
import com.wilson.movie.library.resource.model.Movie;
import com.wilson.movie.library.resource.model.Rating;
import com.wilson.movie.library.resource.model.TvShow;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Adapts shared DTO (data transfer objects) to local JPA entities.
 *
 * @author Zach Wilson
 */
public final class Adapters {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private Adapters() {
    }

    @Nullable
    public static Movie toMovie(@Nullable MovieEntity movie) {
        if (movie == null) {
            return null;
        }

        return Movie.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .studio(movie.getStudio())
                .rating(movie.getRating().getName())
                .plotSummary(movie.getPlotSummary())
                .notes(movie.getNotes())
                .build();
    }

    @Nullable
    public static MovieEntity toMovie(@Nullable Movie movie, @Nullable Rating rating) {
        if (movie == null || rating == null) {
            return null;
        }

        return new MovieEntity(movie.getTitle(),
                               movie.getReleaseDate(),
                               movie.getStudio(),
                               toRating(rating),
                               movie.getPlotSummary(),
                               movie.getNotes());
    }

    @Nonnull
    public static Collection<Movie> toMovies(@Nonnull Collection<MovieEntity> movies) {
        return movies.stream().map(Adapters::toMovie).collect(Collectors.toList());
    }

    @Nullable
    public static TvShow toTvShow(@Nullable TvShowEntity tvShow) {
        if (tvShow == null) {
            return null;
        }

        return TvShow.builder()
                .id(tvShow.getId())
                .title(tvShow.getTitle())
                .dateAired(tvShow.getDateAired())
                .network(tvShow.getNetwork())
                .rating(tvShow.getRating().getName())
                .plotSummary(tvShow.getPlotSummary())
                .series(tvShow.isSeries())
                .build();
    }

    @Nullable
    public static TvShowEntity toTvShow(@Nullable TvShow tvShow, @Nullable Rating rating) {
        if (tvShow == null || rating == null) {
            return null;
        }

        return new TvShowEntity(tvShow.getTitle(),
                                tvShow.getDateAired(),
                                tvShow.getNetwork(),
                                toRating(rating),
                                tvShow.getPlotSummary(),
                                tvShow.isSeries());
    }

    @Nonnull
    public static Collection<TvShow> toTvShows(@Nonnull Collection<TvShowEntity> tvShows) {
        return tvShows.stream().map(Adapters::toTvShow).collect(Collectors.toList());
    }

    @Nullable
    public static Rating toRating(@Nullable RatingEntity rating) {
        if (rating == null) {
            return null;
        }

        return Rating.builder()
                .id(rating.getId())
                .name(rating.getName())
                .description(rating.getDescription())
                .build();
    }

    @Nullable
    public static RatingEntity toRating(@Nullable Rating rating) {
        if (rating == null) {
            return null;
        }

        return new RatingEntity(rating.getName(), rating.getDescription());
    }

    @Nonnull
    public static Collection<Rating> toRatings(@Nonnull Collection<RatingEntity> ratings) {
        return ratings.stream().map(Adapters::toRating).collect(Collectors.toList());
    }

}
