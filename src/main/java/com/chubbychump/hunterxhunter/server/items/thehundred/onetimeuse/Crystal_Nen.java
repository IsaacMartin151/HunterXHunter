package com.chubbychump.hunterxhunter.server.items.thehundred.onetimeuse;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.screens.PuzzleScreen;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.property.Properties;

import javax.annotation.Nullable;

import java.util.List;

import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider.NENUSER;

public class Crystal_Nen extends Item {
    public Crystal_Nen() {
        super(new Properties().stacksTo(64).rarity(Rarity.RARE).tab(HunterXHunter.TAB));
    }

    @OnlyIn(Dist.CLIENT)
    private void openGui(Player player) {
        INenUser yo = player.getCapability(NENUSER).orElseThrow(null);
        if (yo.getNenPower() == 0) {
            yo.increaseNenPower();
            NenUser.updateServer(player, yo);
        }
        else {
            Minecraft.getInstance().pushGuiLayer(new PuzzleScreen(60 - (2 * yo.getNenPower())));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        // Setup return results
        ItemStack stack = player.getItemInHand(hand);
        InteractionResultHolder<ItemStack> result = new InteractionResultHolder<>(InteractionResult.PASS, stack);
        HunterXHunter.LOGGER.info("Right clicked crystal nen");
        // Ensure server-side only & the player's not in creative or spectator
        if (world.isClientSide) {
            openGui(player);
            return result;
        }



        // Get capability
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        INenUser yo = NenUser.getFromPlayer(player);
        if (yo.getNenPower() != 0) {
            player.getServer().sendSystemMessage(Component.literal("Facing a trial. Duration: " + (120 - (2 * yo.getNenPower()))).setStyle(Style.EMPTY.withColor(32896).withItalic(true)));
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
            MoreHealth.updateClient((ServerPlayer) player, cap);
            if (Type == 1) {
                HunterXHunter.applyHealthModifier(player, cap.getEnhancerModifier());
            }
            else {
                HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
            }
            player.setHealth(player.getMaxHealth());

        }

        // Remove item and mark as success
        stack.setCount(stack.getCount() - 1);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Used to increase one's Aura"));
    }
}