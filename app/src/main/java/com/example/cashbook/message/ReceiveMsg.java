package com.example.cashbook.message;

/**
 * Created by dsz62 on 2017/7/12.
 */

public class ReceiveMsg extends Msg {
    //传入输入的消息，根据此消息回复特定的消息
    public ReceiveMsg(String kind, float money, int _type) {
        //根据传入的kind和money决定content， 还未实现
        switch (kind) {
            case "一般" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "用餐" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "交通" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "水果" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "服饰" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "日用品" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "娱乐" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "零食" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "学习" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "医疗" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "住房" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            case "通讯" :
                if(money < 10) content = "没想到你这么节俭持家！";
                else if(money < 100) content = "该剁的手要剁的呀！";
                else content = "这下又得吃土了。。";
                break;
            default:
                content = "输入不正确，请先选择种类，待输入框弹出后在输入金额";
                break;
        }
        type = _type;
    }
}
