package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(HunterXHunter.MOD_ID, "packetmanager"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, SyncHealthPacket.class, SyncHealthPacket::encode, SyncHealthPacket::decode, SyncHealthPacket::handle);
        INSTANCE.registerMessage(id++, SyncNenPacket.class, SyncNenPacket::encode, SyncNenPacket::decode, SyncNenPacket::handle);
        INSTANCE.registerMessage(id++, SyncBookPacket.class, SyncBookPacket::encode, SyncBookPacket::decode, SyncBookPacket::handle);
    }

    /**
     * Sends a packet from the client to the server.
     * Must be called client side.
     */
    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }

    /**
     * Sends a packet from the server to a player.
     * Must be called server side.
     */
    public static void sendTo(ServerPlayerEntity player, Object msg) {
        INSTANCE.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

}
