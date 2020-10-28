package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;

public class Transmuter extends NenUser {
    public Transmuter() {
        this.nenPower = 0;
        this.currentNen = 100;
        this.control = 'c';
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
    }

    public boolean isNenActivated() {
        return nenActivated;
    }

    public int getCurrentNen() {
        return currentNen;
    }

    public void increaseNenPower() {
        nenPower = nenPower + 15;
        HunterXHunter.LOGGER.info("User nen power has increased. Total is now " + nenPower);
    }

    public void setCurrentNen(int currentNen) {
        this.currentNen = currentNen;
    }

    public int getNenPower() {
        return nenPower;
    }

    public void resetNen() {
        gyo = false;
        en = false;
        ren = false;
        zetsu = false;
    }

    public void toggleNen() {
        this.nenActivated = !this.nenActivated;
        if (nenActivated == false) {
            resetNen();
            HunterXHunter.LOGGER.info("Nen toggled, nen is now " + nenActivated);
        }
    }

    public void toggleGyo() {
        gyo = !gyo;
    }
    public boolean getGyo() {
        return gyo;
    }
    public void toggleEn() {
        en = !en;
    }
    public boolean getEn() {
        return en;
    }
    public void toggleZetsu() {
        zetsu = !zetsu;
    }
    public boolean getZetsu() {
        return zetsu;
    }
    public void toggleRen() { ren = !ren; }
    public boolean getRen() {
        return ren;
    }
}
