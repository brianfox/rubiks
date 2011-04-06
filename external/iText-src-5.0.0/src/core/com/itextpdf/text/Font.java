/*
 * $Id: Font.java,v 1.1 2010/01/02 22:57:11 bfox Exp $
 *
 * This file is part of the iText project.
 * Copyright (c) 1998-2009 1T3XT BVBA
 * Authors: Bruno Lowagie, Paulo Soares, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY 1T3XT,
 * 1T3XT DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA, 02110-1301 USA, or download the license from the following URL:
 * http://itextpdf.com/terms-of-use/
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License,
 * you must retain the producer line in every PDF that is created or manipulated
 * using iText.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the iText software without
 * disclosing the source code of your own applications.
 * These activities include: offering paid services to customers as an ASP,
 * serving PDFs on the fly in a web application, shipping iText with a closed
 * source product.
 *
 * For more information, please contact iText Software Corp. at this
 * address: sales@itextpdf.com
 */
package com.itextpdf.text;

import com.itextpdf.text.html.Markup;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.BaseColor;

/**
 * Contains all the specifications of a font: fontfamily, size, style and color.
 * <P>
 * Example: <BLOCKQUOTE>
 * 
 * <PRE>
 * 
 * Paragraph p = new Paragraph("This is a paragraph", <STRONG>new
 * Font(Font.HELVETICA, 18, Font.BOLDITALIC, new BaseColor(0, 0, 255)) </STRONG>);
 * 
 * </PRE>
 * 
 * </BLOCKQUOTE>
 */

public class Font implements Comparable {

	// static membervariables for the different families

	/** a possible value of a font family. */
	public static final int COURIER = 0;

	/** a possible value of a font family. */
	public static final int HELVETICA = 1;

	/** a possible value of a font family. */
	public static final int TIMES_ROMAN = 2;

	/** a possible value of a font family. */
	public static final int SYMBOL = 3;

	/** a possible value of a font family. */
	public static final int ZAPFDINGBATS = 4;

	// static membervariables for the different styles

	/** this is a possible style. */
	public static final int NORMAL = 0;

	/** this is a possible style. */
	public static final int BOLD = 1;

	/** this is a possible style. */
	public static final int ITALIC = 2;

	/** this is a possible style. */
	public static final int UNDERLINE = 4;

	/** this is a possible style. */
	public static final int STRIKETHRU = 8;

	/** this is a possible style. */
	public static final int BOLDITALIC = BOLD | ITALIC;

	// static membervariables

	/** the value of an undefined attribute. */
	public static final int UNDEFINED = -1;

	/** the value of the default size. */
	public static final int DEFAULTSIZE = 12;

	// membervariables

	/** the value of the fontfamily. */
	private int family = UNDEFINED;

	/** the value of the fontsize. */
	private float size = UNDEFINED;

	/** the value of the style. */
	private int style = UNDEFINED;

	/** the value of the color. */
	private BaseColor color = null;

	/** the external font */
	private BaseFont baseFont = null;

	// constructors

