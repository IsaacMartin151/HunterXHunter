package com.container;


import com.abilities.greedislandbook.BookItemStackHandler;
import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BookMenu extends AbstractContainerMenu {
    private final BookItemStackHandler bookItemStackHandler;

    private static final int HOTBAR_SLOT_COUNT = 10;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int BAG_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    public static final int BAG_INVENTORY_YPOS = 16;
    public static final int PLAYER_INVENTORY_YPOS = 162;

    public BookMenu(int containerId, Inventory playerInv, FriendlyByteBuf friendlyByteBuf) {
        this(containerId, playerInv, new BookItemStackHandler());
    }

    public BookMenu(int containerId, Inventory playerInv, BookItemStackHandler bookItemStackHandler) {
        super(MenuType.STONECUTTER, containerId);
        this.bookItemStackHandler = bookItemStackHandler;

        int SLOT_X_SPACING = 18;
        int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 27;
        final int HOTBAR_YPOS = 220;
        // Add the players hotbar to the gui - the [xpos, ypos] location of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlot(new Slot(playerInv, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 27;
        // Add the rest of the player's inventory to the gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(playerInv, slotNumber, xpos, ypos));
            }
        }

        SLOT_X_SPACING = 14;
        SLOT_Y_SPACING = 14;
        int bagSlotCount = bookItemStackHandler.getSlots();

        final int BOOK_SLOTS_PER_ROW = 12;
        final int BOOK_INVENTORY_XPOS = 10;
        // Add the tile inventory container to the gui
        for (int bagSlot = 0; bagSlot < bagSlotCount; ++bagSlot) {
            int slotNumber = bagSlot;
            int bagRow = bagSlot / BOOK_SLOTS_PER_ROW;
            int bagCol = bagSlot % BOOK_SLOTS_PER_ROW;
            int xpos = BOOK_INVENTORY_XPOS + SLOT_X_SPACING * bagCol;
            int ypos = BAG_INVENTORY_YPOS + SLOT_Y_SPACING * bagRow;
            addSlot(new SlotItemHandler(bookItemStackHandler, slotNumber, xpos, ypos));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int sourceSlotIndex) {
        Slot sourceSlot = slots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        final int BAG_SLOT_COUNT = bookItemStackHandler.getSlots();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the bag inventory
            if (!moveItemStackTo(sourceStack, BAG_INVENTORY_FIRST_SLOT_INDEX, BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT, false)){
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (sourceSlotIndex >= BAG_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT) {
            // This is a bag slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            HunterXHunter.LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
