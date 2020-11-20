package ui;

import model.Function;
import model.Workspace;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

// The panel where all of the graphical plotting of the Function objects happens
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class CanvasPanel extends JPanel {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int PX_PER_MARKING_X = WIDTH / 10; // distance between x-unit markings in pixels
    public static final int PX_PER_MARKING_Y = HEIGHT / 10; // distance between y-unit markings in pixels
    public static final int MARKING_SIZE = 10; // length of unit markings in pixels
    public static final int FONT_SIZE = 16;
    public static final int DIGIT_OFFSET_Y = FONT_SIZE + 25; // y-distance of unit marking digits from axes in pixels
    public static final int DIGIT_OFFSET_X = FONT_SIZE / 2; // x-distance of unit marking digits from axes in pixels

    private Workspace workspace;

    // REQUIRES: workspace is this program's active workspace object
    // EFFECTS: creates the graphical plotting panel
    public CanvasPanel(Workspace workspace) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.workspace = workspace;
        setOpaque(true);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: paints all the graphics to the panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //CITATION: https://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));

        drawGridLines(g2d);
        drawWorkspace(g2d);
    }

    // REQUIRES: g is the Graphics2D object inside paintComponent()
    // MODIFIES: this
    // EFFECTS: draws the gridlines, including axes, unit markings, and digits
    private void drawGridLines(Graphics2D g) {
        // draw x and y axes
        g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
        g.drawLine(WIDTH / 2, HEIGHT, WIDTH / 2, 0);

        // draw unit markings
        for (int i = 1; i * PX_PER_MARKING_X <= WIDTH; i++) {
            g.drawLine(i * PX_PER_MARKING_X, (HEIGHT - MARKING_SIZE) / 2,
                    i * PX_PER_MARKING_X, (HEIGHT + MARKING_SIZE) / 2);
            g.drawLine((WIDTH - MARKING_SIZE) / 2, i * PX_PER_MARKING_Y,
                    (WIDTH + MARKING_SIZE) / 2, i * PX_PER_MARKING_Y);
        }

        // draw digits on unit markings
        g.drawString("-0.4", 4 * PX_PER_MARKING_X - DIGIT_OFFSET_X, (HEIGHT + DIGIT_OFFSET_Y) / 2);
        g.drawString("0.4", 6 * PX_PER_MARKING_X - DIGIT_OFFSET_X, (HEIGHT + DIGIT_OFFSET_Y) / 2);
        g.drawString("-0.4", (WIDTH + 2 * DIGIT_OFFSET_X) / 2, 6 * PX_PER_MARKING_Y + DIGIT_OFFSET_Y / 7);
        g.drawString("0.4", (WIDTH + 2 * DIGIT_OFFSET_X) / 2, 4 * PX_PER_MARKING_Y + DIGIT_OFFSET_Y / 7);
        g.drawString("-1.6", PX_PER_MARKING_X - DIGIT_OFFSET_X, (HEIGHT + DIGIT_OFFSET_Y) / 2);
        g.drawString("1.6", 9 * PX_PER_MARKING_X - DIGIT_OFFSET_X, (HEIGHT + DIGIT_OFFSET_Y) / 2);
        g.drawString("-1.6", (WIDTH + 2 * DIGIT_OFFSET_X) / 2, 9 * PX_PER_MARKING_Y + DIGIT_OFFSET_Y / 7);
        g.drawString("1.6", (WIDTH + 2 * DIGIT_OFFSET_X) / 2, PX_PER_MARKING_Y + DIGIT_OFFSET_Y / 7);
    }

    // REQUIRES: g is the Graphics2D object inside paintComponent()
    // MODIFIES: this
    // EFFECTS: plots the functions in the workspace onto this panel
    private void drawWorkspace(Graphics2D g) {
        HashMap<String, Function> functions = workspace.getFunctionList();
        for (String funcName : functions.keySet()) {
            drawFunction(g, functions.get(funcName));
        }
    }

    // REQUIRES: g is the Graphics2D object inside paintComponent(),
    //           function is a Function object inside the workspace
    // MODIFIES: this
    // EFFECTS: plots the function onto this panel
    private void drawFunction(Graphics2D g, Function function) {
        List<Double> valuesX = function.getValuesX();
        List<Double> valuesY = function.getValuesY();
        List<Double> domain = function.getDomain();

        // draw lines between each (x,y) point of the function
        for (int i = 0; i < valuesX.size(); i++) {
            if (i == 0 || i == valuesX.size() - 1) {
                // skip first and last point, as plotting those properly requires
                // calculating the derivative of the function at that point
                continue;
            } else {
                int x1 = convertCartesianXToPanelCoordinates(valuesX.get(i), domain);
                int y1 = convertCartesianYToPanelCoordinates(valuesY.get(i), domain);
                int x2 = convertCartesianXToPanelCoordinates(valuesX.get(i + 1), domain);
                int y2 = convertCartesianYToPanelCoordinates(valuesY.get(i + 1), domain);
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

    // REQUIRES: domain is the domain returned from a Function object
    // EFFECTS: converts x cartesian coordinate to canvas panel x coordinate
    private int convertCartesianXToPanelCoordinates(double cartesianCoord, List<Double> domain) {
        double conversionFactor = WIDTH / (domain.get(1) - domain.get(0)); //converts from cart. to px
        return (int) ((WIDTH / 2) + (cartesianCoord * conversionFactor));
    }

    // REQUIRES: domain is the domain returned from a Function object
    // EFFECTS: converts y cartesian coordinate to canvas panel y coordinate
    private int convertCartesianYToPanelCoordinates(double cartesianCoord, List<Double> codomain) {
        double conversionFactor = HEIGHT / (codomain.get(1) - codomain.get(0)); //converts from cart. to px
        return (int) ((HEIGHT / 2) - (cartesianCoord * conversionFactor));
    }
}
