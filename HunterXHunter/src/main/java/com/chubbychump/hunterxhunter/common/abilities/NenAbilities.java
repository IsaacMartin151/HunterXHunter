package com.chubbychump.hunterxhunter.common.abilities;

public class NenAbilities {
    int nenPower = 1;
    char control = 'c';
    boolean nenActivated = false;
    boolean gyo = false;
    boolean en = false;
    boolean ren = false;
    boolean zetsu = false;
    public void setKey(char bruh) {
        control = bruh;
    }
    public char getKey() {
        return control;
    }
    public void increaseNenPower() {
        nenPower = nenPower + 1;
        System.out.println("User nen power has increased. Total is now "+nenPower);
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

/* public class Enhancer extends NenAbilities {

}

public class Transmuter extends NenAbilities {

}

public class Manipulator extends NenAbilities {

}

public class Conjurer extends NenAbilities {

}

public class Emitter extends NenAbilities {

} */