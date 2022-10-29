package com.packets;

import com.chubbychump.hunterxhunter.client.screens.CameraScreen;
import com.chubbychump.hunterxhunter.server.entities.entityclasses.CameraEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.LocalPlayer;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.chubbychump.hunterxhunter.server.items.CardFunctions.getCorrespondingStack;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.CAMERA_ENTITY;

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
        buff.writeCompoundTag(msg.nbt);
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
                CameraEntity bruh = new CameraEntity(CAMERA_ENTITY.get(), serverPlayer.level, (LocalPlayer) serverPlayer.level.getEntity(msg.nbt.getInt("entityid4")), corresponding);
                held.shrink(held.getCount());
                bruh.setPos(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                serverPlayer.getLevel().addEntity(bruh);
                //HunterXHunter.LOGGER.info("Adding camera entity");
                PacketManager.sendTo(serverPlayer, new SyncTransformCardPacket(bruh.getId(), new CompoundTag()));
            }
            else {
                Minecraft mc = Minecraft.getInstance();
                int oof = msg.nbt.getInt("entityid4");
                CameraEntity camera = (CameraEntity) mc.world.getEntity(oof);
                mc.world.addEntity(camera);
                Minecraft.getInstance().displayGuiScreen(CameraScreen.cameraScreenInstance);
                Minecraft.getInstance().setRenderViewEntity(camera);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}