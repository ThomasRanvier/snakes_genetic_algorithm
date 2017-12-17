package snake_genetic_algorithm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
import static snake_genetic_algorithm.Snake_genetic_algorithm.*;

public class Population extends JPanel {

    private Snake snakes[];
    private Target target;
    private int distRefToTarget;
    private Point refPoint;

    public Population(Snake[] snakes, Point refPoint, Target target) {
        this.snakes = snakes;
        this.target = target;
        this.refPoint = refPoint;
        this.distRefToTarget = this.distance(this.target, this.refPoint);
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Snake s : this.snakes) {
            if (s != null) {
                s.draw(g);
            }
        }

        this.target.draw(g);

        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("Avg fitness : " + Math.round(this.getAverageFitness() * 100.0) / 100.0, this.getWidth() - 125, 60);
    }

    public Snake[] getSnakes() {
        return snakes;
    }

    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }

    public void setFitnesses() {
        for (Snake s : this.snakes) {
            s.setFitness(calculateFitness(s));
        }
        this.repaint();
    }

    private float calculateFitness(Snake s) {
        return ((this.distRefToTarget * 1.0f - distance(s.getLastPoint(), this.target) * 1.0f) / (this.distRefToTarget * 1.0f)) * 100.0f;
    }

    private int distance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public void sortPopulation() {
        int startIndex = 0;
        for (int size = this.snakes.length; size > 1; size--) {
            int maxIndex = getMax(startIndex);
            this.swap(maxIndex, startIndex);
            startIndex++;
        }
        this.repaint();
    }

    private int getMax(int startIndex) {
        int maxIndex = startIndex;
        for (int i = startIndex; i < this.snakes.length; i++) {
            if (this.snakes[i].getFitness() >= this.snakes[maxIndex].getFitness()) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private void swap(int maxIndex, int startIndex) {
        Snake mem = this.snakes[startIndex];
        this.snakes[startIndex] = this.snakes[maxIndex];
        this.snakes[maxIndex] = mem;
    }

    public void killLessFits() {
        for (int i = KILL_RATE; i < POPULATION_SIZE; i++) {
            this.snakes[i] = null;
        }
        this.repaint();
    }

    public void reproduce() {
        for (int i = KILL_RATE; i < POPULATION_SIZE; i++) {
            Dna dna = new Dna(this.createChildGenome());
            this.snakes[i] = new Snake(dna, generatePoints(this.refPoint, dna));
        }
        this.repaint();
    }

    private double[] createChildGenome() {
        Snake father = this.getRandParent();
        Snake mother = this.getRandParent();
        double fatherGenome[] = father.getDna().getGenome();
        double motherGenome[] = mother.getDna().getGenome();
        double childGenome[] = crossover(fatherGenome, motherGenome);
        return childGenome;
    }

    private Snake getRandParent() {
        Random r = new Random();
        int rand1 = r.nextInt(KILL_RATE);
        int rand2 = r.nextInt(KILL_RATE);
        int index = Math.abs(rand1 - rand2);
        return this.snakes[index];
    }

    private double[] crossover(double[] fatherGenome, double[] motherGenome) {
        double childGenome[] = new double[ANGLE_NUM];
        Random r = new Random();
        for (int i = 0; i < ANGLE_NUM; i++) {
            if (r.nextInt(2) == 1) {
                childGenome[i] = fatherGenome[i];
            } else {
                childGenome[i] = motherGenome[i];
            }
        }
        return childGenome;
    }

    public double getAverageFitness() {
        float total = 0;
        int count = 0;
        for (Snake s : this.snakes) {
            if (s != null) {
                total += s.getFitness();
                count++;
            }
        }
        return (total / (float) count);
    }

}
