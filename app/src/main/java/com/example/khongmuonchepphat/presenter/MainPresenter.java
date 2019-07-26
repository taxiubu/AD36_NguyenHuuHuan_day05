package com.example.khongmuonchepphat.presenter;

import com.example.khongmuonchepphat.model.CheckUser;
import com.example.khongmuonchepphat.model.User;

import java.util.List;

public class MainPresenter implements CheckUser {

    @Override
    public int duplicateUsername(String username, List<User> userList) {
        int check=0;
        if(username==null){
            check=0;
        }
        else {
            for(User user:userList){
                if(username.equals(user.getUsername())){
                    check++;
                }
            }
        }
        return check;
    }

    @Override
    public int duplicatePassword(String password, List<User> userList) {
        int check=0;
        if(password==null)
            check=0;
        else {
            for(User user:userList){
                if(password.equals(user.getPassword()))
                    check++;
            }
        }
        return check;
    }

    @Override
    public boolean passwordConditions(String password) {
        if (password==null)
            return false;
        else {
            String number="0123456789";
            String sepChar="!@#$%^&*()><?_+=-";
            String lowLet= "qwertyuiopasdfghjklzxcvbnm";
            String capLet= lowLet.toUpperCase();
            int check1=0, check2=0, check3=0, check4=0;
            int size= password.length();

            for(int i=0; i<size; i++){
                if(number.contains(String.valueOf(password.charAt(i))))
                    check1++;
                if(sepChar.contains(String.valueOf(password.charAt(i))))
                    check2++;
                if(lowLet.contains(String.valueOf(password.charAt(i))))
                    check3++;
                if(capLet.contains(String.valueOf(password.charAt(i))))
                    check4++;
            }
            if (check1!=0 && check2!=0 && check3!=0 && check4!=0)
                return true;
            else
                return false;
        }

    }

    @Override
    public int userCheck(User user, List<User> userList) {
        int check=0;
        if (user==null)
            check=0;
        else {
            for (User userInList:userList){
                if (user.getUsername().equals(userInList.getUsername()) &&
                        user.getPassword().equals(userInList.getPassword()))
                    check++;
            }
        }
        return check;
    }

    @Override
    public int checkRePass(String password, String rePassword) {
        int check=0;
        if (password==null || rePassword==null)
            check=0;
        else if(password.equals(rePassword))
            check++;
        return check;
    }
}
