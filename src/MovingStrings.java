import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MovingStrings extends JFrame {

    private String[] strings = {"Hello", "World", "Java", "Swing", "Random", "Text", "Animation"};
    private int[] xPositions;
    private int[] yPositions;
    private int[] xDirections;
    private int[] yDirections;
    private Color[] colors;
    private int speed = 5;

    private MovingStrings() {
        setTitle("Moving Strings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        initPositions();
        initDirections();
        initColors();
        new Thread(this::animate).start();
        setVisible(true);
    }

    private void initPositions() {
        int numOfStrings = strings.length;
        xPositions = new int[numOfStrings];
        yPositions = new int[numOfStrings];
        Random random = new Random();
        for (int i = 0; i < numOfStrings; i++) {
            xPositions[i] = random.nextInt(getWidth());
            yPositions[i] = random.nextInt(getHeight());
        }
    }

    private void initDirections() {
        int numOfStrings = strings.length;
        xDirections = new int[numOfStrings];
        yDirections = new int[numOfStrings];
        Random random = new Random();
        for (int i = 0; i < numOfStrings; i++) {
            xDirections[i] = random.nextInt(2) == 0 ? -1 : 1;
            yDirections[i] = random.nextInt(2) == 0 ? -1 : 1;
        }
    }

    private void initColors() {
        int numOfStrings = strings.length;
        colors = new Color[numOfStrings];
        Random random = new Random();
        for (int i = 0; i < numOfStrings; i++) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            colors[i] = new Color(r, g, b);
        }
    }

    private void animate() {
        while (true) {
            for (int i = 0; i < strings.length; i++) {
                xPositions[i] += xDirections[i] * speed;
                yPositions[i] += yDirections[i] * speed;
                if (xPositions[i] < 0 || xPositions[i] + getStringWidth(strings[i]) > getWidth()) {
                    xDirections[i] *= -1;
                }
                if (yPositions[i] < 0 || yPositions[i] + getStringHeight(strings[i]) > getHeight()) {
                    yDirections[i] *= -1;
                }
            }
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getStringWidth(String string) {
        FontMetrics fontMetrics = getGraphics().getFontMetrics();
        return fontMetrics.stringWidth(string);
    }

    private int getStringHeight(String string) {
        FontMetrics fontMetrics = getGraphics().getFontMetrics();
        return fontMetrics.getHeight();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < strings.length; i++) {
            g.setColor(colors[i]);
            g.drawString(strings[i], xPositions[i], yPositions[i]);
        }
    }

    public static void main(String[] args) {
        new MovingStrings();
    }
}
