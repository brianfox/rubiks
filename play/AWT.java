import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class AWT {
	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();	
		for (Font f : fonts)
				System.out.println(f);
	
		Font myFont = new Font("Serif", Font.ITALIC, 12);
		System.out.println(myFont);
	}
}
