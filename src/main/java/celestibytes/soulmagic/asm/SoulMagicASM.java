package celestibytes.soulmagic.asm;

import java.util.Arrays;

import net.minecraft.launchwrapper.LaunchClassLoader;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class SoulMagicASM extends DummyModContainer {
	
	private static Boolean obfEnv;
	
	public static boolean isObfuscatedEnv() {
		if(obfEnv != null) {
			return obfEnv;
		}
		try {
			byte[] bts = ((LaunchClassLoader)SoulMagicASM.class.getClassLoader()).getClassBytes("net.minecraft.world.World");
			if(bts == null) {
				obfEnv = true;
			} else {
				obfEnv = false;
			}
		} catch(Exception e) {}
		
		return obfEnv;
	}
	
	public SoulMagicASM() {
		super(new ModMetadata());
		ModMetadata m = getMetadata();
		m.modId = "soulmagic_asm";
		m.name = "Soul Magic ASM";
		m.version = "@VERSION@";
		m.authorList = Arrays.asList(new String[]{"Okkapel"});
		m.description = "asm tweaks for Soul Magic";
		this.setEnabledState(true);
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		//bus.register(this);
		return true;
	}
}
