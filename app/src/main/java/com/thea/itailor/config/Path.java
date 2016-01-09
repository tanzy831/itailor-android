package com.thea.itailor.config;

import android.os.Environment;

/**
 * Created by Thea on 2015/8/9.
 */
public class Path {
    public static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/iTailor";
    public static final String DIRECTORY_ARMOIRE = "/armoire";
    public static final String DIRECTORY_USER = "/user";

    public static final String IP = "192.168.0.101";
    public static final String BASE_URL = "http://192.168.0.101:8080/itailor/";

    public static final String BAIDU_CONVERT_URL = "http://api.map.baidu.com/geocoder/v2/?" +
            "ak=BWDBjksxBvesb5wIVTOz9Te3&output=json&location=";
    public static final String GET_WEATHER_URL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=";

    public static final String TEST = "example";

    public static final String ACCOUNT = "Account";  //用户注册、查询、编辑
    public static final String PORTRAIT = "Account/portrait";  //用户注册、查询、编辑
    public static final String LOGIN_RECORD = "LoginRecord"; //用户登录，注销
    public static final String BODY_STATUS = "BodyStatus";  //用户身体数据

    public static final String FRIENDS = "Group"; //获得全部好友
    public static final String MEMBER = "Member"; //一个好友
    public static final String PURSUER = "Pursuer"; //好友添加请求

    public static final String SHARE_ITEM = "ShareItem"; //用户分享
    public static final String SHARE_ITEM_TIMELINE_NEW = SHARE_ITEM + "/TimeLine/New"; //用户分享
    public static final String MY_SHARE_ITEM = SHARE_ITEM + "/mine"; //用户分享
    public static final String SHARE_ITEM_COMMENT = SHARE_ITEM + "/ct"; //评论
    public static final String SHARE_ITEM_FAVOR = "TimeLine/ILoveIt"; //评论
    public static final String TIMELINE = "TimeLine/All"; //朋友圈
    public static final String TIMELINE_COMMENT = "TimeLine/Comment"; //评论
    public static final String POSTS = SHARE_ITEM + "/CommunityFavor"; //社区热帖

    public static final String RECOMMEND = "Recommend"; //今日推荐
    public static final String PREFERENCE_GAME = "Preference/Game"; //测试喜好
    public static final String FAVOURITES = "Recommend/MyFavors"; //我的收藏
    public static final String WEATHER = "Recommend/Weather"; //天气

    public static final String MESSAGE = "Message"; //消息

    public static final String DEVELOPER_SYSTEM = "developer/System"; //系统设置
    public static final String DEVELOPER_FEEDBACK = "developer/Feedback"; //反馈

    public static final String CLOTH_IMAGE = "ClothImageJson";

    public static final String IMAGE_SERVER = "imageServer";
}
