/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.data.value.mutable;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.immutable.ImmutableOptionalValue;
import org.spongepowered.api.data.value.mutable.MutableValue;
import org.spongepowered.api.data.value.mutable.MutableOptionalValue;
import org.spongepowered.common.data.value.immutable.ImmutableSpongeOptionalValue;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

public class SpongeMutableOptionalValue<E> extends SpongeMutableValue<Optional<E>> implements MutableOptionalValue<E> {

    public SpongeMutableOptionalValue(Key<? extends Value<Optional<E>>> key) {
        this(key, Optional.<E>empty());
    }

    public SpongeMutableOptionalValue(Key<? extends Value<Optional<E>>> key, Optional<E> actualValue) {
        this(key, Optional.<E>empty(), actualValue);
    }

    public SpongeMutableOptionalValue(Key<? extends Value<Optional<E>>> key, Optional<E> defaultValue, Optional<E> actualValue) {
        super(key, defaultValue, actualValue);
    }

    @Override
    public MutableOptionalValue<E> set(Optional<E> value) {
        this.actualValue = checkNotNull(value);
        return this;
    }

    @Override
    public MutableOptionalValue<E> transform(Function<Optional<E>, Optional<E>> function) {
        this.actualValue = checkNotNull(function.apply(this.actualValue));
        return this;
    }

    @Override
    public ImmutableOptionalValue<E> asImmutable() {
        return new ImmutableSpongeOptionalValue<>(getKey(), this.actualValue);
    }

    @Override
    public MutableOptionalValue<E> copy() {
        return new SpongeMutableOptionalValue<>(getKey(), this.actualValue);
    }

    @Override
    public MutableOptionalValue<E> setTo(@Nullable E value) {
        return set(Optional.ofNullable(value));
    }

    @Override
    public MutableValue<E> or(E defaultValue) { // TODO actually construct the keys
        return new SpongeMutableValue<>(null, null, get().isPresent() ? get().get() : checkNotNull(defaultValue));
    }
}