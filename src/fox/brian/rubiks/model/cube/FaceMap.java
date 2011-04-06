package fox.brian.rubiks.model.cube;


public enum FaceMap {

	// FRT                  -> FRT FTL FLB FBR
	FRT ("FRT",  0, Face.FRONT,  Face.RIGHT,   Face.TOP    ),	
	FTL ("FTL",  1, Face.FRONT,  Face.TOP,     Face.LEFT   ),	
	FLB ("FLB",  2, Face.FRONT,  Face.LEFT,    Face.BOTTOM ),	
	FBR ("FBR",  3, Face.FRONT,  Face.BOTTOM,  Face.RIGHT  ),

	// FRT + RCW            -> BRF BFL BLK BKR
	BRF ("BRF",  4, Face.BOTTOM, Face.RIGHT,   Face.FRONT  ),	
	BFL ("BFL",  5, Face.BOTTOM, Face.FRONT,   Face.LEFT   ), 
	BLK ("BLK",  6, Face.BOTTOM, Face.LEFT,    Face.BACK   ),	   
	BKR ("BKR",  7, Face.BOTTOM, Face.BACK,    Face.RIGHT  ), 

	// FRT + RCCW           -> TRK TKL TLF TFR
	TRK ("TRK",  8, Face.TOP,    Face.RIGHT,   Face.BACK   ),	
	TKL ("TKL",  9, Face.TOP,    Face.BACK,    Face.LEFT   ),	
	TLF ("TLF", 10, Face.TOP,    Face.LEFT,    Face.FRONT  ),	
	TFR ("TFR", 11, Face.TOP,    Face.FRONT,   Face.RIGHT  ), 

	// FRT + TCW            -> RKT RTF RFB RBK
	RKT ("RKT", 12, Face.RIGHT,  Face.BACK,    Face.TOP    ),	
	RTF ("RTF", 13, Face.RIGHT,  Face.TOP,     Face.FRONT  ),	
	RFB ("RFB", 14, Face.RIGHT,  Face.FRONT,   Face.BOTTOM ),	
	RBK ("RBK", 15, Face.RIGHT,  Face.BOTTOM,  Face.BACK   ), 

	// FRT + TCCW           -> LFT LTK LKB LBF
	LFT ("LFT", 16, Face.LEFT,   Face.FRONT,   Face.TOP    ),	
	LTK ("LTK", 17, Face.LEFT,   Face.TOP,     Face.BACK   ),	
	LKB ("LKB", 18, Face.LEFT,   Face.BACK,    Face.BOTTOM ),	
	LBF ("LBF", 19, Face.LEFT,   Face.BOTTOM,  Face.FRONT  ), 

	// FRT + RCW + RCW      -> KRB KBL KLT KTR
	KRB ("KRB", 20, Face.BACK,   Face.RIGHT,   Face.BOTTOM ),	
	KBL ("KBL", 21, Face.BACK,   Face.BOTTOM,  Face.LEFT   ),	
	KLT ("KLT", 22, Face.BACK,   Face.LEFT,    Face.TOP    ),	
	KTR ("KTR", 23, Face.BACK,   Face.TOP,     Face.RIGHT  );
		
	private String abbreviation;
	private int val;
	Face[] frtblk;
	Face[] frtblk_inv;
		
	private FaceMap (String abbreviation, int val, Face front, Face right, Face top) {
		
		this.abbreviation = abbreviation;
		this.val = val;
		
		frtblk = new Face[6];
		frtblk_inv = new Face[6];

		frtblk[0] = front;
		frtblk[1] = right;
		frtblk[2] = top;
		frtblk[3] = top.getOpposite();
		frtblk[4] = right.getOpposite();
		frtblk[5] = front.getOpposite();
		
		frtblk_inv[front.val()]               = Face.FRONT;
		frtblk_inv[right.val()]               = Face.RIGHT;
		frtblk_inv[top.val()]                 = Face.TOP;
		frtblk_inv[top.getOpposite().val()]   = Face.BOTTOM;
		frtblk_inv[right.getOpposite().val()] = Face.LEFT;
		frtblk_inv[front.getOpposite().val()] = Face.BACK;
		
	}

