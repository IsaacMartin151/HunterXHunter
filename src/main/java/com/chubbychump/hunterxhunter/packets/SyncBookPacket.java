package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

import static com.chubbychump.hunterxhunter.server.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;

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
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncBookPacket decode(FriendlyByteBuf buff) {
        return new SyncBookPacket(buff.readCompoundTag());
    }

    public static void handle(SyncBookPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {
                //Player player = (Player) Minecraft.getInstance().world.getEntityByID(msg.nbt.getInt("entityid3"));
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
