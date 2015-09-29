package com.telenav.lucene.core.utils;

import ch.hsr.geohash.GeoHash;
import com.spatial4j.core.context.SpatialContext;
import org.apache.lucene.spatial.prefix.tree.Cell;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;

import java.util.ArrayList;
import java.util.List;

public class GeohashHelper {

    public final static SpatialContext spatialContext = SpatialContext.GEO;

    public static List<String> getAdjacentGeohashes(double lat, double lon, int len) {
        GeoHash geohash = GeoHash.withCharacterPrecision(lat, lon, len);

        List<String> geohashStrs = new ArrayList<String>();
        geohashStrs.add(geohash.toBase32());
        for (GeoHash adjacent : geohash.getAdjacent()) {
            geohashStrs.add(adjacent.toBase32());
        }
        return geohashStrs;
    }

    public static List<Cell> getCircleGeohashCells(double lat, double lon, double distance, int len) {
        SpatialPrefixTree grid = new GeohashPrefixTree(spatialContext, len);
        List<Cell> cells = grid.getCells(
                GeohashHelper.spatialContext.makeCircle(lon, lat, distance), grid.getLevelForDistance(0), false, false);
        return cells;
    }

}

