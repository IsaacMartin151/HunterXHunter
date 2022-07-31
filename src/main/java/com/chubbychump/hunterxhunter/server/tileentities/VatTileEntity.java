package com.chubbychump.hunterxhunter.server.tileentities;

import com.chubbychump.hunterxhunter.server.recipes.IVatRecipe;
import com.chubbychump.hunterxhunter.server.recipes.VatRecipes;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.VAT_TILE_ENTITY;

public class VatTileEntity extends TileEntity implements IRecipeHolder, IInventory, IHopper, IRecipeHelperPopulator, ITickableTileEntity {
    private long tickedGameTime;
    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return 6400;
                case 1:
                    return recipesUsed;
                case 2:
                    return cookTime;
                case 3:
                    return cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    break;
                case 1:
                    recipesUsed = value;
                    break;
                case 2:
                    cookTime = value;
                    break;
                case 3:
                    cookTimeTotal = value;
            }

        }

        public int size() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
    protected final IRecipeType<? extends IVatRecipe> recipeType;

    public VatTileEntity() {
        super(VAT_TILE_ENTITY.get());
        recipeType = VatRecipes.VAT_RECIPE_TYPE;
    }

    public void read(BlockState state, CompoundTag nbt) { //TODO: MARK
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        CompoundTag compoundnbt = nbt.getCompound("RecipesUsed");

        for(String s : compoundnbt.keySet()) {
            this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }

    }

    public CompoundTag write(CompoundTag compound) {
        super.write(compound);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compound, this.items);
        CompoundTag compoundnbt = new CompoundTag();
        this.recipes.forEach((recipeId, craftedAmount) -> {
            compoundnbt.putInt(recipeId.toString(), craftedAmount);
        });
        return compound;
    }

    public static boolean pullItems(IHopper hopper) {
        Boolean ret = net.minecraftforge.items.VanillaInventoryCodeHooks.extractHook(hopper);
        if (ret != null) return ret;
        for(ItemEntity itementity : getCaptureItems(hopper)) {
            if (captureItem(hopper, itementity)) {
                hopper.getWorld().playSound(null, itementity.getPosition(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.BLOCKS, 1, 1);
                return true;
            }
        }
        return false;
    }

    public static List<ItemEntity> getCaptureItems(IHopper p_200115_0_) {
        return p_200115_0_.getCollectionArea().toBoundingBoxList().stream().flatMap((p_200110_1_) -> {
            return p_200115_0_.getWorld().getEntitiesWithinAABB(ItemEntity.class, p_200110_1_.offset(p_200115_0_.getXPos() - 0.5D, p_200115_0_.getYPos() - 0.5D, p_200115_0_.getZPos() - 0.5D), EntityPredicates.IS_ALIVE).stream();
        }).collect(Collectors.toList());
    }

    public static boolean captureItem(IInventory p_200114_0_, ItemEntity p_200114_1_) {
        boolean flag = false;
        ItemStack itemstack = p_200114_1_.getItem().copy();
        ItemStack itemstack1 = putStackInInventoryAllSlots((IInventory)null, p_200114_0_, itemstack, (Direction)null);
        if (itemstack1.isEmpty()) {
            flag = true;
            p_200114_1_.remove();
        } else {
            p_200114_1_.setItem(itemstack1);
        }

        return flag;
    }

    public static ItemStack putStackInInventoryAllSlots(@Nullable IInventory source, IInventory destination, ItemStack stack, @Nullable Direction direction) {
        if (destination instanceof ISidedInventory && direction != null) {
            ISidedInventory isidedinventory = (ISidedInventory)destination;
            int[] aint = isidedinventory.getSlotsForFace(direction);

            for(int k = 0; k < aint.length && !stack.isEmpty(); ++k) {
                stack = insertStack(source, destination, stack, aint[k], direction);
            }
        } else {
            int i = destination.getSizeInventory();

            for(int j = 0; j < i && !stack.isEmpty(); ++j) {
                stack = insertStack(source, destination, stack, j, direction);

            }
        }

        return stack;
    }

    private static ItemStack insertStack(@Nullable IInventory source, IInventory destination, ItemStack stack, int index, @Nullable Direction direction) {
        ItemStack itemstack = destination.getStackInSlot(index);
        if (canInsertItemInSlot(destination, stack, index, direction)) {
            boolean flag = false;
            if (itemstack.isEmpty()) {
                destination.setInventorySlotContents(index, stack);
                stack = ItemStack.EMPTY;
                flag = true;
            } else if (canCombine(itemstack, stack)) {
                int i = stack.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag) {
                destination.markDirty();
            }
        }

        return stack;
    }

    private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, @Nullable Direction side) {
        return true;
    }

    private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() != stack2.getItem()) {
            return false;
        } else if (stack1.getDamage() != stack2.getDamage()) {
            return false;
        } else if (stack1.getCount() > stack1.getMaxStackSize()) {
            return false;
        } else {
            return ItemStack.areItemStackTagsEqual(stack1, stack2);
        }
    }

    public void onEntityCollision(Entity p_200113_1_) {
        if (p_200113_1_ instanceof ItemEntity) {
            BlockPos blockpos = this.getPos();
            if (VoxelShapes.compare(VoxelShapes.create(p_200113_1_.getBoundingBox().offset((double)(-blockpos.getX()), (double)(-blockpos.getY()), (double)(-blockpos.getZ()))), this.getCollectionArea(), IBooleanFunction.AND)) {
                captureItem(this, (ItemEntity)p_200113_1_);
            }
        }

    }

    public void tick() {
        if (this.world != null && !this.world.isClientSide) {
            this.tickedGameTime = this.world.getGameTime();
            this.updateHopper(() -> {
                return pullItems(this);
            });

        }
        boolean flag = true;
        boolean flag1 = false;
    //Check hopper tile entity for how to suck in items
        if (!this.world.isClientSide) {
            ItemStack itemstack = this.items.get(1);
            if (!itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<IVatRecipe>)recipeType, this, this.world).orElse(null);

                if (this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.smelt(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            }

            if (flag != true) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, true), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    private boolean updateHopper(Supplier<Boolean> p_200109_1_) {
        return false;
    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        ItemStack item1 = this.items.get(0);
        ItemStack item2 = this.items.get(1);
        if (!item1.isEmpty() && !item2.isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                Item ing1 = recipeIn.getIngredients().get(0).getMatchingStacks()[0].getItem();
                Item ing2 = recipeIn.getIngredients().get(1).getMatchingStacks()[0].getItem();
                if (ing1 == item1.getItem() && ing2 == item2.getItem()) {
                    if (item1.getCount() >= 5 && item2.getCount() >= 5) {
                        return true;
                    }
                }
                if (ing1 == item2.getItem() && ing2 == item1.getItem()) {
                    if (item1.getCount() >= 5 && item2.getCount() >= 5) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private void smelt(@Nullable IRecipe<?> recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.items.get(1);

            if (!this.world.isClientSide) {
                this.setRecipeUsed(recipe);
            }

            itemstack.shrink(5);
            itemstack2.shrink(5);

            Random rand = new Random();
            int directionX = rand.nextInt(2);
            if (directionX == 0) {
                directionX = -1;
            }
            int directionZ = rand.nextInt(2);
            if (directionZ == 0) {
                directionZ = -1;
            }

            ItemEntity add = new ItemEntity(this.world, this.getXPos(), this.getYPos(), this.getZPos(), itemstack1);
            add.addVelocity(.1 * directionX, .1, .1 * directionZ);
            this.world.addFreshEntity(add);
        }
    }

    protected int getCookTime() {
        return this.world.getRecipeManager().getRecipe((IRecipeType<IVatRecipe>)this.recipeType, this, this.world).map(IVatRecipe::getCookTime).orElse(200);
    }

    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(Player player) {
        return false;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return isFuel(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
        }
    }

    public void clear() {
        this.items.clear();
    }

    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipes.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    public void onCrafting(Player player) {
    }

    public void fillStackedContents(RecipeItemHelper helper) {
        for(ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }

    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler> handler = LazyOptional.of(() -> new
            net.minecraftforge.items.wrapper.InvWrapper(this));

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(capability, facing);
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
    }

    @Override
    public double getXPos() {
        return this.getPos().getX() + .5;
    }

    @Override
    public double getYPos() {
        return this.getPos().getY() + .5;
    }

    @Override
    public double getZPos() {
        return this.getPos().getZ() + .5;
    }
}

