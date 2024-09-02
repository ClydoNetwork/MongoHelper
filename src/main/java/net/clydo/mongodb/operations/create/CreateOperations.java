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
 */

package net.clydo.mongodb.operations.create;

import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import net.clydo.mongodb.loader.classes.values.MongoModelValue;
import net.clydo.mongodb.operations.AbstractOperation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CreateOperations<M> extends AbstractOperation<M> implements CreateOneOperations<M>, CreateManyOperations<M> {
    public CreateOperations(MongoModelValue<M> model) {
        super(model);
    }

    @Override
    public @NotNull InsertOneResult one(@NotNull M datum) {
        return this.collection().insertOne(datum);
    }

    @SafeVarargs
    @Override
    public final @NotNull InsertManyResult many(@NotNull M... data) {
        return this.collection().insertMany(List.of(data));
    }
}