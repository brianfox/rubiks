package com.seefoxrun.rubiks.apps.drawing.proofs3d.paths;

import com.seefoxrun.rubiks.apps.drawing.proofs3d.PathProof;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Triangle;

public class ProofTriangle extends PathProof {

	public static void main(String[] args) {
        PathProof proof = new ProofTriangle();
		proof.createProof(
				new Triangle(
						0,0,0,
						1,0,0,
						0,1,0
						)
		);
	}

}

