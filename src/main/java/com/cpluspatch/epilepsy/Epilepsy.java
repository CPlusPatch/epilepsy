package com.cpluspatch.epilepsy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.util.registry.Registry;

public class Epilepsy implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final String MOD_ID = "epilepsy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item RAINBOW_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MISC));

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "rainbow_ingot"), RAINBOW_INGOT);

		LOGGER.info("Hello Fabric world!");
	}
}
