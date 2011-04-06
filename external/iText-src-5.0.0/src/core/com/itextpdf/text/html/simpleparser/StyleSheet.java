/*
 * $Id: StyleSheet.java,v 1.1 2010/01/02 22:57:17 bfox Exp $
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
package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.html.Markup;
import java.util.HashMap;

public class StyleSheet {

	public HashMap classMap = new HashMap();

	public HashMap tagMap = new HashMap();

	/** Creates a new instance of StyleSheet */
	public StyleSheet() {
	}

	public void applyStyle(String tag, HashMap props) {
		HashMap map = (HashMap) tagMap.get(tag.toLowerCase());
		if (map != null) {
			HashMap temp = new HashMap(map);
			temp.putAll(props);
			props.putAll(temp);
		}
		String cm = (String) props.get(Markup.HTML_ATTR_CSS_CLASS);
		if (cm == null)
			return;
		map = (HashMap) classMap.get(cm.toLowerCase());
		if (map == null)
			return;
		props.remove(Markup.HTML_ATTR_CSS_CLASS);
		HashMap temp = new HashMap(map);
		temp.putAll(props);
		props.putAll(temp);
	}

	public void loadStyle(String style, HashMap props) {
		classMap.put(style.toLowerCase(), props);
	}

	public void loadStyle(String style, String key, String value) {
		style = style.toLowerCase();
		HashMap props = (HashMap) classMap.get(style);
		if (props == null) {
			props = new HashMap();
			classMap.put(style, props);
		}
		props.put(key, value);
	}

	public void loadTagStyle(String tag, HashMap props) {
		tagMap.put(tag.toLowerCase(), props);
	}

	public void loadTagStyle(String tag, String key, String value) {
		tag = tag.toLowerCase();
		HashMap props = (HashMap) tagMap.get(tag);
		if (props == null) {
			props = new HashMap();
			tagMap.put(tag, props);
		}
		props.put(key, value);
	}

}