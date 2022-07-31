package com.chubbychump.hunterxhunter.server.abilities.heartstuff;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MoreHealthProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<IMoreHealth> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    private LazyOptional<IMoreHealth> instance = LazyOptional.of(MoreHealth::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        IMoreHealth nninstance = instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
        return nninstance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        IMoreHealth nninstance = instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
        nninstance.setVersion(tag.getByte("version"));
        nninstance.setModifier(tag.getFloat("modifier"));
        nninstance.setRampPosition(tag.getShort("position"));
        nninstance.setHeartContainers(tag.getByte("containers"));
    }

}
