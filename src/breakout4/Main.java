package breakout4;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		String title = "BreakOut";
		JFrame p = new JFrame();
		Panel frame = new Panel();
		p.setBounds(10,10,800,700);
		p.setTitle(title);
		p.setResizable(false);
		p.setVisible(true);
		p.setLocationRelativeTo(null);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.add(frame);
		}

}
