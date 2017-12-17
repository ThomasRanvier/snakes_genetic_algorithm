package snake_genetic_algorithm;

import java.util.Random;
import javax.swing.JFrame;

public class Snake_genetic_algorithm {

    static final int WIDTH = 1000;
    static final int HEIGHT = 700;
    static final int LINE_LENGTH = 40;
    static final int POPULATION_SIZE = 20;
    static final int KILL_RATE = 10;//will be killed each round
    static final int REST_TIME = 500;
    static final float MUTATION_RATE = 0.15f;
    static final int ANGLE_NUM = 25;

    public static void main(String[] args) {

        JFrame frame;
        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Snakes genetic Algorithm !");

        Point refPoint = new Point(WIDTH / 2, HEIGHT / 2);

        Target target = randomTarget();

        Snake snakes[] = initPopulation(refPoint);

        Population population = new Population(snakes, refPoint, target);
        frame.setContentPane(population);

        try {
            Thread.sleep(REST_TIME);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        go(population);
    }

    public static void go(Population population) {

        while (true) {
            population.setFitnesses();

            population.sortPopulation();

            if (population.getAverageFitness() >= 90) {
                population.setTarget(randomTarget());

                population.repaint();

                continue;
            }

            try {
                Thread.sleep(REST_TIME);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            population.killLessFits();

            try {
                Thread.sleep(REST_TIME);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            population.reproduce();
        }

    }

    private static Dna createDna() {
        return new Dna(createGenome());
    }

    private static double[] createGenome() {
        double angles[] = new double[ANGLE_NUM];

        for (int i = 0; i < ANGLE_NUM; i++) {
            angles[i] = randomAngle();
        }

        return angles;
    }

    public static double randomAngle() {
        Random r = new Random();
        return -Math.PI + r.nextFloat() * (2 * Math.PI);
    }

    public static Point[] generatePoints(Point refPoint, Dna dna) {
        Point points[] = new Point[ANGLE_NUM + 1];
        points[0] = refPoint;
        for (int i = 0; i < ANGLE_NUM; i++) {
            points[i + 1] = generatePoint(points[i], dna.getGenome()[i]);
        }
        return points;
    }

    private static Point generatePoint(Point refPoint, double angle) {
        return new Point((int) (refPoint.getX() + LINE_LENGTH * Math.cos(angle)), (int) (refPoint.getY() + LINE_LENGTH * Math.sin(angle)));
    }

    private static Snake[] initPopulation(Point refPoint) {
        Snake snakes[] = new Snake[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            Dna dna = createDna();
            snakes[i] = new Snake(dna, generatePoints(refPoint, dna));
        }
        return snakes;
    }

    private static Target randomTarget() {
        Random r = new Random();
        int x;
        int y;
        if (r.nextInt(2) == 0) {
            x = 20;
            y = 20 + (int) (r.nextFloat() * (HEIGHT - 80) * 1.0f);
        } else {
            x = 20 + (int) (r.nextFloat() * (WIDTH - 40) * 1.0f);
            y = HEIGHT - 60;
        }
        System.out.println("x : " + x + ", y : " + y);
        return new Target(x, y);
    }

}
