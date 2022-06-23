package com.chubbychump.hunterxhunter.common.items.thehundred.tools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.items.StaffBase;
import javafx.geometry.BoundingBox;
import net.minecraft.client.gui.SpectatorGui;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.impl.GameModeCommand;
import net.minecraft.command.impl.SpectateCommand;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PhantomStaff extends StaffBase {
    private boolean clipping = false;

    public PhantomStaff() {
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        HunterXHunter.LOGGER.info("Toggled clipping");
        clipping = !clipping;
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    public boolean isOn() {
        return clipping;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Right click to toggle, allows player to phase through walls"));
    }
}
