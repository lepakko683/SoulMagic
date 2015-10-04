package celestibytes.soulmagic.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiKnowledge extends GuiScreen implements IGuiRoot {
	
	protected SimpleGui page;
	
	public GuiKnowledge() {
		page = new SimpleGui(this) {
			public void drawGui(int width, int height, int mx, int my) {
				Gui.drawRect(0, 50, 50, 100, 0x7FAFAF00);
			}
		};
	}
	
	@Override
	public void drawScreen(int mx, int my, float pt) {
		super.drawScreen(mx, my, pt);
		
		page.drawGui(width, height, mx, my);
		
		Tessellator tes = Tessellator.instance;
//		tes.startDrawingQuads();
		drawString(fontRendererObj, "Hello", 50, 50, 0xFF700090);
		drawRect(0, 0, 50, 50, 0xFF700090);
//		System.out.println("width: " + width + " height: " + height);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void display(SimpleGui gui) {
		this.page = gui;
	}

	@Override
	public GuiScreen getGuiScreen() {
		return this;
	}

	@Override
	public void setTexture(ResourceLocation tex) {
		mc.renderEngine.bindTexture(tex);
	}

	@Override
	public void drawText(String text, int x, int y, int color) {
		fontRendererObj.drawString(text, x, y, color == 0 ? 0xFFFFFFFF : color, false);
	}

	@Override
	public Minecraft getMinecraft() {
		return mc;
	}

	@Override
	public int getScrWidth() {
		return width;
	}

	@Override
	public int getScrHeight() {
		return height;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}
}
