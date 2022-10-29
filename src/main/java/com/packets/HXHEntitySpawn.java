package com.packets;


import com.abilities.nenstuff.INenUser;
import com.abilities.nenstuff.NenUser;
import com.example.hunterxhunter.HunterXHunter;
import com.mojang.math.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
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
                Entity bruh = null;
                INenUser yo = NenUser.getFromPlayer(serverPlayer);
                Vec3 oof = serverPlayer.position();
                switch(eNumber) {
                    case 1:
                        if (eID == -3) {
                            Entity ee = serverPlayer.level.getEntity(yo.getEntityID());
                            if (ee != null) {
                                serverPlayer.setPos(ee.getX(), ee.getY(), ee.getZ());
                                serverPlayer.fallDistance = 0;
                                ee.remove();
                                yo.setEntityID(-1);
                            }
                            break;
                        }
                        else {
                            HunterXHunter.LOGGER.info("Added entity is ManipulatorTpProjectile");
                            bruh = new ManipulatorTpProjectile(serverPlayer.level, serverPlayer);
                            Vec3 yeet = serverPlayer.getLookVec().normalize().scale(2);
                            bruh.setVelocity(yeet.x, yeet.y, yeet.z);
                            yo.setEntityID(bruh.getId());
                            NenUser.updateClient(serverPlayer, yo);
                            break;
                        }
                    case 2:
                        HunterXHunter.LOGGER.info("Added entity is ConjurerMount");
                        bruh = new ConjurerMount(CONJURER_MOUNT.get(), serverPlayer.level);
                        bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                    case 3:
                        HunterXHunter.LOGGER.info("Added entity is EmitterBaseProjectile, variation "+eID);
                        switch (eID) {
                            case -1:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 1, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -2:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 2, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -3:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 3, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -4:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 4, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -5:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 5, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -6:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 6, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -7:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 7, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                            case -8:
                                bruh = new EmitterBaseProjectile(NO_GRAVITY_PROJECTILE.get(), 8, yo.getNenPower(), serverPlayer.level);
                                bruh.setPos(oof.x, oof.y, oof.z);
                                break;
                        }
                        break;
                    case 4:
                        HunterXHunter.LOGGER.info("Added entity is Transmuter Experience Item");
                        bruh = new ItemEntity(serverPlayer.level, oof.x, oof.y, oof.z, EXPERIENCE_ITEM.get().getDefaultInstance());
                        ((ItemEntity) bruh).setNoPickupDelay();
                        //bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                    case 5:
                        HunterXHunter.LOGGER.info("Added entity is Transmuter Experience Item");
                        bruh = new ItemEntity(serverPlayer.level, oof.x, oof.y, oof.z, EXPERIENCE_ITEM.get().getDefaultInstance());
                        ((ItemEntity) bruh).setNoPickupDelay();
                        //bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                    case 96:
                        HunterXHunter.LOGGER.info("Added entity is Shiapouf Clone");
                        bruh = new ShiapoufClone(SHIAPOUF_CLONE_ENTITY.get(), serverPlayer.level);
                        bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                    case 97:
                        HunterXHunter.LOGGER.info("Added entity is Shiapouf");
                        bruh = new Shiapouf(SHIAPOUF_ENTITY.get(), serverPlayer.level);
                        bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                    case 98:
                        HunterXHunter.LOGGER.info("Added entity is Neferpitou");
                        bruh = new Neferpitou(NEFERPITOU_ENTITY.get(), serverPlayer.level);
                        bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                    case 99:
                        HunterXHunter.LOGGER.info("Added entity is Youpi");
                        bruh = new Youpi(YOUPI_ENTITY.get(), serverPlayer.level);
                        bruh.setPos(oof.x, oof.y, oof.z);
                        break;
                }
                serverPlayer.getLevel().addFreshEntity(bruh);
                PacketManager.sendTo(serverPlayer, new HXHEntitySpawn(eNumber, bruh.getId(), new CompoundTag()));
            }
            else {
                int yee = msg.nbt.getInt("eID");
                Minecraft mc = Minecraft.getInstance();
                INenUser yo = NenUser.getFromPlayer(mc.player);
                Entity bruh = mc.level.getEntity(yee);
                if (bruh != null) {
                    HunterXHunter.LOGGER.info("Adding the entity to world HXHEntitySpawn");
                    Minecraft.getInstance().level.addFreshEntity(bruh);
                    yo.setEntityID(yee);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
