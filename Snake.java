package snake_genetic_algorithm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.w3c.dom.css.RGBColor;

import static snake_genetic_algorithm.Snake_genetic_algorithm.*;

public class Snake {

    private final Dna dna;
    private final Point points[];
    private float fitness;

    public Snake(Dna dna, Point points[]) {
        this.dna = dna;
        this.points = points;
    }

    void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < ANGLE_NUM; i++) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(this.points[i].getX(), this.points[i].getY(), this.points[i + 1].getX(), this.points[i + 1].getY());
        }
    }

    public Dna getDna() {
        return dna;
    }

    public Point[] getPoints() {
        return points;
    }

    public float getFitness() {
        return this.fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public Point getLastPoint() {
        return this.points[20];
    }
}
