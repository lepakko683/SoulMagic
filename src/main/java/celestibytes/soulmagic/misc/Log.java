package celestibytes.soulmagic.misc;

import org.apache.logging.log4j.Level;

import celestibytes.soulmagic.Ref;
import cpw.mods.fml.common.FMLLog;


public class Log {
	
	private static void log(Level logLevel, Object object) {
		FMLLog.log(Ref.MOD_NAME, logLevel, String.valueOf(object));
	}
	
	public static void debug(Object object) {
		log(Level.INFO, "DEBUG: " + object);
//		log(Level.DEBUG, object);
	}
	
	public static void info(Object object) {
		log(Level.INFO, object);
	}
	
	public static void warn(Object object) {
		log(Level.WARN, object);
	}
	
	public static void err(Object object) {
		log(Level.ERROR, object);
	}
	
	public static void fatal(Object object) {
		log(Level.FATAL, object);
	}
}
