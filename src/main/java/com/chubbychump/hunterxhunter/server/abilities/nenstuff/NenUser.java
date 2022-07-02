package com.chubbychump.hunterxhunter.server.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.packets.HXHEntitySpawn;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ToolType;

import java.util.List;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;
import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider.NENUSER;

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

    public static void updateClient(ServerPlayerEntity player, INenUser cap) {
        PacketManager.sendTo(player, new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }

    public static void updateServer(PlayerEntity player, INenUser cap) {
        PacketManager.sendToServer(new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
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

    public static INenUser getFromPlayer(PlayerEntity player) {
        return player.getCapability(NENUSER, null).orElseThrow(() -> new IllegalArgumentException("NenUser must not be empty!"));
    }

    public int getMaxCurrentNen() {
        return 100 + nenPower * 16;
    }
    public void nenpower1(PlayerEntity player) { }

    public void enhancer1(PlayerEntity player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Enhancer look jump");
                float setTo = getCurrentNen() - 50;
                if (setTo > 0) {
                    setCurrentNen(setTo);
                    setBurnout(60);
                    setBlockDamage(true);
                    if (player.isOnGround()) {
                        player.addVelocity(0, 2, 0);
                        player.velocityChanged = true;
                    } else {
                        player.setMotion(0, 0, 0);
                        Vector3d stare = player.getLookVec();
                        stare.normalize();
                        Vector3d move = new Vector3d(stare.x * 3, .2, stare.z * 3);
                        player.addVelocity(move.x, move.y, move.z);
                        player.velocityChanged = true;
                    }
                }
                break;
            case 1:
                HunterXHunter.LOGGER.info("Reversing motion");
                player.setMotion(player.getMotion().add(0, .1, 0).inverse());
                player.velocityChanged = true;
                break;
            case 2:
                HunterXHunter.LOGGER.info("Give Regeneration");
                player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 600));
                break;
            case 3:
                HunterXHunter.LOGGER.info("Maybe nothing?");
                break;
        }
    }

    public void manipulator1(PlayerEntity player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Iterating through spectated player");
                manipulatorSelection++;
                break;
            case 1:
                HunterXHunter.LOGGER.info("Adding tp entity/swapping with it");
                Entity bruh = player.world.getEntityByID(p0);
                if (bruh == null) {
                    PacketManager.sendToServer(new HXHEntitySpawn(1, -2, new CompoundNBT()));
                }
                else {
                    PacketManager.sendToServer(new HXHEntitySpawn(1, -3, new CompoundNBT()));
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

    public void transmuter1(PlayerEntity player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Attempting to enchant held item");
                float setTo = getCurrentNen() - 50 - getNenPower() * 10;
                if (setTo > 0) {
                    //Repair item durability
                    ItemStack held = player.getHeldItemMainhand();
                    if (held.isEnchantable()) {
                        setCurrentNen(setTo);
                        setBurnout(60);
                        if (held.getToolTypes().contains(ToolType.PICKAXE) || held.getToolTypes().contains(ToolType.AXE) || held.getToolTypes().contains(ToolType.SHOVEL)) {
                            held.addEnchantment(Enchantments.EFFICIENCY, 1 + getNenPower() * 2 / 4);
                        } else if (held.getItem() instanceof ArmorItem) {
                            held.addEnchantment(Enchantments.PROTECTION, 1 + getNenPower() * 2 / 4);
                        } else if (held.getItem() instanceof SwordItem) {
                            held.addEnchantment(Enchantments.SHARPNESS, 1 + getNenPower() * 2 / 4);
                        } else if (held.getItem() instanceof BowItem) {
                            held.addEnchantment(Enchantments.POWER, 1 + getNenPower() * 2 / 6);
                        } else {
                            held.addEnchantment(Enchantments.UNBREAKING, 1 + (getNenPower()) / 4);
                        }
                    }
                }
                break;
            case 1:
                HunterXHunter.LOGGER.info("Converting XP into item");
                if (player.experienceTotal >= 160 && getCurrentNen() >= 100) {
                    player.experienceTotal -= 160;
                    setCurrentNen(getCurrentNen() - 100);
                    PacketManager.sendToServer(new HXHEntitySpawn(4, -1, new CompoundNBT()));
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
                Vector3d radiusLow = player.getPositionVec().subtract(i, i, i);
                Vector3d radiusHigh = player.getPositionVec().add(i, i, i);

                List<Entity> yah = player.getEntityWorld().getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(radiusLow, radiusHigh));
                for (Entity ee : yah) {
                    ee.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 1f);
                }
                // Electrocute
        }
    }

    public void conjurer1(PlayerEntity player) {
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
                    ee[0] = (int) player.getPosX();
                    ee[1] = (int) player.getPosY();
                    ee[2] = (int) player.getPosZ();
                    setRiftwalkPos(ee);
                }
                else {
                    HunterXHunter.LOGGER.info("Exiting riftwalk, setting position");
                    riftwalk = false;
                    int[] bruh = getRiftwalkPos();
                    BlockPos yee = player.getPosition();
                    int[] oo = new int[3];
                    oo[0] = 8 * (yee.getX() - bruh[0]) + bruh[0];
                    oo[1] = 8 * (yee.getY() - bruh[1]) + bruh[1];
                    oo[2] = 8 * (yee.getZ() - bruh[2]) + bruh[2];
                    LOGGER.info("Setting player position to "+oo[0]+", "+oo[1]+", "+oo[2]);
                    player.setPosition(oo[0], oo[1], oo[2]);
                    // TODO: update player location - this doesn't work, it's only on client
                }
                break;
            case 2:
                HunterXHunter.LOGGER.info("Hrmmmm");
                //iterate through formations?
                break;
            case 3:
                HunterXHunter.LOGGER.info("Spawning ConjurerMount");
                PacketManager.sendToServer(new HXHEntitySpawn(2, -2, new CompoundNBT()));
                break;
        }
    }

    public void emitter1(PlayerEntity player) {
        Vector3d look = player.getLookVec();
        HunterXHunter.LOGGER.info("Adding emitter projectile");
        switch (passivePower) {
            case 0:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -1, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 1:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -2, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 2:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -3, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 3:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -4, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 4:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -5, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 5:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -6, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 6:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -7, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 7:
                PacketManager.sendToServer(new HXHEntitySpawn(3, -8, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
        }
    }
}