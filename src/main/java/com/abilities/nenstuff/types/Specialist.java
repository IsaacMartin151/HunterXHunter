package com.abilities.nenstuff.types;

import com.abilities.nenstuff.NenUser;

public class Specialist extends NenUser {
    //private boolean holdingBreath = false;
    public Specialist() {
        this.nenPower = 0;
        this.currentNen = 100;
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
    }

    @Override
    public int getNenType() {
        return 6;
    }
}
