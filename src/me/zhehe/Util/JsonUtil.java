/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.zhehe.FishR.Fish;

public class JsonUtil {
    public static String toJsonString(Fish fish) {
        Gson gson = new Gson();
        return gson.toJson(fish);
    }
    
    public static void main(String[] args) {
        Fish fish = new Fish();
        String fish_json = toJsonString(fish);
        System.out.println(fish_json);
        fish = (new Gson()).fromJson(fish_json, Fish.class);
        System.out.println((new Gson()).toJson(fish));
    }
}
