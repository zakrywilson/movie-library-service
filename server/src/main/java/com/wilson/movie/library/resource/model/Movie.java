package com.wilson.movie.library.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wilson.movie.library.resource.utils.Deserializers;
import com.wilson.movie.library.resource.utils.Serializers;
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

    @JsonProperty("releaseDate")
    @JsonSerialize(using = Serializers.LocalDateSerializer.class)
    @JsonDeserialize(using = Deserializers.LocalDateDeserializer.class)
    @JsonPropertyDescription("ISO date format, e.g., 1980-05-23")
    @Wither
    private final LocalDate releaseDate;

    @JsonProperty("studio")
    @Wither
    private final String studio;

    @JsonProperty("rating")
    @Wither
    private final String rating;

    @JsonProperty("genre")
    @Wither
    private final String genre;

    @JsonProperty("plotSummary")
    @Wither
    private final String plotSummary;

    @JsonProperty("notes")
    @Wither
    private final String notes;

}
