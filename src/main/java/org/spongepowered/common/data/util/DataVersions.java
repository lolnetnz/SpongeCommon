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
package org.spongepowered.common.data.util;

import org.spongepowered.api.data.DataSerializable;

/**
 * A common utility class for various versions of various
 * {@link DataSerializable}s that are implemented in Sponge.
 *
 * Note that the organization is by groups, where a
 * {@link org.spongepowered.api.block.BlockState} may have
 * previous values based on damage values and a newer version
 * eliminates the need for those damage values and uses
 * the block state id. It helps to keep the constant names
 * organized.
 */
public final class DataVersions {

    public static final class BlockState {

        public static final int BLOCK_TYPE_WITH_DAMAGE_VALUE = 1;
        public static final int STATE_AS_CATALOG_ID = 2;

        private BlockState() {
        }
    }

    public static final class TileEntitArchetype {

        public static final int BASE_VERSION = 1;

        private TileEntitArchetype() {
        }
    }

    public static final class EntityArchetype {

        public static final int BASE_VERSION = 1;

    }

    private DataVersions() {

    }

    public static final class PlayerData {

        public static final int RESPAWN_DATA_1_9_VERSION = 0;

        private PlayerData() {
        }

    }

    public static final class World {

        public static final int WORLD_UUID_1_9_VERSION = 0;

        private World() {
        }

    }

    public static final class Entity {

        public static final int TRACKER_ID_VERSION = 0;

        private Entity() {
        }

    }

    public static final class Potion {

        public static final int BROKEN_POTION_ID = 1;
        public static final int POTION_V2 = 2;
        public static final int CURRENT_VERSION = POTION_V2;

        private Potion() {
        }

    }

    public static final class Data {

        public static final int INVISIBILITY_DATA_PRE_1_9 = 1;
        public static final int INVISIBILITY_DATA_WITH_VANISH = 2;

        public static final int CLASS_BASED_CUSTOM_DATA = 1;
        public static final int CUSTOM_DATA_WITH_DATA_IDS = 2;
        public static final int CURRENT_CUSTOM_DATA = CUSTOM_DATA_WITH_DATA_IDS;

    }

    public static final class ItemStackSnapshot {
        public static final int DUPLICATE_MANIPULATOR_DATA_VERSION = 1;
        public static final int REMOVED_DUPLICATE_DATA = 2;
        public static final int CURRENT_VERSION = REMOVED_DUPLICATE_DATA;
    }

}
