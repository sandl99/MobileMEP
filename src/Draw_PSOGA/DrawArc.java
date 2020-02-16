package Draw_PSOGA;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class DrawArc extends JComponent {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2.0f));
        g2.setPaint(Color.RED);
//        g2.draw(new Arc2D.Double(0, 0, 300, 300, 0, 90, Arc2D.PIE));
//        g2.setPaint(Color.GREEN);
//        g2.draw(new Arc2D.Double(0, 0, 300, 300, 90, 90, Arc2D.PIE));
//        g2.setPaint(Color.BLUE);
        g2.fill(new Arc2D.Double(0, 0, 300, 300, 0, 360, Arc2D.OPEN));
        Rectangle2D shape = new Rectangle2D.Float();
        shape.setFrame(0, 0, 300,300);
        g2.draw(shape);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Arc Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new DrawArc());
        frame.pack();
        frame.setSize(new Dimension(300, 300));
        frame.setVisible(true);
    }
}