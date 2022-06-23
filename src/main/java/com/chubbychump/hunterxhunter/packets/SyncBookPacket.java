package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Supplier;

import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.OPEN_BOOK;

public class SyncBookPacket {

    private final CompoundNBT nbt;

    public SyncBookPacket(int entityId, CompoundNBT nbt) {
        // Add entity id
        nbt.putInt("entityid3", entityId);
        this.nbt = nbt;
    }

    private SyncBookPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncBookPacket msg, PacketBuffer buff) {
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncBookPacket decode(PacketBuffer buff) {
        return new SyncBookPacket(buff.readCompoundTag());
    }

    public static void handle(SyncBookPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {
                //PlayerEntity player = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(msg.nbt.getInt("entityid3"));
                //ItemStackHandler cap = NenUser.getFromPlayer(player);
            }
            else {
                HunterXHunter.LOGGER.info("Opening GUI");
                BookItemStackHandler cap = (BookItemStackHandler) serverPlayer.getCapability(BOOK_CAPABILITY).orElseThrow(null);
                INamedContainerProvider container = new SimpleNamedContainerProvider((w, p, pl) -> new GreedIslandContainer(w, p, cap), new TranslationTextComponent("Book!"));
                INenUser nenUser = NenUser.getFromPlayer(serverPlayer);
                nenUser.setOpenedBook(true);
                nenUser.setLastOpenedBook(Util.milliTime());
                NetworkHooks.openGui(serverPlayer, container);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
