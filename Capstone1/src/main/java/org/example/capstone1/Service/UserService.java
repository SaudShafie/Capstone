package org.example.capstone1.Service;

import org.example.capstone1.Model.Product;
import org.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    ArrayList<User> users= new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }
    public boolean addUser(User user){
        for (User x : users){
            if (x.getId().equals(user.getId())){
                return false;
            }

        }
        users.add(user);
        return true;
    }
    public boolean updateUser(String id , User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }
    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean checkId(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public User getById(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                return users.get(i);
            }
        }
        return null;
    }
    public void deductFromBalance(String userId,double amount){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)){
                users.get(i).setBalance(users.get(i).getBalance()-amount);
            }
        }


    }
    public void addToBalance(String userId,double amount){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)){
                users.get(i).setBalance(users.get(i).getBalance()+amount);
            }
        }


    }
}
