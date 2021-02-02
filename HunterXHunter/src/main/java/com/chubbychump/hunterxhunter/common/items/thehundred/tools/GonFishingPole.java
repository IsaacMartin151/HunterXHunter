package com.chubbychump.hunterxhunter.common.items.thehundred.tools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.item.Rarity.UNCOMMON;

public class GonFishingPole extends Item implements IVanishable {
    public GonFishingPole() {
        super(new Item.Properties().maxStackSize(1).group(HunterXHunter.TAB).rarity(UNCOMMON).maxDamage(100).group(ItemGroup.TOOLS));
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (playerIn.fishingBobber != null) {
            if (!worldIn.isRemote) {
                int i = playerIn.fishingBobber.handleHookRetraction(itemstack);
                itemstack.damageItem(i, playerIn, (player) -> {
                    player.sendBreakAnimation(handIn);
                });
            }

            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        } else {
            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote) {
                int k = 5;
                int j = 5;
                worldIn.addEntity(new FishingBobberEntity(playerIn, worldIn, j, k));
            }

            playerIn.addStat(Stats.ITEM_USED.get(this));
        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Gon's fishing rod. Has Luck 5 and Lure 5"));
    }
}
