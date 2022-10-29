package com.abilities.heartstuff;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MoreHealthProvider implements ICapabilitySerializable<CompoundTag> {

    @CapabilityInject(IMoreHealth.class)
    public static final Capability<IMoreHealth> CAPABILITY = null;
    private LazyOptional<IMoreHealth> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) CAPABILITY.getStorage().writeNBT(CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }

}
