package com.wilson.movie.library.resource.utils;

import com.wilson.movie.library.domain.MovieEntity;
import com.wilson.movie.library.domain.TvShowEntity;
import com.wilson.movie.library.resource.model.Movie;
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
                .plotSummary(movie.getPlotSummary())
                .notes(movie.getNotes())
                .build();
    }

    @Nullable
    public static MovieEntity toMovieEntity(@Nullable Movie movie) {
        if (movie == null) {
            return null;
        }

        return new MovieEntity(movie.getTitle(),
                               movie.getReleaseDate(),
                               movie.getStudio(),
                               movie.getPlotSummary(),
                               movie.getNotes());
    }

    @Nonnull
    public static Collection<Movie> toMovies(@Nonnull Collection<MovieEntity> movies) {
        return movies.stream().map(Adapters::toMovie).collect(Collectors.toList());
    }

    @Nonnull
    public static Collection<MovieEntity> toMovieEntities(@Nonnull Collection<Movie> movies) {
        return movies.stream().map(Adapters::toMovieEntity).collect(Collectors.toList());
    }

    @Nullable
    public static TvShow toTvShow(@Nullable TvShowEntity entity) {
        if (entity == null) {
            return null;
        }

        return TvShow.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .dateAired(entity.getDateAired())
                .network(entity.getNetwork())
                .rateId(entity.getRatedId())
                .plotSummary(entity.getPlotSummary())
                .series(entity.isSeries())
                .build();
    }

    @Nullable
    public static TvShowEntity toTvShowEntity(@Nullable TvShow tvShow) {
        if (tvShow == null) {
            return null;
        }

        return new TvShowEntity(tvShow.getTitle(),
                                tvShow.getDateAired(),
                                tvShow.getNetwork(),
                                tvShow.getRateId(),
                                tvShow.getPlotSummary(),
                                tvShow.isSeries());
    }

    @Nonnull
    public static Collection<TvShowEntity> toTvShowEntities(@Nonnull Collection<TvShow> tvShows) {
        return tvShows.stream().map(Adapters::toTvShowEntity).collect(Collectors.toList());
    }

    @Nonnull
    public static Collection<TvShow> toTvShows(@Nonnull Collection<TvShowEntity> tvShows) {
        return tvShows.stream().map(Adapters::toTvShow).collect(Collectors.toList());
    }

}
