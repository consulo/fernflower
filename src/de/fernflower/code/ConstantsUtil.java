/*
 *    Fernflower - The Analytical Java Decompiler
 *    http://www.reversed-java.com
 *
 *    (C) 2008 - 2010, Stiver
 *
 *    This software is NEITHER public domain NOR free software 
 *    as per GNU License. See license.txt for more details.
 *
 *    This software is distributed WITHOUT ANY WARRANTY; without 
 *    even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 *    A PARTICULAR PURPOSE. 
 */

package de.fernflower.code;

import de.fernflower.code.optinstructions.ALOAD;
import de.fernflower.code.optinstructions.ANEWARRAY;
import de.fernflower.code.optinstructions.ASTORE;
import de.fernflower.code.optinstructions.BIPUSH;
import de.fernflower.code.optinstructions.CHECKCAST;
import de.fernflower.code.optinstructions.DLOAD;
import de.fernflower.code.optinstructions.DSTORE;
import de.fernflower.code.optinstructions.FLOAD;
import de.fernflower.code.optinstructions.FSTORE;
import de.fernflower.code.optinstructions.GETFIELD;
import de.fernflower.code.optinstructions.GETSTATIC;
import de.fernflower.code.optinstructions.GOTO;
import de.fernflower.code.optinstructions.GOTO_W;
import de.fernflower.code.optinstructions.IINC;
import de.fernflower.code.optinstructions.ILOAD;
import de.fernflower.code.optinstructions.INSTANCEOF;
import de.fernflower.code.optinstructions.INVOKEINTERFACE;
import de.fernflower.code.optinstructions.INVOKESPECIAL;
import de.fernflower.code.optinstructions.INVOKESTATIC;
import de.fernflower.code.optinstructions.INVOKEVIRTUAL;
import de.fernflower.code.optinstructions.ISTORE;
import de.fernflower.code.optinstructions.JSR;
import de.fernflower.code.optinstructions.JSR_W;
import de.fernflower.code.optinstructions.LDC;
import de.fernflower.code.optinstructions.LDC2_W;
import de.fernflower.code.optinstructions.LDC_W;
import de.fernflower.code.optinstructions.LLOAD;
import de.fernflower.code.optinstructions.LOOKUPSWITCH;
import de.fernflower.code.optinstructions.LSTORE;
import de.fernflower.code.optinstructions.MULTIANEWARRAY;
import de.fernflower.code.optinstructions.NEW;
import de.fernflower.code.optinstructions.NEWARRAY;
import de.fernflower.code.optinstructions.PUTFIELD;
import de.fernflower.code.optinstructions.PUTSTATIC;
import de.fernflower.code.optinstructions.RET;
import de.fernflower.code.optinstructions.SIPUSH;
import de.fernflower.code.optinstructions.TABLESWITCH;

public class ConstantsUtil {
	
	public static String getName(int opcode) {
		return opcodeNames[opcode];
	}

	public static Instruction getInstructionInstance(int opcode, boolean wide, int group, int[] operands) {

		Instruction instr = getInstructionInstance(opcode);
		instr.wide = wide;
		instr.group = group;
		instr.setOperands(operands);

		return instr;
	}
	
