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
                if(money < 20) content = "注意荤素搭配哦！";
                else if(money < 100) content = "羡慕你能大餐一顿";
                else content = "说好的要减肥呢。。";
                break;
            case "交通" :
                if(money < 20) content = "注意交通安全！";
                else if(money < 100) content = "这是去什么地方了呀？";
                else content = "去旅游了么？好幸福";
                break;
            case "水果" :
                if(money < 20) content = "爱吃水果的人都很美";
                else if(money < 100) content = "分我一点啊！";
                else content = "你是有多能吃啊。。";
                break;
            case "服饰" :
                if(money < 50) content = "人靠衣装";
                else if(money < 500) content = "买买买！不买不是人";
                else content = "这下又得吃土了。。";
                break;
            case "日用品" :
                if(money < 20) content = "牙膏、毛巾要记得勤换哦";
                else if(money < 100) content = "买了不少东西嘛";
                else content = "嘿嘿，购物大行动";
                break;
            case "娱乐" :
                if(money < 20) content = "该去学习了呀";
                else if(money < 200) content = "开开心心最重要！";
                else content = "这下又得吃土了。。";
                break;
            case "零食" :
                if(money < 10) content = "推荐你田园薯片番茄味";
                else if(money < 100) content = "边吃零食边看番";
                else content = "买的不是零食，是扎实的体重";
                break;
            case "学习" :
                if(money < 10) content = "爱学习的好少年！";
                else if(money < 100) content = "放下手机，认真看书吧";
                else content = "学。。。学霸哥？";
                break;
            case "医疗" :
                if(money < 10) content = "多喝热水吧。。。";
                else if(money < 100) content = "平时也要多注意身体啊";
                else content = "早日康复，地球需要你！";
                break;
            case "住房" :
                if(money < 50) content = "没想到你这么节俭持家！";
                else if(money < 500) content = "肉疼不？";
                else content = "有个房子可真难";
                break;
            case "通讯" :
                if(money < 20) content = "记得常给家里打电话";
                else if(money < 100) content = "不会又没流量了吧。。";
                else content = "这下又得吃土了。。";
                break;
            default:
                content = "输入不正确，请先选择种类，待输入框弹出后再输入金额";
                break;
        }
        type = _type;
    }
}
