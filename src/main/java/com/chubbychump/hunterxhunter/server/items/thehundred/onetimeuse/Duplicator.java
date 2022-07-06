package com.chubbychump.hunterxhunter.server.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.HunterXHunter;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class Duplicator extends Item {

    public Duplicator() {
        super(new Item.Properties().stacksTo(64).tab(HunterXHunter.TAB).rarity(Rarity.RARE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        Inventory yeet = player.getInventory();
        ItemStack yo = yeet.getSelected();
        if (player.getInventory().add(player.getOffhandItem())) {
            yo.shrink(1);
            return InteractionResultHolder.pass(yo);
        }
        return InteractionResultHolder.fail(yo);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Duplicates the item in your offhand"));
    }
}
