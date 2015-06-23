package celestibytes.soulmagic.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion("1.7.10")
public class SoulMLoadingPlugin implements IFMLLoadingPlugin {
	
	private static final String[] ASMTCls = new String[] {
		"celestibytes.soulmagic.asm.CTEntityPlayer"
	};
	
	@Override
	public String[] getASMTransformerClass() {
		return ASMTCls;
	}

	@Override
	public String getModContainerClass() {
		return "celestibytes.soulmagic.asm.SoulMagicASM";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
