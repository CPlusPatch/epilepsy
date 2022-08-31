package com.cpluspatch.epilepsy.screens;

import com.cpluspatch.epilepsy.Epilepsy;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static ScreenHandlerType<ElectricFurnaceScreenHandler> ELECTRIC_FURNACE_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ElectricFurnaceScreenHandler::new);

    public static void registerScreenHandlers() {
        Epilepsy.LOGGER.info("Init screen handlers");
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(Epilepsy.MOD_ID, "electric_furnace_smelting"), ELECTRIC_FURNACE_SCREEN_HANDLER);
    }
}
