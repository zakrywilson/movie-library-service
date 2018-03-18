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
 * Immutable person DTO.
 *
 * @author Zach Wilson
 */
@JsonRootName("person")
@Value
@Builder
public final class Person {

    @JsonProperty("id")
    @Wither
    private final int id;

    @JsonProperty("firstName")
    @Wither
    private final String firstName;

    @JsonProperty("middleName")
    @Wither
    private final String middleName;

    @JsonProperty("lastName")
    @Wither
    private final String lastName;

    @JsonProperty("dateOfBirth")
    @JsonSerialize(using = Serializers.LocalDateSerializer.class)
    @JsonDeserialize(using = Deserializers.LocalDateDeserializer.class)
    @JsonPropertyDescription("ISO date format, e.g., 1980-05-23")
    @Wither
    private final LocalDate dateOfBirth;

    @JsonProperty("dateOfDeath")
    @JsonSerialize(using = Serializers.LocalDateSerializer.class)
    @JsonDeserialize(using = Deserializers.LocalDateDeserializer.class)
    @JsonPropertyDescription("ISO date format, e.g., 1980-05-23")
    @Wither
    private final LocalDate dateOfDeath;

}
