package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;


public class GonFishingPole extends FishingRodItem implements Vanishable {
    public GonFishingPole() {
        super(new Item.Properties().stacksTo(1).tab(HunterXHunter.TAB).rarity(Rarity.UNCOMMON).defaultDurability(100));
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Gon's fishing rod. Has Luck 5 and Lure 5"));
    }
}
