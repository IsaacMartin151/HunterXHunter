package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.packets.HXHEntitySpawn;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ToolType;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;

public class NenUser implements INenUser {
    protected int type = 0;
    protected int nenPower = 0;
    protected int passivePower = 0;
    protected float currentNen = 100;
    protected int failCounter = 0;
    public boolean nenActivated = false;
    public boolean conjurerActivated = false;
    public boolean gyo = false;
    public boolean en = false;
    public boolean ren = false;
    public boolean zetsu = false;
    protected int nencolor = 0;
    protected boolean blockDamage = false;
    protected boolean openedBook = false;
    protected int burnout = 0;
    public long lastOpenedBook = 2000;
    protected boolean manipulatorOverlay = false;
    public int manipulatorSelection = 0;
    public int formationSelection = 0;
    public int p0 = -1;
    private boolean clipping = false;
    public String entityID = null;

    public NenUser() { }

    public void setType(int nenType) {
        this.type = nenType;
    }

    public int getNenType() {
        return type;
    }

    public void setClipping(boolean clip) {
        this.clipping = clip;
    }

    public boolean getClipping() {
        return clipping;
    }

    public boolean openedBook() {
        return openedBook;
    }

    public void setOpenedBook(boolean opened) {
        this.openedBook = opened;
    }

    public long getLastOpenedBook() {
        return lastOpenedBook;
    }

    public void setLastOpenedBook(long last) {
        this.lastOpenedBook = last;
    }

    public int getBurnout() {
        return burnout;
    }

    public boolean getOverlay() {
        return manipulatorOverlay;
    }

    public void setBurnout(int burn) {
        this.burnout = burn;
    }

    public static void updateClient(ServerPlayerEntity player, INenUser cap) {
        PacketManager.sendTo(player, new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }

    public static void updateServer(PlayerEntity player, INenUser cap) {
        PacketManager.sendToServer(new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }

    public int getPassivePower() {
        return passivePower;
    }

    public void setPassivePower(int i) {
        this.passivePower = i;
    }

    public int getManipulatorSelection() {
        return manipulatorSelection;
    }

    public void setManipulatorSelection(int i) {
        this.manipulatorSelection = i;
    }

    public void setNenPower(int power) {
        this.nenPower = power;
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
        blockDamage = false;
    }

    public void toggleNen() {
        this.nenActivated = !this.nenActivated;
        HunterXHunter.LOGGER.info("Nen toggled, nen is now " + nenActivated);
        if (!nenActivated) {
            resetNen();
        }
    }

    public int getFailCounter() {
        return failCounter;
    }

    public void setFailCounter(int counter) {
        failCounter = counter;
    }

    public void copy(INenUser other) {
        this.setCurrentNen(other.getCurrentNen());
        this.setNenPower(other.getNenPower());
        this.setPassivePower(other.getPassivePower());
        this.setType(other.getNenType());
        this.resetNen();
        //TODO: carry over boolean states?
    }

    public static INenUser getFromPlayer(PlayerEntity player) {
        return player.getCapability(NENUSER, null).orElseThrow(() -> new IllegalArgumentException("NenUser must not be empty!"));
    }

    public int getMaxCurrentNen() {
        return 100 + nenPower * 16;
    }
    public boolean blockDamage() {return blockDamage;}
    public void setBlockDamage(boolean blockNext) { blockDamage = blockNext; }
    public boolean getNenActivated() {
        return nenActivated;
    }
    public void setNenActivated(boolean nenActivated) {
        this.nenActivated = nenActivated;
    }
    public boolean getConjurerActivated() {
        return conjurerActivated;
    }
    public void setConjurerActivated(boolean oof) { this.conjurerActivated = oof; }
    public int getNenPower() {
        return nenPower;
    }
    public float getCurrentNen() {
        return currentNen;
    }
    public void setCurrentNen(float newNen) {
        this.currentNen = newNen;
    }
    public void setNencolor(int color) {
        this.nencolor = color;
    }
    public int getNencolor() { return nencolor; }
    public void toggleGyo() {
        gyo = !gyo;
    }
    public void setGyo(boolean on) {
        this.gyo = on;
    }
    public boolean getGyo() {
        return gyo;
    }
    public void toggleEn() {
        en = !en;
    }
    public void setEn(boolean on) {
        this.en = on;
    }
    public boolean getEn() {
        return en;
    }
    public void toggleZetsu() {
        zetsu = !zetsu;
    }
    public void setZetsu(boolean on) {
        this.zetsu = on;
    }
    public boolean getZetsu() {
        return zetsu;
    }
    public void toggleRen() { ren = !ren; }
    public void setRen(boolean on) {
        this.ren = on;
    }
    public boolean getRen() {
        return ren;
    }
    public void nenpower1(PlayerEntity player) { }
    public void setEntityID(int e) {
        this.p0 = e;
    }
    public int getEntityID() {
        return p0;
    }

    public void enhancer1(PlayerEntity player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Enhancer jump");
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
                HunterXHunter.LOGGER.info("Placeholder logging");
                break;
            case 2:
                HunterXHunter.LOGGER.info("Placeholder logging");
                break;
            case 3:
                HunterXHunter.LOGGER.info("Placeholder logging");
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
                HunterXHunter.LOGGER.info("Placeholder logging");
                //Dash move
                break;
            case 2:
                HunterXHunter.LOGGER.info("Placeholder logging");
                //Electrical crackle
                break;
            case 3:
                HunterXHunter.LOGGER.info("Placeholder logging");
                // ???
        }
    }

    public void conjurer1(PlayerEntity player) {
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Toggling ConjurerActivated");
                setConjurerActivated(!conjurerActivated);
                break;
            case 1:
                HunterXHunter.LOGGER.info("Placeholder logging");
                //iterate through blocks?
                break;
            case 2:
                HunterXHunter.LOGGER.info("Placeholder logging");
                //iterate through formations?
                break;
            case 3:
                HunterXHunter.LOGGER.info("Placeholder logging");
                PacketManager.sendToServer(new HXHEntitySpawn(2, -2, new CompoundNBT()));
                break;
        }
    }

    public void emitter1(PlayerEntity player) {
        Vector3d look = player.getLookVec();
        switch (passivePower) {
            case 0:
                HunterXHunter.LOGGER.info("Adding emitter projectile");
                PacketManager.sendToServer(new HXHEntitySpawn(3, -1, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 1:
                HunterXHunter.LOGGER.info("Adding emitter projectile");
                PacketManager.sendToServer(new HXHEntitySpawn(3, -2, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 2:
                HunterXHunter.LOGGER.info("Adding emitter projectile");
                PacketManager.sendToServer(new HXHEntitySpawn(3, -3, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
            case 3:
                HunterXHunter.LOGGER.info("Adding emitter projectile");
                PacketManager.sendToServer(new HXHEntitySpawn(3, -4, new CompoundNBT()));
                player.addVelocity(-look.x, -look.y, -look.z);
                break;
        }
    }
}