package com.seefoxrun.rubiks.visualization.itext3d.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.seefoxrun.matrix.IdentityMatrix;
import com.seefoxrun.matrix.Matrix;

public abstract class Compound extends Object3D {

	protected ArrayList<Object3D> objects;

	protected Compound() {
		super();
		objects = new ArrayList<Object3D>();
	}

	protected void add(Object3D o) {
		objects.add(o);
	}

	@Override
	public void setColor(Color c) {
		for (Object3D o : objects)
			o.setColor(c);
	}

	@Override
	protected BoundingBox getBoundingBox(Matrix m) {
		BoundingBox b = new BoundingBox();
		for (Object3D o : objects)
			b = b.expandByBoundingBox(o.getBoundingBox(squeeze(m)));
		return b;
	}

	@Override
	public void draw(Graphics2D g, Matrix m) {
		if (m == null)
			m = IdentityMatrix.create(4);
		
		if (objects.size() == 0) 
			return;
		
		System.out.println("Sorting " + objects.size() + " children...");
		HashMap<Float,ArrayList<Object3D>> sorted = new HashMap<Float,ArrayList<Object3D>>();

		for (Object3D o : objects) {
			Matrix abscenter = o.getCenter(squeeze(m));
			Matrix normal = o.getNormal(squeeze(m));
			if (normal.getAt(2) <= 0)
				continue;
			float z = (float)(abscenter.getAt(2));
			ArrayList<Object3D> l;
			if (!sorted.containsKey(z)) {
				l = new ArrayList<Object3D>();
				sorted.put(z, l);
			}
			else
				l = sorted.get(z);
			l.add(o);	
		}

		ArrayList<Float> orderedKeys = new ArrayList<Float>(sorted.keySet());
		java.util.Collections.sort(orderedKeys);


		System.out.println("Sorted: ");
		for (Float f : orderedKeys) {
			for (Object3D o : sorted.get(f)) {
				System.out.println(o.toDebugString(this.squeeze(m)));
			}
		}
		
		for (Float f : orderedKeys) {
			ArrayList<Object3D> l = sorted.get(f);
			for (Object3D o : l) {
				o.draw(g, this.squeeze(m));
			}
			
		}
	}	

	@Override
	public String toDebugString(Matrix m) {
		Matrix beg = getCenter();
		Matrix begn = getNormal();
		Matrix fin = this.getCenter(m);
		Matrix endn = this.getNormal(m);
		return String.format(
				"(complex) %-15s    memo=%-20s  begcenter=(%7.2f, %7.2f, %7.2f)  fincenter=(%7.2f, %7.2f, %7.2f)   begnormal=(%7.2f, %7.2f, %7.2f)   endnormal=(%7.2f, %7.2f, %7.2f)   ", 
				toString(), 
				this.getMemo(),
				beg.getAt(0), beg.getAt(1), beg.getAt(2), 
				fin.getAt(0), fin.getAt(1), fin.getAt(2), 
				begn.getAt(0), begn.getAt(1), begn.getAt(2),
				endn.getAt(0), endn.getAt(1), endn.getAt(2) 
		);
	}
	
	/*
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s%n",super.toString()));
		sb.append(String.format("%sChildren%n", indent(1)));
		if (objects.size() == 0) 
			sb.append(String.format("%sNone%n",indent(2)));
		for (Object3D o : objects) 
			sb.append(String.format("%s%s%n", indent(2), o.toString().replace("\n", "\n" + indent(2))));
		return sb.toString();
	}
	*/
	
	
}
