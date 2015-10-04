package celestibytes.soulmagic.api.datatypes;

public class PageGuiInfo {
	
	public final String pagePath;
	
	public final String titleKey;
	public final String descriptionKey;
	public final String documentKey;
	
	public PageGuiInfo(String pagePath, String titleKey, String descriptionKey, String documentKey) {
		this.pagePath = pagePath;
		this.titleKey = titleKey;
		this.descriptionKey = descriptionKey;
		this.documentKey = documentKey;
	}
	
	@Override
	public String toString() {
		return pagePath + ":{" + titleKey + ";" + descriptionKey + ";" + documentKey + "}";
	}
}
