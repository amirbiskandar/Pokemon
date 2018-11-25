/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amir.pokemon.window;

import com.amir.pokemon.framework.KeyInput;
import com.amir.pokemon.framework.ObjectId;
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

    //private static final long serialVersionUID = -12352739284726437289L;
    private boolean running = false;
    private Thread thread;
    public static int WIDTH,HEIGHT;
    
    private BufferedImage level = null;
    
    
    //object handling
    Handler handler;

    Random r = new Random();
 
    private void init() {
        
        WIDTH = getWidth();
        HEIGHT = getHeight();
        
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/level.png");
        
        
        handler = new Handler();
        
        LoadImageLevel(level);
        
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

    private void LoadImageLevel(BufferedImage image){
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
                    handler.addObject(new Block(xx*16,yy*16,ObjectId.Block,16));
                }
                if (red == 0 && green == 0 && blue == 255) {
                    handler.addObject(new Pointer(xx*16,yy*16,handler,ObjectId.Pointer));
                }
            }
            
        }
        
    }
    
    
    public static void main(String[] args) {
        new Window(800, 576, "pokemon", new Game());
    }

}
