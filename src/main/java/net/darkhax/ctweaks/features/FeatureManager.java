package net.darkhax.ctweaks.features;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.ctweaks.features.serverlist.FeatureServerList;
import net.darkhax.ctweaks.handler.ConfigurationHandler;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;

public class FeatureManager {
    
    /**
     * List of all registered features.
     */
    public static final List<Feature> FEATURES = new ArrayList<>();
    
    /**
     * This method is called before any mods have had a chance to initialize. Constructors
     * should take care not to reference any actual game code.
     */
    public static void initFeatures () {
        
        if (FMLLaunchHandler.side() == Side.CLIENT) {
            
            registerFeature(new FeatureServerList(), "Server List", "Allows for server entries to edited through config file.");
        }
        
    }
    
    /**
     * Registers a new feature with the feature manager. This will automatically create an
     * entry in the configuration file to enable/disable this feature. If the feature has been
     * disabled, it will not be registered. This will also handle event bus subscriptions.
     *
     * @param feature The feature being registered.
     * @param name The name of the feature.
     * @param description A short description of the feature.
     */
    private static void registerFeature (Feature feature, String name, String description) {
        
        feature.enabled = ConfigurationHandler.isFeatureEnabled(feature, name, description);
        feature.configName = name.toLowerCase().replace(' ', '_');
        feature.setupConfig(ConfigurationHandler.config);
        
        if (feature.enabled)
            FEATURES.add(feature);
    }
}
