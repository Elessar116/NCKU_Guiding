package com.example.miles.nckuguiding.Models;

import android.content.Context;

import com.example.miles.nckuguiding.GPSTracker;

/**
 * Created by miles on 2016/10/24.
 */

public class AllCampusData {
    //Models
    public static Model loco;
    public static Model navi;
    public static Model stop;
    public static Model stop_sign;
    public static Campus test;
    public static Campus ck;
    public static Campus sl;
    public static Campus kf;

    public static float[] map_zero = new float[]{989.285767f, 817.882629f};
    //GPS
    public static GPSTracker gpsTracker;


    //campus cc
    private float[] cc_map = new float[]{
            1685.020752f, 1502.139648f,
            1371.405640f, 1508.599609f,
            1354.412964f, 350.512787f,
            1665.924438f, 331.17697f,
    };

    private String cc_filepath = "cc_stl/";
    private String[] cc_files = new String[]{
            cc_filepath + "cc_aero.STL", cc_filepath + "cc_art.STL",
            cc_filepath + "cc_chemis.STL", cc_filepath + "cc_chimei.STL",
            cc_filepath + "cc_doorgod.STL", cc_filepath + "cc_ee.STL", cc_filepath + "cc_equip.STL", cc_filepath + "cc_gate.STL",
            cc_filepath + "cc_me.STL", cc_filepath + "cc_system.STL", cc_filepath + "cc_taichi.STL", cc_filepath + "cc_tech.STL"
    };
    private String[] cc_name = new String[]{
            "航太系館", "裝置藝術",
            "化工系館", "奇美樓",
            "門神", "電機系館", "照坤儀器大樓", "自強大門",
            "機械系館", "系統系館", "自強太極", "科技大樓"
    };

    private float[] cc_location = new float[]{
            (1600.377075f + 1515.377441f) / 2.0f, (636.360596f + 631.251709f) / 2.0f,
            1519.189575f, 949.409790f,
            (1462.624512f + 1397.700684f) / 2.0f, (1030.356689f + 1030.614380f) / 2.0f,
            1430.713989f, 765.698914f,
            1513.539551f, 833.509949f,
            1413.427368f, 873.227173f,
            (1573.982422f + 1638.929321f) / 2.0f, (1067.892285f + 1064.965698f) / 2.0f,
            1457.405640f, 724.407837f,
            (1623.792358f + 1566.833496f) / 2.0f, (935.426331f + 936.034851f) / 2.0f,
            (1572.755371f + 1623.416382f) / 2.0f, (772.066956f + 819.998108f) / 2.0f,
            (1521.236084f + 1512.470825f) / 2.0f, (1035.904053f + 1026.375366f) / 2.0f,
            (1445.920166f + 1403.801025f) / 2.0f, (545.191345f + 482.413269f) / 2.0f
    };


    //campus ck
    private float[] ck_map = new float[]{
            1342.370728f, 1512.883667f,
            793.995972f, 1518.972778f,
            785.361206f, 741.346313f,
            1333.609131f, 723.839844f,
    };

    private String ck_filepath = "ck_stl/";
    private String[] ck_files = new String[]{
            ck_filepath + "ck_ccenter.STL", ck_filepath + "ck_cm.STL", ck_filepath + "ck_cs.STL", ck_filepath + "ck_eas.STL",
            ck_filepath + "ck_ens.STL", ck_filepath + "ck_ens2.STL", ck_filepath + "ck_envir.STL", ck_filepath + "ck_guard.STL",
            ck_filepath + "ck_hall.STL", ck_filepath + "ck_lib.STL", ck_filepath + "ck_life.STL", ck_filepath + "ck_material.STL",
            ck_filepath + "ck_math.STL", ck_filepath + "ck_mes.STL", ck_filepath + "ck_mu_circle.STL", ck_filepath + "ck_multi.STL",
            ck_filepath + "ck_museum.STL", ck_filepath + "ck_new_build.STL", ck_filepath + "ck_outstand.STL", ck_filepath + "ck_phy2.STL",
            ck_filepath + "ck_phy_che.STL", ck_filepath + "ck_pond.STL", ck_filepath + "ck_res.STL", ck_filepath + "ck_star.STL",
            ck_filepath + "ck_water.STL"

    };
    private String[] ck_name = new String[]{
            "機算機網路中心", "土木系館", "資訊系館", "地科系館",
            "工科系館", "工科新系館", "環工系館", "警衛室",
            "格致堂", "總圖書館", "生科系館", "材料系館",
            "數學系館", "測量系館", "圓環雕像", "綜合大樓",
            "成大博物館", "材料 資訊 資源新系館", "卓群大樓", "物理二館",
            "物理與化學系館", "小魚池", "資源系館", "能量光裕",
            "水利系館"
    };

    //campus sl
    private float[] sl_map = new float[]{
            1324.132813f, 723.746094f,
            789.158691f, 731.988647f,
            808.333435f, 99.998878f,
            1314.648804f, 78.596375f,
    };

