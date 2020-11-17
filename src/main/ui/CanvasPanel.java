package ui;

import model.Workspace;

import javax.swing.*;
import java.awt.*;

// The panel where all of the graphical plotting of the Function objects happens
public class CanvasPanel extends JPanel {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Workspace workspace;

    // EFFECTS: sets up underlying JPanel properties
    public CanvasPanel(Workspace workspace) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.workspace = workspace;

        //TEST
        setBackground(Color.lightGray);
        //TEST

        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        paintGridLines(g2d);



        //TEST
//        int[] pointsX = {200, 400, 400, 200};
//        int[] pointsY = {200, 200, 400, 400};
//
//        for (int i = 0; i < pointsX.length - 1; i++) {
//            g2d.drawLine(pointsX[i], pointsY[i], pointsX[i + 1], pointsY[i + 1]);
//        }
//        g2d.drawLine(pointsX[pointsX.length - 1], pointsY[pointsY.length - 1], pointsX[0], pointsY[0]);
//
//
//        g2d.drawLine(0,0,500,500);
        //TEST
    }

    private void paintGridLines(Graphics2D g) {

    }
}
