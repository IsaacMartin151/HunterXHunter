package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.item.Rarity.UNCOMMON;

public class SelfDestructButton extends Item implements IVanishable {

    public SelfDestructButton() {
        super(new Item.Properties().maxStackSize(1).group(HunterXHunter.TAB).rarity(UNCOMMON).maxDamage(100).group(ItemGroup.COMBAT));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            worldIn.createExplosion(playerIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), 10f, false, Explosion.Mode.NONE);
            playerIn.getHeldItemMainhand().attemptDamageItem(1, new Random(), (ServerPlayerEntity) playerIn);
            playerIn.setHealth(0);
            //((ServerPlayerEntity) playerIn).getAdvancements()

            //TODO: give achievement + send server wide message?
            return ActionResult.resultPass(playerIn.getHeldItemMainhand());
        }
        return ActionResult.resultFail(playerIn.getHeldItemMainhand());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Pretty self-explanatory"));
    }
}
