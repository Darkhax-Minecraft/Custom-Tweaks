package net.darkhax.ctweaks;

import net.darkhax.ctweaks.features.Feature;
import net.darkhax.ctweaks.features.FeatureManager;
import net.darkhax.ctweaks.handler.ConfigurationHandler;
import net.darkhax.ctweaks.lib.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptedMinecraftVersions = "[1.9.4,1.10.2]")
public class CTweaks {
    
    @Mod.Instance(Constants.MOD_ID)
    public static CTweaks instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());
        FeatureManager.initFeatures();
        ConfigurationHandler.syncConfig();
        
        FeatureManager.FEATURES.forEach(Feature::onPreInit);
        
        for (final Feature feature : FeatureManager.FEATURES)
            if (feature.usesEvents())
                MinecraftForge.EVENT_BUS.register(feature);
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
        
        FeatureManager.FEATURES.forEach(Feature::onInit);
    }
    
    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        
        FeatureManager.FEATURES.forEach(Feature::onPostInit);
    }
}