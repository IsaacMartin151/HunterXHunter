package com.chubbychump.hunterxhunter.common.recipes;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.VAT;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.VAT_RECIPE_SERIALIZER;

public class VatRecipes implements IVatRecipe {
    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient1;
    protected final Ingredient ingredient2;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookTime;

    public static final IRecipeType<VatRecipes> VAT_RECIPE_TYPE = new IRecipeType<VatRecipes>() {
        @Override
        public String toString() {
            return new ResourceLocation(HunterXHunter.MOD_ID, "vat").toString();
        }
    };

    public VatRecipes(ResourceLocation resourceLocation, String s, Ingredient ingredient, Ingredient ingredient1, ItemStack itemStack, float v, int i) {
        this.type = VAT_RECIPE_TYPE;
        this.id = resourceLocation;
        this.group = s;
        this.ingredient1 = ingredient;
        this.ingredient2 = ingredient1;
        this.result = itemStack;
        this.experience = v;
        this.cookTime = i;
    }

    public ItemStack getIcon() {
        return new ItemStack(VAT.get());
    }

    public IRecipeSerializer<?> getSerializer() {
        return VAT_RECIPE_SERIALIZER.get();
    }



    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient1.test(inv.getStackInSlot(0)) && this.ingredient2.test(inv.getStackInSlot(1));
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return true;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient1);
        nonnulllist.add(this.ingredient2);
        return nonnulllist;
    }

    /**
     * Gets the experience of this recipe
     */
    public float getExperience() {
        return this.experience;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    /**
     * Recipes with equal group are combined into one button in the recipe book
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Gets the cook time in ticks
     */
    public int getCookTime() {
        return this.cookTime;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public IRecipeType<?> getType() {
        return this.type;
    }
}
