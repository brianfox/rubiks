package fox.brian.rubiks.apps.drawing.proofs.proof3d.path;

import fox.brian.rubiks.apps.drawing.proofs.proof3d.PathProof3d;
import fox.brian.rubiks.visualization.itext3d.objects.paths.collection.Circle;


public class ProofCircle extends PathProof3d {

	public static void main(String[] args) {
        PathProof3d proof = new ProofCircle();
		proof.createProof(new Circle());
	}

}

