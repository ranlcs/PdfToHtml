

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import java.awt.Color;

public class HtmlBuilder{
	private String emplacement="";
	private String texte="";
	private FileWriter htmlFile;
	private String titre="";

	public HtmlBuilder(String emplacement){
		setEmplacement(emplacement);
		build();
	}
	public HtmlBuilder(){
	}

	public void setEmplacement(String empl){
		emplacement = empl;
		String resultFile = empl.substring(0,empl.lastIndexOf("."));
		titre = empl.substring(empl.lastIndexOf(File.separator)+1,empl.lastIndexOf("."));
		try{
			htmlFile = new FileWriter(new File(resultFile+".html"));
		}catch(Exception e){
			System.out.println("une erreur s'est produite");
		}
	}

	public String renderPage(String page){
		String finalPage = page.substring(page.indexOf("\n"));
		finalPage = finalPage.replace("&","&amp;");
		finalPage = finalPage.replace("<","&lt;");
		finalPage = finalPage.replace(">","&gt;");
		finalPage = finalPage.replace("é","&eacute;");
		finalPage = finalPage.replace("è","&egrave;");
		finalPage = finalPage.replace("ç","&ccddil;");
		finalPage = finalPage.replace("\n","</br>");
		finalPage = finalPage.replace(" ","&nbsp;");
		finalPage = finalPage.replace("\t","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		//finalPage = ToHtml.color(finalPage,new Color(67,87,64));
		finalPage+="</br></br>";
		return finalPage;
	}

	public void head(String titre){
		texte=texte+"<!DOCTYPE html><html><head><title>"+titre+"</title></head><body>";//sans meta charset=utf-8 car l'encodage est correcte
	}
	public void foot(){
		texte+="</body></html>";
	}

	public void body(){
		try{
			PdfReader pdf = new PdfReader(emplacement);
			int nbPages = pdf.getNumberOfPages();
			for(int i=1;i<=nbPages;i++){
				String pageTemp = renderPage(PdfTextExtractor.getTextFromPage(pdf,i));
				texte+=pageTemp;
			}
			pdf.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	public void build(String emplacement){
		try{
			head(titre);
			body();
			foot();
			htmlFile.write(texte);
			htmlFile.close();
			System.out.println("La page a ete genere");
		}catch(Exception e){
			System.out.println("une erreur s'est produite.");
		}
		
	}
	public void build(){
		build(emplacement);
	}
}