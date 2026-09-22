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
        System.out.println("Is "+numReduce+" time.");
        BufferedImage bufferedImage = ImageIO.read(file);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (width <= 1 && height <= 1) {
            return;
        }

        int[] rgbs = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, rgbs, 0, width);

        int newWidth = (width + 1) / 2;
        int newHeight = (height + 1) / 2;

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int newY = 0; newY < newHeight; newY++) {
            for (int newX = 0; newX < newWidth; newX++) {

                int startX = newX * 2;
                int startY = newY * 2;

                int endX = Math.min(startX + 2, width);
                int endY = Math.min(startY + 2, height);

                int r = 0;
                int g = 0;
                int b = 0;
                int a = 0;
                int count = 0;

                for (int y = startY; y < endY; y++) {
                    for (int x = startX; x < endX; x++) {
                        Color color = new Color(rgbs[y * width + x], true);

                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                        a += color.getAlpha();

                        count++;
                    }
                }

                Color finalColor = new Color(r / count, g / count, b / count, a / count);

                newImage.setRGB(newX, newY, finalColor.getRGB());
            }
        }

        File newFile = new File(PATH + numReduce + "-reduce.png");
        ImageIO.write(newImage, "png", newFile);
        reduce(newFile, numReduce + 1);
    }
}