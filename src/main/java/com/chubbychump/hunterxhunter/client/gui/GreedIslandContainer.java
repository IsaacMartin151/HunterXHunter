package com.chubbychump.hunterxhunter.client.gui;


import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookItemStackHandler;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.GREED_ISLAND_CONTAINER;

/**
 * The ContainerFlowerBag is used to manipulate the contents of the FlowerBag (BookItemStackHandler).
 * The master copy is on the server side, with a "dummy" copy stored on the client side
 * The GUI (ContainerScreen) on the client side interacts with the dummy copy.
 * Vanilla ensures that the server and client copies remain synchronised.
 */

public class GreedIslandContainer extends Container {
    private final BookItemStackHandler itemStackHandlerFlowerBag;

    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 51 = TileInventory slots, which map to our bag slot numbers 0 - 15)

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int BAG_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int MAX_EXPECTED_BAG_SLOT_COUNT = 100;

    public static final int BAG_INVENTORY_YPOS = 16;  // the ContainerScreenFlowerBag needs to know these so it can tell where to draw the Titles
    public static final int PLAYER_INVENTORY_YPOS = 162;

    /**
     * Creates the container to be used on the server side
     * @param windowID
     * @param playerInventory
     * @param bagContents
     * @return
     */
    public static GreedIslandContainer createContainerServerSide(int windowID, PlayerInventory playerInventory, BookItemStackHandler bagContents) {
        return new GreedIslandContainer(windowID, playerInventory, bagContents);
    }

    /**
     * Creates the container to be used on the client side  (contains dummy data)
     * @param windowID
     * @param playerInventory
     * @param extraData extra data sent from the server
     * @return
     */
    public static GreedIslandContainer createContainerClientSide(int windowID, PlayerInventory playerInventory, net.minecraft.network.FriendlyByteBuf extraData) {

        try {
            BookItemStackHandler itemStackHandlerFlowerBag = new BookItemStackHandler(100);
            // on the client side there is no parent ItemStack to communicate with - we use a dummy inventory
            return new GreedIslandContainer(windowID, playerInventory, itemStackHandlerFlowerBag);
        } catch (IllegalArgumentException iae) {
            LOGGER.warn(iae);
        }
        return null;
    }



    /**
     * Creates a container suitable for server side or client side
     * @param windowId ID of the container
     * @param playerInv the inventory of the player
     * @param itemStackHandlerFlowerBag the inventory stored in the bag
     */
    public GreedIslandContainer(int windowId, PlayerInventory playerInv, BookItemStackHandler itemStackHandlerFlowerBag) {
        super(GREED_ISLAND_CONTAINER.get(), windowId);
        this.itemStackHandlerFlowerBag = itemStackHandlerFlowerBag;

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
        int bagSlotCount = itemStackHandlerFlowerBag.getSlots();
        if (bagSlotCount < 1 || bagSlotCount > MAX_EXPECTED_BAG_SLOT_COUNT) {
            LOGGER.warn("Unexpected invalid slot count in BookItemStackHandler(" + bagSlotCount + ")");
            bagSlotCount = MathHelper.clamp(bagSlotCount, 1, MAX_EXPECTED_BAG_SLOT_COUNT);
        }

        final int BAG_SLOTS_PER_ROW = 12;
        final int BAG_INVENTORY_XPOS = 10;
        // Add the tile inventory container to the gui
        for (int bagSlot = 0; bagSlot < bagSlotCount; ++bagSlot) {
            int slotNumber = bagSlot;
            int bagRow = bagSlot / BAG_SLOTS_PER_ROW;
            int bagCol = bagSlot % BAG_SLOTS_PER_ROW;
            int xpos = BAG_INVENTORY_XPOS + SLOT_X_SPACING * bagCol;
            int ypos = BAG_INVENTORY_YPOS + SLOT_Y_SPACING * bagRow;
            addSlot(new SlotItemHandler(itemStackHandlerFlowerBag, slotNumber, xpos, ypos));
        }
    }

    // Called on the server side only.
    @Override
    public boolean canInteractWith(@Nonnull Player player) {
        return true;
    }

    // This is where you specify what happens when a player shift clicks a slot in the gui
    //  (when you shift click a slot in the Bag Inventory, it moves it to the first available position in the hotbar and/or
    //    player inventory.  When you you shift-click a hotbar or player inventory item, it moves it to the first available
    //    position in the Bag inventory)
    // At the very least you must override this and return ItemStack.EMPTY or the game will crash when the player shift clicks a slot
    // returns ItemStack.EMPTY if the source slot is empty, or if none of the the source slot item could be moved
    //   otherwise, returns a copy of the source stack

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(Player player, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();
        final int BAG_SLOT_COUNT = itemStackHandlerFlowerBag.getSlots();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the bag inventory
            if (!mergeItemStack(sourceStack, BAG_INVENTORY_FIRST_SLOT_INDEX, BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT, false)){
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (sourceSlotIndex >= BAG_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT) {
            // This is a bag slot so merge the stack into the players inventory
            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    /**
     * Because capability nbt is not actually stored in the ItemStack nbt (it is created fresh each time we need to transmit or save an nbt), detectAndSendChanges
     *   does not work for our ItemFlowerBag ItemStack.  i.e. when the contents of BookItemStackHandler are changed, the nbt of ItemFlowerBag ItemStack don't change,
     *   so it is not sent to the client.
     * For this reason, we need to manually detect when it has changed and mark it dirty.
     * The easiest way is just to set a counter in the nbt tag and let the vanilla code notice that the itemstack has changed.
     * The side effect is that the player's hand moves down and up (because the client thinks it is a new ItemStack) but that's not objectionable.
     * Alternatively you could copy the code from vanilla detectAndSendChanges and tweak it to find the slot for itemStackBeingHeld and send it manually.
     *
     * Of course, if your ItemStack's capability doesn't affect the rendering of the ItemStack, i.e. the Capability is not needed on the client at all, then
     *   you don't need to bother with marking it dirty.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    private static final Logger LOGGER = HunterXHunter.LOGGER;

}
