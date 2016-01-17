package com.mygdx.game.system.passive;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Waypoint;
import com.mygdx.game.util.PathHelper;

@Wire
public class Astar extends BaseSystem {
	
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Waypoint> waypointMapper;
	
	private Vector2 helper1 = new Vector2();
	private Vector2 helper2 = new Vector2();
	
	public LinkedList<Entity> findPath(Entity from, Entity to) {
        List<PathHelper> search = new LinkedList<PathHelper>();
        search.add(createPathPoint(from,null,to));
        HashSet<Integer> saw = new HashSet<>();

        boolean found = false;
        PathHelper pop = null;
        int counter = 0;
        
        saw.add(from.id);
        while(!found) {
        	counter++;
        	if(counter > 500 || search.size() == 0) {
        		System.out.println("Loop");
        		return null;
        	}
        	
        	Collections.sort(search);
        	 pop = search.get(0);
        	 search.remove(0);
        	 if(pop.obj == to)
        		 break;

        	 Waypoint waypoint = waypointMapper.get(pop.obj);
        	 for(Entity ent : waypoint.neighbors) {
        		 if( !saw.contains(ent.id)) {
        			 PathHelper pathPoint = createPathPoint(ent, pop, to);
        			 search.add(pathPoint);
	        		 saw.add(ent.id);	 
        		 }
        	 }
        }

        LinkedList<Entity> path = new LinkedList<Entity>();
        path.add(pop.obj);
        while(pop.previous != null)
        {
            pop = pop.previous;
            path.addFirst(pop.obj);
        }
        
        return path;
	}
	
	public PathHelper createPathPoint(Entity startEntity, PathHelper from, Entity target) {
        PathHelper ph = new PathHelper();
        ph.obj = startEntity;
        
        Position posStart = positionMapper.get(startEntity);
        if(from != null) {
        	Position posActual = positionMapper.get(from.obj);
        	helper1.set(posStart.x, posStart.y);
        	helper2.set(posActual.x, posActual.y);
        	ph.fromStart = from.fromStart + helper1.sub(helper2).len();
        } else
        	ph.fromStart = 0;
        
        Position posTarget = positionMapper.get(target);
        helper1.set(posStart.x, posStart.y);
        helper2.set(posTarget.x, posTarget.y);
        ph.heurestic = helper2.sub(helper1).len();
        ph.previous = from;

        return ph;
    }

	@Override
	protected void processSystem() {
	}
}
