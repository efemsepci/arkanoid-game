import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ArkanoidGame extends JPanel implements KeyListener,ActionListener{
	
	//paddle variables
	private int paddleX = 310;
	private int dirPaddle = 8;
	private Icon paddle;
	private JLabel lblPaddle;
	
	//ball variables
	private int ballX = 350;
	private int ballY = 300;
	private int xDirBall = 2;
	private int yDirBall = 1;
	private Icon ball;
	private JLabel lblBall;
	
	//bricks variables
	private int brickX = 100;
	private int brickY = 50;
	private int brickWidth = 50;
	private int brickHeight = 30;
	private ArrayList<Integer> xCoordinates = new ArrayList<Integer>();
	private ArrayList<Integer> yCoordinates = new ArrayList<Integer>();
	private ArrayList<JLabel> bricks = new ArrayList<JLabel>();
	private int numberOfBricks = 24;
	private Icon brick;
	private JLabel lblBrick;
	
	private int lives = 3;
	private JLabel livesLbl;
	private int level = 1;
	private JLabel levelLbl;
	private int score = 0;
	private JLabel scoreLbl;
	private String nickName;
	private String date;
	private boolean playable = false;
	private Timer timer;
	
	
	ArkanoidGame(){
		this.setLayout(null);
		this.creatingStuffs();
		this.setBackground(Color.black);
		timer = new Timer(8,this);
		timer.start();
		
		
	}
	
	public void creatingStuffs() {
		paddle = new ImageIcon(getClass().getResource("paddle.jpg"));
		lblPaddle = new JLabel(paddle);
		lblPaddle.setBounds(paddleX,550,100,20);
		this.add(lblPaddle);
		
		ball = new ImageIcon(getClass().getResource("ball.JPG"));
		lblBall = new JLabel(ball);
		lblBall.setBounds(ballX,ballY,20,20);
		this.add(lblBall);
		
		int count = 0;
		for(int i = 0; i < numberOfBricks; i++) {
			brick = new ImageIcon(getClass().getResource("box.JPG"));
			lblBrick = new JLabel(brick);
			lblBrick.setBounds(brickX,brickY,brickWidth,brickHeight);
			this.add(lblBrick);
			bricks.add(lblBrick);
			xCoordinates.add(brickX);
			yCoordinates.add(brickY);
			brickX += 60;
			count++;
			if(count == 8) {
				brickX = 100;
				brickY += 40;
				count = 0;
			}
			
		}
		
		levelLbl = new JLabel();
		levelLbl.setBounds(10,5,100,50);
		levelLbl.setForeground(Color.red);
		this.add(levelLbl);
		
		scoreLbl = new JLabel("Score: " + score);
		scoreLbl.setBounds(310,5,100,50);
		scoreLbl.setForeground(Color.red);
		this.add(scoreLbl);
		
		livesLbl = new JLabel("Lives: " + lives);
		livesLbl.setBounds(620,5,100,50);
		livesLbl.setForeground(Color.red);
		this.add(livesLbl);
		
	}
	

	@Override
	public void keyTyped(KeyEvent e) {} //we won't use it

	@Override
	public void keyPressed(KeyEvent e) {
		String key=KeyEvent.getKeyText(e.getKeyCode());
		
		if(key.compareTo("Left") == 0) {
			if(isAllowed(paddleX-dirPaddle,550)) {
				moveLeft();
			}
		}
		else if(key.compareTo("Right") == 0) {
			if(isAllowed(paddleX+dirPaddle,550)) {
				moveRight();
			}
		}
		
		else if((e.getKeyCode() == KeyEvent.VK_Q) && (e.isControlDown())) {
			JOptionPane.showMessageDialog(null, "Good Bye!", "Exit", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}//we won't use it

	@Override
	public void actionPerformed(ActionEvent e) {
		
		levelLbl.setText("Level: " + getLevel());
		
		timer.start();
		
		if(playable) {
			
			for(int i = 0; i < bricks.size(); i++) {
				int x = xCoordinates.get(i);
				int y = yCoordinates.get(i);
				JLabel tmpLbl = bricks.get(i);
				
				if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(x, y, brickWidth, brickHeight))){
					yDirBall = -yDirBall;
					tmpLbl.setBounds(x,y,0,0);
					xCoordinates.set(i,1000);
					yCoordinates.set(i,1000);
					updateScore();
					numberOfBricks--;
				}
			}
			
			if(ballX <= 0) {
				xDirBall = -xDirBall;
			}
			else if(ballX >= 670) {
				xDirBall = -xDirBall;
			}
			else if(ballY >= 550) {
				updateLives();
				if(lives > 0) {
					restart();
				}
				else {
					playable = false;
					JOptionPane.showMessageDialog(null, "Score: " + score, "GAME OVER!", JOptionPane.PLAIN_MESSAGE );
					nickName = JOptionPane.showInputDialog("Nickname: ");
					date = JOptionPane.showInputDialog("Date: ");
					
					FileWriter scores;
					
					try {
					      scores = new FileWriter("scores.txt",true);
					      scores.write(this.toString());
					      scores.close();
					    } catch (IOException exception) {
					      // TODO Auto-generated catch block    	
					      exception.printStackTrace();
					    }
					
				}
			}
			else if(ballY <= 0) {
				yDirBall = -yDirBall;
			}
			else if(new Rectangle(ballX, ballY,20,20).intersects(new Rectangle(paddleX,550,100,20))) {
				yDirBall = -yDirBall;
			}
			else if(numberOfBricks <= 0) {
				playable = false;
				JOptionPane.showMessageDialog(null, "Score: " + score, "GAME OVER!, YOU WIN!", JOptionPane.PLAIN_MESSAGE );
				nickName = JOptionPane.showInputDialog("Nickname: ");
				date = JOptionPane.showInputDialog("Date: ");
				
				FileWriter scores;
				
				try {
				      scores = new FileWriter("scores.txt",true);
				      scores.write(this.toString());
				      scores.close();
				    } catch (IOException exception) {
				      // TODO Auto-generated catch block    	
				      exception.printStackTrace();
				    }
				
			}
			ballX += xDirBall;
			ballY += yDirBall;
		}
		
		moveBall(ballX,ballY);
	}
	
	public void moveLeft() {
		paddleX -= dirPaddle;
		movePaddle(paddleX);
	}
	
	public void moveRight() {
		paddleX += dirPaddle;
		movePaddle(paddleX);
	}
	
	public void movePaddle(int newX) {
		lblPaddle.setBounds(newX,550,100,20);
	}
	
	public void moveBall(int newX, int newY) {
		lblBall.setBounds(newX,newY,20,20);
	}
	
	
	
	public boolean isAllowed(int x, int y) {
		if(x >= 590 || x <= 0) {
			return false;
		}
		else if(y >= 600 || y <= 0) {
			return false;
		}
		return true;
	}
	
	public void restart() {
		ballX = 350;
		ballY = 300;
		moveBall(ballX,ballY);
	}
	
	public void setLevel(int x) {
		this.level = x;
	}
	
	public void setXDirBall(int x) {
		this.xDirBall = x;
	}
	
	public void setYDirBall(int x) {
		this.yDirBall = x;
	}
	
	public void setPlayable(boolean x) {
		this.playable = x;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getXDirBall() {
		return xDirBall;
	}
	
	public void setDifficulties() {
		if(getLevel() == 1) {
			setXDirBall(2);
		}
		else if(getLevel() == 2) {
			setXDirBall(3);
		}
		else if(getLevel() == 3) {
			setXDirBall(4);
			setYDirBall(2);
		}
	}
	
	public void updateScore() {
		score += 10;
		scoreLbl.setText("Score: " + score);
	}
	
	public void updateLives() {
		lives--;
		livesLbl.setText("Lives: " + lives);
	}
	
	public String toString() {
		return nickName + ": " + score + " (Level: " + getLevel() + ")" + " " + date + "\n";
	}

}
