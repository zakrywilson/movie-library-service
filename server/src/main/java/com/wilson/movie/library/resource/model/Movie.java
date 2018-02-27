package com.wilson.movie.library.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.time.LocalDate;

/**
 * Immutable movie DTO.
 *
 * @author Zach Wilson
 */
@JsonRootName("movie")
@Value
@Builder
public final class Movie {

    @JsonProperty("id")
    @Wither
    private final int id;

    @JsonProperty("title")
    @Wither
    private final String title;

    @JsonProperty("release_date")
    @Wither
    private final LocalDate releaseDate;

    @JsonProperty("studio")
    @Wither
    private final String studio;

    @JsonProperty("plot_summary")
    @Wither
    private final String plotSummary;

    @JsonProperty("notes")
    @Wither
    private final String notes;

}
