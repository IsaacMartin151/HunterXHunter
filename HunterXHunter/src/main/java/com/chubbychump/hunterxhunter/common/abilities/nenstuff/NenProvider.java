package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Mana provider
 *
 * This class is responsible for providing a capability. Other modders may
 * attach their own provider with implementation that returns another
 * implementation of NenUser to the target's (Entity, TE, ItemStack, etc.) disposal.
 */
public class NenProvider implements ICapabilitySerializable<INBT>
{
    @CapabilityInject(NenUser.class)
    public static final Capability<NenUser> MANA_CAP = null;
    private LazyOptional<NenUser> holder = LazyOptional.of(MANA_CAP::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
    {
           return capability == MANA_CAP ? holder.cast() : LazyOptional.empty();

    }

    @Override
    public INBT serializeNBT()
    {
        return MANA_CAP.getStorage().writeNBT(MANA_CAP, this.holder.orElseThrow(null), null);
    }

    @Override
    public void deserializeNBT(INBT nbt)
    {
        MANA_CAP.getStorage().readNBT(MANA_CAP, this.holder.orElseThrow(null), null, nbt);
    }
}