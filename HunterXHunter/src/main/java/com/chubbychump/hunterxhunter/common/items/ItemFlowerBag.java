package com.chubbychump.hunterxhunter.common.items;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.client.gui.ItemStackHandlerFlowerBag;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;


public class ItemFlowerBag extends Item {
    private static ItemStackHandlerFlowerBag itemStackHandlerFlowerBag = null;

    public ItemFlowerBag() {
        super(new Item.Properties().maxStackSize(1).group(HunterXHunter.TAB) // the item will appear on the Miscellaneous tab in creative
        );
    }

    /**
     * When the player right clicks while holding the bag, open the inventory screen
     * @param world
     * @param player
     * @param hand
     * @return the new itemstack
     */
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        HunterXHunter.LOGGER.info("Right clicked the item");
        if (!world.isRemote) {  // server only!
            HunterXHunter.LOGGER.info("On server");
            INamedContainerProvider containerProviderFlowerBag = new ContainerProviderFlowerBag(stack);
            NetworkHooks.openGui((ServerPlayerEntity) player,
                    containerProviderFlowerBag,
                    (packetBuffer)->{packetBuffer.writeInt(100);});
            // We use the packetBuffer to send the bag size; not necessary since it's always 16, but just for illustration purposes
            HunterXHunter.LOGGER.info("Closing gui?");
        }
        itemStackHandlerFlowerBag = getItemStackHandlerFlowerBag(player);
        return ActionResult.resultSuccess(stack);
    }

    // ------  Code used to generate a suitable Container for the contents of the FlowerBag

    /**
     * Uses an inner class as an INamedContainerProvider.  This does two things:
     *   1) Provides a name used when displaying the container, and
     *   2) Creates an instance of container on the server which is linked to the ItemFlowerBag
     * You could use SimpleNamedContainerProvider with a lambda instead, but I find this method easier to understand
     * I've used a static inner class instead of a non-static inner class for the same reason
     */
    public static class ContainerProviderFlowerBag implements INamedContainerProvider {
        public ContainerProviderFlowerBag(ItemStack itemStackFlowerBag) {
            this.itemStackFlowerBag = itemStackFlowerBag;
            this.itemFlowerBag = new ItemFlowerBag();
        }

        @Override
        public ITextComponent getDisplayName() {
            return itemStackFlowerBag.getDisplayName();
        }

        /**
         * The name is misleading; createMenu has nothing to do with creating a Screen, it is used to create the Container on the server only
         */
        @Override
        public GreedIslandContainer createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            GreedIslandContainer newContainerServerSide =
                    GreedIslandContainer.createContainerServerSide(windowID, playerInventory,
                            itemFlowerBag.getItemStackHandlerFlowerBag(playerEntity),
                            itemStackFlowerBag);
            return newContainerServerSide;
        }

        private ItemFlowerBag itemFlowerBag;
        private ItemStack itemStackFlowerBag;
    }

    // ---------------- Code related to Capabilities
    //

    // The CapabilityProvider returned from this method is used to specify which capabilities the ItemFlowerBag possesses
    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT oldCapNbt) {
        return new GreedIslandProvider();
    }

    /**
     * Retrieves the ItemStackHandlerFlowerBag for this itemStack (retrieved from the Capability)
     * param itemStack sike, its a playerentity
     * @return
     */
    private static ItemStackHandlerFlowerBag getItemStackHandlerFlowerBag(PlayerEntity player) {
        //IItemHandler flowerBag = player.getHeldItemMainhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        LazyOptional<NenUser> yo = player.getCapability(NENUSER, null);
        IItemHandler flowerBag = yo.orElseThrow(null).getBook().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(null);
        //IItemHandler flowerBag = yo.orElseThrow(null).getBook().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

        //itemStackHandlerFlowerBag = yo.orElseThrow(null).getCachedInventory();

        return (ItemStackHandlerFlowerBag) flowerBag;
    }

    private final String BASE_NBT_TAG = "base";
    private final String CAPABILITY_NBT_TAG = "cap";

    /**
     * Ensure that our capability is sent to the client when transmitted over the network.
     * Not needed if you don't need the capability information on the client
     *
     * Note that this will sometimes be applied multiple times, the following MUST
     * be supported:
     *   Item item = stack.getItem();
     *   NBTTagCompound nbtShare1 = item.getShareTag(stack);
     *   stack.readShareTag(nbtShare1);
     *   NBTTagCompound nbtShare2 = item.getShareTag(stack);
     *   assert nbtShare1.equals(nbtShare2);
     *
     * @param stack The stack to send the NBT tag for
     * @return The NBT tag
     */
    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        CompoundNBT capabilityTag = itemStackHandlerFlowerBag.serializeNBT();
        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) {
            combinedTag.put(BASE_NBT_TAG, baseTag);
        }
        if (capabilityTag != null) {
            combinedTag.put(CAPABILITY_NBT_TAG, capabilityTag);
        }
        return combinedTag;
    }

    /** Retrieve our capability information from the transmitted NBT information
     *
     * @param stack The stack that received NBT
     * @param nbt   Received NBT, can be null
     */
    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundNBT baseTag = nbt.getCompound(BASE_NBT_TAG);              // empty if not found
        CompoundNBT capabilityTag = nbt.getCompound(CAPABILITY_NBT_TAG); // empty if not found
        stack.setTag(baseTag);
        itemStackHandlerFlowerBag.deserializeNBT(capabilityTag);
    }
}
