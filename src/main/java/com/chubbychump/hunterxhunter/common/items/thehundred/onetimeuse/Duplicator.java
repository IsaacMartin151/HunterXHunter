package com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static net.minecraft.item.Rarity.RARE;

public class Duplicator extends Item {

    public Duplicator() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        PlayerInventory yeet = player.inventory;
        ItemStack yo = yeet.getCurrentItem();
        if (player.inventory.addItemStackToInventory(new ItemStack(player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem(), 1))) {
            yo.shrink(1);
            return ActionResult.resultPass(yo);
        }
        return ActionResult.resultFail(yo);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Duplicates the item in your offhand"));
    }
}
