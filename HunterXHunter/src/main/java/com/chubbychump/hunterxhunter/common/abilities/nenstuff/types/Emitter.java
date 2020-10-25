package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;

public class Emitter extends NenUser {
    public Emitter() {
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
    public void activateGyo() {
        if (nenActivated == true) {
            gyo = !gyo;
        }
    }
    public void activateEn() {
        if (nenActivated == true) {
            en = !en;
        }
    }
    public void activateRen() {
        if (nenActivated == true) {
            ren = !ren;
        }
    }
    public void activateZetsu() {
        if (nenActivated == true) {
            zetsu = !zetsu;
        }
    }
}
