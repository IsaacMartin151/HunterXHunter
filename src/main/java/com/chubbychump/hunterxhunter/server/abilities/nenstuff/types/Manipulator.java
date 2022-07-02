package com.chubbychump.hunterxhunter.server.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;

public class Manipulator extends NenUser {
    public Manipulator() {
        this.nenPower = 1;
        this.currentNen = 100;
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
        this.passivePower = 0;
    }

    @Override
    public int getNenType() {
        return 3;
    }
}
