package com.chubbychump.hunterxhunter.server.abilities.nenstuff;


import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class NenProvider implements ICapabilitySerializable<CompoundTag> {

    @CapabilityInject(INenUser.class)
    public static final Capability<INenUser> NENUSER = null;
    private LazyOptional<INenUser> instance = LazyOptional.of(NENUSER::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == NENUSER ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) NENUSER.getStorage().writeNBT(NENUSER, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        NENUSER.getStorage().readNBT(NENUSER, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
