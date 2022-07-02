package com.chubbychump.hunterxhunter.server.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.screens.PuzzleScreen;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.*;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import java.util.List;

import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider.NENUSER;

public class Crystal_Nen extends Item {
    public Crystal_Nen() {
        super(new Properties().maxStackSize(64).rarity(Rarity.RARE).group(HunterXHunter.TAB));
    }

    @OnlyIn(Dist.CLIENT)
    private void openGui(PlayerEntity player) {
        INenUser yo = player.getCapability(NENUSER).orElseThrow(null);
        if (yo.getNenPower() == 0) {
            yo.increaseNenPower();
            NenUser.updateServer(player, yo);
        }
        else {
            Minecraft.getInstance().displayGuiScreen(new PuzzleScreen(60 - (2 * yo.getNenPower())));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        // Setup return results
        ItemStack stack = player.getHeldItem(hand);
        ActionResult<ItemStack> result = new ActionResult<>(ActionResultType.PASS, stack);
        HunterXHunter.LOGGER.info("Right clicked crystal nen");
        // Ensure server-side only & the player's not in creative or spectator
        if (world.isRemote) {
            openGui(player);
            return result;
        }



        // Get capability
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        INenUser yo = NenUser.getFromPlayer(player);
        if (yo.getNenPower() != 0) {
            player.getServer().sendMessage(new TranslationTextComponent("Facing a trial. Duration: " + (120 - (2 * yo.getNenPower()))).setStyle(Style.EMPTY.setColor(Color.fromInt(32896)).setItalic(true)), Util.DUMMY_UUID);
        }

        int Type = yo.getNenType();

        // If the player's at max health, or they've reached the heart container limit, only fill health bar
        int max = Config.maxHealth.get();
        if (Type == 1) {
            max = (int) (max * 1.5);
        }
        if (cap.getHeartContainers() + 1 > 127 || (max > 0 && player.getMaxHealth() >= max)) {
            player.setHealth(player.getMaxHealth());
        } else {
            cap.addHeartContainer();
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            if (Type == 1) {
                HunterXHunter.applyHealthModifier(player, cap.getEnhancerModifier());
            }
            else {
                HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
            }
            player.setHealth(player.getMaxHealth());

        }

        //yo.increaseNenPower(player);
        //NenUser.updateClient((ServerPlayerEntity) player, yo);

        // Remove item and mark as success
        stack.setCount(stack.getCount() - 1);
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Used to increase one's Aura"));
    }
}