	public static FaceMap getMap(int n) {
		// FRT                  -> FRT FTL FLB FBR
		// FRT + RCW            -> BRF BFL BLK BKR
		// FRT + RCCW           -> TRK TKL TLF TFR
		// FRT + TCW            -> RKT RTF RFB RBK
		// FRT + TCCW           -> LFT LTK LKB LBF
		// FRT + RCW + RCW      -> KRB KBL KLT KTR
		
		switch(n) {
			case  0: return FRT;  case  1: return FTL;  case  2: return FLB;  case  3: return FBR;
			case  4: return BRF;  case  5: return BFL;  case  6: return BLK;  case  7: return BKR;
			case  8: return TRK;  case  9: return TKL;  case 10: return TLF;  case 11: return TFR;
			case 12: return RKT;  case 13: return RTF;  case 14: return RFB;  case 15: return RBK;
			case 16: return LFT;  case 17: return LTK;  case 18: return LKB;  case 19: return LBF;
			case 20: return KRB;  case 21: return KBL;  case 22: return KLT;  case 23: return KTR;
		}
		throw new CubeException("FaceMap integer out of range");
	}
	
	
	public Face translate(Face s) {
		if (s == Face.FRONT)  return frtblk[0]; 
		if (s == Face.RIGHT)  return frtblk[1]; 
		if (s == Face.TOP)    return frtblk[2]; 
		if (s == Face.BOTTOM) return frtblk[3]; 
		if (s == Face.LEFT)   return frtblk[4]; 
		if (s == Face.BACK)   return frtblk[5];
		throw new CubeException("Invalid face");
	}

	public Face translateInvert(Face s) {
		if (s == Face.FRONT)  return frtblk_inv[0]; 
		if (s == Face.RIGHT)  return frtblk_inv[1]; 
		if (s == Face.TOP)    return frtblk_inv[2]; 
		if (s == Face.BOTTOM) return frtblk_inv[3]; 
		if (s == Face.LEFT)   return frtblk_inv[4]; 
		if (s == Face.BACK)   return frtblk_inv[5];
		throw new CubeException("Invalid face");
	}

	public static FaceMap getMap(Face front, Face right, Face top) {
		// FRT                  -> FRT FTL FLB FBR
		// FRT + RCW            -> BRF BFL BLK BKR
		// FRT + RCCW           -> TRK TKL TLF TFR
		// FRT + TCW            -> RKT RTF RFB RBK
		// FRT + TCCW           -> LFT LTK LKB LBF
		// FRT + RCW + RCW      -> KRB KBL KLT KTR
		
		// FRT FTL FLB FBR
		if (front == Face.FRONT) {
			if (right == Face.RIGHT)   return FRT;
			if (right == Face.TOP)     return FTL;
			if (right == Face.LEFT)    return FLB;
			if (right == Face.BOTTOM)  return FBR;
		}
		// BRF BFL BLK BKR
		if (front == Face.BOTTOM) {
			if (right == Face.RIGHT)   return BRF;
			if (right == Face.FRONT)   return BFL;
			if (right == Face.LEFT)    return BLK;
			if (right == Face.BACK)    return BKR;
		}
		// TRK TKL TLF TFR
		if (front == Face.TOP) {
			if (right == Face.RIGHT)   return TRK;
			if (right == Face.BACK)    return TKL;
			if (right == Face.LEFT)    return TLF;
			if (right == Face.FRONT)   return TFR;
		}
		// RKT RTF RFB RBK
		if (front == Face.RIGHT) {
			if (right == Face.BACK)    return RKT;
			if (right == Face.TOP)     return RTF;
			if (right == Face.FRONT)   return RFB;
			if (right == Face.BOTTOM)  return RBK;
		}
		// LFT LTK LKB LBF
		if (front == Face.LEFT) {
			if (right == Face.FRONT)   return LFT;
			if (right == Face.TOP)     return LTK;
			if (right == Face.BACK)    return LKB;
			if (right == Face.BOTTOM)  return LBF;
		}
		// KRB KBL KLT KTR
		if (front == Face.BACK) {
			if (right == Face.BOTTOM)   return KBL;
			if (right == Face.LEFT)     return KLT;
			if (right == Face.TOP)      return KTR;
			if (right == Face.RIGHT)    return KRB;
		}
		throw new CubeException("Map values invalid");
	}

	public static FaceMap remapBackward(FaceMap original, int n) {
		FaceMap latest = getMap(n);
		Face front   = original.translate(latest.frtblk_inv[0]);
		Face right   = original.translate(latest.frtblk_inv[1]);
		Face top     = original.translate(latest.frtblk_inv[2]);
		return getMap(front,right,top);
	}

	public static FaceMap remapForward(FaceMap original, int n) {
		FaceMap latest = getMap(n);
		Face front   = original.translate(latest.frtblk[0]);
		Face right   = original.translate(latest.frtblk[1]);
		Face top     = original.translate(latest.frtblk[2]);
		return getMap(front,right,top);
	}
		
	public int val() {
		return val;
	}
		

	@Override public String toString() {
		return abbreviation;
	}

}
