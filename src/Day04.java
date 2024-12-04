import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day04 {
    String filePath = "input/day04.txt";

    public static void main(String[] args) {
        new Day04().day04();
    }

    public void day04() {
        char[][] data = readInput();

        System.out.println("Number of occurrences of 'XMAS': " + countXmas(data));
        System.out.println("Number of occurrences of 'X-MAS': " + countXPattern(data));
    }

    public char[][] readInput() {
        List<char[]> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.toCharArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.toArray(new char[0][]);
    }

    public int countXmas(char[][] data) {
        int count = 0;
        int maxX = data.length;
        int maxY = data[0].length;
        String word = "XMAS";
        int wordLength = word.length();

        // Directions: right, down, down-right, down-left, left, up, up-left, up-right
        int[][] directions = {
                {0, 1}, {1, 0}, {1, 1}, {1, -1},
                {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}
        };

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                for (int[] dir : directions) {
                    int dx = dir[0], dy = dir[1];
                    int nx = x, ny = y;
                    int i;
                    for (i = 0; i < wordLength; i++) {
                        if (nx < 0 || nx >= maxX || ny < 0 || ny >= maxY || data[nx][ny] != word.charAt(i)) {
                            break;
                        }
                        nx += dx;
                        ny += dy;
                    }
                    if (i == wordLength) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int countXPattern(char[][] data) {
        int count = 0;
        int maxX = data.length;
        int maxY = data[0].length;

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (data[x][y] == 'A' && checkDiagonals(data, x, y, maxX, maxY)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean checkDiagonals(char[][] data, int x, int y, int maxX, int maxY) {
        int[][] directions = {
                {1, 1}, {1, -1},
                {-1, 1}, {-1, -1}
        };

        boolean foundPattern1 = false;
        boolean foundPattern2 = false;

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int nx = x - dx, ny = y - dy;
            int px = x + dx, py = y + dy;
            if (isValid(nx, ny, maxX, maxY) && data[nx][ny] == 'M' &&
                    isValid(px, py, maxX, maxY) && data[px][py] == 'S') {
                if (dx == dy) {
                    foundPattern1 = true;
                } else {
                    foundPattern2 = true;
                }
            }
        }
        return foundPattern1 && foundPattern2;
    }

    private boolean isValid(int x, int y, int maxX, int maxY) {
        return x >= 0 && x < maxX && y >= 0 && y < maxY;
    }
}