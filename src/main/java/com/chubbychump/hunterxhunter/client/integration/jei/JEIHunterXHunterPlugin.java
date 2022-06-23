package com.chubbychump.hunterxhunter.client.integration.jei;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.recipes.VatRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.GON_FISHING_POLE;

@JeiPlugin
public class JEIHunterXHunterPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(HunterXHunter.MOD_ID, "main");

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry) {
        //registry.registerSubtypeInterpreter(ModItems.brewVial, ItemBrewBase::getSubtype);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new VatRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        //registration.getCraftingCategory().addCategoryExtension(AncientWillRecipe.class, AncientWillRecipeWrapper::new);
        //registration.getCraftingCategory().addCategoryExtension(TerraPickTippingRecipe.class, TerraPickTippingRecipeWrapper::new);
        //registration.getCraftingCategory().addCategoryExtension(CompositeLensRecipe.class, CompositeLensRecipeWrapper::new);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        registry.addRecipes(Minecraft.getInstance().world.getRecipeManager().getRecipesForType(VatRecipes.VAT_RECIPE_TYPE), VatRecipeCategory.UID);
        //registry.getVanillaRecipeFactory().createBrewingRecipe(Collections.singletonList(CARAPACE.get().getDefaultInstance()), Items.POTION.getDefaultInstance(), PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), BLOODLUST_POTION.get()));
        //registry.addRecipes(ModRecipeTypes.getRecipes(world, ModRecipeTypes.BREW_TYPE).values(), BreweryRecipeCategory.UID);
        //registry.addRecipes(ModRecipeTypes.getRecipes(world, ModRecipeTypes.PURE_DAISY_TYPE).values(), PureDaisyRecipeCategory.UID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
        //registry.addRecipeTransferHandler(ContainerCraftingHalo.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(GON_FISHING_POLE.get().getDefaultInstance(), VatRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        //jeiRuntime.getRecipeManager().;
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
