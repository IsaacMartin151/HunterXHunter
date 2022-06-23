package com.chubbychump.hunterxhunter;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {

    // Config Spec
    public static final ForgeConfigSpec SPEC = Config.build();

    // General
    public static ConfigValue<Boolean> enableDebug;
    public static ConfigValue<Boolean> enableHardcore;
    public static ConfigValue<Boolean> enableItems;
    public static ConfigValue<Boolean> enableLoot;
    public static ConfigValue<Boolean> enableOhko;

    // Health
    public static ConfigValue<Integer> defHealth;
    public static ConfigValue<Integer> maxHealth;
    public static ConfigValue<ArrayList<Integer>> levelRamp;
    public static ConfigValue<Integer> punishAmount;

    // Gui
    public static ConfigValue<Boolean> customHud;
    public static ConfigValue<Boolean> minimalHud;
    public static ConfigValue<Boolean> hideHud;

    // Experience
    public static ConfigValue<Double> xpMultiplier;
    public static ConfigValue<Boolean> loseXpOnDeath;
    public static ConfigValue<Boolean> loseInvOnDeath;

    private static final ForgeConfigSpec build() {
        // Create the builder
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // @formatter:off
        /////////////
        // General //
        /////////////
        builder.push("General");

        enableDebug = builder
                .comment("When enabled, an extra level of debugging output will be provided to the logs.")
                .define("Debug", false);

        enableHardcore = builder
                .comment("When enabled, the player's health will get reset back to default on death. Overrides punish amount.")
                .define("Hardcore", false);

        enableItems = builder
                .comment("When disabled, LevelHearts' items' functionality will be disabled.")
                .define("Items", true);

        enableLoot = builder
                .comment("When disabled, LevelHearts will not spawn heart pieces and heart containers in chests around the world.")
                .define("Loot", true);

        enableOhko = builder
                .comment("When enabled, upon any damage to the player, the player will immediately die. \"One-Hit Knockout\"")
                .define("Ohko", false);

        builder.pop();

        ////////////
        // Health //
        ////////////
        builder.push("Health");

        defHealth = builder
                .comment("The amount of health a user will have in a new game or after death.")
                .defineInRange("Default Health", 4, 1, 200);

        maxHealth = builder
                .comment("The maximum amount of health LevelHearts will allow a user to have. -1 to disable.")
                .defineInRange("Maximum Health", 14, -1, 14);

        levelRamp = builder
                .comment("The levels at which a user will gain a heart.")
                .define("Level Ramp", new ArrayList<Integer>(Arrays.asList(
                        100, 150, 1000, 1500, 2000, 2500, 3000, 3400, 3800, 4200,
                        4600, 5000, 5300, 5600, 5900, 6200, 6400, 6600, 6800, 7000,
                        7500, 8000, 8500, 9000, 9500, 10000, 11000, 12000, 13000,
                        14000, 15000, 16000, 17000, 18000, 19000, 20000
                )));

        punishAmount = builder
                .comment("How many hearts (not health) to take away from the user each time they die. -1 to disable.")
                .defineInRange("Punish Health", -1, -1, 10);


        builder.pop();

        //////////
        // Gui  //
        //////////
        builder.push("Gui");

        customHud = builder
                .comment("When disabled, LevelHearts will not modify the hud at all. Only use if you're having conflicts with other mods.")
                .define("LevelHearts HUD", true);

        minimalHud = builder
                .comment("When enabled, the health will display in only one row with a number to the left indicating the row you're on.")
                .define("LevelHearts Minimal HUD", false);

        hideHud = builder
                .comment("When enabled, health will not be displayed at all. Works well in combination with one-hit knockout.")
                .define("LevelHearts Hidden HUD", false);

        builder.pop();

        ////////////////
        // Experience //
        ////////////////
        builder.push("Experience");

        xpMultiplier = builder
                .comment("How much to multiply the value of experience by.")
                .defineInRange("XP Multiplier", 1.0, 0.0, 1024.0);

        loseXpOnDeath = builder
                .comment("When enabled, the mod will force players to lose their experience even if the KeepInventory gamerule is enabled.")
                .define("Always Lose XP on Death", false);

        loseInvOnDeath = builder
                .comment("When enabled, the mod will force players to lose their items even if the KeepInventory gamerule is enabled.",
                        "If using a setting to lose health upon death, often health will be regained through experience after respawning, which can be confusing.")
                .define("Always Lose Inventory on Death", false);

        builder.pop();
        // @formatter:on

        // Build the spec
        return builder.build();
    }
}
