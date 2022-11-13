package com.abilities.heartstuff;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MoreHealthProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<IMoreHealth> MORE_HEALTH_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    private LazyOptional<IMoreHealth> instance = LazyOptional.of(MoreHealth::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == MORE_HEALTH_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        IMoreHealth nenUser = instance.orElseThrow(() -> new IllegalArgumentException("Serialize MoreHealth is empty!"));
        return nenUser.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        IMoreHealth nenUser = instance.orElseThrow(() -> new IllegalArgumentException("Deserialize MoreHealth is empty!"));
        nenUser.deserializeNBT(nbt);
    }

}
