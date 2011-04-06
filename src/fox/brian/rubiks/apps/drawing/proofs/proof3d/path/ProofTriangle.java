package fox.brian.rubiks.apps.drawing.proofs.proof3d.path;

import fox.brian.rubiks.apps.drawing.proofs.proof3d.PathProof3d;
import fox.brian.rubiks.visualization.itext3d.objects.paths.collection.Triangle;

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

