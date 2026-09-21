package com.image_resizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static final String PATH = "src/main/resources/images/";

    public static void main(String[] args) throws IOException {
        File file = new File(PATH+"No Reduce.png");
        reduce(file, 1);
    }

    public static void reduce(File file, int numReduce) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] rgbs = new int[width * height];

        bufferedImage.getRGB(0, 0, width, height, rgbs, 0, width);

        int newWidth = width / 2;
        int newHeight = height / 2;

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y+=2) {
            for (int x = 0; x < width; x+=2) {
                Color c1 = new Color(rgbs[y * width + x], true);
                Color c2 = new Color(rgbs[y * width + x + 1], true);
                Color c3 = new Color(rgbs[(y + 1) * width + x], true);
                Color c4 = new Color(rgbs[(y + 1) * width + x + 1], true);

                int r = (c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 4;
                int g = (c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 4;
                int b = (c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 4;
                int a = (c1.getAlpha() + c2.getAlpha() + c3.getAlpha() + c4.getAlpha()) / 4;

                Color finalRgb = new Color(r, g, b, a);

                newImage.setRGB(x / 2, y / 2, finalRgb.getRGB());
            }
        }

        File newFile = new File(PATH+numReduce+"-reduce.png");
        ImageIO.write(newImage, "png", newFile);
        reduce(newFile, ++numReduce);
    }
}