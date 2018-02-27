package com.wilson.movie.library.resource.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Serializes local Java objects to their target JSON equivalents.
 *
 * @author Zach Wilson
 */
public final class Serializers {

    /**
     * No instances of this class should be constructed: all methods and classes intended for
     * external use are <i>static</i>.
     */
    private Serializers() {
    }

    public static final class LocalDateSerializer extends StdSerializer<LocalDate> {

        public LocalDateSerializer() {
            this(null);
        }

        public LocalDateSerializer(@Nullable Class<LocalDate> localDateClass) {
            super(localDateClass);
        }

        @Override
        public void serialize(@Nonnull LocalDate value, @Nonnull JsonGenerator jsonGenerator,
                @Nonnull SerializerProvider provider) throws IOException {
            jsonGenerator.writeString(value.format(DateTimeFormatter.ISO_DATE));
        }
    }

}
