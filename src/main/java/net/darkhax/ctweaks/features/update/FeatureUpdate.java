package net.darkhax.ctweaks.features.update;

import net.darkhax.ctweaks.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FeatureUpdate extends Feature {

    private Minecraft mc;
    private String currentVersion;
    private String updateURL;
    private UpdateInfo info;
    private boolean showMessage;
    private boolean hasShown = false;

    @Override
    public void onPreInit () {

        this.info = UpdateInfo.create(this.updateURL);
        this.showMessage = this.info.getVersion() != null && this.currentVersion != null && !this.currentVersion.equalsIgnoreCase(this.info.getVersion());
    }

    @Override
    public void setupConfig (Configuration config) {

        this.currentVersion = config.getString("currentVersion", this.configName, "", "The version that the client is running. This should be updated when you release a new version, to prevent new versions from being out of date.");
        this.updateURL = config.getString("updateURL", this.configName, "", "A URL that points to a JSON file. This JSON file contains information about pack updates and can be used to send messages to your users.");
    }

    @SubscribeEvent
    public void onPlayerJoin (EntityJoinWorldEvent event) {

        final String message = this.info.getChatMessage();
        if (!this.hasShown && event.getEntity() instanceof EntityPlayer && this.showMessage && message != null && !message.isEmpty()) {
            ((EntityPlayer) event.getEntity()).sendMessage(new TextComponentString(message));
            this.hasShown = true;
        }
    }

    @SubscribeEvent
    public void onUpdate (TickEvent.RenderTickEvent event) {

        if (this.mc == null) {
            this.mc = Minecraft.getMinecraft();
        }

        final GuiScreen guiscreen = this.mc.currentScreen;
        final String message = this.info.getGuiMessage();

        if (guiscreen instanceof GuiMainMenu) {

            if (this.showMessage && message != null && !message.isEmpty()) {

                this.mc.fontRenderer.drawString(message, 5, 5, 0Xffffff);
            }
        }
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}