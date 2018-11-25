/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amir.pokemon.object;

import com.amir.pokemon.framework.GameObject;
import com.amir.pokemon.framework.ObjectId;
import com.amir.pokemon.window.Handler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author amir
 */
public class Pointer extends GameObject {

    private float width = 48, height = 16;
    private int pos = -1;

    private Handler handler;

    public Pointer(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
    }

    public int getPos() {
        return pos;
    }

    public void tick(LinkedList<GameObject> object) {
        x += dx;
        y += dy;
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    pos = 1;
                }else if (getBoundsTop().intersects(tempObject.getBounds())) {
                    pos = -1;
                }
                
//                if (((!getBounds().intersects(tempObject.getBounds())) && (!getBoundsTop().intersects(tempObject.getBounds())))) {
//                    pos = 0;
//                }
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    dx = 0;
                    x = tempObject.getX() - width+1;
                    
                }
            }
            
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
//        g2d.draw(getBounds());
//        g2d.draw(getBoundsRight());
//        g2d.draw(getBoundsLeft());
//        g2d.draw(getBoundsTop());

    }

    @Override
    public Rectangle getBounds() { //bottom
        return new Rectangle((int) x + 2, (int) (y + 2 + (height / 2)), (int) width - 4, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (x - 2 + (width)), (int) y + 2, (int) 4, (int) height - 4);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x - 2, (int) y + 2, (int) 4, (int) height - 4);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) x + 2, (int) y - 2, (int) width - 4, (int) height / 2);
    }

}
