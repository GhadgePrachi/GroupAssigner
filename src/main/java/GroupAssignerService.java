import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GroupAssignerService {
    private static GroupAssignerService service;
    private List<Group> groups;
    private HashMap<String, String> userGroupMapper;

    public static GroupAssignerService getGroupAssignerService() {
        if (service == null) {
            synchronized (GroupAssignerService.class) {
                if (service == null) {
                    service = new GroupAssignerService();
                    service.userGroupMapper = new HashMap<>();
                    service.groups = new ArrayList<>();
                }
            }
        }
        return service;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public HashMap<String, String> getUserGroupMapper() {
        return userGroupMapper;
    }

    public void setGroups(HashMap<String, Double> userGroupPercentage, int userCount) {
        for (String groupName : userGroupPercentage.keySet()) {
            Double percentage = userGroupPercentage.get(groupName);
            percentage = percentage * 100;
            int capacity = (int) Math.round(percentage * userCount / 100);

            Group group = new Group();
            group.setGroupName(groupName);
            group.setCapacity(capacity);
            group.setSize(0);
            service.getGroups().add(group);
        }
    }

    public String getUserGroup(String userId) {
        String groupName;
        if (service.getUserGroupMapper().containsKey(userId)) {
            groupName = service.getUserGroupMapper().get(userId);
        } else {
            groupName = assignGroup(userId);
        }
        return groupName;
    }

    public String assignGroup(String userId) {
        if (areAllGroupsFull()) {
            Util.log("All groups are fully occupied. We can not process new users!");
            return "";
        }

        String groupName;
        while (true) {
            Random random = new Random();
            int randomGroupIndex = random.nextInt(service.getGroups().size());
            Group randomGroup = service.getGroups().get(randomGroupIndex);
            if (randomGroup.getSize() < randomGroup.getCapacity()) {
                groupName = randomGroup.getGroupName();
                randomGroup.setSize(randomGroup.getSize() + 1);
                service.getUserGroupMapper().put(userId, groupName);
                break;
            }
        }
        return groupName;
    }

    public boolean areAllGroupsFull() {
        for (Group group : service.getGroups()) {
            if (group.getSize() < group.getCapacity()) {
                return false;
            }
        }
        return true;
    }
}
