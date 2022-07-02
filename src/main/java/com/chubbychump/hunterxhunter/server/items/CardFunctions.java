package com.chubbychump.hunterxhunter.server.items;

import net.minecraft.item.ItemStack;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;

public class CardFunctions {
    public static ItemStack getCorrespondingStack(ItemStack stackIn) {
        ItemStack test = firstTen(stackIn);
        if (test != ItemStack.EMPTY) {
            return test;
        }

        // New items
        if (stackIn.getItem() == CARD_11.get()) {
            return TASTY_FOOD.get().getDefaultInstance();
        }
        if (stackIn.getItem() == TASTY_FOOD.get()) {
            return CARD_11.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_12.get()) {
            return ROASTED_PORK_DISH.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ROASTED_PORK_DISH.get()) {
            return CARD_12.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_13.get()) {
            return POTATO_SOUP.get().getDefaultInstance();
        }
        if (stackIn.getItem() == POTATO_SOUP.get()) {
            return CARD_13.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_14.get()) {
            return SPIDER_EAGLE_EGG.get().getDefaultInstance();
        }
        if (stackIn.getItem() == SPIDER_EAGLE_EGG.get()) {
            return CARD_14.get().getDefaultInstance();
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack firstTen(ItemStack stackIn) {
        if (stackIn.getItem() == CARD_1.get()) {
            return ASPECT_OF_ANT.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_ANT.get()) {
            return CARD_1.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_2.get()) {
            return ASPECT_OF_BAT.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_BAT.get()) {
            return CARD_2.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_3.get()) {
            return ASPECT_OF_BEAR.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_BEAR.get()) {
            return CARD_3.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_4.get()) {
            return ASPECT_OF_BLAZE.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_BLAZE.get()) {
            return CARD_4.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_5.get()) {
            return ASPECT_OF_CREEPER.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_CREEPER.get()) {
            return CARD_5.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_6.get()) {
            return ASPECT_OF_GHAST.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_GHAST.get()) {
            return CARD_6.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_7.get()) {
            return ASPECT_OF_GUARDIAN.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_GUARDIAN.get()) {
            return CARD_7.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_8.get()) {
            return ASPECT_OF_PHANTOM.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_PHANTOM.get()) {
            return CARD_8.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_9.get()) {
            return ASPECT_OF_SPIDER.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_SPIDER.get()) {
            return CARD_9.get().getDefaultInstance();
        }
        if (stackIn.getItem() == CARD_10.get()) {
            return ASPECT_OF_VILLAGER.get().getDefaultInstance();
        }
        if (stackIn.getItem() == ASPECT_OF_VILLAGER.get()) {
            return CARD_10.get().getDefaultInstance();
        }
        return ItemStack.EMPTY;
    }
}
