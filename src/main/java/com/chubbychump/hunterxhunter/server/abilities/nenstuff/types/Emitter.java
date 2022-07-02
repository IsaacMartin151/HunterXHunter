package com.chubbychump.hunterxhunter.server.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;

public class Emitter extends NenUser {
    public Emitter() {
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
        return 5;
    }
}
