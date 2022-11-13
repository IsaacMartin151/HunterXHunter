package com.packets;


import com.abilities.nenstuff.INenUser;
import com.abilities.nenstuff.NenUser;
import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class HXHEntitySpawn {
    private final CompoundTag nbt;

    public HXHEntitySpawn(int eNumber, int eID, CompoundTag nbt) {
        // Add entity id
        nbt.putInt("entity_number", eNumber);
        nbt.putInt("eID", eID);
        this.nbt = nbt;
    }

    private HXHEntitySpawn(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public static void encode(HXHEntitySpawn msg, FriendlyByteBuf buff) {
        buff.writeNbt(msg.nbt);
    }

    public static HXHEntitySpawn decode(FriendlyByteBuf buff) {
        return new HXHEntitySpawn(buff.readNbt());
    }

    public static void handle(HXHEntitySpawn msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            int eNumber = msg.nbt.getInt("entity_number");
            int eID = msg.nbt.getInt("eID");
            if (serverPlayer != null) {

                //PacketManager.sendTo(serverPlayer, new HXHEntitySpawn(eNumber, bruh.getId(), new CompoundTag()));
            }
            else {
                int yee = msg.nbt.getInt("eID");
                Minecraft mc = Minecraft.getInstance();
                INenUser yo = NenUser.getFromPlayer(mc.player);
                Entity bruh = mc.level.getEntity(yee);
                if (bruh != null) {
                    HunterXHunter.LOGGER.info("Adding the entity to world HXHEntitySpawn");
                    Minecraft.getInstance().level.addFreshEntity(bruh);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
