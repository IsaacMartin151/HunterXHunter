package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncNenPacket {

    private final CompoundNBT nbt;

    public SyncNenPacket(int entityId, CompoundNBT nbt) {
        // Add entity id
        nbt.putInt("entityid2", entityId);
        this.nbt = nbt;
    }

    private SyncNenPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncNenPacket msg, PacketBuffer buff) {
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncNenPacket decode(PacketBuffer buff) {
        return new SyncNenPacket(buff.readCompoundTag());
    }

    public static void handle(SyncNenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {
                PlayerEntity player = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(msg.nbt.getInt("entityid2"));
                NenUser cap = NenUser.getFromPlayer(player);
                NenProvider.NENUSER.readNBT(cap, null, msg.nbt);
            }
            else {
                //PlayerEntity player = serverPlayer.server.getPlayerList().getPlayerByUUID(serverPlayer.getUniqueID());
                NenUser cap = NenUser.getFromPlayer(serverPlayer);
                NenProvider.NENUSER.readNBT(cap, null, msg.nbt);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
