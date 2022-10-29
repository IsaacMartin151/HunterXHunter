package com.packets;


import com.abilities.nenstuff.INenUser;
import com.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncNenPacket {

    private final CompoundTag nbt;

    public SyncNenPacket(int entityId, CompoundTag nbt) {
        // Add entity id
        nbt.putInt("entityid2", entityId);
        this.nbt = nbt;
    }

    private SyncNenPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncNenPacket msg, FriendlyByteBuf buff) {
        buff.writeNbt(msg.nbt);
    }

    public static SyncNenPacket decode(FriendlyByteBuf buff) {
        return new SyncNenPacket(buff.readNbt());
    }

    public static void handle(SyncNenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {
                LocalPlayer player = (LocalPlayer) Minecraft.getInstance().level.getEntity(msg.nbt.getInt("entityid2"));
                INenUser cap = NenUser.getFromPlayer(player);
                cap.deserializeNBT(msg.nbt);
            }
            else {
                //LocalPlayer player = serverPlayer.server.getPlayerList().getPlayerByUUID(serverPlayer.getUniqueID());
                INenUser cap = NenUser.getFromPlayer(serverPlayer);
                cap.deserializeNBT(msg.nbt);
//                if (cap.getNenPower() > 0) {
//                    AbilityUseTrigger.INSTANCE.trigger(serverPlayer);
//                    if (cap.getNenPower() == 16) {
//                        AbilityMaxTrigger.INSTANCE.trigger(serverPlayer);
//                    }
//                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
