/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.Util;

import java.util.HashMap;
import java.util.Map;
import me.zhehe.FishR.Type;
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
    
    public static final String LIFE = "寿命";
    public static final String PRO = "生产速度";
    public static final String TEMP_ADAP = "温度适应性";
    public static final String OXYGEN_ADAP = "耗氧量";
    public static final String WATER_TYPE = "习性";
    public static final String WATER_SALT = "咸水鱼";
    public static final String WATER_SWEET = "淡水鱼";
    public static final String FISH_SPECIAL = "特性";
    public static final String GENDER = "性别";
    public static final String MALE = "雄";
    public static final String FEMALE = "雌";
    public static final String DARK = "穴居性";
    public static final String MUL_TYPE = "混血";
    public static Map<FishSpecial, String> FISHSPECIAL_DICT;
    public static Map<Integer, String> LIFE_DICT;
    public static Map<FishType, String> FISHTYPE_DICT;
    public static String LORE_COLOR = "§2";
    
    public static final String RED = "§4";
    public static final String BLUE = "§1";
    public static final String WHITE = "§f";
    
    public static final String FISH_CATCHER = "捕鱼箱";
    public static final String FISH_CATCHER_PLACE = "成功创建捕鱼箱";
    public static final String FISH_CATCHER_FAIL = "捕鱼箱需放在宽阔的水中";
    
    public static final String FISH_WORLD = "fish_world";
    
    public static final String FISH_DECO = "饰品";
    public static final String FISH_COOKIE = "幸运海韵曲奇";
    public static final String FISH_COOKIE_TAG = "LuckyCookie";
    
    public static final String FISH_BREAD = "量子鳕鱼三明治";
    public static final String FISH_BREAD_TAG = "DimensionBread";
    
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
        FISHSPECIAL_DICT = new HashMap<>();
        FISHSPECIAL_DICT.put(FishSpecial.NONE, "无特性");
        FISHSPECIAL_DICT.put(FishSpecial.YANHUOWANHUI, "烟火晚会");
        FISHSPECIAL_DICT.put(FishSpecial.YUANDING, "园丁");
        FISHSPECIAL_DICT.put(FishSpecial.HULI, "护理");
        FISHSPECIAL_DICT.put(FishSpecial.XIDAPUBEN, "喜大普奔");
        FISHSPECIAL_DICT.put(FishSpecial.GUOJIABAOZANG, "国家宝藏");
        FISHSPECIAL_DICT.put(FishSpecial.SHIDAI, "时代");
        FISHSPECIAL_DICT.put(FishSpecial.DABAOJIAO, "大堡礁");
        
        //15 18 20 25 30 32 35
        LIFE_DICT = new HashMap<>();
        LIFE_DICT.put(0, "早夭");
        LIFE_DICT.put(1, "短寿");
        LIFE_DICT.put(2, "较短寿");
        LIFE_DICT.put(3, "正常");
        LIFE_DICT.put(4, "较长寿");
        LIFE_DICT.put(5, "长寿");
        LIFE_DICT.put(6, "寿星");
        
        FISHTYPE_DICT = new HashMap<>();
        FISHTYPE_DICT.put(FishType.PINGFAN, "平凡");
        FISHTYPE_DICT.put(FishType.PUSU, "朴素");
        FISHTYPE_DICT.put(FishType.PUSHI, "朴实");
        FISHTYPE_DICT.put(FishType.CHONGLANG, "冲浪");
        FISHTYPE_DICT.put(FishType.QIANSHUI, "潜水");
        FISHTYPE_DICT.put(FishType.TOUTIE, "头铁");
        FISHTYPE_DICT.put(FishType.TIAOYUE, "跳跃");
        FISHTYPE_DICT.put(FishType.JILIU, "激流");
        FISHTYPE_DICT.put(FishType.JIYOU, "激游");
        FISHTYPE_DICT.put(FishType.NILIU, "逆流");
        FISHTYPE_DICT.put(FishType.JIANYUE, "溅跃");
        FISHTYPE_DICT.put(FishType.YUANGU, "远古");
        FISHTYPE_DICT.put(FishType.DITU, "地图");
        FISHTYPE_DICT.put(FishType.SHIZU, "始祖");
        FISHTYPE_DICT.put(FishType.CHENCHUAN, "沉船");
        FISHTYPE_DICT.put(FishType.BAOZANG, "宝藏");
        FISHTYPE_DICT.put(FishType.HUASHI, "化石");
        FISHTYPE_DICT.put(FishType.HAIREN, "海壬");
        FISHTYPE_DICT.put(FishType.DENGLONG, "灯笼");
        FISHTYPE_DICT.put(FishType.HUAYANG, "花漾");
        FISHTYPE_DICT.put(FishType.MEILI, "美丽");
        FISHTYPE_DICT.put(FishType.ZHUANGJIA, "装甲");
        FISHTYPE_DICT.put(FishType.HEJIN, "合金");
        FISHTYPE_DICT.put(FishType.DONGHAI, "东海");
        FISHTYPE_DICT.put(FishType.SANSHA, "三沙");
        FISHTYPE_DICT.put(FishType.BAODAO, "宝岛");
        FISHTYPE_DICT.put(FishType.BOHAI, "渤海");
        FISHTYPE_DICT.put(FishType.WEIZHI, "未知");
        FISHTYPE_DICT.put(FishType.YOULING, "幽灵");
        FISHTYPE_DICT.put(FishType.SHENMI, "神秘");
        FISHTYPE_DICT.put(FishType.ZHENZHU, "珍珠");
        FISHTYPE_DICT.put(FishType.ZUANSHI, "钻石");
    }
}
