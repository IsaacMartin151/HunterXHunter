package com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class Crystal_Nen extends Item {

    public Crystal_Nen() {
        super(new Properties().maxStackSize(64).rarity(Rarity.RARE).group(HunterXHunter.TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        // Setup return results
        ItemStack stack = player.getHeldItem(hand);
        ActionResult<ItemStack> result = new ActionResult<>(ActionResultType.PASS, stack);

        // Ensure server-side only & the player's not in creative or spectator
        if (world.isRemote || player.abilities.disableDamage) {
            return result;
        }

        // If disabled don't do anything
        if (!Config.enableItems.get()) {
            return result;
        }

        // Get capability
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        LazyOptional<NenUser> yo = player.getCapability(NenProvider.MANA_CAP, null);
        int Type = yo.orElseThrow(null).getNenType();

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
            yo.orElseThrow(null).increaseNenPower(player);
            player.sendMessage(new TranslationTextComponent("Just leveled up!"), Util.DUMMY_UUID);

        }

        // Remove item and mark as success
        stack.setCount(stack.getCount() - 1);
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}