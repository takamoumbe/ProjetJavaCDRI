package model;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

import javax.swing.text.StyleConstants.FontConstants;
import javax.swing.text.StyledEditorKit.FontFamilyAction;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.element.Table;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

public class PdfItext {
	
	public static void listeArticlePDF(String file) throws IOException
	{

		PdfWriter writer   = new PdfWriter(file);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document doc 	   = new Document(pdfDoc, PageSize.A4.rotate());
		doc.setMargins(20, 20, 20, 20);
		//PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
		PdfFont bold = PdfFontFactory.createFont();
		
		/*----- entete -----*/
		ImageData imageData = ImageDataFactory.create("images/logo.png");
		Image img = new Image(imageData); 
		doc.add(img);
		PdfFont myFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		Paragraph p1 = new Paragraph();
		p1.add("Liste des Articles");
		p1.setTextAlignment(TextAlignment.CENTER);
		p1.setFont(myFont);
		p1.setFontSize(20);  
		doc.add(p1);
		
		/*------- faire le tableau pour la liste ----*/
		Table table = new Table(new float[]{2, 6, 4, 4, 3, 3, 3});
		String entete = "Code;Article;Désignation;Quantité;Prix.U;Prix.T;Emplacement";
		
		table.setWidth(UnitValue.createPercentValue(100));
		process(table, entete, bold, true);
		
		String produit= "";
		Article article = new Article();
		Object[][] listeArt = article.liste_article();
		int taille  = listeArt.length;  
		int i = 0;
		while (i < taille) {
			produit = ""+listeArt[i][1]+";"+listeArt[i][2]+";"+listeArt[i][3]+";"+listeArt[i][4]+";"+listeArt[i][5]+";"+listeArt[i][6]+";"+listeArt[i][7];
			process(table, produit, bold, false);
			i++;
		}
		
		doc.add(table);
		doc.close();
	}
	
	
	public static void process(Table table, String line, PdfFont font, boolean isHeader) {

	    StringTokenizer tokenizer = new StringTokenizer(line, ";");

	    while (tokenizer.hasMoreTokens()) {

	        if (isHeader) {

	            table.addHeaderCell(

	                new Cell().add(

	                    new Paragraph(tokenizer.nextToken()).setFont(font).setTextAlignment(TextAlignment.CENTER)));

	        } else {

	            table.addCell(

	              new Cell().add(
	                   new Paragraph(tokenizer.nextToken()).setFont(font)));  
	        }

	    }

	}
}
