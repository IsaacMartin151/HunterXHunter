package com.chubbychump.hunterxhunter.server.items.devtools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.Player;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class Clearing extends Item {
    public Clearing() { super(new Item.Properties().group(HunterXHunter.TAB)); }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, Player playerIn, Hand handIn) {
        BlockPos playerpos = playerIn.getPosition();
        for (int i = playerpos.getX() - 50; i < playerpos.getX() + 51; i++) {
            for (int j = playerpos.getY() - 50; i < playerpos.getY() + 51; j++) {
                for (int k = playerpos.getZ() - 50; i < playerpos.getZ() + 51; k++) {
                    BlockPos pos = new BlockPos(i, j, k);
                    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Clears a 100x100 area around the player"));
    }
}
