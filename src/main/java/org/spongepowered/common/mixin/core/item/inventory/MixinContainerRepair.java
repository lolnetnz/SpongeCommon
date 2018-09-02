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
package org.spongepowered.common.mixin.core.item.inventory;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.api.event.item.inventory.UpdateAnvilEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.common.event.SpongeCommonEventFactory;
import org.spongepowered.common.item.inventory.adapter.InventoryAdapter;
import org.spongepowered.common.item.inventory.adapter.impl.slots.InputSlotAdapter;
import org.spongepowered.common.item.inventory.adapter.impl.slots.OutputSlotAdapter;
import org.spongepowered.common.item.inventory.lens.Fabric;
import org.spongepowered.common.item.inventory.lens.Lens;
import org.spongepowered.common.item.inventory.lens.LensProvider;
import org.spongepowered.common.item.inventory.lens.SlotProvider;
import org.spongepowered.common.item.inventory.lens.impl.collections.SlotCollection;
import org.spongepowered.common.item.inventory.lens.impl.comp.MainPlayerInventoryLensImpl;
import org.spongepowered.common.item.inventory.lens.impl.comp.OrderedInventoryLensImpl;
import org.spongepowered.common.item.inventory.lens.impl.minecraft.container.ContainerLens;
import org.spongepowered.common.item.inventory.lens.impl.slots.InputSlotLensImpl;
import org.spongepowered.common.item.inventory.lens.impl.slots.OutputSlotLensImpl;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(ContainerRepair.class)
public abstract class MixinContainerRepair extends MixinContainer implements LensProvider {

    @Shadow private String repairedItemName;

    @Shadow @Final private IInventory outputSlot;

    @Shadow public int maximumCost;

    @Shadow private int materialCost;

    @Shadow @Final public IInventory inputSlots;

    @Override
    public Lens rootLens(Fabric fabric, InventoryAdapter adapter) {
        List<Lens> lenses = new ArrayList<>();
        lenses.add(new OrderedInventoryLensImpl(0, 3, 1, inventory$getSlotProvider()));
        lenses.add(new MainPlayerInventoryLensImpl(3, inventory$getSlotProvider(), true));
        return new ContainerLens(adapter, inventory$getSlotProvider(), lenses);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SlotProvider slotProvider(Fabric fabric, InventoryAdapter adapter) {
        SlotCollection.Builder builder = new SlotCollection.Builder()
                .add(2, InputSlotAdapter.class, i -> new InputSlotLensImpl(i, s -> true, t -> true))
                .add(1, OutputSlotAdapter.class, i -> new OutputSlotLensImpl(i, s -> false, t -> false))
                .add(36);
        return builder.build();
    }

    @Inject(method = "updateRepairOutput", at = @At(value = "RETURN"))
    private void onUpdateRepairOutput(CallbackInfo ci) {
        ItemStack itemstack = this.inputSlots.getStackInSlot(0);
        ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
        ItemStack result = this.outputSlot.getStackInSlot(0);
        UpdateAnvilEvent event = SpongeCommonEventFactory.callUpdateAnvilEvent((ContainerRepair) (Object) this, itemstack, itemstack2, result, this.repairedItemName, this.maximumCost, this.materialCost);

        ItemStackSnapshot finalItem = event.getItem().getFinal();
        if (event.isCancelled() || finalItem.isEmpty()) {
            this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
            this.maximumCost = 0;
            this.materialCost = 0;
            this.detectAndSendChanges();
            return;
        }

        this.outputSlot.setInventorySlotContents(0, ItemStackUtil.fromSnapshotToNative(event.getItem().getFinal()));
        this.maximumCost = event.getLevelCost();
        this.materialCost = event.getMaterialCost();
        this.listeners.forEach(l -> l.sendWindowProperty(((ContainerRepair)(Object) this), 0, this.maximumCost));
        this.detectAndSendChanges();
    }

}
