package snake_genetic_algorithm;

import java.awt.Color;
import java.awt.Graphics;

public class Target extends Point {

    public Target(int x, int y) {
        super(x, y);
    }

    void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawOval(this.getX() - 15, this.getY() - 15, 30, 30);
        g.fillOval(this.getX() - 8, this.getY() - 8, 16, 16);
    }
}
