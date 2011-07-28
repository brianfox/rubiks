package com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.path;

import com.seefoxrun.rubiks.drawing.proofs.PathProof3d;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Circle;


public class ProofCircle extends PathProof3d {

	public static void main(String[] args) {
        PathProof3d proof = new ProofCircle();
		proof.createProof(new Circle());
	}

}

