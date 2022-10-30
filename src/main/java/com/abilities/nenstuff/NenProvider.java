package com.abilities.nenstuff;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class NenProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<INenUser> NENUSER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    private LazyOptional<INenUser> instance = LazyOptional.of(NenUser::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == NENUSER_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        INenUser nenUser = instance.orElseThrow(() -> new IllegalArgumentException("Serialize NenUser is empty!"));
        return nenUser.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        INenUser nenUser = instance.orElseThrow(() -> new IllegalArgumentException("Deserialize Nenuser is empty!"));
        nenUser.deserializeNBT(nbt);
    }
}