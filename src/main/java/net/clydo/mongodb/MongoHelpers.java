/*
 * This file is part of MongoHelper.
 *
 * MongoHelper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * MongoHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MongoHelper. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 ClydoNetwork
 */

package net.clydo.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.clydo.mongodb.util.MongoUtil;
import org.bson.codecs.BsonTypeClassMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class that provides helper methods for working with MongoDB in a synchronous context.
 * The class includes methods for creating instances of {@link MongoHelper} with different configurations.
 */
@UtilityClass
public class MongoHelpers {

    /**
     * The default {@link BsonTypeClassMap} used by MongoDB for mapping BSON types to Java classes.
     * This map is retrieved using reflection to access the private field in {@link BsonTypeClassMap}.
     */
    @Getter
    private static final BsonTypeClassMap defaultBsonTypeClassMap = MongoUtil.make(() -> {
        try {
            val clazz = BsonTypeClassMap.class;
            val field = clazz.getDeclaredField("DEFAULT_BSON_TYPE_CLASS_MAP");
            field.setAccessible(true);
            return (BsonTypeClassMap) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    });

    /**
     * Creates a new {@link MongoHelper} instance using the provided {@link MongoClientSettings}.
     *
     * @param settings The settings to configure the MongoDB client.
     * @return A new {@link MongoHelper} instance.
     */
    @Contract("_ -> new")
    public static @NotNull MongoHelper create(MongoClientSettings settings) {
        return create(settings, null);
    }

    /**
     * Creates a new {@link MongoHelper} instance using the provided MongoDB connection string.
     *
     * @param connectionString The connection string for connecting to MongoDB.
     * @return A new {@link MongoHelper} instance.
     */
    @Contract("_ -> new")
    public static @NotNull MongoHelper create(String connectionString) {
        return create(new ConnectionString(connectionString));
    }

    /**
     * Creates a new {@link MongoHelper} instance using the provided {@link ConnectionString}.
     *
     * @param connectionString The connection string for connecting to MongoDB.
     * @return A new {@link MongoHelper} instance.
     */
    @Contract("_ -> new")
    public static @NotNull MongoHelper create(ConnectionString connectionString) {
        return create(connectionString, null);
    }

    /**
     * Creates a new {@link MongoHelper} instance using the provided {@link ConnectionString} and {@link MongoDriverInformation}.
     *
     * @param connectionString       The connection string for connecting to MongoDB.
     * @param mongoDriverInformation Additional driver information for MongoDB.
     * @return A new {@link MongoHelper} instance.
     */
    @Contract("_, _ -> new")
    public static @NotNull MongoHelper create(ConnectionString connectionString, @Nullable MongoDriverInformation mongoDriverInformation) {
        return create(MongoClientSettings.builder().applyConnectionString(connectionString).build(), mongoDriverInformation);
    }

    /**
     * Creates a new {@link MongoHelper} instance using the provided {@link MongoClientSettings} and {@link MongoDriverInformation}.
     *
     * @param settings               The settings to configure the MongoDB client.
     * @param mongoDriverInformation Additional driver information for MongoDB, or null if not needed.
     * @return A new {@link MongoHelper} instance.
     */
    @Contract("_, _ -> new")
    public static @NotNull MongoHelper create(MongoClientSettings settings, @Nullable MongoDriverInformation mongoDriverInformation) {
        val builder = mongoDriverInformation == null ? MongoDriverInformation.builder() : MongoDriverInformation.builder(mongoDriverInformation);
        val mongoClient = new MongoClientImpl(settings, builder.driverName("sync").build());
        return new MongoHelper(mongoClient);
    }
}
