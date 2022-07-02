package com.chubbychump.hunterxhunter.server.abilities.greedislandbook;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;
import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class BookItemStackHandler extends ItemStackHandler {
    public static ResourceLocation THEONEHUNDRED = new ResourceLocation(MOD_ID, "theonehundred");
    public static ResourceLocation THEONEHUNDREDCARDS = new ResourceLocation(MOD_ID, "theonehundredcards");

    public static final int MIN_FLOWER_SLOTS = 1;
    public static final int MAX_FLOWER_SLOTS = 100;

    public BookItemStackHandler(int numberOfSlots) {
        super(MathHelper.clamp(numberOfSlots, MIN_FLOWER_SLOTS, MAX_FLOWER_SLOTS));
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= MAX_FLOWER_SLOTS) {
            throw new IllegalArgumentException("Invalid slot number:"+slot);
        }
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item.isIn(ItemTags.getCollection().get(THEONEHUNDREDCARDS))) {
            LOGGER.info("Target item is a collection card, verifying it's not already in there");
            for (int i = 0; i < this.getSlots(); i++) {
                for (int j = 0; j < this.getSlots(); j++) {
                    if (j != i) {
                        if (item == stacks.get(j).getItem()) {
                            HunterXHunter.LOGGER.info("Returning false, item is already in a slot");
                            return false;
                        }
                    }
                }
            }
            return true; //Changed it to accept only the 100
        }
        return false;
    }

    public boolean isComplete() {
        ResourceLocation bruh[] = new ResourceLocation[this.getSlots()];
        for (int i = 0; i < this.getSlots(); i++) {
            Item item = stacks.get(i).getItem();
            if (item.isIn(ItemTags.getCollection().get(THEONEHUNDREDCARDS))) {
                for (int j = 0; j < this.getSlots(); j++) {
                    if (j != i) {
                        if (item.getRegistryName() == stacks.get(j).getItem().getRegistryName()) {
                            HunterXHunter.LOGGER.info("Returning false, book is not complete");
                            return false;
                        }
                    }
                }
                bruh[i] = item.getRegistryName();
            }
        }
        HunterXHunter.LOGGER.info("Returning true, bag is complete");
        return true;
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return 64;
    }

    /** returns true if the contents have changed since the last call.
     * Resets to false after each call.
     * @return true if changed since the last call
     */
    public boolean isDirty() {
        boolean currentState = isDirty;
        isDirty = false;
        return currentState;
    }

    /** Called whenever the contents of the bag have changed.
     *   We need to do this manually in order to make sure that the server sends a synchronisation packet to the client for the parent ItemStack
     *   The reason is because capability information is not stored in the ItemStack nbt tag, so vanilla does not notice when
     *   the flowerbag's capability has changed.
     * @param slot
     */
    protected void onContentsChanged(int slot) {
        isDirty = true;
    }

    private boolean isDirty = true;

}