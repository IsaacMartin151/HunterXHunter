package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.server.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealthProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncHealthPacket {

    private final CompoundNBT nbt;

    public SyncHealthPacket(int entityId, CompoundNBT nbt) {
        // Add entity id
        nbt.putInt("entityid", entityId);
        this.nbt = nbt;
    }

    private SyncHealthPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncHealthPacket msg, PacketBuffer buff) {
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncHealthPacket decode(PacketBuffer buff) {
        return new SyncHealthPacket(buff.readCompoundTag());
    }

    public static void handle(SyncHealthPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Fetch Capability
            PlayerEntity player = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(msg.nbt.getInt("entityid"));
            IMoreHealth cap = MoreHealth.getFromPlayer(player);

            // Read NBT Data into Capability
            MoreHealthProvider.CAPABILITY.readNBT(cap, null, msg.nbt);
        });
        ctx.get().setPacketHandled(true);
    }

}

