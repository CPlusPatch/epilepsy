package com.cpluspatch.epilepsy.blockentity;

import com.cpluspatch.epilepsy.Epilepsy;
import com.cpluspatch.epilepsy.api.machine.MachineStatus;
import com.cpluspatch.epilepsy.blocks.ElectricFurnace;
import com.cpluspatch.epilepsy.inventory.ImplementedInventory;
import com.cpluspatch.epilepsy.screens.ElectricFurnaceScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Optional;

public class ElectricFurnaceBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(30000, 300, 300) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 66;
    private boolean isWorking = false;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_FURNACE, pos, state);
        this.energyStorage.amount = 30000;

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricFurnaceBlockEntity.this.progress;
                    case 1 -> ElectricFurnaceBlockEntity.this.maxProgress;
                    case 2 -> (int) ElectricFurnaceBlockEntity.this.energyStorage.amount;
                    case 3 -> (int) ElectricFurnaceBlockEntity.this.energyStorage.capacity;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricFurnaceBlockEntity.this.progress = value;
                    case 1 -> ElectricFurnaceBlockEntity.this.maxProgress = value;
                    case 2 -> ElectricFurnaceBlockEntity.this.energyStorage.amount = value;

                    // Can't set the capacity
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putLong("energyStorageAmount", energyStorage.amount);
        Inventories.writeNbt(nbt, inventory);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        energyStorage.amount = nbt.getLong("energyStorageAmount");
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    /**
     * Checks if a recipe is possible and the output slots are free to use
     * **/
    private static boolean canCraft(ElectricFurnaceBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0, entity.getStack(0));

        assert world != null;
        Optional<SmeltingRecipe> match = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, inventory, world);

        return match.isPresent()
                && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput())
                && entity.energyStorage.amount > 0;
    }

    /**
     * Checks if a recipe is possible, doesn't check for anything else
     * **/
    private static boolean hasRecipe(ElectricFurnaceBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0, entity.getStack(0));
        Epilepsy.LOGGER.info(entity.getStack(0).getTranslationKey());

        assert world != null;
        Optional<SmeltingRecipe> match = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, inventory, world);
        return match.isPresent();
    }

    /**
     * Crafts the item and changes the stack
     * **/
    private static void craftItem(ElectricFurnaceBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(2);
        inventory.setStack(0, entity.getStack(0));
        inventory.setStack(1, entity.getStack(1));

        assert world != null;
        Optional<SmeltingRecipe> match = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, inventory, world);

        if (match.isPresent()) {
            entity.removeStack(0, 1);
            entity.setStack(1, new ItemStack(match.get().getOutput().getItem(), entity.getStack(1).getCount() + 1));
            entity.resetProgress();
        }
    }

    private void resetProgress() { this.progress = 0; }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(1).getMaxCount() > inventory.getStack(1).getCount();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(1).getItem() == output.getItem() || inventory.getStack(1).isEmpty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, ElectricFurnaceBlockEntity entity) {
        if (canCraft(entity)) {
            world.setBlockState(pos, state.with(ElectricFurnace.WORKING, true));
            entity.isWorking = true;
            entity.progress++;
            entity.energyStorage.amount -= 80;
            if (entity.progress > entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            world.setBlockState(pos, state.with(ElectricFurnace.WORKING, false));
            entity.isWorking = false;
            entity.resetProgress();
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Electric Furnace");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ElectricFurnaceScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    /**
     * Get the current status of the machine
     * **/
    public static MachineStatus.StatusType getStatus(ElectricFurnaceBlockEntity entity) {
        if (entity.isWorking) return MachineStatus.StatusType.WORKING;
        if (entity.energyStorage.amount <= 0) return MachineStatus.StatusType.MISSING_ENERGY;
        if (!hasRecipe(entity)) return MachineStatus.StatusType.MISSING_ITEMS;
        if (!canCraft(entity)) return MachineStatus.StatusType.OUTPUT_FULL;
        return MachineStatus.StatusType.OTHER;
    }
}
