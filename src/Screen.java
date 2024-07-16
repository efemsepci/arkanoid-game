import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Screen {

	public static void main(String[] args) {
		
		ArkanoidGame game = new ArkanoidGame();
		
		JFrame menuScreen = new JFrame("Arkanoid Game");
		menuScreen.setLayout(null);
		menuScreen.setBounds(400,150,700,600);
		menuScreen.setResizable(false);
		menuScreen.setVisible(true);
		menuScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btn1 = new JButton("New Game");
		btn1.setBounds(280,50,120,50);
		menuScreen.add(btn1);
		JButton btn2 = new JButton("Options");
		btn2.setBounds(280,125,120,50);
		menuScreen.add(btn2);
		JButton btn3 = new JButton("Scores");
		btn3.setBounds(280,200,120,50);
		menuScreen.add(btn3);
		JButton btn4 = new JButton("Help");
		btn4.setBounds(280,275,120,50);
		menuScreen.add(btn4);
		JButton btn5 = new JButton("About");
		btn5.setBounds(280,350,120,50);
		menuScreen.add(btn5);
		JButton btn6 = new JButton("Exit");
		btn6.setBounds(280,425,120,50);
		menuScreen.add(btn6);
		
		JPanel background = new JPanel();
		background.setLayout(null);
		background.setBounds(0,0,700,600);
		background.setBackground(Color.DARK_GRAY);
		menuScreen.add(background);
		
		
		
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame gameScreen = new JFrame();
				gameScreen.setBounds(400,150,700,600);
				gameScreen.setTitle("Arkanoid Game");
				gameScreen.setResizable(false);
				gameScreen.addKeyListener(game);
				gameScreen.setVisible(true);
				game.setPlayable(true);
				gameScreen.add(game);
				
			}
			
		});
		
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] levels = {"1","2","3"};
				int result = JOptionPane.showOptionDialog(null, "Select level(change ball speed)", "Options",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, levels, null);
				
				if(result == JOptionPane.YES_OPTION) {
					game.setLevel(1);
					game.setDifficulties();
					//System.out.println(game.getLevel());
					//System.out.println(game.getXDirBall());
				}
				else if(result == JOptionPane.NO_OPTION) {
					game.setLevel(2);
					game.setDifficulties();
					//System.out.println(game.getLevel());
					//System.out.println(game.getXDirBall());
				}
				else if(result == JOptionPane.CANCEL_OPTION) {
					game.setLevel(3);
					game.setDifficulties();
					//System.out.println(game.getLevel());
					//System.out.println(game.getXDirBall());
				}
				
			}
			
		});
		
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileReader scores;
				
				try {
					scores = new FileReader("scores.txt");
					Scanner sc = new Scanner(scores);
					while(sc.hasNextLine()) {
						JOptionPane.showMessageDialog(null, sc.nextLine(), "Scores", JOptionPane.PLAIN_MESSAGE );
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Click 'New Game' for play the game\n"
						+ "Click 'Options' for select level\n"
						+ "Click 'Scores' to see scores\n"
						+ "Click 'About' to get information about game\n"
						+ "Click 'Exit' for close the game.";
				JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.PLAIN_MESSAGE);
				JOptionPane.showMessageDialog(null, "Each player has 3 chances\nand players get 10 point for one brick.", "About", JOptionPane.PLAIN_MESSAGE);
				
			}
			
		});
		
		btn5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String message = "This game made by Efe Mert Þepçi(20200702021)\n"
						+ "For Term Project CSE212\n"
						+ "Contact me via efemert.sepci@std.yeditepe.edu.tr";
				JOptionPane.showMessageDialog(null, message, "About", JOptionPane.PLAIN_MESSAGE);
				
			}
			
		});
		
		btn6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Good Bye!", "Exit", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
				
			}
			
		});
		

	}

}
