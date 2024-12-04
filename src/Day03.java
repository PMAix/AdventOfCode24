import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class Day03 {
    private static final String MUL_REGEX = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
    private static final String DONT_REGEX = "don't\\(\\)";
    private static final String DO_REGEX = "do\\(\\)";
    String filePath = "input/day03.txt";

    public static void main(String[] args) {
        new Day03().day03();
    }

    public void day03() {
        System.out.println("Result: " + parseAndCalculate(false));
        System.out.println("Result with conditions: " + parseAndCalculate(true));
    }

    public int parseAndCalculate(boolean includingConditions) {
        String data = readInput();
        List<int[]> dataList;
        if (includingConditions) {
            dataList = cleanDataWithConditions(data);
        } else {
            dataList = cleanData(data);
        }
        return calculate(dataList);
    }

    private int calculate(List<int[]> dataList) {
        return dataList.stream().mapToInt(arr -> arr[0] * arr[1]).sum();
    }

    public List<int[]> cleanData(String data) {
        List<int[]> dataList = new ArrayList<>();
        Pattern pattern = Pattern.compile(MUL_REGEX);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            dataList.add(new int[]{x, y});
        }
        return dataList;
    }

   public List<int[]> cleanDataWithConditions(String data) {
    List<int[]> dataList = new ArrayList<>();
    Pattern mulPattern = Pattern.compile(MUL_REGEX);
    Pattern dontPattern = Pattern.compile(DONT_REGEX);
    Pattern doPattern = Pattern.compile(DO_REGEX);
    Matcher mulMatcher = mulPattern.matcher(data);
    Matcher dontMatcher = dontPattern.matcher(data);
    Matcher doMatcher = doPattern.matcher(data);

    boolean collecting = true;
    int lastEnd = 0;

    while (mulMatcher.find()) {


        while (dontMatcher.find(lastEnd) && dontMatcher.start() < mulMatcher.start()) {
            collecting = false;
            lastEnd = dontMatcher.end();
        }
        while (doMatcher.find(lastEnd) && doMatcher.start() < mulMatcher.start()) {
            collecting = true;
            lastEnd = doMatcher.end();
        }

        if (collecting) {
            int x = Integer.parseInt(mulMatcher.group(1));
            int y = Integer.parseInt(mulMatcher.group(2));
            dataList.add(new int[]{x, y});
        }

        lastEnd = mulMatcher.end();
    }
    return dataList;
}
    private String readInput() {
        String content = "";
        try {
            content = Files.readString(Paths.get(filePath));
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}