package com.seefoxrun.rubiks.model.stateful;

import java.util.ArrayList;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.*;
import com.seefoxrun.rubiks.model.operators.*;

public class Toy {
	
	private Cube toy;
	private Cube origin;
	private ArrayList<Twist> moves;
	private ArrayList<Twist> undos;
	
	
	public Toy(int size) {
		toy = Cube.createCube(size);
		origin = toy;
		moves = new ArrayList<Twist>();
		undos = new ArrayList<Twist>();
	}

	
	

	@Override
	public String toString() {
		
		TreeSet<Cube> unique = new TreeSet<Cube>();
		Cube reenact = origin;
		unique.add(reenact);
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s%n",toy));
		sb.append(String.format("==HISTORY==(MAP=%s)===============%n", toy.getFaceMap()));
		sb.append(String.format("%3d. %s%n", 0, origin));
		for (int i = 0; i < moves.size(); i++) {
			Twist o = moves.get(i);
			reenact = o.apply(reenact);
			sb.append(String.format("%3d. %s %s%n", i + 1, o, unique.contains(reenact) ? "LOOP" : ""));			
			unique.add(reenact);
		}
		if (undos.size() > 0) 
			sb.append(String.format("--UNDO STACK-----------------------%n"));
		for (int i = 0; i < undos.size(); i++) {
			Twist o = undos.get(i);
			sb.append(String.format("%3d. %s%n", i, o));			
		}

		sb.append(String.format("===================================%n"));
		return sb.toString();
	}


	public void reset() {
		toy = origin;
		moves = new ArrayList<Twist>();
		undos = new ArrayList<Twist>();
	}

	public void resize(int size) {
		toy = Cube.createCube(size);
		origin = toy;
		moves = new ArrayList<Twist>();
		undos = new ArrayList<Twist>();
	}

	public String twist(Face f, Dir d, int s) {
		// TODO: Input validation
		toy = toy.twist(f, d, s);
		moves.add(Twist.createTwist(f, d, s));
		return null;
	}

}
