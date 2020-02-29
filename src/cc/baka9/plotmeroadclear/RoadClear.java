package cc.baka9.plotmeroadclear;

import com.worldcretornica.plotme.PlotMapInfo;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;

public class RoadClear {
    private double plotsize;
    private double pathsize;

    private byte wall;
    private short wallid;
    private byte floor1;
    private short floor1id;
    private byte floor2;
    private short floor2id;

    private byte pillar;
    private short pillarid;


    private int roadheight;


    public RoadClear(PlotMapInfo pmi){
        plotsize = pmi.PlotSize;
        pathsize = pmi.PathWidth;
        wall = pmi.WallBlockValue;
        wallid = pmi.WallBlockId;
        floor2 = pmi.RoadMainBlockValue;
        floor2id = pmi.RoadMainBlockId;
        floor1 = pmi.RoadStripeBlockValue;
        floor1id = pmi.RoadStripeBlockId;
        roadheight = pmi.RoadHeight;

        pillar = pmi.PlotFillingBlockValue;
        pillarid = pmi.PlotFillingBlockId;
    }

    public void populate(World w, Chunk chunk){
        int cx = chunk.getX();
        int cz = chunk.getZ();

        int xx = cx << 4;
        int zz = cz << 4;

        double size = plotsize + pathsize;
        int valx;
        int valz;

        double n1;
        double n2;
        double n3;
        int mod2 = 0;
        int mod1 = 1;

        if (pathsize % 2 == 1) {
            n1 = Math.ceil(pathsize / 2) - 2;
            n2 = Math.ceil(pathsize / 2) - 1;
            n3 = Math.ceil(pathsize / 2);
        } else {
            n1 = Math.floor(pathsize / 2) - 2;
            n2 = Math.floor(pathsize / 2) - 1;
            n3 = Math.floor(pathsize / 2);
        }

        if (pathsize % 2 == 1) {
            mod2 = -1;
        }

        for (int x = 0; x < 16; x++) {
            valx = (cx * 16 + x);

            for (int z = 0; z < 16; z++) {
                valz = (cz * 16 + z);

                int y = roadheight;

                if ((valx - n3 + mod1) % size == 0 || (valx + n3 + mod2) % size == 0) {
                    boolean found = false;
                    for (double i = n2; i >= 0; i--) {
                        if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        setBlock(w, x + xx, y, z + zz, floor1, floor1id);
                        fillAndClear(w, x + xx, y, z + zz);
                    } else {
                        setBlock(w, x + xx, y + 1, z + zz, wall, wallid);
                        fillAndClear(w, x + xx, y + 1, z + zz);


                    }
                } else {
                    boolean found5 = false;
                    for (double i = n2; i >= 0; i--) {
                        if ((valx - i + mod1) % size == 0 || (valx + i + mod2) % size == 0) {
                            found5 = true;
                            break;
                        }
                    }

                    if (!found5) {
                        if ((valz - n3 + mod1) % size == 0 || (valz + n3 + mod2) % size == 0) {
                            setBlock(w, x + xx, y + 1, z + zz, wall, wallid);
                            fillAndClear(w, x + xx, y + 1, z + zz);
                        }
                    }


                    if ((valx - n2 + mod1) % size == 0 || (valx + n2 + mod2) % size == 0) //middle+2
                    {
                        if ((valz - n3 + mod1) % size == 0 || (valz + n3 + mod2) % size == 0
                                || (valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0) {
                            setBlock(w, x + xx, y, z + zz, floor1, floor1id);
                            fillAndClear(w, x + xx, y, z + zz);
                        } else {
                            setBlock(w, x + xx, y, z + zz, floor2, floor2id);
                            fillAndClear(w, x + xx, y, z + zz);
                        }
                    } else if ((valx - n1 + mod1) % size == 0 || (valx + n1 + mod2) % size == 0) //middle+2
                    {
                        if ((valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0
                                || (valz - n1 + mod1) % size == 0 || (valz + n1 + mod2) % size == 0) {

                            setBlock(w, x + xx, y, z + zz, floor2, floor2id);
                            fillAndClear(w, x + xx, y, z + zz);
                        } else {

                            setBlock(w, x + xx, y, z + zz, floor1, floor1id);
                            fillAndClear(w, x + xx, y, z + zz);
                        }
                    } else {
                        boolean found = false;
                        for (double i = n1; i >= 0; i--) {
                            if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
                                found = true;
                                break;
                            }
                        }

                        if (found) {

                            setBlock(w, x + xx, y, z + zz, floor1, floor1id);
                            fillAndClear(w, x + xx, y, z + zz);
                        } else {
                            if ((valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0) {

                                setBlock(w, x + xx, y, z + zz, floor2, floor2id);
                                fillAndClear(w, x + xx, y, z + zz);
                            } else {
                                boolean found2 = false;
                                for (double i = n1; i >= 0; i--) {
                                    if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
                                        found2 = true;
                                        break;
                                    }
                                }

                                if (found2) {

                                    setBlock(w, x + xx, y, z + zz, floor1, floor1id);
                                    fillAndClear(w, x + xx, y, z + zz);
                                } else {
                                    boolean found3 = false;
                                    for (double i = n3; i >= 0; i--) {
                                        if ((valx - i + mod1) % size == 0 || (valx + i + mod2) % size == 0) {
                                            found3 = true;
                                            break;
                                        }
                                    }

                                    if (found3) {
                                        setBlock(w, x + xx, y, z + zz, floor1, floor1id);
                                        fillAndClear(w, x + xx, y, z + zz);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void fillAndClear(World w, int x, int y, int z){
        for (int i = y - 1; i > 0; i--) {
            setBlock(w, x, i, z, pillar, pillarid);
        }
        for (int i = y + 1; i < 256; i++) {
            setBlock(w, x, i, z, (byte) 0, (short) 0);
        }
    }

    private void setBlock(World w, int x, int y, int z, byte val, short id){
        Block block = w.getBlockAt(x, y, z);
        if (block.getType() != Material.WALL_SIGN) {
            if (block.getTypeId() != id || block.getData() != val) {
                if (val != 0) {
                    block.setTypeIdAndData(id, val, false);
                } else {
                    block.setTypeId(id);
                }
            }
        }
    }
}
