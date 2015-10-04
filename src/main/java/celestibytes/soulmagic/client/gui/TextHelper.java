package celestibytes.soulmagic.client.gui;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class TextHelper {
	
	private static final String translationErrorStr = "Translation Error";
	
	private String[] translationCache = null;
	private final FontRenderer fontRenderer;
	
	private final String titleKey, descKey;
	private int currentMaxWidth, updateWidth;
	private int updateTimeout = 0;
	
	public TextHelper(FontRenderer fontRenderer, String titleKey, String descKey) {
		this.fontRenderer = fontRenderer;
		this.titleKey = titleKey;
		this.descKey = descKey;
	}
	
	public void onRenderGui(int maxStringWidth) {
		if(maxStringWidth != currentMaxWidth) {
			if(updateWidth != maxStringWidth) {
				updateTimeout = 60; // 60 fps is most common: after 60 render calls after a window resize, the cache is reloaded
			} else {
				updateTimeout--;
			}
			
			if(updateTimeout <= 0) {
				currentMaxWidth = 0;
				translationCache = null;
			}
		}
	}
	
	public String getTranslated(int i) {
		return readCache(i);
	}
	
	public int getLineCount() {
		readCache(-1);
		return translationCache.length;
	}
	
	private String readCache(int i) {
		if(translationCache == null) {
			List<String> cache = new LinkedList<String>();
			cache.add(translateKey(titleKey));
			String[] descWords = translateKey(descKey).split(" ");
			
			if(descWords.length == 1) {
				cache.add(descWords[0]);
			} else {
				String buffer = "";
				for(int j = 0; j < descWords.length; j++) {
					if(descWords[j].length() > 0) {
						String test = buffer + " " + descWords[j];
						if(fontRenderer.getStringWidth(test) > currentMaxWidth) {
							cache.add(buffer);
							buffer = descWords[j];
						} else {
							buffer = test;
						}
					}
				}
			}
			
			translationCache = cache.toArray(new String[0]);
			if(translationCache != null) {
				return readCache(i);
			} else {
				return translationErrorStr;
			}
			
		} else {
			return inRange(i, translationCache.length) ? translationCache[i] : translationErrorStr;
		}
	}
	
	private boolean inRange(int i, int length) {
		return i >= 0 && i < length;
	}
	
	private String translateKey(String key) {
		String val = StatCollector.translateToLocal(key);
		return val == null || val.length() == 0 ? translationErrorStr : val;
	}
}
