/*
 * $Id: PageSize.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: PageSize.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.visualization.medium;

import fox.brian.visualization.measurement.Units;
import fox.brian.visualization.measurement.Value;

public enum PageSize {

	// NORTH AMERICAN LOOSE SIZES
	LETTER       (new Value( 8.5F, Units.INCHES), new Value(11   , Units.INCHES)),
	LEGAL        (new Value( 8.5F, Units.INCHES), new Value(14   , Units.INCHES)),
	JUNIOR_LEGAL (new Value( 8.0F, Units.INCHES), new Value( 5.0F, Units.INCHES)),
	LEDGER_2     (new Value(17   , Units.INCHES), new Value(11   , Units.INCHES)),
	TABLOID      (new Value(11   , Units.INCHES), new Value(17   , Units.INCHES)),
	
	
	// INTERNATIONAL STANDARDS (ISO 216) 

	// ISO 216: A series
	ISO_A0  (new Value(841, Units.MM), new Value(1189, Units.MM)),
	ISO_A1  (new Value(594, Units.MM), new Value( 841, Units.MM)),
	ISO_A2  (new Value(420, Units.MM), new Value( 594, Units.MM)),
	ISO_A3  (new Value(297, Units.MM), new Value( 420, Units.MM)),
	ISO_A4  (new Value(210, Units.MM), new Value( 297, Units.MM)),
	ISO_A5  (new Value(148, Units.MM), new Value( 210, Units.MM)),
	ISO_A6  (new Value(105, Units.MM), new Value( 148, Units.MM)),
	ISO_A7  (new Value( 74, Units.MM), new Value( 105, Units.MM)),
	ISO_A8  (new Value( 52, Units.MM), new Value(  74, Units.MM)),
	ISO_A9  (new Value( 37, Units.MM), new Value(  52, Units.MM)),
	ISO_A10 (new Value( 26, Units.MM), new Value(  37, Units.MM)),

	// ISO 216: B series
	ISO_B0  (new Value(1000, Units.MM), new Value(1414, Units.MM)),
	ISO_B1  (new Value( 707, Units.MM), new Value(1000, Units.MM)),
	ISO_B2  (new Value( 500, Units.MM), new Value( 707, Units.MM)),
	ISO_B3  (new Value( 353, Units.MM), new Value( 500, Units.MM)),
	ISO_B4  (new Value( 250, Units.MM), new Value( 353, Units.MM)),
	ISO_B5  (new Value( 176, Units.MM), new Value( 250, Units.MM)),
	ISO_B6  (new Value( 125, Units.MM), new Value( 176, Units.MM)),
	ISO_B7  (new Value(  88, Units.MM), new Value( 125, Units.MM)),
	ISO_B8  (new Value(  62, Units.MM), new Value(  88, Units.MM)),
	ISO_B9  (new Value(  44, Units.MM), new Value(  62, Units.MM)),
	ISO_B10 (new Value(  31, Units.MM), new Value(  44, Units.MM)),

	// ISO 216: C series
	ISO_C0   (new Value(917, Units.MM), new Value(1297, Units.MM)),
	ISO_C1   (new Value(648, Units.MM), new Value( 917, Units.MM)),
	ISO_C2   (new Value(458, Units.MM), new Value( 648, Units.MM)),
	ISO_C3   (new Value(324, Units.MM), new Value( 458, Units.MM)),
	ISO_C4   (new Value(229, Units.MM), new Value( 324, Units.MM)),
	ISO_C5   (new Value(162, Units.MM), new Value( 229, Units.MM)),
	ISO_C6   (new Value(114, Units.MM), new Value( 162, Units.MM)),
	ISO_C7   (new Value( 81, Units.MM), new Value( 114, Units.MM)),
	ISO_C8   (new Value( 57, Units.MM), new Value(  81, Units.MM)),
	ISO_C9   (new Value( 40, Units.MM), new Value(  57, Units.MM)),
	ISO_C10  (new Value( 28, Units.MM), new Value(  40, Units.MM)),



	// ARCHITECTURAL SIZES
	ARCH_A  (new Value( 9, Units.INCHES), new Value(12, Units.INCHES)),
	ARCH_B  (new Value(12, Units.INCHES), new Value(18, Units.INCHES)),
	ARCH_C  (new Value(18, Units.INCHES), new Value(24, Units.INCHES)),
	ARCH_D  (new Value(24, Units.INCHES), new Value(36, Units.INCHES)),
	ARCH_E  (new Value(36, Units.INCHES), new Value(48, Units.INCHES)),
	ARCH_E1 (new Value(30, Units.INCHES), new Value(42, Units.INCHES)),
	ARCH_E2 (new Value(26, Units.INCHES), new Value(38, Units.INCHES)),
	ARCH_E3 (new Value(27, Units.INCHES), new Value(39, Units.INCHES)),


	
	// TRADITIONAL INCH-BASED PAGE SIZES
	//
	ATLAS             (new Value( 26    , Units.INCHES), new Value(34    , Units.INCHES)),  // still used
	IMPERIAL          (new Value( 22    , Units.INCHES), new Value(30    , Units.INCHES)),  // still used
	ELEPHANT          (new Value( 23    , Units.INCHES), new Value(28    , Units.INCHES)),  // still used
	ROYAL             (new Value( 20    , Units.INCHES), new Value(25    , Units.INCHES)),  // still used
	MEDIUM            (new Value( 17.5F , Units.INCHES), new Value(23    , Units.INCHES)),  // still used
	DEMY              (new Value( 17.5F , Units.INCHES), new Value(22.5F , Units.INCHES)),  // still used
	POST              (new Value( 15.5F , Units.INCHES), new Value(19.25F, Units.INCHES)),  // still used
	CROWN             (new Value( 15    , Units.INCHES), new Value(20    , Units.INCHES)),  // still used
	FOOLSCAP          (new Value( 13.5F , Units.INCHES), new Value(17    , Units.INCHES)),  // still used
	;
	
	Value height;
	Value width;
	
	PageSize(Value height, Value width) {
		this.height = height;
		this.width = width;
	}

	public Value getWidth() {
		return width;
	}

	public Value getHeight() {
		return height;
	}

}
