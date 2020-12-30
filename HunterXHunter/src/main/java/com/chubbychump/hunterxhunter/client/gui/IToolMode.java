package com.chubbychump.hunterxhunter.client.gui;

import net.minecraft.item.ItemStack;

public interface IToolMode {

    void setMode(int i);

    String getName();

    String name();

    boolean isDisabled();

    int ordinal();

}
