package com.seefoxrun.rubiks.apps.drawing.proofs3d.objects;

import com.seefoxrun.rubiks.apps.drawing.proofs3d.PathProof;
import com.seefoxrun.rubiks.visualization.itext3d.objects.collection.Cube3D;

public class ProofCube extends PathProof {

	public static void main(String[] args) {
        PathProof proof = new ProofCube();
        Cube3D obj = new Cube3D();
		proof.createProof(obj);
	}

}

