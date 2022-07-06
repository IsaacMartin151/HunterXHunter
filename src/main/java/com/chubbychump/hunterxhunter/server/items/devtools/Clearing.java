package com.chubbychump.hunterxhunter.server.items.devtools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.Player;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.InteractionResultHolder;
import net.minecraft.util.InteractionResultHolderType;
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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BlockPos playerpos = playerIn.getPosition();
        for (int i = playerpos.getX() - 50; i < playerpos.getX() + 51; i++) {
            for (int j = playerpos.getY() - 50; i < playerpos.getY() + 51; j++) {
                for (int k = playerpos.getZ() - 50; i < playerpos.getZ() + 51; k++) {
                    BlockPos pos = new BlockPos(i, j, k);
                    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }

        ItemStack itemstack = playerIn.getMainHandItem(handIn);
        return new InteractionResultHolder<>(InteractionResultHolderType.SUCCESS, itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Clears a 100x100 area around the player"));
    }
}
