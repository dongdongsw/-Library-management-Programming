package application;

import java.util.ArrayList;

public class LoginController {
    private static ArrayList<User> users = new ArrayList<>();

    static {
        // 임의의 사용자 추가
        users.add(new User("user1", "pass1"));
        users.add(new User("user2", "pass2"));
       
    }

    public static boolean checkLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // 일치하는 사용자가 발견되면 true 반환
            }
        }
        return false; // 일치하는 사용자가 없으면 false 반환
    }
}
