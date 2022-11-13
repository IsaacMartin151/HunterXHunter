package com.packets;


import com.abilities.greedislandbook.BookItemStackHandler;
import com.abilities.nenstuff.INenUser;
import com.abilities.nenstuff.NenUser;
import com.container.BookMenu;
import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

import static com.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;


public class SyncBookPacket {

    private final CompoundTag nbt;

    public SyncBookPacket(int entityId, CompoundTag nbt) {
        // Add entity id
        nbt.putInt("entityid3", entityId);
        this.nbt = nbt;
    }

    private SyncBookPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncBookPacket msg, FriendlyByteBuf buff) {
        buff.writeNbt(msg.nbt);
    }

    public static SyncBookPacket decode(FriendlyByteBuf buff) {
        return new SyncBookPacket(buff.readNbt());
    }

    public static void handle(SyncBookPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {

            }
            else {
                HunterXHunter.LOGGER.info("Opening GUI");
                BookItemStackHandler cap = serverPlayer.getCapability(BOOK_CAPABILITY, serverPlayer.getDirection()).orElseThrow(null);
                MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new BookMenu(w, p, cap), Component.literal("Book"));
                INenUser nenUser = NenUser.getFromPlayer(serverPlayer);
                nenUser.setOpenedBook(true);
                nenUser.setLastOpenedBook(Util.getMillis());
                NetworkHooks.openScreen(serverPlayer, container);
                HunterXHunter.LOGGER.info("Opened GUI");
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
