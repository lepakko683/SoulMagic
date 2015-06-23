package celestibytes.soulmagic.asm;

import com.google.common.eventbus.EventBus;

import scala.actors.threadpool.Arrays;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class SoulMagicASM extends DummyModContainer {
	@SuppressWarnings("unchecked")
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
		bus.register(this);
		return true;
	}
}
