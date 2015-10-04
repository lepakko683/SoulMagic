package celestibytes.soulmagic.client.gui;

import java.lang.ref.WeakReference;


public abstract class SimpleGui {

	protected final WeakReference<IGuiRoot> guiRoot;
	
	public SimpleGui(IGuiRoot guiRoot) {
		this.guiRoot = new WeakReference<IGuiRoot>(guiRoot);
	}
	
	public abstract void drawGui(int width, int height, int mx, int my);
	
	public void onMouseClick(int x, int y, int width, int height) {}
	
	public void onMouseMove(int x, int y, int width, int height) {}
	
}
