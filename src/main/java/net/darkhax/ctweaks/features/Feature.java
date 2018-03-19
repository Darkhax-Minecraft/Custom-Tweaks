package net.darkhax.ctweaks.features;

import net.minecraftforge.common.config.Configuration;

public class Feature {

    /**
     * Whether or not this feature is enabled.
     */
    protected boolean enabled;

    /**
     * The category name used for this feature in the config file.
     */
    protected String configName;

    /**
     * Called when the mod enters the preInit phase of loading. This is after
     * {@link #setupConfiguration(Configuration)} but before {@link #onClientPreInit()}.
     */
    public void onPreInit () {

    }

    /**
     * Called when the mod enters the init phase of loading.
     */
    public void onInit () {

    }

    /**
     * Called when the mod enters the postInit phase of loading.
     */
    public void onPostInit () {

    }

    /**
     * Checks if the feature listens to any events.
     *
     * @return Whether or not the feature listends to events.
     */
    public boolean usesEvents () {

        return false;
    }

    /**
     * Called before {@link #onPreInit()}. Allows for configuration options to be
     * detected/generated. A feature being enabled or not is handled automatically by the
     * feature manager.
     *
     * @param config The configuration object to pull data from.
     */
    public void setupConfig (Configuration config) {

    }

    /**
     * Checks if the feature should be enabled by default.
     *
     * @return Whether or not the feature should be enabled by default.
     */
    public boolean enabledByDefault () {

        return false;
    }
}