package com.registry;

import com.container.BookMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.hunterxhunter.HunterXHunter.MODID;

public class MenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<BookMenu>> BOOK_MENU = MENU_TYPES.register("book_menu", () -> IForgeMenuType.create(BookMenu::new));
}
