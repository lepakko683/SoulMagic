package celestibytes.soulmagic.asm;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class CTEntityPlayer implements IClassTransformer {
	
	private static final String E_PLR_CLS = "net.minecraft.entity.player.EntityPlayer";
	private static final String E_PLR_CLS_OBF = "yz";
	private static final String ADD_EXH_M_OBF = "a";
	private static final String ADD_EXH_M = "addExhaustion";
	private static final String ADD_EXH_M_DESC = "(F)V";
	
	private static final String ASM_CALLS = "celestibytes/soulmagic/asm/ASMCalls";
	
	@Override
	public byte[] transform(String name, String srgName, byte[] bytes) {
		boolean obf = false;
		if(E_PLR_CLS.equals(srgName)) {
			obf = SoulMagicASM.isObfuscatedEnv();
		} else {
			return bytes;
		}
		
		String mtdName;
		
		if(obf) {
			mtdName = ADD_EXH_M_OBF;
		} else {
			mtdName = ADD_EXH_M;
		}
		
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		cr.accept(cn, 0);
		
		Iterator<MethodNode> methods = cn.methods.iterator();
		while(methods.hasNext()) {
			MethodNode mn = methods.next();
			if(mtdName.equals(mn.name) && ADD_EXH_M_DESC.equals(mn.desc)) {
				Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
				while(iter.hasNext()) {
					AbstractInsnNode insn = iter.next();
					if(insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
						MethodInsnNode min = (MethodInsnNode) insn;
						if("(F)V".equals(min.desc)) {
							InsnList inj = new InsnList();
							inj.add(new VarInsnNode(Opcodes.ALOAD, 0));
							if(obf) {
								inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ASM_CALLS, "getExhaustion", "(FLyz;)F", false));
							} else {
								inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ASM_CALLS, "getExhaustion", "(FLnet/minecraft/entity/player/EntityPlayer;)F", false));
							}
							
							mn.instructions.insertBefore(min, inj);
							ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS); //  | ClassWriter.COMPUTE_FRAMES
							cn.accept(cw);
							
							System.out.println("Injection done");
							return cw.toByteArray();
						}
					}
				}
			}
		}
		
		System.out.println("Injection failed");
		return bytes;
	}

}
