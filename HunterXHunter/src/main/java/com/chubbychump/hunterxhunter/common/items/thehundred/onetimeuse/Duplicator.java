package com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        player.inventory.addItemStackToInventory(new ItemStack(player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem(), 5));
        PlayerInventory yeet = player.inventory;
        ItemStack yo = yeet.getCurrentItem();
        yo.shrink(1);
        return ActionResult.resultPass(yo);
    }
}
