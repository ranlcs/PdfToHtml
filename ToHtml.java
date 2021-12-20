import java.awt.Color;


public class ToHtml{
	private static String[] HEXA={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	private ToHtml(){}

	private static String balise(String balise,String attributes, String texte){
		return "<"+balise+ " " +attributes+">"+texte+"</"+balise+">";
	}
	private static String balise(String balise,String texte){
		return balise(balise,"",texte);
	}
	private static String hn(String texte,int n){
		return balise("h"+String.valueOf(n),texte);
	}
	private static String intToStringHexa(int nb){
		int second = nb%16;
		int first = (int)((nb-second)/16);
		return HEXA[first]+HEXA[second];
	}
	private static String colorToString(Color c){

		String r = intToStringHexa(c.getRed());
		String g = intToStringHexa(c.getGreen());
		String b = intToStringHexa(c.getBlue());
		return "#"+r+g+b;
	}

	public static String h1(String texte){
		return hn(texte,1);
	}
	public static String h2(String texte){
		return hn(texte,2);
	}
	public static String h3(String texte){
		return hn(texte,3);
	}
	public static String h4(String texte){
		return hn(texte,4);
	}
	public static String h5(String texte){
		return hn(texte,5);
	}
	public static String h6(String texte){
		return hn(texte,6);
	}

	public static String bold(String texte){
		return balise("b",texte);
	}
	public static String italic(String texte){
		return balise("i",texte);
	}

	public static String color(String texte,Color col){
		return balise("span","style='color:"+colorToString(col)+"'",texte);
	}

}