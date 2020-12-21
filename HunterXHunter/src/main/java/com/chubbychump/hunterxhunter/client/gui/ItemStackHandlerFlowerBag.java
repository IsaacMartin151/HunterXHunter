package com.chubbychump.hunterxhunter.client.gui;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class ItemStackHandlerFlowerBag extends ItemStackHandler {
    ResourceLocation THEONEHUNDRED = new ResourceLocation(MOD_ID, "theonehundred");

    public static final int MIN_FLOWER_SLOTS = 1;
    public static final int MAX_FLOWER_SLOTS = 100;

    public ItemStackHandlerFlowerBag(int numberOfSlots) {
        super(MathHelper.clamp(numberOfSlots, MIN_FLOWER_SLOTS, MAX_FLOWER_SLOTS));
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= MAX_FLOWER_SLOTS) {
            throw new IllegalArgumentException("Invalid slot number:"+slot);
        }
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item.isIn(ItemTags.getCollection().get(THEONEHUNDRED))) return true; //Changed it to accept only the 100
        return false;
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return 999999;
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
        // A problem - the ItemStack and the ItemStackHandler don't know which player is holding the flower bag.  Or in fact whether
        //   the bag is being held by any player at all.
        // We have a few choices -
        // * we can search all the players on the server to see which one is holding the bag; or
        // * we can try to store the owner of the ItemStack in the ItemStackHandler, ItemStack, or ContainerFlowerBag,
        //   (which becomes problematic if the owner drops the ItemStack, or if there is no container); or
        // * we can mark the bag as dirty and let the containerFlowerBag detect that.
        // I've used the third method because it is easier to code, produces less coupling between classes, and probably more efficient
        // Fortunately, we only need to manually force an update when the player has the container open.  If changes could occur while the
        //   item was discarded (inside an ItemEntity) it would be much trickier.
        isDirty = true;
    }

    private boolean isDirty = true;

}