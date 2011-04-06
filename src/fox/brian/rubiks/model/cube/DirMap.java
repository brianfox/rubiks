package fox.brian.rubiks.model.cube;


public enum DirMap {

	AAA ("AAA",  0, true,  true,  true),	
	AAX ("AAX",  1, true,  true,  false),	
	AXA ("AXA",  2, true,  false, true),	
	AXX ("AXX",  3, true,  false, false),

	XAA ("XAA",  4, false, true,  true  ),	
	XAX ("XAX",  5, false, true,  false ),	
	XXA ("XXA",  6, false, false, true  ),	
	XXX ("XXX",  7, false, false, false );
		
	private String abbreviation;
	private int val;
	boolean[] aligned;
		
	private DirMap (String abbreviation, int val, boolean frontCw, boolean rightCw, boolean topCw) {
		
		this.abbreviation = abbreviation;
		this.val = val;
		
		aligned = new boolean[3];

		aligned[0] = frontCw;
		aligned[1] = rightCw;
		aligned[2] = topCw;
	}

	public static DirMap getMap(int n) {
		switch(n) {
			case 0 : return AAA;	
			case 1 : return AAX;	
			case 2 : return AXA;	
			case 3 : return AXX;	
			case 4 : return XAA;	
			case 5 : return XAX;	
			case 6 : return XXA;	
			case 7 : return XXX;	
		}
		throw new CubeException("DirMap integer out of range");
	}
	
	
	public boolean aligned(Face s) {
		if ( s == Face.FRONT || s == Face.BACK   ) return aligned[0]; 
		if ( s == Face.RIGHT || s == Face.LEFT   ) return aligned[1]; 
		if ( s == Face.TOP   || s == Face.BOTTOM ) return aligned[2]; 
		throw new CubeException("Invalid face");
	}

	public static DirMap getMap(boolean frontCw, boolean rightCw, boolean topCw) {
		int n = (frontCw ? 4 : 0) + (rightCw ? 2 : 0) + (topCw ? 1 : 0);
		return getMap(n);
	}
	
	public int val() {
		return val;
	}

	@Override public String toString() {
		return abbreviation;
	}

}
