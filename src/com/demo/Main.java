package com.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    private static final List<Integer> depth = new ArrayList<>();
    private static List<String> holes = new ArrayList<>();
    private static Integer chosenIndex = 0;

    public static void main(String[] args) {
        try {
            readFile();
            inputIndex();
            calculatePercentage();
            writeFile();
            getNumberOfHoles();
            informationsAboutHoles(chosenIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1.Feladat
     */
    public static void readFile() throws FileNotFoundException {
        File file = new File("melyseg.txt");
        Scanner in = new Scanner(file);
        while (in.hasNextLine()){
            depth.add(Integer.parseInt(in.nextLine()));
        }
        in.close();
        System.out.println("1. Feladat:");
        System.out.println("File beolvasva, a hossza: \" + depth.size()");
    }

    /**
     * 2.Feladat
     */
    public static void inputIndex(){
        System.out.println("2. Feladat:");
        System.out.println("Kérlek add meg az keresendő indexet!");
        Scanner in = new Scanner(System.in);

        chosenIndex = Integer.parseInt(in.nextLine());
        System.out.println(depth.get(chosenIndex-1));

        in.close();
    }
    /**
     * 3.Feladat
     */
    public static void calculatePercentage(){
        int counter = 0;
        for (Integer integer : depth) {
            if (integer == 0) {
                counter++;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        System.out.println("3-as feladat:");
        System.out.println("Az érintetlen terület aránya " + decimalFormat.format((double) counter / depth.size() * 100) + "%");
    }

    /**
     * 4.Feladat
     */
    public static void writeFile(){
        holes = getHoles();

        try {
            FileWriter fileWriter = new FileWriter("godrok.txt");
            for (String hole : holes) {
                fileWriter.write(hole + "\n");
            }
            fileWriter.close();
            System.out.println("4.Feladat:");
            System.out.println("Létrehoztam a fájlt.");
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    private static List<String> getHoles(){
        List<String> holes = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        int lastNumber = 0;
        for (Integer current : depth) {
            if (current > 0) {
                temp.append(current);
            }
            if (current == 0 && lastNumber > 0) {
                holes.add(temp.toString());
                temp.setLength(0);
            }
            lastNumber = current;
        }
        return holes;
    }
    /**
     * 5.Feladat
     */
    public static void getNumberOfHoles(){
        System.out.println("5.Feladat:");
        System.out.println("Gödrök száma: " + holes.size());
    }

    /**
     * 6.Feladat
     */
    public static void informationsAboutHoles(int index){

        System.out.println("6-os feladat:");
        int indexOf = index - 1;

        while (depth.get(indexOf)>0){
            indexOf--;
        }
        int start = indexOf + 2;

        indexOf = index;

        while(depth.get(indexOf) > 0){
            indexOf++;
        }
        int end = indexOf;
        System.out.println("A)");
        System.out.println("A gödör kezdete: " + start + " méter, a gödör vége: " + end + " méter");

        indexOf = start;
        while (depth.get(indexOf) >= depth.get(indexOf-1) && indexOf <= end){
            indexOf++;
        }
        while(depth.get(indexOf) <= depth.get(indexOf-1) && indexOf <= end){
            indexOf++;
        }
        System.out.println("B)");
        System.out.println(indexOf>end? "Folyamatosan mélyül": "Nem mélyül folyamatosan.");

        int max = 0;
        int v = 0;
        for (int i=start;i<=end;i++){
            Integer value = depth.get(i);
            v = v + value * 10;
            if (value > max){
                max = value;
            }
        }
        System.out.println("C)");
        System.out.println("A legnagyobb mélysége " + max + " méter");

        System.out.println("D)");
        System.out.println("Térfogata: " + v + " m^3");

        int safeWaterLevel = v - 10 * (end - start+1);
        System.out.println("E)");
        System.out.println("A vízmennyiség " + safeWaterLevel + " m^3");
    }
}