import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    String filePath = "input/day01.txt";

    public void day01() {
        readInput();
        System.out.println("List 1 sorted: " + list1);
        System.out.println("List 2 sorted: " + list2);
        System.out.println("Total distance: " + calculateDistance());
        System.out.println("Similarity: " + calculateSimilarity());
    }

    private void readInput() {
        try (BufferedReader in = Files.newBufferedReader(Paths.get(filePath))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                String[] columns = inputLine.split("\\s+");
                if (columns.length >= 2) {
                    list1.add(Integer.parseInt(columns[0]));
                    list2.add(Integer.parseInt(columns[1]));
                }
            }
            list1 = list1.stream().sorted().collect(Collectors.toList());
            list2 = list2.stream().sorted().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateDistance() {
        int totalDistance = 0;
        for (int i = 0; i < list1.size(); i++) {
            totalDistance +=(Math.abs(list1.get(i) - list2.get(i)));
        }
        return totalDistance;
    }

    private int calculateSimilarity() {
        int similarity = 0;
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    similarity += list1.get(i);
                }
            }
        }
        return similarity;
    }

    public static void main(String[] args) {
        new Day01().day01();
    }
}