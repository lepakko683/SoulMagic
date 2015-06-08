package celestibytes.soulmagic.init;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.content.curses.CurseGluttony;

public class ModCurses {
	
	public static final String CURSE_GLUTTONY = Ref.MOD_ID + ":gltt";
	
	public static void init() {
		SoulMagicAPI.registerCurse(CURSE_GLUTTONY, CurseGluttony.class);
	}
}
