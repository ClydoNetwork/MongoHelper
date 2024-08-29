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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MongoHelper.  If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 ClydoNetwork
 *
 */

package net.clydo.mongodb.loader.classes.values;

import com.mongodb.client.MongoCollection;
import net.clydo.mongodb.loader.CacheValue;
import net.clydo.mongodb.operations.Operations;
import net.clydo.mongodb.schematic.MongoSchemaHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class MongoModelValue<M> implements CacheValue, ClassCacheValue {
    private final Class<M> type;
    private final String modelName;
    private final MongoCollection<M> collection;
    private final List<String> uniques;
    private final HashMap<String, MongoMutableField> fields;
    private final MongoSchemaHolder parent;
    private Operations<M> operations;

    public MongoModelValue(
            Class<M> type,
            String modelName,
            MongoCollection<M> collection,
            List<String> uniques,
            HashMap<String, MongoMutableField> fields,
            MongoSchemaHolder parent
    ) {
        this.type = type;
        this.modelName = modelName;
        this.collection = collection;
        this.uniques = uniques;
        this.fields = fields;
        this.parent = parent;
    }

    @Contract("_, _, _, _ -> new")
    public static <M> @NotNull MongoModelValue<M> of(
            final Class<M> type,
            final HashMap<String, MongoMutableField> fields,
            final String modelName,
            final @NotNull MongoSchemaHolder parent
    ) {
        return new MongoModelValue<>(
                type,
                modelName,
                parent.database().getCollection(modelName, type),
                fields.entrySet().stream().filter(entry -> entry.getValue().unique()).map(Map.Entry::getKey).collect(Collectors.toList()),
                fields,
                parent
        );
    }

    public @NotNull Operations<M> operations() {
        if (this.operations == null) {
            this.operations = new Operations<>(this);
        }
        return this.operations;
    }

    public Class<M> type() {
        return this.type;
    }

    public String modelName() {
        return this.modelName;
    }

    public MongoCollection<M> collection() {
        return this.collection;
    }

    public List<String> uniques() {
        return this.uniques;
    }

    @Override
    public HashMap<String, MongoMutableField> fields() {
        return this.fields;
    }

    public MongoSchemaHolder parent() {
        return this.parent;
    }
}