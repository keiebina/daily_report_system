package controllers.follow;


import java.util.List;

import models.Employee;

public class FollowDecision {

    public static Boolean isFollowing(Integer followId, List<Employee> follows){

        if(follows.size() > 0 ){
            for(int i = 0; i < follows.size(); i++){
                if(followId.equals(follows.get(i).getId())){
                    return  true;
                }
            }
            return false;

        }else{
            return false;
        }

    }
}