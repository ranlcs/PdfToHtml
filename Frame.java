import javax.swing.JFrame;

import javax.swing.JFileChooser;
import java.io.File;

public class Frame extends JFrame{
	public Frame(){
		setLocation(0,0);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
		new HtmlBuilder(chooseFile());
		System.exit(0);
	}

	public String chooseFile(){
		JFileChooser fileChooser = new JFileChooser();
		String emplacement ="";
		fileChooser.setCurrentDirectory(new File("D:/Documents"));
		int returnVal = fileChooser.showDialog(null,"OK");
		add(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			emplacement = fileChooser.getSelectedFile().toString();
		}
		return emplacement;
	}

	public static void main(String[] arg){
		new Frame();
	}
}
