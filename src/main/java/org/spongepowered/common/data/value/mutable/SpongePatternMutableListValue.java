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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.meta.PatternLayer;
import org.spongepowered.api.data.type.BannerPatternShape;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.mutable.PatternMutableListValue;
import org.spongepowered.common.data.meta.SpongePatternLayer;
import org.spongepowered.common.data.value.immutable.ImmutableSpongePatternListValue;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SpongePatternMutableListValue extends SpongeMutableListValue<PatternLayer> implements PatternMutableListValue {

    public SpongePatternMutableListValue(Key<? extends Value<List<PatternLayer>>> key) {
        super(key);
    }

    public SpongePatternMutableListValue(Key<? extends Value<List<PatternLayer>>> key, List<PatternLayer> actualValue) {
        super(key, actualValue);
    }

    @Override
    public PatternMutableListValue set(List<PatternLayer> value) {
        super.set(value);
        return this;
    }

    @Override
    public PatternMutableListValue filter(Predicate<? super PatternLayer> predicate) {
        super.filter(predicate);
        return this;
    }

    @Override
    public PatternMutableListValue add(BannerPatternShape patternShape, DyeColor color) {
        super.add(new SpongePatternLayer(patternShape, color));
        return this;
    }

    @Override
    public PatternMutableListValue add(int index, BannerPatternShape patternShape, DyeColor color) {
        return add(index, new SpongePatternLayer(patternShape, color));
    }

    @Override
    public PatternMutableListValue add(int index, PatternLayer value) {
        super.add(index, value);
        return this;
    }

    @Override
    public PatternMutableListValue add(int index, Iterable<PatternLayer> values) {
        super.add(index, values);
        return this;
    }

    @Override
    public PatternMutableListValue remove(int index) {
        super.remove(index);
        return this;
    }

    @Override
    public PatternMutableListValue set(int index, PatternLayer element) {
        super.set(index, element);
        return this;
    }

    @Override
    public PatternMutableListValue transform(Function<List<PatternLayer>, List<PatternLayer>> function) {
        super.transform(function);
        return this;
    }

    @Override
    public PatternMutableListValue add(PatternLayer element) {
        super.add(element);
        return this;
    }

    @Override
    public PatternMutableListValue addAll(Iterable<PatternLayer> elements) {
        super.addAll(elements);
        return this;
    }

    @Override
    public PatternMutableListValue remove(PatternLayer element) {
        super.remove(element);
        return this;
    }

    @Override
    public PatternMutableListValue removeAll(Iterable<PatternLayer> elements) {
        super.removeAll(elements);
        return this;
    }

    @Override
    public PatternMutableListValue removeAll(Predicate<PatternLayer> predicate) {
        super.removeAll(predicate);
        return this;
    }

    @Override
    public I asImmutable() {
        return new ImmutableSpongePatternListValue(getKey(), ImmutableList.copyOf(this.actualValue));
    }

    @Override
    public PatternMutableListValue copy() {
        return new SpongePatternMutableListValue(getKey(), Lists.newArrayList(this.actualValue));
    }
}