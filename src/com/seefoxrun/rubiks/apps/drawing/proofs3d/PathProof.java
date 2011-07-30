package com.seefoxrun.rubiks.apps.drawing.proofs3d;

import com.seefoxrun.rubiks.visualization.itext3d.objects.Simple;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.Path;

public abstract class PathProof extends ObjectProof {

	public void createProof(Path p) {
		super.createProof(new Simple(p));

	}

}