    private String sl_filepath = "sl_stl/";
    private String[] sl_files = new String[]{
            sl_filepath + "sl_ck_mark.STL", sl_filepath + "sl_door1.STL", sl_filepath + "sl_door2.STL", sl_filepath + "sl_dorm1.STL",
            sl_filepath + "sl_dorm2.STL", sl_filepath + "sl_dorm3.STL", sl_filepath + "sl_dorm4.STL", sl_filepath + "sl_dorm6.STL",
            sl_filepath + "sl_dorm81.STL", sl_filepath + "sl_dorm82.STL", sl_filepath + "sl_roof.STL", sl_filepath + "sl_stcenter.STL",
            sl_filepath + "sl_study.STL",
    };
    private String[] sl_name = new String[]{
            "舊成大入口", "勝利大門", "勝利大門", "勝一宿",
            "勝二宿", "勝三宿", "勝四宿", "勝六宿",
            "勝八宿-1", "勝八宿-2", "勝利涼亭", "學生活動中心",
            "K館"
    };

    //campus kf
    private float[] kf_map = new float[]{
            759.799133f, 1513.838501f,
            156.654388f, 1552.364990f,
            52.589336f, 805.825623f,
            789.458691f, 731.988647f,
    };

    private String kf_filepath = "kf_stl/";
    private String[] kf_files = new String[]{
            kf_filepath + "kf_aircoll.STL", kf_filepath + "kf_arch.STL", kf_filepath + "kf_arch_res.STL", kf_filepath + "kf_ben.STL",
            kf_filepath + "kf_ben_name.STL", kf_filepath + "kf_city.STL", kf_filepath + "kf_dachen.STL", kf_filepath + "kf_dorm1.STL",
            kf_filepath + "kf_dorm2.STL", kf_filepath + "kf_dorm3.STL", kf_filepath + "kf_earthquake.STL", kf_filepath + "kf_feipu.STL",
            kf_filepath + "kf_field.STL", kf_filepath + "kf_field.STL", kf_filepath + "kf_gate.STL", kf_filepath + "kf_gym.STL",
            kf_filepath + "kf_his.STL", kf_filepath + "kf_hismuseum.STL", kf_filepath + "kf_lisian.STL", kf_filepath + "kf_lit.STL",
            kf_filepath + "kf_lit_stat.STL", kf_filepath + "kf_manage.STL", kf_filepath + "kf_mil.STL", kf_filepath + "kf_pond.STL",
            kf_filepath + "kf_pond_tree.STL", kf_filepath + "kf_scenter.STL", kf_filepath + "kf_scman.STL", kf_filepath + "kf_uinping.STL",
            kf_filepath + "kf_vinom.STL",
    };
    private String[] kf_name = new String[]{
            "舊成大入口", "勝利大門", "勝利大門", "勝一宿",
            "勝二宿", "勝三宿", "勝四宿", "勝六宿",
            "勝八宿-1", "勝八宿-2", "勝利涼亭", "學生活動中心",
            "勝八宿-1", "勝八宿-2", "勝利涼亭", "學生活動中心",
            "勝八宿-1", "勝八宿-2", "勝利涼亭", "學生活動中心",
            "勝八宿-1", "勝八宿-2", "勝利涼亭", "學生活動中心",
            "勝八宿-1", "勝八宿-2", "勝利涼亭", "學生活動中心",
            "K館"
    };

    public AllCampusData(Context mContext) {

        gpsTracker = new GPSTracker(mContext);

        loco = new Model("loco.STL", new float[]{1, 0, 0}, 1.0f, mContext);
        navi = new Model("loco.STL", new float[]{0, 0, 1}, 1.0f, mContext);
        stop = new Model("stop.STL", new float[]{0, 0, 1}, 1.0f, mContext);
        stop_sign = new Model("sp/bike_stop.STL", new float[]{0, 0, 1}, 1.0f, mContext);

        test = new Campus("half_land.STL", 1.0f, mContext, 12);
        test.set_Models(cc_name, cc_files, new float[]{189 / 255.0f, 252 / 255.0f, 201 / 255.0f});
        test.set_corner(cc_map);
        test.set_location(cc_location);

        ck = new Campus(cc_filepath + "cc_ball1.STL", 1.0f, mContext, ck_name.length);
        ck.set_Models(ck_name, ck_files, new float[]{255 / 255.0f, 227 / 255.0f, 132 / 255.0f});
        ck.set_corner(ck_map);

        sl = new Campus(cc_filepath + "cc_ball2.STL", 1.0f, mContext, sl_name.length);
        sl.set_Models(sl_name, sl_files, new float[]{255 / 255.0f, 192 / 255.0f, 203 / 255.0f});
        sl.set_corner(sl_map);

        kf = new Campus(ck_filepath + "ck_land.STL", 1.0f, mContext, kf_name.length);
        kf.set_Models(kf_name, kf_files, new float[]{255 / 255.0f, 248 / 255.0f, 220 / 255.0f});
        kf.set_corner(kf_map);
    }
}
