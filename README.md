# Project Info
## Requirements :
### Input -
Args  : UserGroupPercentage File
Stdin : UserCount
Stdin : Random UserIds

### Output -
For a given UserId, print the assigned GroupName in the format getUserGroup("{user_id}") --> "{group_name}}"

## Assumptions :
Following are the assumptions made while designing the solution.

1. Total number of users is known and does not change.
2. UserId details are provided via standard input.
3. Total of all group percentage is always 1.0
4. The effective distribution of capacity among all groups will be done by giving priority to
accommodating requested number of users. So in certain cases we might have capacity slight above
the total number of requested users.

## Solution
### Approaches considered for mapping user to a group

1. Round Robin -
This is sequential group assignment where we assign user to a group if the group has not reached capacity and
move to next group for next user and so on

2. First Come First Serve -
This is sequential group assignment where we start assigning users to groupA and once we max groupA's capacity
to hold users, proceed to group B and so on.

3. Randomized Group Selection -
This will imply that the group selection will be done randomly. So we select the group at random, however if 
the group is full then the function will keep recomputing to find the suitable random group, keeping the 
criteria in check.

### Implemented Approach - Approach 3


# How to Run

1. Download this maven project or clone from github.
2. Run mvn clean install.
3. Run the Application.java class by specifying the input file userGroupPercentages.json as follows -
    java Application src/main/resources/userGroupPercentages.json
   

# Sample Run Log

Enter total number of users :
10
Enter user_id or Q to exit:
user1
getUserGroup(user1) --> groupA
Enter user_id or Q to exit:
user2
getUserGroup(user2) --> groupB
Enter user_id or Q to exit:
user2
getUserGroup(user2) --> groupB
Enter user_id or Q to exit:
user 3
getUserGroup(user 3) --> groupC
Enter user_id or Q to exit:
user4
getUserGroup(user4) --> groupA
Enter user_id or Q to exit:
user5
getUserGroup(user5) --> groupC
Enter user_id or Q to exit:
user6
getUserGroup(user6) --> groupA
Enter user_id or Q to exit:
user7
getUserGroup(user7) --> groupC
Enter user_id or Q to exit:
user8
getUserGroup(user8) --> groupC
Enter user_id or Q to exit:
user9
getUserGroup(user9) --> groupC
Enter user_id or Q to exit:
user10
getUserGroup(user10) --> groupA
Enter user_id or Q to exit:
user3
All groups are fully occupied. We can not process new users!
Enter user_id or Q to exit:
user 3
getUserGroup(user 3) --> groupC
Enter user_id or Q to exit:
Q

Process finished with exit code 0

