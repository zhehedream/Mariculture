/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.FishR;

/**
 *
 * @author Zhehe
 */
public class Type {
    public static enum WaterType { SWEET, SALT, BOTH; };
    
    public static enum FishType {
        PINGFAN(0),
        PUSU(1),
        PUSHI(2),
        //PINGFAN x PUSU
        CHONGLANG(3),
        QIANSHUI(4),
        //PINGFAN x PUSHI
        TOUTIE(5),
        //PUSU x PUSHI
        TIAOYUE(6),
        //CHONGLANG x QIANSHUI
        JILIU(7),
        JIYOU(8),
        //TOUTIE x TIAOYUE
        NILIU(9),
        JIANYUE(10),
        
        //PUSU/PINGFAN/PUSHI x QIANSHUI
        YUANGU(11),
        DITU(12),
        SHIZU(13),
        
        //JILIUE x JIYOU
        CHENCHUAN(14),
        BAOZANG(15),
        
        //YUANGU x SHIZU
        HUASHI(16),
        
        //YUANGU x NILIU
        HAIREN(17),
        
        //CHONGLANG x TOUTIE
        DENGLONG(18),
        //CHONGLANG x TIAOYUE
        HUAYANG(19),
        
        //HUAYANG x HAIREN
        MEILI(20),
        
        //TOUTIE x CHENCHUAN
        ZHUANGJIA(21),
        
        //TOUTIE x BAOZANG
        HEJIN(22),
        
        //DITU x HAIREN
        DONGHAI(23),
        SANSHA(24),
        BAODAO(25),
        BOHAI(26),
        
        //ZHUANGJIA x HEJIN
        WEIZHI(27),
        
        //WEIZHI x HAIREN
        YOULING(28),
        SHENMI(29),
        
        //DONGHAI/SANSHA/BOHAI/BAODAO x HAIREN
        ZHENZHU(30),
        ZUANSHI(31),
        ;
        private final int value;
        private FishType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static FishType fromId(int id) {
            for (FishType type : values()) {
                if (type.getValue() == id) {
                    return type;
                }
            }
                return null;
        }
    }
    
    public static enum FishSpecial {
        NONE(0),
        XIDAPUBEN(1),
        YANHUOWANHUI(2),
        YUANDING(3),
        HULI(4),
        GUOJIABAOZANG(5),
        SHIDAI(6),
        DABAOJIAO(7),
        ;
        private final int value;
        private FishSpecial(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static FishSpecial fromId(int id) {
            for (FishSpecial type : values()) {
                if (type.getValue() == id) {
                    return type;
                }
            }
                return null;
        }
    }
}
