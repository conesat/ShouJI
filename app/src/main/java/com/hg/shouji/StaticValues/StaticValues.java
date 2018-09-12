package com.hg.shouji.StaticValues;

import com.arcsoft.facerecognition.AFR_FSDKFace;

import java.util.ArrayList;
import java.util.List;

public class StaticValues {
    public static int finger = 0;
    public static int face = 0;
    public static int action = 0;

    public static String appid = "3KhX2pUyqeeoWM6jx5ss1NpPG1u4LesFYCqC8mt7xRa1";
    public static String ft_key = "9gfxp5KES8MNBV1d37B2VzaNLaC9y3RokNfDFK6wFTsT";
    public static String fd_key = "9gfxp5KES8MNBV1d37B2VzaVVyTFu28ZSV8U6VJzyVHC";
    public static String fr_key = "9gfxp5KES8MNBV1d37B2Vzaz9aVzRMWbYMUig9jeYjZ2";
    public static String age_key = "9gfxp5KES8MNBV1d37B2VzbEUP2K4TahSm8wJicuYBYz";
    public static String gender_key = "9gfxp5KES8MNBV1d37B2VzbMdnHSrARizddppherExE2";

    public static List<AFR_FSDKFace> mFaceList=new ArrayList<>();

    public static int time=0;

    public static void reset(){
        finger=0;
        face=0;
        action=0;
    }


}
