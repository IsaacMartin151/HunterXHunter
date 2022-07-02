package com.chubbychump.hunterxhunter.packets;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.CameraEntity;
import com.chubbychump.hunterxhunter.client.screens.CameraScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.chubbychump.hunterxhunter.server.items.CardFunctions.getCorrespondingStack;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.CAMERA_ENTITY;

public class SyncTransformCardPacket {

    private final CompoundNBT nbt;

    public SyncTransformCardPacket(int entityId, CompoundNBT nbt) {
        // Add entity id
        nbt.putInt("entityid4", entityId);
        this.nbt = nbt;
    }

    private SyncTransformCardPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(SyncTransformCardPacket msg, PacketBuffer buff) {
        buff.writeCompoundTag(msg.nbt);
    }

    public static SyncTransformCardPacket decode(PacketBuffer buff) {
        return new SyncTransformCardPacket(buff.readCompoundTag());
    }

    public static void handle(SyncTransformCardPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity serverPlayer = ctx.get().getSender();
            if (serverPlayer != null) {
                ItemStack held = serverPlayer.getHeldItemMainhand();
                ItemStack corresponding = getCorrespondingStack(held);
                corresponding.setCount(held.getCount());
                CameraEntity bruh = new CameraEntity(CAMERA_ENTITY.get(), serverPlayer.world, (PlayerEntity) serverPlayer.world.getEntityByID(msg.nbt.getInt("entityid4")), corresponding);
                held.shrink(held.getCount());
                bruh.setPosition(serverPlayer.getPosX(), serverPlayer.getPosY(), serverPlayer.getPosZ());
                serverPlayer.getEntityWorld().addEntity(bruh);
                //HunterXHunter.LOGGER.info("Adding camera entity");
                PacketManager.sendTo(serverPlayer, new SyncTransformCardPacket(bruh.getEntityId(), new CompoundNBT()));
            }
            else {
                Minecraft mc = Minecraft.getInstance();
                int oof = msg.nbt.getInt("entityid4");
                CameraEntity camera = (CameraEntity) mc.world.getEntityByID(oof);
                mc.world.addEntity(camera);
                Minecraft.getInstance().displayGuiScreen(CameraScreen.cameraScreenInstance);
                Minecraft.getInstance().setRenderViewEntity(camera);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
