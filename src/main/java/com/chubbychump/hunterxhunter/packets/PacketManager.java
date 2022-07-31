package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(HunterXHunter.MOD_ID, "packetmanager"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, SyncHealthPacket.class, SyncHealthPacket::encode, SyncHealthPacket::decode, SyncHealthPacket::handle);
        INSTANCE.registerMessage(id++, SyncNenPacket.class, SyncNenPacket::encode, SyncNenPacket::decode, SyncNenPacket::handle);
        INSTANCE.registerMessage(id++, SyncBookPacket.class, SyncBookPacket::encode, SyncBookPacket::decode, SyncBookPacket::handle);
        INSTANCE.registerMessage(id++, HXHEntitySpawn.class, HXHEntitySpawn::encode, HXHEntitySpawn::decode, HXHEntitySpawn::handle);
        INSTANCE.registerMessage(id++, SyncTransformCardPacket.class, SyncTransformCardPacket::encode, SyncTransformCardPacket::decode, SyncTransformCardPacket::handle);
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
    public static void sendTo(ServerPlayer player, Object msg) {
        INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

}
