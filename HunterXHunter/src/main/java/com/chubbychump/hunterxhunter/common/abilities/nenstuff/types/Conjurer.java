package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;

public class Conjurer extends NenUser {
    public Conjurer() {
        this.nenPower = 0;
        this.currentNen = 100;
        this.control = 'c';
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
    }

    @Override
    public int getNenType() {
        return 2;
    }
}
