/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amir.pokemon.framework;

import com.amir.pokemon.window.Handler;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author amir
 */
public class KeyInput extends KeyAdapter {

    Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Pointer) {
                if (true||true) {//if menu/turn still in progress
                    System.out.println("Pos : " + tempObject.getPos());
                    if (key == KeyEvent.VK_DOWN && tempObject.getPos() != 1) {
                        tempObject.setY(tempObject.getY() + 32);
                    }
                    if (key == KeyEvent.VK_UP && tempObject.getPos() != -1) {
                        tempObject.setY(tempObject.getY() - 32);
                    }
                    if (key == KeyEvent.VK_ENTER) {
                        tempObject.setDx(tempObject.getDx()+1);
                    }
                }
            }

        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    public void keyReleased(KeyEvent e) {

    }

}
