package com.chubbychump.hunterxhunter.init;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.NEN_LIGHT_TILE_ENTITY;

@ObjectHolder(HunterXHunter.MOD_ID)
public class ModTileEntityTypes {
    public static final TileEntityType<?> NENLIGHT = NEN_LIGHT_TILE_ENTITY.get(); //I think this part is right, just need to do the eventhandler portion correctly.
}
