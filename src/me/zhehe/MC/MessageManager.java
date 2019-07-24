/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import me.zhehe.Config.Config;
import me.zhehe.Util.Constant.TankErrorCode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 *
 * @author zhiqiang.hao
 */
public class MessageManager {
    public static String TankErrorCodeToString(TankErrorCode err) {
        if(null == err) {
            return Config.instance.UnknownErrorMessage;
        } else switch (err) {
            case Gender:
                return Config.instance.GenderErrorMessage;
            case NoFish:
                return Config.instance.NoFishErrorMessage;
            case NoSaltWater:
                return Config.instance.NotSaltWaterErrorMessage;
            case NoSweetWater:
                return Config.instance.NotSweetWaterErrorMessage;
            case NoWater:
                return Config.instance.NoWaterErrorMessage;
            case Oxygen:
                return Config.instance.OxygenErrorMessage;
            case Temperature:
                return Config.instance.TemperatureErrorMessage;
            case TooDark:
                return Config.instance.TooDarkErrorMessage;
            case TooLight:
                return Config.instance.TooLightErrorMessage;
            default:
                return Config.instance.UnknownErrorMessage;
        }
    }
    
    public static void sendPlayerMessage(Player player, String msg) {
        player.sendMessage(msg);
    }
    
    public static void sendPlayerSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_FISH_SWIM, 1, 1);
    }
}
