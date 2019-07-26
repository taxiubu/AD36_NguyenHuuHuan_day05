package com.example.khongmuonchepphat.model;
import java.util.List;

public interface CheckUser {
    public int duplicateUsername(String username, List<User> userList);// kiểm tra username có trùng k
    public int duplicatePassword(String password, List<User> userList);// kiểm tra pass có trùng k
    public boolean passwordConditions(String password); // kiểm tra dkien tạo mk
    public int userCheck(User user, List<User> userList); // kiểm tra thông tin đăng nhập
    public int checkRePass(String password, String rePassword);

}
