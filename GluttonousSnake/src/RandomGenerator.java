import java.util.Random;

public class RandomGenerator {
    private static Random random;
    public static void init() {
        random = new Random();
    }

    public static int getRandom(int start, int end) {
        return random.nextInt(start, end);
    }
}
