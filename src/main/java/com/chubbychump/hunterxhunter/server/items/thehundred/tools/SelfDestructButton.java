package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.HunterXHunter;

import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;



public class SelfDestructButton extends Item implements Vanishable {

    public SelfDestructButton() {
        super(new Item.Properties().stacksTo(1).tab(HunterXHunter.TAB).rarity(Rarity.UNCOMMON).defaultDurability(100));
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide) {
            worldIn.explode(playerIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), 10f, false, Explosion.BlockInteraction.NONE);
            playerIn.getMainHandItem().hurt(1, RandomSource.create(), (ServerPlayer) playerIn);
            playerIn.setHealth(0);
            //((ServerPlayer) playerIn).getAdvancements()

            //TODO: give achievement + send server wide message?
            return InteractionResultHolder.pass(playerIn.getMainHandItem());
        }
        return InteractionResultHolder.fail(playerIn.getMainHandItem());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Pretty self-explanatory"));
    }
}
