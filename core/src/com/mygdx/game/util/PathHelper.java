package com.mygdx.game.util;

import com.artemis.Entity;

public class PathHelper implements Comparable<PathHelper> {
	
	 public Entity obj;
     public float fromStart;
     public float heurestic;
     public PathHelper previous;

     @Override
     public int compareTo(PathHelper other) {
         if (other == null)
             return 1;
         else if (Math.abs((fromStart + heurestic) - (other.fromStart + other.heurestic)) < 0.1f)
         	return 0;
         else if (fromStart + heurestic > other.fromStart + other.heurestic)
             return 1;
         else
             return -1;
     }
}
