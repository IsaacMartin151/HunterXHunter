package com.chubbychump.hunterxhunter.server.tileentities;



import net.minecraft.client.renderer.MobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.SATURATION_STAND_TILE_ENTITY;

public class SaturationStandTileEntity extends BlockEntity implements BlockEntityTicker {

    public SaturationStandTileEntity() {
        super(SATURATION_STAND_TILE_ENTITY.get());
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity t) {
        if (this.level.getGameTime() % 80L == 0L) {
                this.addEffectsToPlayers();
        }
    }

    private void addEffectsToPlayers() {
        if (!this.level.isClientSide) {
            AABB axisalignedbb = (new AABB(this.worldPosition)).inflate(50).expandTowards(0.0D, (double)this.level.getHeight(), 0.0D);
            List<Player> list = this.level.getEntities(this, axisalignedbb, Predicate.isEqual(Player.class));

            for(Player playerentity : list) {
                playerentity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 1, true, true));
            }
        }
    }
}
