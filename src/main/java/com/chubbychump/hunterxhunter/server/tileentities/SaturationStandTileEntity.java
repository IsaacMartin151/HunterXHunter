package com.chubbychump.hunterxhunter.server.tileentities;

import net.minecraft.entity.player.Player;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.SATURATION_STAND_TILE_ENTITY;

public class SaturationStandTileEntity extends TileEntity implements ITickableTileEntity {

    public SaturationStandTileEntity() {
        super(SATURATION_STAND_TILE_ENTITY.get());
    }

    public void tick() {
        if (this.world.getGameTime() % 80L == 0L) {
                this.addEffectsToPlayers();
        }
    }

    private void addEffectsToPlayers() {
        if (!this.world.isRemote) {
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.pos)).grow(50).expand(0.0D, (double)this.world.getHeight(), 0.0D);
            List<Player> list = this.world.getEntitiesWithinAABB(Player.class, axisalignedbb);

            for(Player playerentity : list) {
                playerentity.addPotionEffect(new EffectInstance(Effects.SATURATION, 20, 1, true, true));
            }
        }
    }
}
