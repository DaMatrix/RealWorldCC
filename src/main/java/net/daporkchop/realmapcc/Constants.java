package net.daporkchop.realmapcc;

import lombok.NonNull;
import net.daporkchop.lib.graphics.util.ImageInterpolator;
import net.daporkchop.lib.math.interpolation.CubicInterpolationEngine;
import net.daporkchop.lib.math.interpolation.LinearInterpolationEngine;
import net.daporkchop.lib.math.vector.d.Vec2d;
import net.daporkchop.lib.math.vector.i.Vec2i;
import net.minecraft.util.math.BlockPos;
import sun.misc.SoftCache;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.lang.Math.abs;
import static net.daporkchop.lib.math.primitive.PMath.floorI;

/**
 * Some random constant values that are used throughout the program for easy access
 *
 * @author DaPorkchop_
 */
public interface Constants {
    //ImageParser tiffParser = new TiffImageParser();

    int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    //the limits of the data that exists
    //only data inside of this region will be used
    //these values imply the presence of data up until the very end of that number (i.e. until -179.99999999999)
    int LONGITUDE_MAX = 180;
    int LATITUDE_MAX = 59;
    int LONGITUDE_MIN = -179;
    int LATITUDE_MIN = -56;

    //the total size of the usable data region (in °)
    int LON_DEGREES_TOTAL = abs(LONGITUDE_MIN - LONGITUDE_MAX) + 1;
    int LAT_DEGREES_TOTAL = abs(LATITUDE_MIN - LATITUDE_MAX) + 1;

    //the total number of 1°² (one degree squared) segments across the entire globe
    int DEGREE_SEGMENTS = LON_DEGREES_TOTAL * LAT_DEGREES_TOTAL;

    //the number of arc-seconds per degree of arc
    int ARCSECONDS_PER_DEGREE = 3600;

    //the total size of the usable data region (in arc-seconds)
    int LON_ARCSECONDS_TOTAL = LON_DEGREES_TOTAL * ARCSECONDS_PER_DEGREE;
    int LAT_ARCSECONDS_TOTAL = LAT_DEGREES_TOTAL * ARCSECONDS_PER_DEGREE;

    //the number of steps per degree of arc, a step being an ambiguously defined number
    // which represents the number of tiles that each degree of arc will be split into
    // along both axes (i.e. each degree of arc will be split into 16² tiles)
    int STEPS_PER_DEGREE = 16;

    //the total size of the usable data region (in steps)
    int LON_STEPS_TOTAL = LON_DEGREES_TOTAL * STEPS_PER_DEGREE;
    int LAT_STEPS_TOTAL = LAT_DEGREES_TOTAL * STEPS_PER_DEGREE;

    //the number of arc-seconds per step
    int TILE_SIZE = ARCSECONDS_PER_DEGREE / STEPS_PER_DEGREE;
    int TILE_LIMIT = TILE_SIZE * TILE_SIZE * 4;

    double PI = 3.141592653589793238462d;

    int EARTH_RADIUS = 6_378_000;
    int EARTH_CIRCUMFERENCE = floorI((EARTH_RADIUS << 1) * PI);

    double METERS_PER_ARCSECOND = (double) EARTH_CIRCUMFERENCE / (double) LON_ARCSECONDS_TOTAL;

    double ARCSECONDS_PER_METER = (double) LON_ARCSECONDS_TOTAL / (double) EARTH_CIRCUMFERENCE;
    ImageInterpolator INTERPOLATOR_LINEAR = new ImageInterpolator(new LinearInterpolationEngine());
    ImageInterpolator INTERPOLATOR_CUBIC = new ImageInterpolator(new CubicInterpolationEngine());
    //some older values from way back when
    int GLOBCOVER_valuesPerDegree = 360;
    int GLOBCOVER_minLatitude = -65;
    int GLOBCOVER_maxLatitude = 90;

    /**
     * Creates a new instance of {@link SoftCache}.
     * <p>
     * This simply allows not showing compile-time warnings for using internal classes when creating
     * new instances.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return a new {@link SoftCache}
     */
    @SuppressWarnings("unchecked")
    static <K, V> Map<K, V> newSoftCache() {
        return (Map<K, V>) new SoftCache();
    }

    static int mod(int i, int n) {
        int r = i % n;
        return r < 0 ? r + n : r;
        /*if (r < 0) {
            r += n;
        }
        return r;*/
    }

    default void ensureDirExists(@NonNull File dir) {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException(String.format("Couldn't create directory: %s", dir.getAbsolutePath()));
        }
    }

    default void ensureFileExists(@NonNull File f) throws IOException {
        if (!f.exists()) {
            this.ensureDirExists(f.getParentFile());
            if (!f.createNewFile()) {
                throw new IllegalStateException(String.format("Couldn't create file: %s", f.getAbsolutePath()));
            }
        }
    }
}
