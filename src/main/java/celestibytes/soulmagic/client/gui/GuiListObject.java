package celestibytes.soulmagic.client.gui;

import java.lang.ref.WeakReference;

public class GuiListObject {
	
	protected final WeakReference<SimpleGui> parent;
	private final String titleKey, descKey;
	private final TextHelper texth;
	
	public GuiListObject(SimpleGui parent, String titleKey, String descKey) {
		this.parent = new WeakReference<SimpleGui>(parent);
		this.titleKey = titleKey;
		this.descKey = descKey;
		this.texth = new TextHelper(parent.guiRoot.get().getFontRenderer(), titleKey, descKey);
	}
	
	public String getTitle() {
		return texth.getTranslated(0);
	}
	
	public String getDescriptionLine(int i) {
		return texth.getTranslated(i + 1);
	}
	
	public int getDescriptionLength() {
		return texth.getLineCount() - 1;
	}
}