	/**
	 * Copy constructor of a Font
	 * 
	 * @param other
	 *            the font that has to be copied
	 */
	public Font(Font other) {
		this.family = other.family;
		this.size = other.size;
		this.style = other.style;
		this.color = other.color;
		this.baseFont = other.baseFont;
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param family
	 *            the family to which this font belongs
	 * @param size
	 *            the size of this font
	 * @param style
	 *            the style of this font
	 * @param color
	 *            the <CODE>BaseColor</CODE> of this font.
	 */

	public Font(int family, float size, int style, BaseColor color) {
		this.family = family;
		this.size = size;
		this.style = style;
		this.color = color;
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param bf
	 *            the external font
	 * @param size
	 *            the size of this font
	 * @param style
	 *            the style of this font
	 * @param color
	 *            the <CODE>BaseColor</CODE> of this font.
	 */

	public Font(BaseFont bf, float size, int style, BaseColor color) {
		this.baseFont = bf;
		this.size = size;
		this.style = style;
		this.color = color;
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param bf
	 *            the external font
	 * @param size
	 *            the size of this font
	 * @param style
	 *            the style of this font
	 */
	public Font(BaseFont bf, float size, int style) {
		this(bf, size, style, null);
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param bf
	 *            the external font
	 * @param size
	 *            the size of this font
	 */
	public Font(BaseFont bf, float size) {
		this(bf, size, UNDEFINED, null);
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param bf
	 *            the external font
	 */
	public Font(BaseFont bf) {
		this(bf, UNDEFINED, UNDEFINED, null);
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param family
	 *            the family to which this font belongs
	 * @param size
	 *            the size of this font
	 * @param style
	 *            the style of this font
	 */

	public Font(int family, float size, int style) {
		this(family, size, style, null);
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param family
	 *            the family to which this font belongs
	 * @param size
	 *            the size of this font
	 */

	public Font(int family, float size) {
		this(family, size, UNDEFINED, null);
	}

	/**
	 * Constructs a Font.
	 * 
	 * @param family
	 *            the family to which this font belongs
	 */

	public Font(int family) {
		this(family, UNDEFINED, UNDEFINED, null);
	}

	/**
	 * Constructs a Font.
	 */

	public Font() {
		this(UNDEFINED, UNDEFINED, UNDEFINED, null);
	}

	// implementation of the Comparable interface

	/**
	 * Compares this <CODE>Font</CODE> with another
	 * 
	 * @param object
	 *            the other <CODE>Font</CODE>
	 * @return a value
	 */
	public int compareTo(Object object) {
		if (object == null) {
			return -1;
		}
		Font font;
		try {
			font = (Font) object;
			if (baseFont != null && !baseFont.equals(font.getBaseFont())) {
				return -2;
			}
			if (this.family != font.getFamily()) {
				return 1;
			}
			if (this.size != font.getSize()) {
				return 2;
			}
			if (this.style != font.getStyle()) {
				return 3;
			}
			if (this.color == null) {
				if (font.color == null) {
					return 0;
				}
				return 4;
			}
			if (font.color == null) {
				return 4;
			}
			if (this.color.equals(font.getColor())) {
				return 0;
			}
			return 4;
		} catch (ClassCastException cce) {
			return -3;
		}
	}

	// FAMILY

	/**
	 * Gets the family of this font.
	 * 
	 * @return the value of the family
	 */
	public int getFamily() {
		return family;
	}

	/**
	 * Gets the familyname as a String.
	 * 
	 * @return the familyname
	 */
	public String getFamilyname() {
		String tmp = "unknown";
		switch (getFamily()) {
		case Font.COURIER:
			return FontFactory.COURIER;
		case Font.HELVETICA:
			return FontFactory.HELVETICA;
		case Font.TIMES_ROMAN:
			return FontFactory.TIMES_ROMAN;
		case Font.SYMBOL:
			return FontFactory.SYMBOL;
		case Font.ZAPFDINGBATS:
			return FontFactory.ZAPFDINGBATS;
		default:
			if (baseFont != null) {
				String[][] names = baseFont.getFamilyFontName();
				for (int i = 0; i < names.length; i++) {
					if ("0".equals(names[i][2])) {
						return names[i][3];
					}
					if ("1033".equals(names[i][2])) {
						tmp = names[i][3];
					}
					if ("".equals(names[i][2])) {
						tmp = names[i][3];
					}
				}
			}
		}
		return tmp;
	}

	/**
	 * Sets the family using a <CODE>String</CODE> ("Courier", "Helvetica",
	 * "Times New Roman", "Symbol" or "ZapfDingbats").
	 * 
	 * @param family
	 *            A <CODE>String</CODE> representing a certain font-family.
	 */
	public void setFamily(String family) {
		this.family = getFamilyIndex(family);
	}

	/**
	 * Translates a <CODE>String</CODE> -value of a certain family into the
	 * index that is used for this family in this class.
	 * 
	 * @param family
	 *            A <CODE>String</CODE> representing a certain font-family
	 * @return the corresponding index
	 */
	public static int getFamilyIndex(String family) {
		if (family.equalsIgnoreCase(FontFactory.COURIER)) {
			return COURIER;
		}
		if (family.equalsIgnoreCase(FontFactory.HELVETICA)) {
			return HELVETICA;
		}
		if (family.equalsIgnoreCase(FontFactory.TIMES_ROMAN)) {
			return TIMES_ROMAN;
		}
		if (family.equalsIgnoreCase(FontFactory.SYMBOL)) {
			return SYMBOL;
		}
		if (family.equalsIgnoreCase(FontFactory.ZAPFDINGBATS)) {
			return ZAPFDINGBATS;
		}
		return UNDEFINED;
	}

	// SIZE
	
	/**
	 * Gets the size of this font.
	 * 
	 * @return a size
	 */
	public float getSize() {
		return size;
	}

	/**
	 * Gets the size that can be used with the calculated <CODE>BaseFont
	 * </CODE>.
	 * 
	 * @return the size that can be used with the calculated <CODE>BaseFont
	 *         </CODE>
	 */
	public float getCalculatedSize() {
		float s = this.size;
		if (s == UNDEFINED) {
			s = DEFAULTSIZE;
		}
		return s;
	}

	/**
	 * Gets the leading that can be used with this font.
	 * 
	 * @param linespacing
	 *            a certain linespacing
	 * @return the height of a line
	 */
	public float getCalculatedLeading(float linespacing) {
		return linespacing * getCalculatedSize();
	}

	/**
	 * Sets the size.
	 * 
	 * @param size
	 *            The new size of the font.
	 */
	public void setSize(float size) {
		this.size = size;
	}

	// STYLE
	
	/**
	 * Gets the style of this font.
	 * 
	 * @return a size
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * Gets the style that can be used with the calculated <CODE>BaseFont
	 * </CODE>.
	 * 
	 * @return the style that can be used with the calculated <CODE>BaseFont
	 *         </CODE>
	 */
	public int getCalculatedStyle() {
		int style = this.style;
		if (style == UNDEFINED) {
			style = NORMAL;
		}
		if (baseFont != null)
			return style;
		if (family == SYMBOL || family == ZAPFDINGBATS)
			return style;
		else
			return style & (~BOLDITALIC);
	}

	/**
	 * checks if this font is Bold.
	 * 
	 * @return a <CODE>boolean</CODE>
	 */
	public boolean isBold() {
		if (style == UNDEFINED) {
			return false;
		}
		return (style & BOLD) == BOLD;
	}

	/**
	 * checks if this font is italic.
	 * 
	 * @return a <CODE>boolean</CODE>
	 */
	public boolean isItalic() {
		if (style == UNDEFINED) {
			return false;
		}
		return (style & ITALIC) == ITALIC;
	}

	/**
	 * checks if this font is underlined.
	 * 
	 * @return a <CODE>boolean</CODE>
	 */
	public boolean isUnderlined() {
		if (style == UNDEFINED) {
			return false;
		}
		return (style & UNDERLINE) == UNDERLINE;
	}

	/**
	 * checks if the style of this font is STRIKETHRU.
	 * 
	 * @return a <CODE>boolean</CODE>
	 */
	public boolean isStrikethru() {
		if (style == UNDEFINED) {
			return false;
		}
		return (style & STRIKETHRU) == STRIKETHRU;
	}

	/**
	 * Sets the style.
	 * 
	 * @param style
	 *            the style.
	 */
	public void setStyle(int style) {
		this.style = style;
	}

	/**
	 * Sets the style using a <CODE>String</CODE> containing one of more of
	 * the following values: normal, bold, italic, underline, strike.
	 * 
	 * @param style
	 *            A <CODE>String</CODE> representing a certain style.
	 */
	public void setStyle(String style) {
		if (this.style == UNDEFINED)
			this.style = NORMAL;
		this.style |= getStyleValue(style);
	}

	/**
	 * Translates a <CODE>String</CODE> -value of a certain style into the
	 * index value is used for this style in this class.
	 * 
	 * @param style
	 *            A <CODE>String</CODE>
	 * @return the corresponding value
	 */
	public static int getStyleValue(String style) {
		int s = 0;
		if (style.indexOf(Markup.CSS_VALUE_NORMAL) != -1) {
			s |= NORMAL;
		}
		if (style.indexOf(Markup.CSS_VALUE_BOLD) != -1) {
			s |= BOLD;
		}
		if (style.indexOf(Markup.CSS_VALUE_ITALIC) != -1) {
			s |= ITALIC;
		}
		if (style.indexOf(Markup.CSS_VALUE_OBLIQUE) != -1) {
			s |= ITALIC;
		}
		if (style.indexOf(Markup.CSS_VALUE_UNDERLINE) != -1) {
			s |= UNDERLINE;
		}
		if (style.indexOf(Markup.CSS_VALUE_LINETHROUGH) != -1) {
			s |= STRIKETHRU;
		}
		return s;
	}

	// COLOR
	
	/**
	 * Gets the color of this font.
	 * 
	 * @return a color
	 */
	public BaseColor getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the new color of the font
	 */

	public void setColor(BaseColor color) {
		this.color = color;
	}

	/**
	 * Sets the color.
	 * 
	 * @param red
	 *            the red-value of the new color
	 * @param green
	 *            the green-value of the new color
	 * @param blue
	 *            the blue-value of the new color
	 */
	public void setColor(int red, int green, int blue) {
		this.color = new BaseColor(red, green, blue);
	}

	// BASEFONT

	/**
	 * Gets the <CODE>BaseFont</CODE> inside this object.
	 * 
	 * @return the <CODE>BaseFont</CODE>
	 */
	public BaseFont getBaseFont() {
		return baseFont;
	}

	/**
	 * Gets the <CODE>BaseFont</CODE> this class represents. For the built-in
	 * fonts a <CODE>BaseFont</CODE> is calculated.
	 * 
	 * @param specialEncoding
	 *            <CODE>true</CODE> to use the special encoding for Symbol and
	 *            ZapfDingbats, <CODE>false</CODE> to always use <CODE>Cp1252
	 *            </CODE>
	 * @return the <CODE>BaseFont</CODE> this class represents
	 */
	public BaseFont getCalculatedBaseFont(boolean specialEncoding) {
		if (baseFont != null)
			return baseFont;
		int style = this.style;
		if (style == UNDEFINED) {
			style = NORMAL;
		}
		String fontName = BaseFont.HELVETICA;
		String encoding = BaseFont.WINANSI;
		BaseFont cfont = null;
		switch (family) {
		case COURIER:
			switch (style & BOLDITALIC) {
			case BOLD:
				fontName = BaseFont.COURIER_BOLD;
				break;
			case ITALIC:
				fontName = BaseFont.COURIER_OBLIQUE;
				break;
			case BOLDITALIC:
				fontName = BaseFont.COURIER_BOLDOBLIQUE;
				break;
			default:
				//case NORMAL:
				fontName = BaseFont.COURIER;
				break;
			}
			break;
		case TIMES_ROMAN:
			switch (style & BOLDITALIC) {
			case BOLD:
				fontName = BaseFont.TIMES_BOLD;
				break;
			case ITALIC:
				fontName = BaseFont.TIMES_ITALIC;
				break;
			case BOLDITALIC:
				fontName = BaseFont.TIMES_BOLDITALIC;
				break;
			default:
			case NORMAL:
				fontName = BaseFont.TIMES_ROMAN;
				break;
			}
			break;
		case SYMBOL:
			fontName = BaseFont.SYMBOL;
			if (specialEncoding)
				encoding = BaseFont.SYMBOL;
			break;
		case ZAPFDINGBATS:
			fontName = BaseFont.ZAPFDINGBATS;
			if (specialEncoding)
				encoding = BaseFont.ZAPFDINGBATS;
			break;
		default:
		case Font.HELVETICA:
			switch (style & BOLDITALIC) {
			case BOLD:
				fontName = BaseFont.HELVETICA_BOLD;
				break;
			case ITALIC:
				fontName = BaseFont.HELVETICA_OBLIQUE;
				break;
			case BOLDITALIC:
				fontName = BaseFont.HELVETICA_BOLDOBLIQUE;
				break;
			default:
			case NORMAL:
				fontName = BaseFont.HELVETICA;
				break;
			}
			break;
		}
		try {
			cfont = BaseFont.createFont(fontName, encoding, false);
		} catch (Exception ee) {
			throw new ExceptionConverter(ee);
		}
		return cfont;
	}
	
	
	// Helper methods

	/**
	 * Checks if the properties of this font are undefined or null.
	 * <P>
	 * If so, the standard should be used.
	 * 
	 * @return a <CODE>boolean</CODE>
	 */
	public boolean isStandardFont() {
		return (family == UNDEFINED && size == UNDEFINED && style == UNDEFINED
				&& color == null && baseFont == null);
	}

	/**
	 * Replaces the attributes that are equal to <VAR>null</VAR> with the
	 * attributes of a given font.
	 * 
	 * @param font
	 *            the font of a bigger element class
	 * @return a <CODE>Font</CODE>
	 */
	public Font difference(Font font) {
		if (font == null) return this;
		// size
		float dSize = font.size;
		if (dSize == UNDEFINED) {
			dSize = this.size;
		}
		// style
		int dStyle = UNDEFINED;
		int style1 = this.style;
		int style2 = font.getStyle();
		if (style1 != UNDEFINED || style2 != UNDEFINED) {
			if (style1 == UNDEFINED)
				style1 = 0;
			if (style2 == UNDEFINED)
				style2 = 0;
			dStyle = style1 | style2;
		}
		// color
		BaseColor dColor = font.color;
		if (dColor == null) {
			dColor = this.color;
		}
		// family
		if (font.baseFont != null) {
			return new Font(font.baseFont, dSize, dStyle, dColor);
		}
		if (font.getFamily() != UNDEFINED) {
			return new Font(font.family, dSize, dStyle, dColor);
		}
		if (this.baseFont != null) {
			if (dStyle == style1) {
				return new Font(this.baseFont, dSize, dStyle, dColor);
			} else {
				return FontFactory.getFont(this.getFamilyname(), dSize, dStyle,
						dColor);
			}
		}
		return new Font(this.family, dSize, dStyle, dColor);
	}

}