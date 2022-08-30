package com.cpluspatch.epilepsy.sounds;

import com.cpluspatch.epilepsy.Epilepsy;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final Identifier BABABOOEY_SOUND_ID = new Identifier(Epilepsy.MOD_ID, "bababooey");
    public static final Identifier INDUSTRIAL_SOCIETY_SOUND_ID = new Identifier(Epilepsy.MOD_ID, "industrial_society");
    public static SoundEvent BABABOOEY = new SoundEvent(BABABOOEY_SOUND_ID);
    public static SoundEvent INDUSTRIAL_SOCIETY = new SoundEvent(INDUSTRIAL_SOCIETY_SOUND_ID);

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, BABABOOEY_SOUND_ID, BABABOOEY);
        Registry.register(Registry.SOUND_EVENT, INDUSTRIAL_SOCIETY_SOUND_ID, INDUSTRIAL_SOCIETY);
    }
}
