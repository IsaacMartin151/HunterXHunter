package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobMobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class VillagerStaff extends StaffBase {
    public VillagerStaff() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getMainHandItem();
        if (!worldIn.isClientSide) {
            itemstack.hurtAndBreak(1, playerIn, (player) -> {
                player.broadcastBreakEvent(handIn);
            });
            ZombieVillager bruh;
            Vec3 pc = playerIn.getEyePosition();
            AABB bounds = new AABB(pc.x-5, pc.y-5, pc.z-5, pc.x+5, pc.y+5, pc.z+5);

            List<ZombieVillager> yo = worldIn.getEntities(EntityTypeTest.forClass(ZombieVillager.class), bounds, (Predicate<? super Entity>) EntityPredicate.Builder.entity().build());
            for (ZombieVillager mob : yo) {
                mob.addEffect(new MobMobEffectInstance(MobEffects.WEAKNESS, 600));
                //element.addPotionEffect(new MobEffectInstance(Effects.STRENGTH, 600));
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, Items.GOLDEN_APPLE.getDefaultInstance());
                mob.mobInteract(playerIn, InteractionHand.MAIN_HAND);
            }
            playerIn.setItemInHand(InteractionHand.MAIN_HAND, itemstack);

        }
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Cures nearby zombie villagers"));
    }
}
