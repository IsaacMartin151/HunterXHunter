package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.server.advancements.AbilityMaxTrigger;
import com.chubbychump.hunterxhunter.server.advancements.AbilityUseTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

    public static void encode(SyncNenPacket msg, PacketBuffer buff) {
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncNenPacket decode(PacketBuffer buff) {
        return new SyncNenPacket(buff.readCompoundTag());
    }

    public static void handle(SyncNenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer == null) {
                Player player = (Player) Minecraft.getInstance().level.getEntity(msg.nbt.getInt("entityid2"));
                INenUser cap = NenUser.getFromPlayer(player);
                NenProvider.NENUSER.readNBT(cap, null, msg.nbt);
            }
            else {
                //PlayerEntity player = serverPlayer.server.getPlayerList().getPlayerByUUID(serverPlayer.getUniqueID());
                INenUser cap = NenUser.getFromPlayer(serverPlayer);
                NenProvider.NENUSER.readNBT(cap, null, msg.nbt);
                if (cap.getNenPower() > 0) {
                    AbilityUseTrigger.INSTANCE.trigger(serverPlayer);
                    if (cap.getNenPower() == 16) {
                        AbilityMaxTrigger.INSTANCE.trigger(serverPlayer);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
