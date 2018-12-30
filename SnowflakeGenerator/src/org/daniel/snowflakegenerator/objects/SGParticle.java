package org.daniel.snowflakegenerator.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.daniel.snowflakegenerator.main.SGJPanel;

public class SGParticle {
	
	public Rectangle hitbox;
	float stopingPoint;
	final float upperBound = 4f;
	
	public SGParticle(int x, int y, int width, float stop) {
		this.hitbox = new Rectangle(width, width);
		this.hitbox.x = x;
		this.hitbox.y = y;
		this.stopingPoint = stop;
	}
	
	public void update() {
		this.hitbox.x -= 1;
		if(Math.random() <= 0.5) this.hitbox.y += 1;
		else this.hitbox.y -= 1;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		for(Rectangle rotatedRectangle : this.getRotatedRects()) g.fillRect((int) rotatedRectangle.x, (int) rotatedRectangle.y, (int) rotatedRectangle.width, (int) rotatedRectangle.width);
	}
	
	public boolean getFinished(Rectangle p) {
		return this.overlaps(this.hitbox, p) || this.hitbox.x <= this.stopingPoint;
	}
	
	private boolean overlaps(Rectangle a, Rectangle b) {
	    return b.x < a.x + a.width && b.x + b.width > a.x && b.y < a.y + a.height && b.y + b.height > a.y;
	}
	
	public Rectangle[] getRotatedRects() {
		Rectangle[] rects = new Rectangle[6];
		for(int i = 0; i < 6; i++) {
			rects[i] = this.rotatedRectangle((float) (i * (Math.PI / 3)));
			rects[i].width = this.hitbox.width;
			rects[i].height = this.hitbox.width;
		}
		return rects;
	}
	
	private Rectangle rotatedRectangle(float theta) {
		Rectangle returnVal = new Rectangle(this.hitbox.width, this.hitbox.width);
		returnVal.x = (int) Math.round((this.hitbox.x * Math.cos(theta)) - (this.hitbox.y * Math.sin(theta)) + SGJPanel.frame.getWidth() / 2);
		returnVal.y = (int) Math.round((this.hitbox.y * Math.cos(theta)) + (this.hitbox.x * Math.sin(theta)) + SGJPanel.frame.getHeight() / 2);
		return returnVal;
	}
	
}
