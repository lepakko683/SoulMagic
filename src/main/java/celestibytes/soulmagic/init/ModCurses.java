package celestibytes.soulmagic.init;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.content.curses.CurseGluttony;
import celestibytes.soulmagic.content.curses.CurseWrath;

public class ModCurses {
	
	public static final String CURSE_GLUTTONY = Ref.MOD_ID + ":gltt";
	public static final String CURSE_WRATH = Ref.MOD_ID + ":wrth";
	public static final String CURSE_LUST = Ref.MOD_ID + ":lust";
	
	public static void init() {
		SoulMagicAPI.registerCurse(CURSE_GLUTTONY, CurseGluttony.class);
		SoulMagicAPI.registerCurse(CURSE_WRATH, CurseWrath.class);
	}
}
