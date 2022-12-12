import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        File f = new File("C:\\Users\\Owen\\Java Projects\\ConvulsionTesting\\test.jpeg");
        BlurImage obj = new BlurImage(f);
        obj.blur();
        System.out.println("Done.");
    }
}