package com.mygdx.game.system.logic;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.mygdx.game.component.Bounds;
import com.mygdx.game.component.Position;


@Wire
public class CollisionSystem extends BaseSystem {

    private ComponentMapper<Bounds> bm;
    private ComponentMapper<Position> pm;

    public final boolean overlaps( final Entity a, final Entity b)
    {
        final Bounds b1 = bm.getSafe(a);
        final Position p1 =  pm.getSafe(a);
        final Bounds b2 = bm.getSafe(b);
        final Position p2 =  pm.getSafe(b);

        if ( b1==null || p1 ==null || b2==null || p2==null)
            return false;

        final float minx = p1.x + b1.offsetX;
        final float miny = p1.y + b1.offsetY;
        final float maxx = p1.x + b1.width;
        final float maxy = p1.y + b1.height;

        final float bminx = p2.x + b2.offsetX;
        final float bminy = p2.y + b2.offsetY;
        final float bmaxx = p2.x + b2.width;
        final float bmaxy = p2.y + b2.height;

        return
                !(minx > bmaxx || maxx < bminx ||
                  miny > bmaxy || maxy < bminy );
    }

    @Override
    protected void processSystem() {

    }
}