	public static Instruction getInstructionInstance(int opcode) {
		try {
			Instruction instr;
			
			if((opcode >= CodeConstants.opc_ifeq &&
					opcode <= CodeConstants.opc_if_acmpne) ||
					opcode == CodeConstants.opc_ifnull ||
					opcode == CodeConstants.opc_ifnonnull) {
				instr = new IfInstruction();
			} else {
				Class cl = opcodeClasses[opcode];
				if(cl == null) {
					instr = new Instruction();
				} else {
					instr = (Instruction)cl.newInstance();
				}
			}
			
			instr.opcode = opcode;
			return instr;
		} catch (Exception ex) {
			return null;
		}
		
	}

	
	private static String[] opcodeNames = {
		"nop",				//    "nop",
		"aconst_null",		//    "aconst_null",
		"iconst_m1",			//    "iconst_m1",
		"iconst_0",			//    "iconst_0",
		"iconst_1",			//    "iconst_1",
		"iconst_2",			//    "iconst_2",
		"iconst_3",			//    "iconst_3",
		"iconst_4",			//    "iconst_4",
		"iconst_5",			//    "iconst_5",
		"lconst_0",			//    "lconst_0",
		"lconst_1",			//    "lconst_1",
		"fconst_0",			//    "fconst_0",
		"fconst_1",			//    "fconst_1",
		"fconst_2",			//    "fconst_2",
		"dconst_0",			//    "dconst_0",
		"dconst_1",			//    "dconst_1",
		"bipush",			//    "bipush",
		"sipush",			//    "sipush",
		"ldc",			//    "ldc",
		"ldc_w",			//    "ldc_w",
		"ldc2_w",			//    "ldc2_w",
		"iload",			//    "iload",
		"lload",			//    "lload",
		"fload",			//    "fload",
		"dload",			//    "dload",
		"aload",			//    "aload",
		"iload_0",			//    "iload_0",
		"iload_1",			//    "iload_1",
		"iload_2",			//    "iload_2",
		"iload_3",			//    "iload_3",
		"lload_0",			//    "lload_0",
		"lload_1",			//    "lload_1",
		"lload_2",			//    "lload_2",
		"lload_3",			//    "lload_3",
		"fload_0",			//    "fload_0",
		"fload_1",			//    "fload_1",
		"fload_2",			//    "fload_2",
		"fload_3",			//    "fload_3",
		"dload_0",			//    "dload_0",
		"dload_1",			//    "dload_1",
		"dload_2",			//    "dload_2",
		"dload_3",			//    "dload_3",
		"aload_0",			//    "aload_0",
		"aload_1",			//    "aload_1",
		"aload_2",			//    "aload_2",
		"aload_3",			//    "aload_3",
		"iaload",			//    "iaload",
		"laload",			//    "laload",
		"faload",			//    "faload",
		"daload",			//    "daload",
		"aaload",			//    "aaload",
		"baload",			//    "baload",
		"caload",			//    "caload",
		"saload",			//    "saload",
		"istore",			//    "istore",
		"lstore",			//    "lstore",
		"fstore",			//    "fstore",
		"dstore",			//    "dstore",
		"astore",			//    "astore",
		"istore_0",			//    "istore_0",
		"istore_1",			//    "istore_1",
		"istore_2",			//    "istore_2",
		"istore_3",			//    "istore_3",
		"lstore_0",			//    "lstore_0",
		"lstore_1",			//    "lstore_1",
		"lstore_2",			//    "lstore_2",
		"lstore_3",			//    "lstore_3",
		"fstore_0",			//    "fstore_0",
		"fstore_1",			//    "fstore_1",
		"fstore_2",			//    "fstore_2",
		"fstore_3",			//    "fstore_3",
		"dstore_0",			//    "dstore_0",
		"dstore_1",			//    "dstore_1",
		"dstore_2",			//    "dstore_2",
		"dstore_3",			//    "dstore_3",
		"astore_0",			//    "astore_0",
		"astore_1",			//    "astore_1",
		"astore_2",			//    "astore_2",
		"astore_3",			//    "astore_3",
		"iastore",			//    "iastore",
		"lastore",			//    "lastore",
		"fastore",			//    "fastore",
		"dastore",			//    "dastore",
		"aastore",			//    "aastore",
		"bastore",			//    "bastore",
		"castore",			//    "castore",
		"sastore",			//    "sastore",
		"pop",			//    "pop",
		"pop2",			//    "pop2",
		"dup",			//    "dup",
		"dup_x1",			//    "dup_x1",
		"dup_x2",			//    "dup_x2",
		"dup2",			//    "dup2",
		"dup2_x1",			//    "dup2_x1",
		"dup2_x2",			//    "dup2_x2",
		"swap",			//    "swap",
		"iadd",			//    "iadd",
		"ladd",			//    "ladd",
		"fadd",			//    "fadd",
		"dadd",			//    "dadd",
		"isub",			//    "isub",
		"lsub",			//    "lsub",
		"fsub",			//    "fsub",
		"dsub",			//    "dsub",
		"imul",			//    "imul",
		"lmul",			//    "lmul",
		"fmul",			//    "fmul",
		"dmul",			//    "dmul",
		"idiv",			//    "idiv",
		"ldiv",			//    "ldiv",
		"fdiv",			//    "fdiv",
		"ddiv",			//    "ddiv",
		"irem",			//    "irem",
		"lrem",			//    "lrem",
		"frem",			//    "frem",
		"drem",			//    "drem",
		"ineg",			//    "ineg",
		"lneg",			//    "lneg",
		"fneg",			//    "fneg",
		"dneg",			//    "dneg",
		"ishl",			//    "ishl",
		"lshl",			//    "lshl",
		"ishr",			//    "ishr",
		"lshr",			//    "lshr",
		"iushr",			//    "iushr",
		"lushr",			//    "lushr",
		"iand",			//    "iand",
		"land",			//    "land",
		"ior",			//    "ior",
		"lor",			//    "lor",
		"ixor",			//    "ixor",
		"lxor",			//    "lxor",
		"iinc",			//    "iinc",
		"i2l",			//    "i2l",
		"i2f",			//    "i2f",
		"i2d",			//    "i2d",
		"l2i",			//    "l2i",
		"l2f",			//    "l2f",
		"l2d",			//    "l2d",
		"f2i",			//    "f2i",
		"f2l",			//    "f2l",
		"f2d",			//    "f2d",
		"d2i",			//    "d2i",
		"d2l",			//    "d2l",
		"d2f",			//    "d2f",
		"i2b",			//    "i2b",
		"i2c",			//    "i2c",
		"i2s",			//    "i2s",
		"lcmp",			//    "lcmp",
		"fcmpl",			//    "fcmpl",
		"fcmpg",			//    "fcmpg",
		"dcmpl",			//    "dcmpl",
		"dcmpg",			//    "dcmpg",
		"ifeq",			//    "ifeq",
		"ifne",			//    "ifne",
		"iflt",			//    "iflt",
		"ifge",			//    "ifge",
		"ifgt",			//    "ifgt",
		"ifle",			//    "ifle",
		"if_icmpeq",		//    "if_icmpeq",
		"if_icmpne",		//    "if_icmpne",
		"if_icmplt",		//    "if_icmplt",
		"if_icmpge",		//    "if_icmpge",
		"if_icmpgt",		//    "if_icmpgt",
		"if_icmple",		//    "if_icmple",
		"if_acmpeq",		//    "if_acmpeq",
		"if_acmpne",		//    "if_acmpne",
		"goto",			//    "goto",
		"jsr",			//    "jsr",
		"ret",			//    "ret",
		"tableswitch",			//    "tableswitch",
		"lookupswitch",			//    "lookupswitch",
		"ireturn",			//    "ireturn",
		"lreturn",			//    "lreturn",
		"freturn",			//    "freturn",
		"dreturn",			//    "dreturn",
		"areturn",			//    "areturn",
		"return",			//    "return",
		"getstatic",		//    "getstatic",
		"putstatic",		//    "putstatic",
		"getfield",		//    "getfield",
		"putfield",		//    "putfield",
		"invokevirtual",		//    "invokevirtual",
		"invokespecial",		//    "invokespecial",
		"invokestatic",		//    "invokestatic",
		"invokeinterface",		//    "invokeinterface",
		"xxxunusedxxx",		//    "xxxunusedxxx",
		"new",				//    "new",
		"newarray",		//    "newarray",
		"anewarray",		//    "anewarray",
		"arraylength",		//    "arraylength",
		"athrow",			//    "athrow",
		"checkcast",		//    "checkcast",
		"instanceof",		//    "instanceof",
		"monitorenter",		//    "monitorenter",
		"monitorexit",		//    "monitorexit",
		"wide",			//    "wide",
		"multianewarray",		//    "multianewarray",
		"ifnull",			//    "ifnull",
		"ifnonnull",		//    "ifnonnull",
		"goto_w",			//    "goto_w",
		"jsr_w"			//    "jsr_w"
	};

