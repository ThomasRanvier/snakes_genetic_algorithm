package snake_genetic_algorithm;

import java.util.Random;
import static snake_genetic_algorithm.Snake_genetic_algorithm.*;

public class Dna {

    private double genome[];

    public Dna(double genome[]) {
        this.genome = genome;
        this.mutate();
    }

    public double[] getGenome() {
        return this.genome;
    }

    public void setGenome(double[] genome) {
        this.genome = genome;
    }

    private void mutate() {
        Random r = new Random();
        if (r.nextFloat() <= MUTATION_RATE) {
            if (r.nextInt(2) == 0) {
                for (int i = 0; i < r.nextInt(ANGLE_NUM + 1); i++) {
                    this.genome[i] = randomAngle();
                }
            } else {
                for (int i = r.nextInt(ANGLE_NUM + 1); i < ANGLE_NUM; i++) {
                    this.genome[i] = randomAngle();
                }
            }
        }
    }
}
