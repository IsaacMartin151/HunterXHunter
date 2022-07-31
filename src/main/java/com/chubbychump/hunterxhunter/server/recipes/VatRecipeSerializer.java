package com.chubbychump.hunterxhunter.server.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class VatRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<VatRecipes> {
    private final VatRecipeSerializer.IFactory<VatRecipes> factory;

    public VatRecipeSerializer(VatRecipeSerializer.IFactory<VatRecipes> factory) {
        this.factory = factory;
    }

    @Override
    public VatRecipes read(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getString(json, "group", "minecraft");
        JsonElement jsonelement_0 = JSONUtils.getJsonObject(json, "ingredient0");
        JsonElement jsonelement_1 = JSONUtils.getJsonObject(json, "ingredient1");

        Ingredient ingredient_0 = Ingredient.deserialize(jsonelement_0);
        Ingredient ingredient_1 = Ingredient.deserialize(jsonelement_1);
        ItemStack itemstack;

        if (!json.has("result")) {
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        }

        if (json.get("result").isJsonObject()) {
            itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        } else {
            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            itemstack = new ItemStack(Registry.ITEM.getOrDefault(resourcelocation));
        }

        float f = JSONUtils.getFloat(json, "experience", 0.0F);
        int i = JSONUtils.getInt(json, "cookingtime", 60);

        return this.factory.create(recipeId, s, ingredient_0, ingredient_1, itemstack, f, i);
    }

    @Override
    public VatRecipes read(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        String s = buffer.readString(32767);
        Ingredient ingredient_0 = Ingredient.read(buffer);
        Ingredient ingredient_1 = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        float f = buffer.readFloat();
        int i = buffer.readVarInt();

        return this.factory.create(recipeId, s, ingredient_0, ingredient_1, itemstack, f, i);
    }

    @Override
    public void write(FriendlyByteBuf buffer, VatRecipes recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient1.write(buffer);
        recipe.ingredient2.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.cookTime);
    }

    public interface IFactory<T extends IVatRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient1, Ingredient ingredient2, ItemStack result, float experience, int cookTime);
    }
}
