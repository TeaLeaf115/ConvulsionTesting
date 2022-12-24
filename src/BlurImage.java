// *********************************************************************
// BlurImage.java
// 
// This class will have all the functionality to take in an image as a
// parameter and output a blurred version of the image.
// 
// *********************************************************************

// --------------------
// Imports
// --------------------

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;


// --------------------
// Class Declaration
// --------------------

public class BlurImage
{
    // --------------------
    // PIV Declaration
    // --------------------

    private BufferedImage image;
    private BufferedImage blurredImg;
    private File f;

    // --------------------
    // Constants Declaration
    // --------------------

    private final int width;
    private final int height;
    private final String fName;

    private final float[][] CONVOLUTION_MULTI_CENTER =
            {{0.046875F, 0.140625F, 0.046875F},
                    {0.140625F, 0.25F, 0.140625F},
                    {0.046875F, 0.140625F, 0.046875F}};

    // --------------------
    // Constructor(s)
    // --------------------

    public BlurImage(File input)
    {
        // try/catch statement to make sure the file is an image.
        try {image = ImageIO.read(input);}
        catch(IOException e) {System.out.println("An error has occurred, please check the following questions:\n	* Your inputted file isn't an image\n	* You do not have the correct path to the image\n	* Check you spelled the name of the image correctly");}

        fName = input.getName();

        width = image.getWidth();
        height = image.getHeight();

        // Variables for creating a new blurred image.
        blurredImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        f = null;
    }

    // --------------------
    // Methods
    // --------------------

    public void blur()
    {
        // loop for each main pixel
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                //
                float avgR = 0.0F, avgG = 0.0F, avgB = 0.0F;

				/*
					rgbList[x-1][y-1]
					rgbList[x][y-1];
					rgbList[x+1][y-1]
					rgbList[x-1][y]
					rgbList[x][y]
					rgbList[x+1][y]
					rgbList[x-1][y+1]
					rgbList[x][y+1]
					rgbList[x+1][y+1]
				*/

                for (int j = -1; j <= 1; j++)
                {
                    for (int i = -1; i <= 1; i++)
                    {
//                        System.out.println(x+"   "+y+"   "+i+"   "+j);
                        float[] avgRGB = avgRGBFinder(x, y, i, j);
//                        System.out.println(Arrays.toString(avgRGB));

                        avgR += avgRGB[0];
                        avgG += avgRGB[1];
                        avgB += avgRGB[2];
                    }
                }

                Color avgColor = new Color(avgR, avgG, avgB);
//                System.out.println(avgColor);
                blurredImg.setRGB(x, y, avgColor.getRGB());

//                System.out.println();
        }
//            System.out.println("X: " + x);

        }
        toImage();
    }

    public float[] avgRGBFinder(int x, int y, int i, int j)
    {
        float[] avgRGB = new float[3];

        if (x+i == -1 || x+i >= width || y+j == -1 || y+j >= height)
        {
            return new float[]{0.0F, 0.0F, 0.0F};
        }
//        System.out.println(x+"   "+ y);
        Color c = new Color(image.getRGB(x + i, y + j));

        avgRGB[0] = rgbToConvolutedDecimal(c.getRed(), 1 + i, 1 + j);
//        System.out.println("I made it here!!!!");
        avgRGB[1] = rgbToConvolutedDecimal(c.getGreen(), 1 + i, 1 + j);
        avgRGB[2] = rgbToConvolutedDecimal(c.getBlue(), 1 + i, 1 + j);


        return avgRGB;
    }

    public float rgbToConvolutedDecimal(int colorVal, int xPos, int yPos)
    {
//        System.out.println(xPos+"   "+ yPos);
//        System.out.println((colorVal/255.0F) * CONVOLUTION_MULTI_CENTER[xPos][yPos]);
        return (colorVal/255.0F) * CONVOLUTION_MULTI_CENTER[xPos][yPos];
    }

    // --------------------
    // Getters
    // --------------------

    // Returns a 'int' array with the values of [red, green, blue] as a representations of a pixel color.
    public int[] getPixelValue(int x, int y)
    {
        int[] rgbArr = new int[3];
        Color c = new Color(image.getRGB(x, y));

        rgbArr[0] = c.getRed();
        rgbArr[1] = c.getGreen();
        rgbArr[2] = c.getBlue();
        // System.out.println(java.util.Arrays.toString(rgbArr));
        return rgbArr;
    }

    // --------------------
    // toImage Method
    // --------------------

    public void toImage()
    {
        try
        {
            f = new File(fName + "_blurred.jpg");
            ImageIO.write(blurredImg, "png", f);
            System.out.println("Image Blurred");
        }
        catch(IOException e)
        { System.out.println("Error: " + e); }
    }

    /*End of Class*/
}

/*

CHANGELOG:
-----------
v1.0 ~ Initial creation of class

*/
