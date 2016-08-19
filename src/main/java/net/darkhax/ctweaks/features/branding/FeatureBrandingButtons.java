package net.darkhax.ctweaks.features.branding;

import net.darkhax.ctweaks.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureBrandingButtons extends Feature {
    
    private String[] promoLinks;
    private int buttonWidth;
    
    @Override
    public void setupConfig (Configuration config) {
        
        promoLinks = config.getStringList("promoLinks", this.configName, new String[] { "twitter|twitter.com/Darkh4x", "patreon|patreon.com/Darkhax", "other|darkhax.net" }, "Each entry on the list represents a promotion button that will be added to the main menu. The format is siteType|Title|URL where | is used to split the message.");
        buttonWidth = config.getInt("buttonWidth", this.configName, 6, 1, 20, "The amount of buttons to add per row. If there are multiple promo buttons they will wrap after hitting the width");
    }
    
    @SubscribeEvent
    public void onGuiOpened (GuiScreenEvent.InitGuiEvent.Post event) {
        
        if (event.getGui() instanceof GuiMainMenu) {
            final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            int index = 1;
            
            for (String promoLink : promoLinks) {
                
                String[] params = promoLink.split("@@");
                int x = res.getScaledWidth() - (index * 25);
                final GuiGraphicButton button = new GuiGraphicButton(params[1], params[0], index, x, 5 + (index / buttonWidth));
                event.getButtonList().add(button);
                index++;
            }
        }
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
}
