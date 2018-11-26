/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amir.pokemon.window;

import com.amir.pokemon.framework.KeyInput;
import com.amir.pokemon.framework.ObjectId;
import com.amir.pokemon.object.Button;
import com.amir.pokemon.object.Block;
import com.amir.pokemon.object.Pointer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author amir
 */
public class Game extends Canvas implements Runnable {

    static int LEVEL = 1;

    //private static final long serialVersionUID = -12352739284726437289L;
    private boolean running = false;
    private Thread thread;
    public static int WIDTH,HEIGHT;
    
    private BufferedImage menu = null;
    
    
    //object handling
    Handler handler;

    Random r = new Random();
 
    private void init() {
        
        WIDTH = getWidth();
        HEIGHT = getHeight();
        handler = new Handler();
        
        BufferedImageLoader loader = new BufferedImageLoader();
        menu = loader.loadImage("/menu.png");
        
        handler.LoadImageLevel(menu);
        
        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public void run() {

        this.requestFocus();
        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS : " + frames + " Ticks : " + updates);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //start drawing
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        handler.render(g);
        //end drawing
        g.dispose();
        bs.show();

    }

    
    
    
    public static void main(String[] args) {
        new Window(928, 576, "pokemon", new Game());
    }

}
