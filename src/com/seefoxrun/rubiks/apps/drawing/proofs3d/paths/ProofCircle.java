package com.seefoxrun.rubiks.apps.drawing.proofs3d.paths;

import com.seefoxrun.rubiks.apps.drawing.proofs3d.PathProof;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Circle;


public class ProofCircle extends PathProof {

	public static void main(String[] args) {
        PathProof proof = new ProofCircle();
		proof.createProof(new Circle());
	}

}

