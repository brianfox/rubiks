package com.seefoxrun.rubiks.model.stateful.tree;

public class AdvancementStats {

	int level;
	int totalTwists;
	int kept;
	int discarded;
	float time;
	
	public AdvancementStats(int level, int totalTwists, int kept, int discarded, float time) {
		this.level = level;
		this.totalTwists = totalTwists;
		this.kept = kept;
		this.discarded = discarded;
		this.time = time;
	}
	
	@Override
	public String toString() {
		return String.format("Level: %2d    Total Twists: %8d    Kept: %8d    Discarded: %8d    Time: %f",
				level,
				totalTwists,
				kept,
				discarded,
				time
		);
	}
}
