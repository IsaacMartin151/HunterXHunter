package com.abilities.greedislandbook;


import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static com.example.hunterxhunter.HunterXHunter.MODID;

public class BookItemStackHandler extends ItemStackHandler {
    public static ResourceLocation THEONEHUNDRED = new ResourceLocation(MODID, "theonehundred");
    public static TagKey<Item> TheOneHundred = ItemTags.create(THEONEHUNDRED);

    private static final int BOOK_SIZE = 100;

    public BookItemStackHandler() {
        super(BOOK_SIZE);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= BOOK_SIZE) {
            throw new IllegalArgumentException("Invalid slot number: " + slot);
        }
        if (stack.isEmpty() || stacks.contains(stack)) {
            return false;
        }
        if (stack.is(TheOneHundred)) {
            HunterXHunter.LOGGER.info("Target item is a collection card");
            return true;
        }
        HunterXHunter.LOGGER.info("Not a valid itemstack, not in the hundred or already there");
        return false;
    }

    public boolean isComplete() {
        ItemStack[] bruh = new ItemStack[100];
        for (int i = 0; i < this.getSlots(); i++) {
            ItemStack item = stacks.get(i).getItem().getDefaultInstance();
            if (item.is(TheOneHundred)) {
                for (int j = 0; j < this.getSlots(); j++) {
                    if (j != i) {
                        if (item.sameItem(stacks.get(j).getItem().getDefaultInstance())) {
                            HunterXHunter.LOGGER.info("Returning false, book is not complete");
                            return false;
                        }
                    }
                }
                bruh[i] = item;
            }
        }
        HunterXHunter.LOGGER.info("Returning true, book is complete");
        return true;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 999;
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return 999;
    }
}