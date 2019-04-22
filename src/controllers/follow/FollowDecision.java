package controllers.follow;


import java.util.List;

public class FollowDecision {

    public static Boolean isFollowing(String follow_id, List<String> ids){

        if(ids.size() > 0 ){
            for(int i = 0; i < ids.size(); i++){
                if(follow_id.equals(ids.get(i))){
                    return  true;
                }
            }
            return false;

        }else{
            return false;
        }

    }
}