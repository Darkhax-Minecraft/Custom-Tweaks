package net.darkhax.ctweaks;

import net.darkhax.ctweaks.lib.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptedMinecraftVersions = "[1.9.4,1.10.2]")
public class CTweaks {
    
    @Mod.Instance(Constants.MOD_ID)
    public static CTweaks instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
    }
}