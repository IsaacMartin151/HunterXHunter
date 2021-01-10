package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Supplier;

import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.CRYSTALLIZEDNEN;

public class SyncTransformCardPacket {

    private final CompoundNBT nbt;

    public SyncTransformCardPacket(int entityId, CompoundNBT nbt) {
        // Add entity id
        nbt.putInt("entityid3", entityId);
        this.nbt = nbt;
    }

    private SyncTransformCardPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncTransformCardPacket msg, PacketBuffer buff) {
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncTransformCardPacket decode(PacketBuffer buff) {
        return new SyncTransformCardPacket(buff.readCompoundTag());
    }

    public static void handle(SyncTransformCardPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity serverPlayer = ctx.get().getSender();
            if (serverPlayer != null) {
                if (serverPlayer.inventory.addItemStackToInventory(CRYSTALLIZEDNEN.get().getDefaultInstance())) {
                    serverPlayer.inventory.decrStackSize(serverPlayer.inventory.currentItem, 1);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
