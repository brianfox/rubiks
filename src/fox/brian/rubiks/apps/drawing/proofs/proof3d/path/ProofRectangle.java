package fox.brian.rubiks.apps.drawing.proofs.proof3d.path;

import fox.brian.rubiks.apps.drawing.proofs.proof3d.PathProof3d;
import fox.brian.rubiks.visualization.itext3d.objects.paths.collection.Rectangle;
import fox.brian.rubiks.visualization.itext3d.objects.paths.collection.Triangle;

public class ProofRectangle extends PathProof3d {

	public static void main(String[] args) {
        PathProof3d proof = new ProofRectangle();
		proof.createProof(
				new Rectangle(
						)
		);
	}

}

