package com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.path;

import com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.PathProof3d;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Rectangle;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Triangle;

public class ProofRectangle extends PathProof3d {

	public static void main(String[] args) {
        PathProof3d proof = new ProofRectangle();
		proof.createProof(
				new Rectangle(
						)
		);
	}

}
