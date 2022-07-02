package com.chubbychump.hunterxhunter.server.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IVatRecipe extends IRecipe<IInventory> {

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(IInventory inv, World worldIn);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(IInventory inv);

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    boolean canFit(int width, int height);

    NonNullList<Ingredient> getIngredients();

    /**
     * Gets the experience of this recipe
     */
    float getExperience();

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    ItemStack getRecipeOutput();

    /**
     * Recipes with equal group are combined into one button in the recipe book
     */
    String getGroup();

    /**
     * Gets the cook time in ticks
     */
    int getCookTime();

    ResourceLocation getId();

    IRecipeType<?> getType();
}

