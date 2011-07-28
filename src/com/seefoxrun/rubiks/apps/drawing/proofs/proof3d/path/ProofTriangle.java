package com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.path;

import com.seefoxrun.rubiks.drawing.proofs.PathProof3d;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Triangle;

public class ProofTriangle extends PathProof3d {

	public static void main(String[] args) {
        PathProof3d proof = new ProofTriangle();
		proof.createProof(
				new Triangle(
						0,0,0,
						1,0,0,
						0,1,0
						)
		);
	}

}

