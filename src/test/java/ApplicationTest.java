import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ApplicationTest {
    @Test
    public void testApplicationSuccess() {
        String args[] = {"src/main/resources/userGroupPercentages.json"};

        String input[] = {"5", "user1", "user2", "user4", "user2", "user5", "user3", "Q"};
        String userInput = buildInput(input);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        Application.main(args);
        String[] output = outputStream.toString().split(System.lineSeparator());

        Assert.assertEquals(Result.SUCCESS.name(), contains(input, output));
    }

    @Test
    public void testApplicationGroupsFull() {
        String args[] = {"src/main/resources/userGroupPercentages.json"};

        String input[] = {"5", "user1", "user2", "user4", "user2", "user5", "user3", "user6", "user7", "Q"};
        String userInput = buildInput(input);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        Application.main(args);
        String[] output = outputStream.toString().split(System.lineSeparator());

        Assert.assertEquals(Result.GROUPS_FULL.name(), (contains(input, output)));
    }

    public String buildInput (String[] input) {
        StringBuilder userInput = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            userInput.append(s);
            if (i != input.length - 1 ) {
                userInput.append(System.lineSeparator());
            }
        }
        return userInput.toString();
    }

    public String contains(String[] input, String[] output) {
        int j = 1;
        for (int i = 2 ; i < output.length; i = i + 2) {
            String inputUser = input[j++];
            String expectedLine = String.format("getUserGroup(%s) -->", inputUser);
            if (!output[i].contains(expectedLine)) {
                if (output[i].contains("All groups are fully occupied. We can not process new users!")) {
                    return Result.GROUPS_FULL.name();
                } else {
                    return Result.FAILED.name();
                }
            }
        }
        return Result.SUCCESS.name();
    }
}
