package net.darkhax.ctweaks.features.branding;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import net.darkhax.ctweaks.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class FeatureBranding extends Feature {

    private String windowTitle;
    private String splashText;
    private String[] brandingTexts;
    private boolean replaceBrandings;

    private boolean shouldSplash = true;

    @Override
    public void onPreInit () {

        if (!this.windowTitle.isEmpty()) {
            Display.setTitle(this.windowTitle);
        }

        if (this.brandingTexts.length > 0) {
            this.addBrandings();
        }
    }

    @Override
    public void setupConfig (Configuration config) {

        this.windowTitle = config.getString("windowTitle", this.configName, "", "Changes the title of the window to a custom one. Leave blank to disable.");
        this.splashText = config.getString("splashText", this.configName, "", "Replaces the yellow splash text on the main menu. Leave blank to disable.");
        this.brandingTexts = config.getStringList("brandingTexts", this.configName, new String[] {}, "Entries to this list will be added to the brandings text on the bottom left side of the main menu.");
        this.replaceBrandings = config.getBoolean("replaceBrandings", this.configName, false, "Should the branding text replace the existing branding text? If not it will be added.");
    }

    @SubscribeEvent
    public void onUpdate (TickEvent.RenderTickEvent event) {

        final GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;

        if (guiscreen instanceof GuiMainMenu) {
            if (this.shouldSplash && !this.splashText.isEmpty()) {
                this.setSplash((GuiMainMenu) guiscreen, this.splashText);
            }
            else {
                this.shouldSplash = true;
            }
        }
    }

    /**
     * Sets a new piece of text to be used for the splash text on the main menu.
     *
     * @param menu: Instance of the GuiMainMenu
     * @param text: The new text to use for the splash text.
     */
    public void setSplash (GuiMainMenu menu, String text) {

        menu.splashText = text;
        this.shouldSplash = false;
    }

    /**
     * Adds brandings to the branding list. Replaces Forge's immutable map with one that can be
     * edited.
     */
    private void addBrandings () {

        final List<String> existingBrandings = FMLCommonHandler.instance().getBrandings(true);
        final List<String> newList = new ArrayList<>();

        for (final String branding : this.brandingTexts) {
            newList.add(branding);
        }

        if (!this.replaceBrandings) {
            newList.addAll(existingBrandings);
        }
        ReflectionHelper.setPrivateValue(FMLCommonHandler.class, FMLCommonHandler.instance(), newList, "brandings");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}