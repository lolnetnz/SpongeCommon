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
package org.spongepowered.common.world.extent;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.world.biome.BiomeType;
import org.spongepowered.api.world.extent.Volume;
import org.spongepowered.api.world.biome.ImmutableBiomeVolume;
import org.spongepowered.api.world.biome.MutableBiomeVolume;
import org.spongepowered.api.world.biome.UnmodifiableBiomeVolume;
import org.spongepowered.api.world.biome.worker.MutableBiomeVolumeWorker;
import org.spongepowered.common.util.gen.ByteArrayImmutableBiomeBuffer;
import org.spongepowered.common.world.extent.worker.SpongeMutableBiomeVolumeWorker;

public class MutableBiomeViewDownsize extends AbstractBiomeViewDownsize<MutableBiomeViewDownsize, MutableBiomeVolume<?>> implements MutableBiomeVolume<MutableBiomeViewDownsize> {

    public MutableBiomeViewDownsize(MutableBiomeVolume<?> volume, Vector3i min, Vector3i max) {
        super(volume, min, max);
    }

    @Override
    public boolean setBiome(int x, int y, int z, BiomeType biome) {
        checkRange(x, y, z);
        return this.volume.setBiome(x, y, z, biome);
    }

    @Override
    public MutableBiomeVolumeWorker<MutableBiomeViewDownsize> getBiomeWorker() {
        return new SpongeMutableBiomeVolumeWorker<>(this);
    }

    @Override
    public UnmodifiableBiomeVolume<?> asUnmodifiableBiomeVolume() {
        return new UnmodifiableBiomeVolumeWrapper(this);
    }

    @Override
    public ImmutableBiomeVolume asImmutableBiomeVolume() {
        return ByteArrayImmutableBiomeBuffer.newWithoutArrayClone(ExtentBufferUtil.copyToArray(this, this.min, this.max, this.size), this.min, this.size);
    }

    @Override
    public Vector3i getBlockMin() {
        return this.min;
    }

    @Override
    public Vector3i getBlockMax() {
        return this.max;
    }

    @Override
    public Vector3i getBlockSize() {
        return this.size;
    }

    @Override
    public boolean containsBlock(int x, int y, int z) {
        checkRange(x, y, z);
        return this.volume.containsBlock(x, y, z);
    }

    @Override
    public boolean isAreaAvailable(int x, int y, int z) {
        return this.volume.containsBlock(x, y, z);
    }

    @Override
    public Volume getView(Vector3i newMin, Vector3i newMax) {
        checkRange(newMin.getX(), newMin.getY(), newMin.getZ());
        checkRange(newMax.getX(), newMax.getY(), newMax.getZ());
        return new MutableBiomeViewDownsize(this.volume, newMin, newMax);
    }
}
