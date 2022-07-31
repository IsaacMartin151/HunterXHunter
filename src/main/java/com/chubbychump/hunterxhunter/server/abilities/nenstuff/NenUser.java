package com.chubbychump.hunterxhunter.server.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.packets.HXHEntitySpawn;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;
import com.mojang.math.Vector3d;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;

@Getter
@Setter
public class NenUser implements INenUser {
    protected int nenType = 0;
    protected int nenPower = 0;
    protected int nenColor = 0;
    protected int passivePower = 0;
    protected float currentNen = 100;
    protected int failCounter = 0;
    protected int burnout = 0;

    public boolean gyo = false;
    public boolean en = false;
    public boolean ren = false;
    public boolean zetsu = false;

    public boolean nenActivated = false;
    public boolean conjurerActivated = false;
    protected boolean blockDamage = false;
    protected boolean openedBook = false;
    protected boolean clipping = false;
    protected boolean overlay = false;
    protected boolean riftwalk = false;

    public long lastOpenedBook = 2000;
    public int manipulatorSelection = 0;
    private int[] riftwalkPos = new int[3];

    public int formationSelection = 0;
    public int p0 = -1;
    public int entityID = 0;

    public NenUser() {

    }

    public static INenUser getFromPlayer(Player player) {
        return player.getCapability(NenProvider.NENUSER_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }

    public static void updateClient(ServerPlayer player, INenUser cap) {
        PacketManager.sendTo(player, new SyncNenPacket(player.getId(), cap.serializeNBT()));
    }

    public static void updateServer(Player player, INenUser cap) {
        PacketManager.sendToServer(new SyncNenPacket(player.getId(), cap.serializeNBT()));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("type", getNenType());
        tag.putBoolean("conjurer", isConjurerActivated());
        tag.putBoolean("book", isOpenedBook());
        tag.putFloat("currentnen", getCurrentNen());
        tag.putInt("nenpower", getNenPower());
        tag.putInt("burnout", getBurnout());
        tag.putBoolean("nenactivated", isNenActivated());
        tag.putBoolean("gyo", isGyo());
        tag.putBoolean("en", isEn());
        tag.putBoolean("ren", isRen());
        tag.putBoolean("zetsu", isZetsu());
        tag.putInt("passivepower", getPassivePower());
        tag.putBoolean("blocknext", isBlockDamage());
        tag.putBoolean("riftwalk", isRiftwalk());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setNenType(tag.getInt("type"));
        setConjurerActivated(tag.getBoolean("conjurer"));
        setOpenedBook(tag.getBoolean("book"));
        setCurrentNen(tag.getFloat("currentnen"));
        setNenPower(tag.getInt("nenpower"));
        setBurnout(tag.getInt("burnout"));
        setNenActivated(tag.getBoolean("nenactivated"));
        setGyo(tag.getBoolean("gyo"));
        setEn(tag.getBoolean("en"));
        setRen(tag.getBoolean("ren"));
        setZetsu(tag.getBoolean("zetsu"));
        setPassivePower(tag.getInt("passivepower"));
        setBlockDamage(tag.getBoolean("blocknext"));
        setRiftwalk(tag.getBoolean("riftwalk"));
    }

    public int[] getRiftwalkPos() {
        return riftwalkPos;
    }
    public void setRiftwalkPos(int[] i) {
        this.riftwalkPos = i;
    }

    public void keybind1() {
        //Minecraft.getInstance().displayGuiScreen(NenEffectSelect.instance);
    }

    public void increaseNenPower() {
        if (nenPower < 16) {
            nenPower += 1;
            HunterXHunter.LOGGER.info("User nen power has increased. Total is now " + nenPower);
        }
        else {
            HunterXHunter.LOGGER.info("Nen Power is capped at 16. Current power is " + nenPower);
        }
    }

    public void resetNen() {
        gyo = false;
        en = false;
        ren = false;
        zetsu = false;
        nenActivated = false;
        conjurerActivated = false;
        riftwalk = false;
        blockDamage = false;
    }

    public void toggleNen() {
        this.nenActivated = !this.nenActivated;
        HunterXHunter.LOGGER.info("Nen toggled, nen is now " + nenActivated);
        if (!nenActivated) {
            resetNen();
        }
    }

    public void toggleGyo() {
        this.gyo = !this.gyo;
    }

    public void toggleEn() {
        this.en = !this.en;
    }

    public void toggleRen() {
        this.ren = !this.ren;
    }

    public void toggleZetsu() {
        this.zetsu = !this.zetsu;
    }

    public void copy(INenUser other) {
        this.setCurrentNen(other.getCurrentNen());
        this.setNenPower(other.getNenPower());
        this.setPassivePower(other.getPassivePower());
        this.setNenType(other.getNenType());
        this.resetNen();
        //TODO: carry over boolean states?
    }

    public int getMaxCurrentNen() {
        return 100 + nenPower * 16;
    }
    public void nenpower1(Player player) { }

    public void enhancer1(Player player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Enhancer look jump");
                float setTo = getCurrentNen() - 50;
                if (setTo > 0) {
                    setCurrentNen(setTo);
                    setBurnout(60);
                    setBlockDamage(true);
                    if (player.isOnGround()) {
                        player.push(0, 2, 0);
                        player.hurtMarked = true;
                    } else {
                        player.lerpMotion(0, 0, 0);
                        Vec3 stare = player.getLookAngle();
                        stare.normalize();
                        Vector3d move = new Vector3d(stare.x * 3, .2, stare.z * 3);
                        player.push(move.x, move.y, move.z);
                        player.hurtMarked = true;
                    }
                }
                break;
            case 1:
                HunterXHunter.LOGGER.info("Reversing motion");
                Vec3 movement = player.getDeltaMovement().add(0, .1, 0).reverse();
                player.lerpMotion(movement.x, movement.y, movement.z);
                player.hurtMarked = true;
                break;
            case 2:
                HunterXHunter.LOGGER.info("Give Regeneration");
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600));
                break;
            case 3:
                HunterXHunter.LOGGER.info("Maybe nothing?");
                break;
        }
    }

    public void manipulator1(Player player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Iterating through spectated player");
                manipulatorSelection++;
                break;
            case 1:
                HunterXHunter.LOGGER.info("Adding tp entity/swapping with it");
                Entity bruh = player.level.getEntity(p0);
                if (bruh == null) {
                    PacketManager.sendToServer(new HXHEntitySpawn(1, -2, new CompoundTag()));
                }
                else {
                    PacketManager.sendToServer(new HXHEntitySpawn(1, -3, new CompoundTag()));
                }
                break;
            case 2:
                HunterXHunter.LOGGER.info("Placeholder logging");
                //random potion effect on hit?
                break;
            case 3:
                HunterXHunter.LOGGER.info("Placeholder logging");
                break;
        }
    }

    public void transmuter1(Player player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Attempting to enchant held item");
                float setTo = getCurrentNen() - 50 - getNenPower() * 10;
                if (setTo > 0) {
                    //Repair item durability
                    ItemStack held = player.getMainHandItem();
                    if (held.isEnchantable()) {
                        setCurrentNen(setTo);
                        setBurnout(60);
                        if (held.getItem() instanceof DiggerItem) {
                            held.enchant(Enchantments.BLOCK_EFFICIENCY, 1 + getNenPower() * 2 / 4);
                        } else if (held.getItem() instanceof ArmorItem) {
                            held.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1 + getNenPower() * 2 / 4);
                        } else if (held.getItem() instanceof SwordItem) {
                            held.enchant(Enchantments.SHARPNESS, 1 + getNenPower() * 2 / 4);
                        } else if (held.getItem() instanceof BowItem) {
                            held.enchant(Enchantments.POWER_ARROWS, 1 + getNenPower() * 2 / 6);
                        } else {
                            held.enchant(Enchantments.UNBREAKING, 1 + (getNenPower()) / 4);
                        }
                    }
                }
                break;
            case 1:
                HunterXHunter.LOGGER.info("Converting XP into item");
                if (player.totalExperience >= 160 && getCurrentNen() >= 100) {
                    player.totalExperience -= 160;
                    setCurrentNen(getCurrentNen() - 100);
                    PacketManager.sendToServer(new HXHEntitySpawn(4, -1, new CompoundTag()));
                }
                //Dash move
                break;
            case 2:
                HunterXHunter.LOGGER.info("Dash move");
                //Dash
                //player.setVelocity();
                break;
            case 3:
                HunterXHunter.LOGGER.info("AOE electrocute");
                int i = getNenPower();
                Vec3 radiusLow = player.getEyePosition().subtract(i, i, i);
                Vec3 radiusHigh = player.getEyePosition().add(i, i, i);

                List<Entity> yah = player.getLevel().getEntities(player, new AABB(radiusLow, radiusHigh));
                for (Entity ee : yah) {
                    ee.hurt(DamageSource.LIGHTNING_BOLT, 1f);
                }
                // Electrocute
        }
    }

    public void conjurer1(Player player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Toggling ConjurerActivated");
                setConjurerActivated(!conjurerActivated);
                break;
            case 1:
                if (!riftwalk && getCurrentNen() > 80) {
                    HunterXHunter.LOGGER.info("Entering riftwalk");
                    riftwalk = true;
                    int[] ee = new int[3];
                    ee[0] = (int) player.getX();
                    ee[1] = (int) player.getY();
                    ee[2] = (int) player.getZ();
                    setRiftwalkPos(ee);
                }
                else {
                    HunterXHunter.LOGGER.info("Exiting riftwalk, setting position");
                    riftwalk = false;
                    int[] bruh = getRiftwalkPos();
                    BlockPos yee = player.getOnPos();
                    int[] oo = new int[3];
                    oo[0] = 8 * (yee.getX() - bruh[0]) + bruh[0];
                    oo[1] = 8 * (yee.getY() - bruh[1]) + bruh[1];
                    oo[2] = 8 * (yee.getZ() - bruh[2]) + bruh[2];
                    LOGGER.info("Setting player position to "+oo[0]+", "+oo[1]+", "+oo[2]);
                    player.setPos(oo[0], oo[1], oo[2]);
                    // TODO: update player location - this doesn't work, it's only on client
                }
                break;
            case 2:
                HunterXHunter.LOGGER.info("Hrmmmm");
                //iterate through formations?
                break;
            case 3:
                HunterXHunter.LOGGER.info("Spawning ConjurerMount");
                PacketManager.sendToServer(new HXHEntitySpawn(2, -2, new CompoundTag()));
                break;
        }
    }

    public void emitter1(Player player) {
        Vec3 look = player.getLookAngle();
        HunterXHunter.LOGGER.info("Adding emitter projectile");
        switch (passivePower) {
            case 0:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -1, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 1:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -2, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 2:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -3, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 3:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -4, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 4:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -5, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 5:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -6, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 6:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -7, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
            case 7:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -8, new CompoundTag()));
                player.push(-look.x, -look.y, -look.z);
                break;
        }
    }
}