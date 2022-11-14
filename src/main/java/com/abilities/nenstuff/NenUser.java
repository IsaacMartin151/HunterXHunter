package com.abilities.nenstuff;


import com.example.hunterxhunter.HunterXHunter;
import com.packets.PacketManager;
import com.packets.SyncNenPacket;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;


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

    public int formationSelection = 0;
    public int p0 = -1;

    public long lastOpenedBook = 2000;
    public int manipulatorSelection = 0;
    private int[] riftwalkPos = new int[3];

    public NenUser() {}

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
        tag.putInt("type", nenType);
        tag.putBoolean("conjurer", conjurerActivated);
        tag.putBoolean("book", openedBook);
        tag.putFloat("currentnen", currentNen);
        tag.putInt("nenpower", nenPower);
        tag.putInt("burnout", burnout);
        tag.putBoolean("nenactivated", nenActivated);
        tag.putBoolean("gyo", gyo);
        tag.putBoolean("en", en);
        tag.putBoolean("ren", ren);
        tag.putBoolean("zetsu", zetsu);
        tag.putInt("passivepower", passivePower);
        tag.putBoolean("blocknext", blockDamage);
        tag.putBoolean("riftwalk", riftwalk);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        nenType = tag.getInt("type");
        conjurerActivated = tag.getBoolean("conjurer");
        openedBook = tag.getBoolean("book");
        currentNen = tag.getFloat("currentnen");
        nenPower = tag.getInt("nenpower");
        burnout = tag.getInt("burnout");
        nenActivated = tag.getBoolean("nenactivated");
        gyo = tag.getBoolean("gyo");
        en = tag.getBoolean("en");
        ren = tag.getBoolean("ren");
        zetsu = tag.getBoolean("zetsu");
        passivePower = tag.getInt("passivepower");
        blockDamage = tag.getBoolean("blocknext");
        riftwalk = tag.getBoolean("riftwalk");
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
        this.currentNen = other.getCurrentNen();
        this.nenPower = other.getNenPower();
        this.passivePower = other.getPassivePower();
        this.nenType = other.getNenType();
        this.resetNen();
        //TODO: carry over boolean states?
    }

    public int getMaxCurrentNen() {
        return 100 + nenPower * 16;
    }
    public void nenpower1(Player player) { }
}