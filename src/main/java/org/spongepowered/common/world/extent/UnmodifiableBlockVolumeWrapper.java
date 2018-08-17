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
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.fluid.FluidState;
import org.spongepowered.api.world.extent.Volume;
import org.spongepowered.api.world.extent.block.ImmutableBlockVolume;
import org.spongepowered.api.world.extent.block.ReadableBlockVolume;
import org.spongepowered.api.world.extent.block.UnmodifiableBlockVolume;
import org.spongepowered.api.world.extent.block.worker.BlockVolumeWorker;
import org.spongepowered.common.world.extent.worker.SpongeBlockVolumeWorker;

public class UnmodifiableBlockVolumeWrapper implements UnmodifiableBlockVolume<UnmodifiableBlockVolumeWrapper> {

    private final ReadableBlockVolume volume;

    public UnmodifiableBlockVolumeWrapper(ReadableBlockVolume volume) {
        this.volume = volume;
    }

    @Override
    public Vector3i getBlockMin() {
        return this.volume.getBlockMin();
    }

    @Override
    public Vector3i getBlockMax() {
        return this.volume.getBlockMax();
    }

    @Override
    public Vector3i getBlockSize() {
        return this.volume.getBlockSize();
    }

    @Override
    public boolean containsBlock(int x, int y, int z) {
        return this.volume.containsBlock(x, y, z);
    }

    @Override
    public boolean isAreaAvailable(int x, int y, int z) {
        return false;
    }

    @Override
    public Volume getView(Vector3i newMin, Vector3i newMax) {

        return new UnmodifiableBlockViewDownsize(this.volume, getBlockMin(), newMax);
    }

    @Override
    public BlockState getBlock(int x, int y, int z) {
        return this.volume.getBlock(x, y, z);
    }

    @Override
    public FluidState getFluid(int x, int y, int z) {
        return null;
    }

    @Override
    public ImmutableBlockVolume asImmutableBlockVolume() {
        return null;
    }

    @Override
    public BlockVolumeWorker<UnmodifiableBlockVolumeWrapper> getBlockWorker() {
        return new SpongeBlockVolumeWorker<>(this);
    }

}
