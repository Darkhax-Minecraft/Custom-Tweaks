package net.darkhax.ctweaks.features.branding;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiGraphicButton extends GuiButton {
    
    /**
     * The image to draw on the button. By default this image is a random file that probably
     * doesn't exist. This texture can be changed in the constructor.
     */
    private ResourceLocation buttonImage = new ResourceLocation("textures/gui/widgets.png");
    private String url;
    private List<String> tooltip;
    
    /**
     * Constructs a new graphic button. A graphic button is a 20x20 button that uses an image
     * rather than text.
     * 
     * @param buttonID The ID to set for the button. This ID is specific to the GUI instance.
     * @param xPosition The X coordinate to position the button at.
     * @param yPosition The Y coordinate to position the button at.
     * @param texture The texture to use for the button. This should be a 20x20 image.
     */
    public GuiGraphicButton(String url, String title, int buttonID, int xPosition, int yPosition) {
        
        super(buttonID + 28170623, xPosition, yPosition, 20, 20, "");
        this.url = url;
        tooltip = new ArrayList<String>();
        tooltip.add(I18n.format("site." + title + ".name"));
        this.buttonImage = new ResourceLocation("ctweaks", "textures/gui/icon_" + title + ".png");
    }
    
    @Override
    public void drawButton (Minecraft mc, int posX, int posY) {
        
        super.drawButton(mc, posX, posY);
        mc.getTextureManager().bindTexture(this.buttonImage);
        drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0f, 0f, 20, 20, 20f, 20f);
        
        if (this.isMouseOver()) {
            
            GlStateManager.pushMatrix();
            renderTooltip(posX, posY, tooltip);
            GlStateManager.popMatrix();
        }
    }
    
    private void renderTooltip (int x, int y, List<String> textLines) {
        
        final Minecraft minecraft = Minecraft.getMinecraft();
        final ScaledResolution scaledresolution = new ScaledResolution(minecraft);
        GuiUtils.drawHoveringText(textLines, x, y, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), -1, minecraft.fontRendererObj);
    }
}