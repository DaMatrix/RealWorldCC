package net.daporkchop.realmapcc.data.client;

import net.daporkchop.lib.common.util.PorkUtil;
import net.daporkchop.lib.graphics.PImage;
import net.daporkchop.lib.graphics.impl.image.DirectImage;
import net.daporkchop.lib.math.arrays.grid.Grid2d;
import net.daporkchop.lib.math.interpolation.InterpolationEngine;
import net.daporkchop.lib.math.interpolation.LinearInterpolationEngine;
import net.daporkchop.realmapcc.Constants;
import net.daporkchop.realmapcc.RealmapCC;
import org.apache.commons.imaging.ImageWriteException;

import java.io.IOException;

import static net.daporkchop.lib.math.primitive.PMath.floorI;

/**
 * @author DaPorkchop_
 */
public class TestDataVisualization implements Constants {
    public static void main(String... args) throws IOException, ImageWriteException {
        if (true)   {
            RealmapCC.Conf.dataCacheDir = "/home/daporkchop/192.168.1.119/Public/minecraft/mods/realworldcc/data/";
            RealmapCC.Conf.maxTileCacheSize = 2048L;
        }

        if (true)   {
            InterpolationEngine engine = ENGINE_CUBIC;
            DataProcessor processor = new DataProcessor();

            //centered precisely on Switzerland!
            double minLon = 5.0d;
            double maxLat = 48.0d;
            int w = 1600;
            double hMult = 3.0d / 5.0d;

            int h = floorI(w * hMult);
            double scaleBase = 5.0d;
            double scale = 1.0d / w * scaleBase;

            PImage img = new DirectImage(w, h, false);
            Grid2d otherGrid = Grid2d.of(w, h, true);
            Grid2d finalGrid = Grid2d.of(w, h, false);

            processor.forEachValueInRange(
                    minLon,
                    maxLat - h * scale,
                    w,
                    h,
                    scale,
                    (xStep, yStep, height, waterPresence, biome) -> {
                        otherGrid.setI(xStep, yStep, height);
                        finalGrid.setI(xStep, yStep, waterPresence ? 1 : 0);
                    },
                    null
            );

            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int x = w - 1; x >= 0; x--) {
                for (int y = h - 1; y >= 0; y--) {
                    int val = otherGrid.getI(x, y);
                    if (val > max)  {
                        max = val;
                    }
                    if (val < min)  {
                        min = val;
                    }
                }
            }

            System.out.println("Finishing up...");
            for (int x = w - 1; x >= 0; x--) {
                for (int y = h - 1; y >= 0; y--) {
                    if (finalGrid.getI(x, y) == 1)   {
                        img.setRGB(x, y, 0xFF0000);
                    } else {
                        img.setBW(x, y, (otherGrid.getI(x, y) - min) * 256 / max);
                    }
                }
            }

            PorkUtil.simpleDisplayImage(img.getAsBufferedImage(), true);
            //Imaging.writeImage(img.getAsBufferedImage(), new File("./cool.png"), ImageFormats.PNG, null);
        }
    }
}
