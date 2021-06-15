import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        //Read userGroupPercentage File
        String  filePath= args[0];
        HashMap<String, Double> userGroupPercentage = setUpPercentageMap(filePath);

        //Read UserCount and UserIds
        GroupAssignerService groupAssignerService = GroupAssignerService.getGroupAssignerService();
        Scanner scanner = new Scanner(System.in);
        Util.log("Enter total number of users : ");
        int users = scanner.nextInt();
        scanner.nextLine();
        groupAssignerService.setGroups(userGroupPercentage, users);

        boolean exit = false;
        while (!exit) {
            Util.log("Enter user_id or Q to exit: ");
            String input = scanner.nextLine();
            if ("Q".equals(input)) {
                exit = true;
            } else {
                String groupName = groupAssignerService.getUserGroup(input);
                if (StringUtils.isNotEmpty(groupName)) {
                    Util.log(String.format("getUserGroup(%s) --> %s", input, groupName));
                }
            }
        }
    }

    public static HashMap<String, Double> setUpPercentageMap(String filePath) {
        HashMap<String, Double> userGroupPercentage = new HashMap<>();
        try  {
            FileReader file = new FileReader(filePath);
            JsonReader jsonReader = new JsonReader(file);
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String groupName = jsonReader.nextName();
                if (groupName.contains("group")) {  //valid group
                    Double percentage = jsonReader.nextDouble();
                    userGroupPercentage.put(groupName, percentage);
                }
            }

        } catch (FileNotFoundException e) {
            Util.log("UserGroupPercentage File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Util.log("Json Exception Occurred");
            e.printStackTrace();
        }
        return userGroupPercentage;
    }
}
