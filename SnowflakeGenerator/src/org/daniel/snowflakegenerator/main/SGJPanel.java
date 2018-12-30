package org.daniel.snowflakegenerator.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.daniel.snowflakegenerator.objects.SGParticle;

public class SGJPanel extends JPanel {
	
	SGParticle current, placeHolder = new SGParticle(-10000, -10000, -10000, -10000);
	ArrayList<SGParticle> snowflake;
	
	private boolean running = false, initial = true;
	
	public static JFrame frame = new JFrame();
	
	public static void main(String[] args) {
		new SGJPanel();
	}
	
	private SGJPanel() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setContentPane(this);
		frame.setVisible(true);
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					running = !running;
					if(initial == true) initial = false;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
		
		});
		
		this.setBackground(new Color(0.3f, 0.3f, 0.75f));
		this.snowflake = new ArrayList<SGParticle>();
		this.current = new SGParticle(frame.getWidth() / 2, 0, 10, 0);
		
		long now = System.nanoTime(), lastLoop = now, loopNanos = 1000000000 / 60;
		
		while(true) {
			now = System.nanoTime();
			
			if(now - lastLoop >= loopNanos) {
				this.repaint();
				lastLoop = now;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(SGParticle particle : this.snowflake) particle.render(g);
		if(this.running) {
			this.current.render(g);
			for(int i = 0; i < 24; i++) {
				this.current.update();
				if(this.snowflake.size() == 0 && this.current.getFinished(placeHolder.hitbox)) {
					snowflake.add(current);
					this.current = new SGParticle(frame.getWidth() / 2, 0, 10, 0);
				} else for(SGParticle particle : this.snowflake) 
					if(this.current.getFinished(particle.hitbox)) {
						snowflake.add(current);
						this.current = new SGParticle(frame.getWidth() / 2, 0, 10, 0);
						return;
					}
			}
		}
		if(this.initial) {
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			g.drawString("Press [SPACE] to start and stop", frame.getWidth() / 2 - g.getFontMetrics().stringWidth("Press [SPACE] to start and stop") / 2 , frame.getHeight() / 2);
		}
	}
	
}
