/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Liam
 */
public class Principal {

    /**
     * Global parameters
     */
    private JFrame ventana;

    /**
     * Object that resolves GUI.
     */
    public Principal() {
        ventana = new JFrame("MineSweeper");
        JPanel mainPanel = new JPanel();
        MineSquare m = new MineSquare();
        m.addMouseListener(m);
                 m.setSize(new Dimension( m.getLength()*15,  m.getLength()*15));
         m.setPreferredSize(new Dimension(  m.getLength()*15,  m.getLength()*15));
         m.setLayout(null);
        ventana.add(m);
        ventana.setSize(500,500);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
