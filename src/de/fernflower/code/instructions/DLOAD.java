package de.fernflower.code.instructions;

import java.io.DataOutputStream;
import java.io.IOException;

import de.fernflower.code.Instruction;

public class DLOAD extends Instruction {

	private static int[] opcodes = new int[] {opc_dload_0,opc_dload_1,opc_dload_2,opc_dload_3}; 
	
	public void writeToStream(DataOutputStream out, int offset) throws IOException {
		int index = getOperand(0);
		if(index>3) {
			if(wide) {
				out.writeByte(opc_wide);
			}
			out.writeByte(opc_dload);
			if(wide) {
				out.writeShort(index);
			} else {
				out.writeByte(index);
			}
		} else {
			out.writeByte(opcodes[index]);
		}
	}
	
	public int length() {
		int index = getOperand(0);
		if(index>3) {
			if(wide) {
				return 4;
			} else {
				return 2;
			}
		} else {
			return 1;
		}
	}
	
}
