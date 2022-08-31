package com.cpluspatch.epilepsy.blockentity;

import com.cpluspatch.epilepsy.blocks.ModBlocks;
import com.cpluspatch.epilepsy.inventory.ImplementedInventory;
import com.cpluspatch.epilepsy.screens.ElectricFurnaceScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ElectricFurnaceBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    private int energy = 10000;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ELECTRIC_FURNACE_BLOCK_ENTITY, pos, state);
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("energy", energy);
        Inventories.writeNbt(nbt, inventory);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        energy = nbt.getInt("energy");
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Electric Furnace");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ElectricFurnaceScreenHandler(syncId, inv, this);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ElectricFurnaceBlockEntity entity) {
        if (hasRecipe(entity) && hasNotReachedStackLimit(entity)) {
            smeltItem(entity);
        }
    }

    private static boolean hasRecipe(ElectricFurnaceBlockEntity entity) {
        boolean hasItemInSlot = entity.getStack(0).getItem() == Items.OAK_LOG;
        boolean hasEnoughEnergy = entity.getEnergy() >= 1000;

        return hasItemInSlot && hasEnoughEnergy;
    }

    private static void smeltItem(ElectricFurnaceBlockEntity entity) {
        entity.removeStack(0, 1);

        entity.setStack(1, new ItemStack(Items.CHARCOAL, entity.getStack(2).getCount() + 1));
    }

    private static boolean hasNotReachedStackLimit(ElectricFurnaceBlockEntity entity) {
        return entity.getStack(2).getCount() < entity.getStack(2).getMaxCount();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
}
