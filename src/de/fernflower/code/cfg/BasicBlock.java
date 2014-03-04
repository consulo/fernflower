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

package de.fernflower.code.cfg;

import java.util.ArrayList;
import java.util.List;

import de.fernflower.code.Instruction;
import de.fernflower.code.InstructionSequence;
import de.fernflower.code.SimpleInstructionSequence;
import de.fernflower.main.DecompilerContext;
import de.fernflower.modules.decompiler.decompose.IGraphNode;

public class BasicBlock implements IGraphNode {

	// *****************************************************************************
	// public fields
	// *****************************************************************************
	
	public int id = 0;

	public int mark = 0;
	
	// *****************************************************************************
	// private fields
	// *****************************************************************************
	
	private InstructionSequence seq = new SimpleInstructionSequence();
	
	private List<BasicBlock> preds = new ArrayList<BasicBlock>();
	
	private List<BasicBlock> succs = new ArrayList<BasicBlock>();
	
	private List<Integer> instrOldOffsets = new ArrayList<Integer>();
	
	private List<BasicBlock> predExceptions = new ArrayList<BasicBlock>();

	private List<BasicBlock> succExceptions = new ArrayList<BasicBlock>();

	
	
	public BasicBlock() {}
	
	public BasicBlock(int id) {
		this.id = id;
	}

	// *****************************************************************************
	// public methods
	// *****************************************************************************
	
	public Object clone() {
		
		BasicBlock block = new BasicBlock();
		block.id = id;
		block.setSeq(seq.clone());
		block.setInstrOldOffsets(new ArrayList<Integer>(instrOldOffsets));
		
		return block;
	}

	public void free() {
		preds.clear();
		succs.clear();
		instrOldOffsets.clear(); 
		succExceptions.clear();
		seq = new SimpleInstructionSequence();
	}
	
	public Instruction getInstruction(int index) {
		return seq.getInstr(index);
	}
	
	public Instruction getLastInstruction() {
		if(seq.isEmpty()) {
			return null;
		} else {
			return seq.getLastInstr();
		}
	}
	
	public int size() {
		return seq.length();
	}
	
	public void addPredecessor(BasicBlock block) {
		preds.add(block);
	}

	public void removePredecessor(BasicBlock block) {
		while(preds.remove(block));
	}
	
	public void addSuccessor(BasicBlock block) {
		succs.add(block);
		block.addPredecessor(this);
	}

	public void removeSuccessor(BasicBlock block) {
		while(succs.remove(block));
		block.removePredecessor(this);
	}
	
	// FIXME: unify block comparisons: id or direkt equality
	public void replaceSuccessor(BasicBlock oldBlock, BasicBlock newBlock) {
		for(int i=0;i<succs.size();i++) {
			if(succs.get(i).id == oldBlock.id) {
				succs.set(i, newBlock);
				oldBlock.removePredecessor(this);
				newBlock.addPredecessor(this);
			}
		}

		for(int i=0;i<succExceptions.size();i++) {
			if(succExceptions.get(i).id == oldBlock.id) {
				succExceptions.set(i, newBlock);
				oldBlock.removePredecessorException(this);
				newBlock.addPredecessorException(this);
			}
		}
	}

	public void addPredecessorException(BasicBlock block) {
		predExceptions.add(block);
	}

	public void removePredecessorException(BasicBlock block) {
		while(predExceptions.remove(block));
	}
	
	public void addSuccessorException(BasicBlock block) {
		if(!succExceptions.contains(block)) {
			succExceptions.add(block);
			block.addPredecessorException(this);
		}
	}

	public void removeSuccessorException(BasicBlock block) {
		while(succExceptions.remove(block));
		block.removePredecessorException(this);
	}
	
	public String toString() {
		return toString(0); 
	}

	public String toString(int indent) {

		String new_line_separator = DecompilerContext.getNewLineSeparator();
		
		return id+":" + new_line_separator +seq.toString(indent); 
	}
	
	public String toStringOldIndices() {
		
		String new_line_separator = DecompilerContext.getNewLineSeparator();
		
		StringBuffer buf = new  StringBuffer();
		
		for(int i=0;i<seq.length();i++) {
			if(i<instrOldOffsets.size()) {
				buf.append(instrOldOffsets.get(i));
			} else {
				buf.append("-1");
			}
			buf.append(": ");
			buf.append(seq.getInstr(i).toString());
			buf.append(new_line_separator);
		}
		
		return buf.toString(); 
	}
	
	public boolean isSuccessor(BasicBlock block) {
		for(BasicBlock succ : succs) {
			if(succ.id == block.id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPredecessor(BasicBlock block) {
		for(int i=0;i<preds.size();i++) {
			if(preds.get(i).id == block.id) {
				return true;
			}
		}
		return false;
	}
	
	// *****************************************************************************
	// getter and setter methods
	// *****************************************************************************
	
	public List<Integer> getInstrOldOffsets() {
		return instrOldOffsets;
	}

	public void setInstrOldOffsets(List<Integer> instrInds) {
		this.instrOldOffsets = instrInds;
	}

	public List<? extends IGraphNode> getPredecessors() {
		List<BasicBlock> lst = new ArrayList<BasicBlock>(preds);
		lst.addAll(predExceptions);
		return lst;
	}
	
	public List<BasicBlock> getPreds() {
		return preds;
	}

	public void setPreds(List<BasicBlock> preds) {
		this.preds = preds;
	}

	public InstructionSequence getSeq() {
		return seq;
	}

	public void setSeq(InstructionSequence seq) {
		this.seq = seq;
	}

	public List<BasicBlock> getSuccs() {
		return succs;
	}

	public void setSuccs(List<BasicBlock> succs) {
		this.succs = succs;
	}


	public List<BasicBlock> getSuccExceptions() {
		return succExceptions;
	}


	public void setSuccExceptions(List<BasicBlock> succExceptions) {
		this.succExceptions = succExceptions;
	}

	public List<BasicBlock> getPredExceptions() {
		return predExceptions;
	}

	public void setPredExceptions(List<BasicBlock> predExceptions) {
		this.predExceptions = predExceptions;
	}

	
}
