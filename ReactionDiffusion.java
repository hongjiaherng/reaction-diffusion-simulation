import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ReactionDiffusion extends JPanel {

    private final double diffuseRateA = 1.0;
    private final double diffuseRateB = .5;
    // Looks great (f,k): (.055, .062)
    public double feedRate = .055;
    public double killRate = .062;
    private double deltaT = 1;
    public static final int width = 600;
    public static final int height = 600;

    private double[][] chemicalA = new double[width][height];
    private double[][] chemicalB = new double[width][height];

    ReactionDiffusion() {
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        initGrid();
        Timer timer = new Timer(1, e -> {
            nextGenerationGrid();
            repaint();
        });
        timer.start();
    }

    void initGrid() {
        int radius = 12;
        int midX = width / 2;
        int midY = height / 2;
        int distXFromMid;
        int distYFromMid;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                distXFromMid = Math.abs(x - midX);
                distYFromMid = Math.abs(y - midY);

                if (distXFromMid < radius && distYFromMid < radius) {
                    chemicalA[x][y] = 0;
                    chemicalB[x][y] = 1;
                } else {
                    chemicalA[x][y] = 1;
                    chemicalB[x][y] = 0;
                }
            }
        }
    }

    void nextGenerationGrid() {
        double[][] nextChemicalA = new double[width][height];
        double[][] nextChemicalB = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double reaction = chemicalA[x][y] * chemicalB[x][y] * chemicalB[x][y];

                // This formula might produce negative number, so in paint() we must use Math.abs() to ensure the values are in positive.
                nextChemicalA[x][y] = chemicalA[x][y] +
                        (diffuseRateA * laplacian(chemicalA, x, y) - reaction + feedRate * (1 - chemicalA[x][y])) * deltaT;
                nextChemicalB[x][y] = chemicalB[x][y] +
                        (diffuseRateB * laplacian(chemicalB, x, y) + reaction - (killRate + feedRate) * chemicalB[x][y]) * deltaT;
            }
        }
        chemicalA = nextChemicalA;
        chemicalB = nextChemicalB;

    }

    double laplacian(double[][] chemical, int x, int y) {
        double sum = 0;
        double[][] weights = {{.05, .2, .05},
                {.2, -1, .2},
                {.05, .2, .05}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int xPos = x + (i - 1);
                int yPos = y + (j - 1);
                if (xPos >= 0 && xPos < width && yPos >= 0 && yPos < height) {
                    sum += chemical[xPos][yPos] * weights[i][j];
                }
            }
        }
        return sum;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        BufferedImage grid = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Figure out why it must be rounded off instead of just doing Math.abs(), otherwise it will produce weird rectangular and circular border
                int r = (int) Math.abs(Math.round(128 * chemicalA[x][y]));
                int g = (int) Math.abs(Math.round(20 * chemicalB[x][y]));
                int b = 0;
                if (r >= 0 && g >= 0 && b >= 0 && r <= 255 && g <= 255 && b <= 255) {
                    Color pixelColor = new Color(r, g, b);
                    grid.setRGB(x, y, pixelColor.getRGB());
                } else {
                    // Mechanism to check if the rgb values produced are eligible.
                    System.out.println("RGB values out of bound: r-" + r + " g-" + g + " b-" + b);
                    return; // return to the method call, to rebuild the whole grid with another set of chemicalA and chemicalB
                }
            }
        }
        graphics.drawImage(grid, 0, 0, null);
    }
}
//    public static void main(String[] args) {
//        ReactionDiffusion r = new ReactionDiffusion();
//        r.setTitle("Reaction-diffusion Simulator");
//        r.setSize(new Dimension(width, height));
//        r.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        r.initGrid();
//        r.setVisible(true);
//        r.nextGenerationGrid();
//
//    }



// Make ReactionDiffusion class a jpanel(or anything else as long as it is not an entire jframe)
// Add some features to let user specify the feed rate/kill rate for themselves


//
//    BufferedImage grid;
//    BufferedImage next;
//    int[][] pixels;
//    int[][] nextPixels;
//    int a = (255 << 24) | (220 << 16) | (220 << 8) | 220;
//    int b = (255 << 24) | (0 << 16) | (0 << 8) | 0;
//
//    ReactionDiffusion() {
//
//        grid = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
//        pixels = new int[600][600];
//        nextPixels = new int[600][600];
//
//        for (int x = 0; x < pixels.length; x++) {
//            for (int y = 0; y < pixels[x].length; y++) {
//                pixels[x][y] = a;
//                nextPixels[x][y] = a;
//                grid.setRGB(x, y, pixels[x][y]);
//            }
//        }
//
//        for (int i = 295; i < 305; i++) {
//            for (int j = 295; j < 305; j++) {
//                pixels[i][j] = b;
//                grid.setRGB(i, j, pixels[i][j]);
//            }
//        }
//
//
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        g.drawImage(grid, getInsets().left, getInsets().top, null);
//
////        for (int x = 0; x < pixels.length; x++) {
////            for (int y = 0; y < pixels[x].length; y++) {
////                if (x % 2 == 0 && y % 2 == 0) {
////                    nextPixels[x][y] = a;
////                } else if (x % 2 == 1 && y % 2 == 1) {
////                    nextPixels[x][y] = b;
////                }
////                grid.setRGB(x, y, nextPixels[x][y]);
////            }
////        }
////
////        for (int x = 0; x < pixels.length; x++) {
////            for (int y = 0; y < pixels[x].length; y++) {
////                if (x % 2 == 0 && y % 2 == 0) {
////                    pixels[x][y] = nextPixels[x][y];
////                } else if (x % 2 == 1 && y % 2 == 1) {
////                    pixels[x][y] = nextPixels[x][y];
////                }
////                grid.setRGB(x, y, pixels[x][y]);
////            }
////        }
////        repaint();
//    }
//}
