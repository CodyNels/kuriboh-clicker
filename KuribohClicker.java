import javax.swing.JFrame;


/*
 * Ugrade Ideas
 * Multiply (current score one time) - Multiply
 * Loser price of all upgrades - Cost Down
*/

public class KuribohClicker {
    public static void main(String args[]){
		JFrame frame = new JFrame("Kuriboh Clicker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new KuribohClickerPanel(850,900));
		frame.pack();
		frame.setVisible(true);
	}
}
