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

package net.clydo.mongodb.operations.find;

import net.clydo.mongodb.error.NotFoundResult;
import net.clydo.mongodb.operations.IOperations;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FindUniqueOperations<M> extends IOperations<M> {
    @Nullable M unique(@NotNull Bson filter);

    @Nullable M unique(@NotNull String fieldName, @Nullable Object value);

    @Nullable M uniqueByUnique(@NotNull Object uniqueValue);

    @NotNull M uniqueOrThrowRaw(@NotNull Bson filter) throws NotFoundResult;
}
