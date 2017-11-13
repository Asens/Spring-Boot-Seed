package cn.asens.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by lenovo on 2015/12/20.
 */
public class StringUtils {
    public static String UpperFirst(String source)
    {
        if(source==null||source.equals(""))
            return "";
        return String.valueOf(source.charAt(0)).toUpperCase()+source.substring(1);
    }

    public static String LowerFirst(String source)
    {
        if(source==null||source.equals(""))
            return "";
        return String.valueOf(source.charAt(0)).toLowerCase()+source.substring(1);
    }

    public static boolean isHan(String source)
    {
        if(source.substring(0,1).matches("[a-zA-Z0-9]"))
            return false;
        return true;
    }

    public static String letterLower(String source) {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<source.length();i++)
        {
            if(source.substring(i,i+1).matches("[A-Z]"))
            {
                result.append(source.substring(i,i+1).toLowerCase());
            }else{
                result.append(source.substring(i,i+1));
            }
        }
        return result.toString();
    }

    public static String hanToUTF8(String source)
    {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < source.length(); i++)
        {
            output.append("\\u" +Integer.toString(source.charAt(i), 16));
        }
        return output.toString();
    }


    public static String getStringDate()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }



    public static long getLongDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
    }

    public static boolean isBlank(String source)
    {
        return source==null||source.equals("");
    }

    public static boolean isNotBlank(String source)
    {
        return !isBlank(source);
    }

    public static String parseImg(String content) {
        if(!content.contains("<img")) return null;
        Integer index=content.indexOf("<img");
        Integer end=content.indexOf("/>",index);
        if(index<0||end<0) return null;
        String img=content.substring(index,end+2);
        img=img.substring(img.indexOf("\"")+1,img.indexOf("\"",img.indexOf("\"")+1));
        if(!img.contains("asens.cn")) return null;
        if(img.endsWith("gif")) return null;
        return img;
    }

    public static String makeCode(){
        return String.valueOf(new Random().nextInt(899999)+100000);
    }

}
