import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day02 {

    List<List<Integer>> data = new ArrayList<>();
    String filePath = "input/day02.txt";

    public void day02() {
        readInput();
        System.out.println("Safe count without dampener: " + checkData(false));
        System.out.println("Safe count with dampener: " + checkData(true));
    }

    private int checkData(boolean isDampened) {
        int safeCount = 0;
        for (List<Integer> line : data) {
            System.out.println("Line: " + line );
            boolean isSafe = checkIfSafe(line);

            if (!isSafe && isDampened) {
                int len = line.size();
                for (int i = 0; i < len; i++) {
                    List<Integer> copy = new ArrayList<>(line);
                    copy.remove(i);
                    if (checkIfSafe(copy)) {
                        isSafe = true;
                        break;
                    }
                }
            }
            System.out.println("isSafe: " + isSafe);
            if (isSafe) {
                safeCount++;
            }
        }
        return safeCount;
    }

    private static boolean checkIfSafe(List<Integer> list) {
        int current = -1;
        boolean isIncreasing = true;
        boolean isSafe = true;
        for (int i = 0; i < list.size(); i++) {
            if (current == -1) {
                isIncreasing = (list.get(i) < list.get(i + 1));
            } else if ((isIncreasing && list.get(i) > current) || (!isIncreasing && list.get(i) < current)) {
                int difference = Math.abs(list.get(i) - current);
                if ( difference > 3 || difference < 1) {
                        isSafe = false;
                }
            } else {
                    isSafe = false;
            }
            current = list.get(i);
        }
        return isSafe;
    }

    private void readInput() {
        try (BufferedReader in = Files.newBufferedReader(Paths.get(filePath))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] columns = inputLine.split("\\s+");
                List <Integer> list = new ArrayList<>();
                for (String column : columns) {
                    list.add(Integer.parseInt(column));
                }
                data.add(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) { new Day02().day02(); }
}
