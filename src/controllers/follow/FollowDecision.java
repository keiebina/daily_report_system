package controllers.follow;


import java.util.List;

public class FollowDecision {

    public static Boolean isFollowing(Integer follow_id, List<Integer> follow_ids){

        if(follow_ids.size() > 0 ){
            for(int i = 0; i < follow_ids.size(); i++){
                if(follow_id.equals(follow_ids.get(i))){
                    return  true;
                }
            }
            return false;

        }else{
            return false;
        }

    }
}