	private static Class[] opcodeClasses = {
		null,				//    "nop",
		null,			//    "aconst_null",
		null,			//    "iconst_m1",
		null,			//    "iconst_0",
		null,			//    "iconst_1",
		null,			//    "iconst_2",
		null,			//    "iconst_3",
		null,			//    "iconst_4",
		null,			//    "iconst_5",
		null,			//    "lconst_0",
		null,			//    "lconst_1",
		null,				//    "fconst_0",
		null,				//    "fconst_1",
		null,				//    "fconst_2",
		null,				//    "dconst_0",
		null,				//    "dconst_1",
		BIPUSH.class,			//    "bipush",
		SIPUSH.class,			//    "sipush",
		LDC.class,			//    "ldc",
		LDC_W.class,			//    "ldc_w",
		LDC2_W.class,			//    "ldc2_w",
		ILOAD.class,			//    "iload",
		LLOAD.class,			//    "lload",
		FLOAD.class,			//    "fload",
		DLOAD.class,			//    "dload",
		ALOAD.class,			//    "aload",
		null,			//    "iload_0",
		null,			//    "iload_1",
		null,			//    "iload_2",
		null,			//    "iload_3",
		null,			//    "lload_0",
		null,			//    "lload_1",
		null,			//    "lload_2",
		null,			//    "lload_3",
		null,			//    "fload_0",
		null,			//    "fload_1",
		null,			//    "fload_2",
		null,			//    "fload_3",
		null,			//    "dload_0",
		null,			//    "dload_1",
		null,			//    "dload_2",
		null,			//    "dload_3",
		null,			//    "aload_0",
		null,			//    "aload_1",
		null,			//    "aload_2",
		null,			//    "aload_3",
		null,			//    "iaload",
		null,			//    "laload",
		null,				//    "faload",
		null,				//    "daload",
		null,				//    "aaload",
		null,				//    "baload",
		null,				//    "caload",
		null,				//    "saload",
		ISTORE.class,			//    "istore",
		LSTORE.class,			//    "lstore",
		FSTORE.class,			//    "fstore",
		DSTORE.class,			//    "dstore",
		ASTORE.class,			//    "astore",
		null,			//    "istore_0",
		null,			//    "istore_1",
		null,			//    "istore_2",
		null,			//    "istore_3",
		null,			//    "lstore_0",
		null,			//    "lstore_1",
		null,			//    "lstore_2",
		null,			//    "lstore_3",
		null,			//    "fstore_0",
		null,			//    "fstore_1",
		null,			//    "fstore_2",
		null,			//    "fstore_3",
		null,			//    "dstore_0",
		null,			//    "dstore_1",
		null,			//    "dstore_2",
		null,			//    "dstore_3",
		null,			//    "astore_0",
		null,			//    "astore_1",
		null,			//    "astore_2",
		null,			//    "astore_3",
		null,			//    "iastore",
		null,			//    "lastore",
		null,				//    "fastore",
		null,				//    "dastore",
		null,				//    "aastore",
		null,				//    "bastore",
		null,				//    "castore",
		null,			//    "sastore",
		null,			//    "pop",
		null,			//    "pop2",
		null,			//    "dup",
		null,			//    "dup_x1",
		null,			//    "dup_x2",
		null,			//    "dup2",
		null,			//    "dup2_x1",
		null,			//    "dup2_x2",
		null,			//    "swap",
		null,			//    "iadd",
		null,			//    "ladd",
		null,			//    "fadd",
		null,			//    "dadd",
		null,			//    "isub",
		null,			//    "lsub",
		null,			//    "fsub",
		null,			//    "dsub",
		null,			//    "imul",
		null,			//    "lmul",
		null,			//    "fmul",
		null,			//    "dmul",
		null,			//    "idiv",
		null,			//    "ldiv",
		null,			//    "fdiv",
		null,			//    "ddiv",
		null,			//    "irem",
		null,			//    "lrem",
		null,			//    "frem",
		null,			//    "drem",
		null,			//    "ineg",
		null,			//    "lneg",
		null,			//    "fneg",
		null,			//    "dneg",
		null,			//    "ishl",
		null,			//    "lshl",
		null,			//    "ishr",
		null,			//    "lshr",
		null,			//    "iushr",
		null,			//    "lushr",
		null,			//    "iand",
		null,			//    "land",
		null,			//    "ior",
		null,			//    "lor",
		null,			//    "ixor",
		null,			//    "lxor",
		IINC.class,			//    "iinc",
		null,			//    "i2l",
		null,			//    "i2f",
		null,			//    "i2d",
		null,			//    "l2i",
		null,			//    "l2f",
		null,			//    "l2d",
		null,			//    "f2i",
		null,			//    "f2l",
		null,			//    "f2d",
		null,			//    "d2i",
		null,			//    "d2l",
		null,			//    "d2f",
		null,			//    "i2b",
		null,			//    "i2c",
		null,			//    "i2s",
		null,			//    "lcmp",
		null,			//    "fcmpl",
		null,			//    "fcmpg",
		null,			//    "dcmpl",
		null,			//    "dcmpg",
		null,			//    "ifeq",
		null,			//    "ifne",
		null,			//    "iflt",
		null,			//    "ifge",
		null,			//    "ifgt",
		null,			//    "ifle",
		null,		//    "if_icmpeq",
		null,		//    "if_icmpne",
		null,		//    "if_icmplt",
		null,		//    "if_icmpge",
		null,		//    "if_icmpgt",
		null,		//    "if_icmple",
		null,		//    "if_acmpeq",
		null,		//    "if_acmpne",
		GOTO.class,			//    "goto",
		JSR.class,			//    "jsr",
		RET.class,			//    "ret",
		TABLESWITCH.class,			//    "tableswitch",
		LOOKUPSWITCH.class,			//    "lookupswitch",
		null,			//    "ireturn",
		null,			//    "lreturn",
		null,				//    "freturn",
		null,				//    "dreturn",
		null,				//    "areturn",
		null,				//    "return",
		GETSTATIC.class,		//    "getstatic",
		PUTSTATIC.class,		//    "putstatic",
		GETFIELD.class,		//    "getfield",
		PUTFIELD.class,		//    "putfield",
		INVOKEVIRTUAL.class,		//    "invokevirtual",
		INVOKESPECIAL.class,		//    "invokespecial",
		INVOKESTATIC.class,		//    "invokestatic",
		INVOKEINTERFACE.class,		//    "invokeinterface",
		null		,		//    "xxxunusedxxx",
		NEW.class,				//    "new",
		NEWARRAY.class,		//    "newarray",
		ANEWARRAY.class,		//    "anewarray",
		null,				//    "arraylength",
		null,				//    "athrow",
		CHECKCAST.class,		//    "checkcast",
		INSTANCEOF.class,		//    "instanceof",
		null,				//    "monitorenter",
		null,				//    "monitorexit",
		null,				//    "wide",
		MULTIANEWARRAY.class,		//    "multianewarray",
		null,			//    "ifnull",
		null,		//    "ifnonnull",
		GOTO_W.class,			//    "goto_w",
		JSR_W.class				//    "jsr_w"
	};
	
	
	
}
