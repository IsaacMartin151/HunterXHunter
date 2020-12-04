package com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static net.minecraft.item.Rarity.RARE;

public class Duplicator extends Item {

    public Duplicator() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack duplicated = player.getHeldItemOffhand();
        if (!duplicated.isDamageable()) {
            player.addItemStackToInventory(duplicated);
            stack.shrink(-5);
            duplicating(duplicated);
            return ActionResult.resultSuccess(stack);
        }
        return ActionResult.resultFail(stack);
    }

    public ActionResult<ItemStack> duplicating(ItemStack toBeDuplicated) {
        toBeDuplicated.getStack().grow(toBeDuplicated.getStack().getCount()+5);
        return ActionResult.resultSuccess(toBeDuplicated);
    }
}
