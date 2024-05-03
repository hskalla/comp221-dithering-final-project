package dithering;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class App {
    Scanner scan;
    String input;
    String img;
    String alg;

    public App() {
        scan = new Scanner(System.in);
        input = "";

        System.out.println("------------------------------------");
        System.out.println("Welcome to Henry Skalla, James McConnell and Jay Lebakken's dithering app. This program was written for Prof. Arehalli's COMP221 Algorithms course at Macalester College in Spring 2024.");
        System.out.println("------------------------------------");
        do {
            if(input.toLowerCase().equals("list")) {
                Set<String> images = listFilesUsingJavaIO("./images");
                List<String> orderedImages = new ArrayList<String>(images);
                Collections.sort(orderedImages);
                for(String image : orderedImages) {
                    System.out.println("\t"+image);
                    
                }
            }
            System.out.print("Enter image filename (for a list of filenames, enter \"list\"): ");
            input = scan.nextLine();
        } while(input.toLowerCase().equals("list"));
        img = input;
        System.out.println("Choose a dithering algorithm (type one of the following):");
        System.out.println("\tthreshold");
        System.out.println("\tlinear");
        System.out.println("\tfloyd-steinburg");
        System.out.println("\tcb-sfc");
        System.out.println("\tcb-sfc-path");
        System.out.println("\tnone");
        System.out.print("Choose a dithering algorithm: ");
        input = scan.nextLine();
        alg = input;

        Viewer frame = new Viewer(img,alg);
        frame.setVisible(true);
    }

    //code taken from https://www.baeldung.com/java-list-directory-files
    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
          .filter(file -> !file.isDirectory())
          .map(File::getName)
          .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        new App();
    }
}
