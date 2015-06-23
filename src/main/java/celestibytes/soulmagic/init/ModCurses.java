package celestibytes.soulmagic.init;

import net.minecraft.init.Blocks;
import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.content.curses.CurseGluttony;
import celestibytes.soulmagic.content.curses.CurseWrath;

public class ModCurses {
	
	public static final String CURSE_GLUTTONY = Ref.MOD_ID + ":gltt";
	public static final String CURSE_WRATH = Ref.MOD_ID + ":wrth";
	public static final String CURSE_LUST = Ref.MOD_ID + ":lust";
	
	public static int CURSE_ID_GLUTTONY = -1;
	public static int CURSE_ID_WRATH = -1;
	public static int CURSE_ID_LUST = -1;
	
	public static void init() {
		CURSE_ID_GLUTTONY = SoulMagicAPI.registerCurse(CURSE_GLUTTONY, CurseGluttony.class, Blocks.gold_block, 0);
		//SoulMagicAPI.registerCurse(CURSE_WRATH, CurseWrath.class);
	}
	
}
