package com.seefoxrun.visualization;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfLayer;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;
import com.seefoxrun.visualization.medium.Border;
import com.seefoxrun.visualization.medium.Page;
import com.seefoxrun.visualization.medium.PageSize;

public class Document implements Closeable {

	private boolean opened = false;

	private String filename;
	private Page page;
	
	private com.itextpdf.text.Document document; 
	private PdfWriter writer;
	private PdfContentByte cb;
	private PdfTemplate tp;
	private Graphics2D g2;
	private RectRegion userRegion;
	private HashMap<String,PdfLayer> namedLayers;
	private boolean layerBegun = false;
	private Graphics2D layerG2;

	private double writeableHeight;
	private double writeableWidth;
	private double pageWidth;
	private double pageHeight;

	private PdfTemplate layerTP;
	
	public Document(String filename) throws FileNotFoundException, DocumentException {
		this(filename, new Page(PageSize.LETTER, new Border()));
	}

	
	public Document(String filename, Page page) throws FileNotFoundException, DocumentException {
		this.filename = filename;
		this.page = new Page(page);

		Point ll = new Point(page.getBorderLeft(), page.getBorderBottom());
		Point ur = new Point(
				page.getWidth().sub(page.getBorderRight()), 
				page.getHeight().sub(page.getBorderTop())
		);
		userRegion = new RectRegion(ll, ur);
		namedLayers = new HashMap<String,PdfLayer>();

		open();
	}

	public void open() throws FileNotFoundException, DocumentException {
		pageHeight = page.getHeight().convertTo(Units.POINTS);
		pageWidth  = page.getWidth().convertTo(Units.POINTS);

		double bLeft   = page.getBorderLeft().convertTo(Units.POINTS);
		double bRight  = page.getBorderRight().convertTo(Units.POINTS);
		double bTop    = page.getBorderTop().convertTo(Units.POINTS);
		double bBottom = page.getBorderBottom().convertTo(Units.POINTS);
		
		if (opened)
			throw new RuntimeException("Document already open.");
		writeableWidth  = pageWidth  - (bLeft + bRight); 
		writeableHeight = pageHeight - (bTop  + bBottom);

		document = new com.itextpdf.text.Document(new Rectangle((int)pageWidth, (int)pageHeight));
		writer   = PdfWriter.getInstance(document, new FileOutputStream(filename));
		
		document.open();
		cb = writer.getDirectContent();
		tp = cb.createTemplate((float)pageWidth,(float)pageHeight);
		g2 = tp.createGraphics((float)writeableWidth,(float)writeableHeight, new DefaultFontMapper());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        opened = true;
	}

	public PdfContentByte getPdfContentByte() {
		return cb;
	}

	public Graphics2D getGraphics2D() {
		return g2;
	}

	
	public void addLayer(String layerName) throws IOException {
		PdfLayer layer = new PdfLayer(layerName, writer);
		namedLayers.put(layerName,layer);
	}

	public Graphics2D switchLayer(String layerName) {
		PdfLayer layer = namedLayers.get(layerName);
		if (layerBegun) {
			
			double left = page.getBorderLeft().convertTo(Units.POINTS);
			double bottom = page.getBorderBottom().convertTo(Units.POINTS);

			layerG2.dispose();
			cb.addTemplate(layerTP, (int)left, (int)bottom); 
			cb.endLayer();
			layerBegun = false;
		}
		
		cb.beginLayer(layer);
		layerTP = cb.createTemplate((float)pageWidth,(float)pageHeight);
		layerG2 = layerTP.createGraphics((float)writeableWidth,(float)writeableHeight, new DefaultFontMapper());
		layerG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		layerBegun = true;
		return layerG2;
	}

	
	@Override
	public void close() {
		if (!opened)
			throw new RuntimeException("Document not open.");

		double left = page.getBorderLeft().convertTo(Units.POINTS);
		double bottom = page.getBorderBottom().convertTo(Units.POINTS);

		if (layerBegun) {
			layerG2.dispose();
			cb.addTemplate(layerTP, (int)left, (int)bottom); 
			cb.endLayer();
			layerBegun = false;
		}

		g2.dispose();
		cb.addTemplate(tp, (int)left, (int)bottom); 
		document.close();
		opened = false;
	}


	public Point center() {
		Value x = userRegion.width().div(2);
		Value y = userRegion.height().div(2);
		return new Point(x,y);
	}
	

	public Value width() {
		return userRegion.width();
	}

	public Value height() {
		return userRegion.height();
	}
}
