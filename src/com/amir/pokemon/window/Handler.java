/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amir.pokemon.window;

import com.amir.pokemon.framework.GameObject;
import com.amir.pokemon.framework.ObjectId;
import com.amir.pokemon.object.Block;
import com.amir.pokemon.object.Button;
import com.amir.pokemon.object.Pointer;
import com.amir.pokemon.object.SpeedAcc;
import com.amir.pokemon.object.SpeedFlag;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author amir
 */
public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private int LEVELS = 6;
    private BufferedImage[] combat = new BufferedImage[LEVELS + 1];

    public Handler() {

        BufferedImageLoader loader = new BufferedImageLoader();
        for (int i = 1; i < combat.length; i++) {
            combat[i] = loader.loadImage("/combat" + i + ".png");

        }

    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void LoadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < h; xx++) {
            for (int yy = 0; yy < w; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = (pixel) & 0xFF;
                //System.out.println("Red : "+red+"\nGreen : "+green+"\nBlue : "+blue);

                if (red == 0 && green == 0 && blue == 0) {
                    addObject(new Block(xx * 16, yy * 16, ObjectId.Block, 16));
                }
                if (red == 0 && green == 0 && blue == 255) {
                    addObject(new Pointer(xx * 16, yy * 16, this, ObjectId.Pointer));
                }

                if (red == 255 && green == 255 && blue == 0) {
                    addObject(new SpeedFlag(xx * 16, yy * 16, ObjectId.SpeedFlag, 16));
                }

                if (red == 0 && green == 255 && blue == 0) {
                    addObject(new Button(xx * 16, yy * 16, ObjectId.Button, 16));
                }

                if (red == 255 && green == 0 && blue == 0) {
                    addObject(new SpeedAcc(xx * 16, yy * 16, this, ObjectId.SpeedAcc));
                }

            }

        }

    }

    public void clearLevel() {
        object.clear();
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void switchLevel() {
        clearLevel();
        if (Game.LEVEL < LEVELS) {
            LoadImageLevel(combat[Game.LEVEL++]);
            System.out.println("hi");
        } else if (Game.LEVEL == LEVELS) {
            LoadImageLevel(combat[Game.LEVEL]);
            Game.LEVEL++;
            System.out.println("hi");
        } else {
            Game.LEVEL = 1;
            LoadImageLevel(combat[Game.LEVEL++]);
        }

    }

}
