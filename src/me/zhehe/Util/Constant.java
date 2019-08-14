/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.Util;

import java.util.HashMap;
import java.util.Map;
import me.zhehe.FishR.Type.FishSpecial;
import me.zhehe.FishR.Type.FishType;
import org.bukkit.block.Biome;

/**
 *
 * @author Zhehe
 */
public class Constant {
    public static final String FISH_TAG = "Mariculture Fish";
    public static final int[] LIFE_ARRAY = {15, 18, 20, 25, 30, 32, 35};
    public static final int[] PRO_TICK = { 30, 26, 22, 18, 14, 10, 6 };
    
    public static String LORE_COLOR = "§2";
    
    public static final String RED = "§4";
    public static final String BLUE = "§1";
    public static final String WHITE = "§f";
    
    public static final String FISH_WORLD = "fish_world";
    
    public static final String FISH_CATCHER_DBNAME = "fish_catcher";
    
    public static enum TankErrorCode {
        None, Unknown, NoWater, NoFish, Gender, TooDark, TooLight, NoSweetWater, NoSaltWater, Oxygen, Temperature,
    }
    
    public static final String DATA_TAG = "data_tag:";
    
    public static Biome[] OCEAN = {
        Biome.WARM_OCEAN,
        Biome.LUKEWARM_OCEAN,
        Biome.DEEP_LUKEWARM_OCEAN,
        Biome.OCEAN,
        Biome.DEEP_OCEAN,
        Biome.COLD_OCEAN,
        Biome.DEEP_COLD_OCEAN,
        Biome.FROZEN_OCEAN,
        Biome.DEEP_FROZEN_OCEAN,
    };
    
    public static double[] TEMPERATURE_LIST = {
        -0.1,
        0,
        0.2, 0.4, 0.6, 0.8, 1,
        1.2,
        1.5,
    };
    
    public static Constant instance = new Constant();
    
    private Constant() {
    }
}
