package celestibytes.soulmagic.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public interface IGuiRoot {
	
	public void display(SimpleGui gui);
	
	public void setTexture(ResourceLocation tex);
	
	public void drawText(String text, int x, int y, int color);
	
	public FontRenderer getFontRenderer();
	
	public GuiScreen getGuiScreen();
	
	public Minecraft getMinecraft();
	
	public int getScrWidth();
	
	public int getScrHeight();
	
}
