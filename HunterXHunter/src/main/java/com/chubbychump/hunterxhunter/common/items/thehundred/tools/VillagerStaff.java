package com.chubbychump.hunterxhunter.common.items.thehundred.tools;

import com.chubbychump.hunterxhunter.common.items.StaffBase;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class VillagerStaff extends StaffBase {
    public VillagerStaff() {
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            itemstack.damageItem(1, playerIn, (player) -> {
                player.sendBreakAnimation(handIn);
            });
            ZombieVillagerEntity bruh;
            Vector3d pc = playerIn.getPositionVec();
            AxisAlignedBB bounds = new AxisAlignedBB(pc.x-5, pc.y-5, pc.z-5, pc.x+5, pc.y+5, pc.z+5);
            Predicate<LivingEntity> ZOMBIE_VILLAGER = (p_213797_0_) -> {
                return (p_213797_0_ instanceof ZombieVillagerEntity);
            };
            EntityPredicate ENEMY_CONDITION = (new EntityPredicate()).setDistance(20.0D).setCustomPredicate(ZOMBIE_VILLAGER);
            List<ZombieVillagerEntity> yo = worldIn.getTargettableEntitiesWithinAABB(ZombieVillagerEntity.class, ENEMY_CONDITION, playerIn, bounds);
            for (ZombieVillagerEntity element : yo) {
                element.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 600));
                //element.addPotionEffect(new EffectInstance(Effects.STRENGTH, 600));
                playerIn.setHeldItem(Hand.MAIN_HAND, Items.GOLDEN_APPLE.getDefaultInstance());
                element.func_230254_b_(playerIn, Hand.MAIN_HAND);
            }
            playerIn.setHeldItem(Hand.MAIN_HAND, itemstack);

        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}
