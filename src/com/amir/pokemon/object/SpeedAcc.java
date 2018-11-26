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
public class SpeedAcc extends GameObject {

    private float width = 16, height = 16;
    private int inX;
    
    private Handler handler;

    public SpeedAcc(float x, float y,Handler handler, ObjectId id) {
        super(x, y, id);
        inX = (int)x;
        dx = 5;
        this.handler=handler;
    }


    public void tick(LinkedList<GameObject> object) {
        x += dx;
        y += dy;
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.SpeedFlag) {
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = inX;
                }
            }
            
        }
    }
    
    
    
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);

    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (x - 2 + (width)), (int) y + 2, (int) 4, (int) height - 4);
    }

    @Override
    public Rectangle getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
