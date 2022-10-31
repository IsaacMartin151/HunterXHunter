package com.packets;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;



public class SyncTransformCardPacket {

    private final CompoundTag nbt;

    public SyncTransformCardPacket(int entityId, CompoundTag nbt) {
        // Add entity id
        nbt.putInt("entityid4", entityId);
        this.nbt = nbt;
    }

    private SyncTransformCardPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncTransformCardPacket msg, FriendlyByteBuf buff) {
        buff.writeNbt(msg.nbt);
    }

    public static SyncTransformCardPacket decode(FriendlyByteBuf buff) {
        return new SyncTransformCardPacket(buff.readNbt());
    }

    public static void handle(SyncTransformCardPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer != null) {
                ItemStack held = serverPlayer.getHeldItemMainhand();
                ItemStack corresponding = getCorrespondingStack(held);
                corresponding.setCount(held.getCount());
                Camera bruh = new Camera(CAMERA_ENTITY.get(), serverPlayer.level, (LocalPlayer) serverPlayer.level.getEntity(msg.nbt.getInt("entityid4")), corresponding);
                held.shrink(held.getCount());
                bruh.setPos(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                serverPlayer.getLevel().addFreshEntity(bruh);
                //HunterXHunter.LOGGER.info("Adding camera entity");
                PacketManager.sendTo(serverPlayer, new SyncTransformCardPacket(bruh.getId(), new CompoundTag()));
            }
            else {
                Minecraft mc = Minecraft.getInstance();
                int oof = msg.nbt.getInt("entityid4");
                Camera camera = (Camera) mc.world.getEntity(oof);
                mc.world.addEntity(camera);
                Minecraft.getInstance().displayGuiScreen(CameraScreen.cameraScreenInstance);
                Minecraft.getInstance().setRenderViewEntity(camera);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
