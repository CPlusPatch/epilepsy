package com.cpluspatch.epilepsy;

import com.cpluspatch.epilepsy.blocks.ModBlocks;
import com.cpluspatch.epilepsy.groups.ModItemGroups;
import com.cpluspatch.epilepsy.items.ModItems;
import com.cpluspatch.epilepsy.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Epilepsy implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final String MOD_ID = "epilepsy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ModSounds.registerSounds();
		ModBlocks.registerBlocks();
		ModItemGroups.registerItemGroups();

		LOGGER.info("welcome to the rice fields motherfucker");
	}
}
