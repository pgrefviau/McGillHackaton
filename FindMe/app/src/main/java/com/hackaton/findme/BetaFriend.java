package com.hackaton.findme;

import android.graphics.Point;
import android.location.Location;

/**
 * Created by WAGINA on 2015-02-21.
 */
public class BetaFriend {

    public String name;
    public int id;
    public Point position;
    public Location location;

    public BetaFriend(String name, int id)
    {
        this.id = id;
        this.name = name;
    }

    // TODO: mettre ca plus haut surement
    public int getId()
    {
        return 0;
    }
}
