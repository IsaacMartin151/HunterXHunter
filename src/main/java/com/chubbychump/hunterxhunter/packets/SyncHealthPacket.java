package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.server.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealthProvider;
import net.minecraft.client.Minecraft;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncHealthPacket {

    private final CompoundTag nbt;

    public SyncHealthPacket(int entityId, CompoundTag nbt) {
        // Add entity id
        nbt.putInt("entityid", entityId);
        this.nbt = nbt;
    }

    private SyncHealthPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncHealthPacket msg, FriendlyByteBuf buff) {
        buff.writeNbt(msg.nbt);
    }

    public static SyncHealthPacket decode(FriendlyByteBuf buff) {
        return new SyncHealthPacket(buff.readNbt());
    }

    public static void handle(SyncHealthPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Fetch Capability
            Player player = (Player) Minecraft.getInstance().level.getEntity(msg.nbt.getInt("entityid"));
            MoreHealth cap = MoreHealth.getFromPlayer(player);

            // Read NBT Data into Capability
            cap.
        });
        ctx.get().setPacketHandled(true);
    }

}

