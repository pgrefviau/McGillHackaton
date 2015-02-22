package com.hackaton.findme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WAGINA on 2015-02-22.
 */
public class DummyFriendProviderService implements IUserProviderService {
    @Override
    public List<BetaFriend> getFriendsList() {

        List<BetaFriend> betaFriends = new ArrayList<BetaFriend>();

        for (int i=0;i<20; ++i)
        {
            String name = "Friend "+i;
            int id = i;
            BetaFriend betaFriend = new BetaFriend(name, id);
            betaFriends.add(betaFriend);
        }

        return betaFriends;
    }

}
