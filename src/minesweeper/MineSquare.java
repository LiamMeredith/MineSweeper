/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Liam
 */
public class MineSquare extends JPanel implements MouseListener {

    private final Casilla[][] c;
    private final int length;
    private final int side;
    private boolean playing;

    /**
     * Constructor que crea una cuadriducla conun numero de bombas aleatorias
     */
    public MineSquare() {
        this.length = 15;
        this.side = 30;
        this.playing = true;
        this.c = new Casilla[length][length];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c.length; j++) {
                if (5 > Math.random() * 100) {
                    c[i][j] = new Casilla(true);
                    c[i][j].setNumBomb(-1);
                } else {
                    c[i][j] = new Casilla(false);
                }

            }
        }

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c.length; j++) {
                if (c[i][j].isBomb()) {
                    calcBombs(i, j);
                }
            }
        }
        imprimirCuadrado();
    }

    /**
     * 
     * @param r 
     */
    @Override
    public void paintImmediately(Rectangle r) {
        super.paintImmediately(r); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Componentes graficos
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        BufferedImage bomb = null, water = null, n1 = null, n2 = null, n3 = null, n4 = null;
        Image b = null;
        try {
            bomb = ImageIO.read(new File("boom.png"));
            water = ImageIO.read(new File("water.jpg"));
            n1 = ImageIO.read(new File("number1.png"));
            n2 = ImageIO.read(new File("number2.png"));
            n3 = ImageIO.read(new File("number3.png"));
            n4 = ImageIO.read(new File("number4.png"));
            b = ImageIO.read(new File("explotion.gif-c200"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        int x = 0, y = 0;
        for (int i = 0; i < c.length; i++) {
            x = 0;
            for (int j = 0; j < c.length; j++) {
                if (!c[i][j].isClicked()) {

                    g.setColor(Color.black);
                    g.drawRect(x, y, side, side);
                    g.setColor(Color.lightGray);
                    g.fillRect(x + 1, y + 1, side - 2, side - 2);
                } else {
                    if (c[i][j].isBomb()) {
                        g.drawImage(b, x, y, side, side, this);
//                    g.setColor(Color.black);
//                    g.drawRect(x, y, side, side);
//                    g.setColor(Color.lightGray);
//                    g.fillRect(x + 1, y + 1, side - 2, side - 2);
//                        g.drawImage(bomb, x, y, side, side, null);

                    } else if (c[i][j].getNumBomb() == 0) {
                        g.drawImage(water, x + 1, y + 1, side, side, null);
                        g.setColor(Color.black);
                        g.drawRect(x, y, side, side);
                    } else if (c[i][j].getNumBomb() != 0) {
                        int number = c[i][j].getNumBomb();
                        g.setColor(Color.black);
                        g.drawRect(x, y, side, side);
                        g.setColor(Color.lightGray);
                        g.fillRect(x + 1, y + 1, side - 2, side - 2);
                        switch (number) {
                            case 1:
                                g.drawImage(n1, x + 1, y + 1, side, side, null);
                                break;
                            case 2:
                                g.drawImage(n2, x + 1, y + 1, side, side, null);
                                break;
                            case 3:
                                g.drawImage(n3, x + 1, y + 1, side, side, null);
                                break;
                            case 4:
                                g.drawImage(n4, x + 1, y + 1, side, side, null);
                                break;
                        }

                        g.setColor(Color.black);
                        g.drawRect(x, y, side, side);

                    }

                }
                x += side;
            }
            y += side;
        }

    }

    /**
     * Calcula el click y sus eventos
     * @param i
     * @param j 
     */
    private void calculoClick(int i, int j) {
        if (!c[i][j].isClicked()) {
            c[i][j].setClicked(true);
            if (c[i][j].getNumBomb() == 0) {
                if (inRange(i + 1, j) && this.c[i + 1][j].getNumBomb() >= 0) {

                    calculoClick(i + 1, j);
                }
                if (inRange(i - 1, j) && this.c[i - 1][j].getNumBomb() >= 0) {

                    calculoClick(i - 1, j);
                }
                if (inRange(i, j + 1) && this.c[i][j + 1].getNumBomb() >= 0) {

                    calculoClick(i, j + 1);
                }
                if (inRange(i, j - 1) && this.c[i][j - 1].getNumBomb() >= 0) {

                    calculoClick(i, j - 1);
                }
            }
        }
    }

    /**
     * Calculo de la cantidad de bombas adyacentes
     * @param i
     * @param j 
     */
    private void calcBombs(int i, int j) {

        if (inRange(i + 1, j) && !this.c[i + 1][j].isBomb()) {
            this.c[i + 1][j].addNumBomb();
        }
        if (inRange(i - 1, j) && !this.c[i - 1][j].isBomb()) {
            this.c[i - 1][j].addNumBomb();
        }
        if (inRange(i, j + 1) && !this.c[i][j + 1].isBomb()) {
            this.c[i][j + 1].addNumBomb();
        }
        if (inRange(i, j - 1) && !this.c[i][j - 1].isBomb()) {
            this.c[i][j - 1].addNumBomb();
        }
        if (inRange(i + 1, j + 1) && !this.c[i + 1][j + 1].isBomb()) {
            this.c[i + 1][j + 1].addNumBomb();
        }
        if (inRange(i + 1, j - 1) && !this.c[i + 1][j - 1].isBomb()) {
            this.c[i + 1][j - 1].addNumBomb();
        }
        if (inRange(i - 1, j + 1) && !this.c[i - 1][j + 1].isBomb()) {
            this.c[i - 1][j + 1].addNumBomb();
        }
        if (inRange(i - 1, j - 1) && !this.c[i - 1][j - 1].isBomb()) {
            this.c[i - 1][j - 1].addNumBomb();
        }
    }

    /**
     * Verifica si esta en rango del recuadro
     * @param i
     * @param j
     * @return 
     */
    private boolean inRange(int i, int j) {
        boolean b = false;
        try {
            this.c[i][j].isBomb();
            b = true;

        } catch (ArrayIndexOutOfBoundsException e) {
            return b;
        }
        return b;
    }

    /**
     * Output para tener feedback
     */
    private void imprimirCuadrado() {
        String s = "";
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c.length; j++) {
                if (c[i][j].isBomb()) {
                    s = "b";
                } else {
                    s = "" + c[i][j].getNumBomb();
                }
                System.out.print("[" + s + "]");
            }
            System.out.print("\n");
        }
    }

    public int getLength() {
        return length;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = e.getY() / side;
        int j = e.getX() / side;
        System.out.println(i + " " + j);
        calculoClick(i, j);
        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
