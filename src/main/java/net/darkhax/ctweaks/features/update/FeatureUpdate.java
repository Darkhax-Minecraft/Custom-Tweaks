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
        
        info = UpdateInfo.create(updateURL);
        this.showMessage = info.getVersion() != null && this.currentVersion != null && !currentVersion.equalsIgnoreCase(info.getVersion());
    }
    
    @Override
    public void setupConfig (Configuration config) {
        
        currentVersion = config.getString("currentVersion", this.configName, "", "The version that the client is running. This should be updated when you release a new version, to prevent new versions from being out of date.");
        updateURL = config.getString("updateURL", this.configName, "", "A URL that points to a JSON file. This JSON file contains information about pack updates and can be used to send messages to your users.");
    }
    
    @SubscribeEvent
    public void onPlayerJoin (EntityJoinWorldEvent event) {
        
        final String message = info.getChatMessage();
        if (!hasShown && event.getEntity() instanceof EntityPlayer && showMessage && message != null && !message.isEmpty()) {
            ((EntityPlayer) event.getEntity()).addChatMessage(new TextComponentString(message));
            hasShown = true;
        }
    }
    
    @SubscribeEvent
    public void onUpdate (TickEvent.RenderTickEvent event) {
        
        if (mc == null)
            mc = Minecraft.getMinecraft();
            
        final GuiScreen guiscreen = mc.currentScreen;
        final String message = info.getGuiMessage();
        
        if (guiscreen instanceof GuiMainMenu) {
            
            if (showMessage && message != null && !message.isEmpty()) {
                
                mc.fontRendererObj.drawString(message, 5, 5, 0Xffffff);
            }
        }
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
}