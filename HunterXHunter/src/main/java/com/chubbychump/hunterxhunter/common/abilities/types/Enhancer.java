package com.chubbychump.hunterxhunter.common.abilities.types;

import com.chubbychump.hunterxhunter.common.abilities.NenUser;

public class Enhancer implements NenUser {

    private int nenPower;
    private int currentNen;
    private char control;
    private boolean nenActivated;
    private boolean gyo;
    private boolean en;
    private boolean ren;
    private boolean zetsu;

    public Enhancer() {
        this.nenPower = 0;
        this.currentNen = 100;
        this.control = 'c';
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
    }

    public char getKey() {
        return control;
    }

    public void setKey(char bruh) {
        this.control = bruh;
    }
    public boolean isNenActivated() {
        return this.nenActivated;
    }
    public int getCurrentNen() {
        return currentNen;
    }

    public void increaseNenPower() {
        nenPower = nenPower + 1;
        System.out.println("User nen power has increased. Total is now "+nenPower);
    }

    public void setCurrentNen(int currentNen) {
        this.currentNen = currentNen;
    }

    public void resetNen() {
        nenActivated = false;
        gyo = false;
        en = false;
        ren = false;
        zetsu = false;
    }
    public void toggleNen() {
        nenActivated = !nenActivated;
        if (nenActivated = false) {
            resetNen();
        }
    }
    public void activateGyo() {
        if (nenActivated = true) {
            gyo = !gyo;
        }
    }
    public void activateEn() {
        if (nenActivated = true) {
            en = !en;
        }
    }
    public void activateRen() {
        if (nenActivated = true) {
            ren = !ren;
        }
    }
    public void activateZetsu() {
        if (nenActivated = true) {
            zetsu = !zetsu;
        }
    }
}
