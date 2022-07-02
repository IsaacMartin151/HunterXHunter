package com.chubbychump.hunterxhunter.client.integration.jei;

import com.chubbychump.hunterxhunter.server.recipes.IVatRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper.prefix;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.GON_FISHING_POLE;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.VAT_ITEM;

public class VatRecipeCategory implements IRecipeCategory<IVatRecipe> {

    public static final ResourceLocation UID = prefix("vat");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public VatRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(262, 262);
        localizedName = "Vat recipes";
        overlay = guiHelper.createDrawable(prefix("textures/gui/page.png"),
                0, 0, 256, 256);
        icon = guiHelper.createDrawableIngredient(VAT_ITEM.get().getDefaultInstance());
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends IVatRecipe> getRecipeClass() {
        return IVatRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(IVatRecipe recipe, IIngredients iIngredients) {
        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getMatchingStacks()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, list);
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void draw(IVatRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        //HUDHandler.renderManaBar(ms, 6, 98, 0x0000FF, 0.75F, recipe.getManaUsage(), TilePool.MAX_MANA / 10);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IVatRecipe recipe, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 118, 116);
        recipeLayout.getItemStacks().set(0, GON_FISHING_POLE.get().getDefaultInstance());

        int index = 1;
        double angleBetweenEach = 360.0 / ingredients.getInputs(VanillaTypes.ITEM).size();
        Vector2f point = new Vector2f(118, 50), center = new Vector2f(118, 116);

        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index, true, (int) point.x, (int) point.y);
            recipeLayout.getItemStacks().set(index, o);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
        }

        recipeLayout.getItemStacks().init(index, false, 200, 50);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    public Vector2f rotatePointAbout(Vector2f in, Vector2f about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vector2f((float) newX, (float) newY);
    }

}
