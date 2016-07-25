package com.wkw.common_lib.utils;

import android.text.TextUtils;
import android.text.format.Time;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by wukewei on 16/7/25.
 */
public class StringUtils {
    /**
     * 空字符串常量
     */
    public static final String EMPTY = "";
    /**
     * "不可用"字符串常量
     */
    public static final String NOT_AVAILABLE = "N/A";
    private static final int CACHE_SIZE = 4096;

    /**
     * 创建指定格式的时间格式化对象
     *
     * @param pattern 时间格式，形如"yyyy-MM-dd HH-mm-ss.SSS"
     * @return Format 时间格式化对象
     */
    public static SimpleDateFormat createDataFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 将s中字符用delimiter分割连接起来
     *
     * @param delimiter 分隔符
     * @param segments  被连接的数据
     * @return 返回连接号的字符串
     * @see StringUtils#join(String, Object[])
     */
    public static String join(String delimiter, Collection<?> segments) {
        StringBuilder stringBuilder = new StringBuilder();
        if (segments != null) {
            appendCollectionObjectToStringBuilder(stringBuilder, delimiter, segments);
        }
        String outString = stringBuilder.toString();
        if (outString.endsWith(delimiter)) {
            return outString.substring(0, outString.length() - delimiter.length());
        }
        return outString;
    }

    /**
     * 将对象链接成一个字符串，使用delimiter传入的字符串分割，
     * <p><b>注意:</b>如果前一个片段为字符串且以delimiter结束或者为空(null获取为"")，将不会重复添加此字符串</p>
     * <p>字符串末尾不会自动添加delimiter字符串</p>
     * <p>如果没有传入参数，返回一个<b>空字符串</b></p>
     *
     * @param delimiter 分割字符串
     * @param segments  所有传入的部分
     * @return 连接完毕后的字符串
     */
    public static String join(String delimiter, Object... segments) {
        StringBuilder stringBuilder = new StringBuilder();
        if (segments != null) {
            int size = segments.length;
            if (size > 0) {
                for (Object segment : segments) {
                    appendObjectToStringBuilder(stringBuilder, delimiter, segment);
                }
            }
        }
        String outString = stringBuilder.toString();
        if (outString.endsWith(delimiter)) {
            return outString.substring(0, outString.length() - delimiter.length());
        }
        return outString;
    }


    private static void appendArrayObjectToStringBuilder(StringBuilder stringBuilder, String delimiter, Object array) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            appendObjectToStringBuilder(stringBuilder, delimiter, Array.get(array, i));
        }
    }

    private static void appendCollectionObjectToStringBuilder(StringBuilder stringBuilder, String delimiter, Collection<?> collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            appendObjectToStringBuilder(stringBuilder, delimiter, iterator.next());
        }
    }

    private static void appendObjectToStringBuilder(StringBuilder stringBuilder, String delimiter, Object object) {
        if (object == null) {
            return;
        }
        if (object.getClass().isArray()) {
            appendArrayObjectToStringBuilder(stringBuilder, delimiter, object);
        } else if (object instanceof Collection) {
            appendCollectionObjectToStringBuilder(stringBuilder, delimiter, (Collection) object);
        } else {
            String objectString = object.toString();
            stringBuilder.append(objectString);
            if (!isEmpty(objectString) && !objectString.endsWith(delimiter)) {
                stringBuilder.append(delimiter);
            }
        }
    }

    /**
     * 测试传入的字符串是否为空
     *
     * @param string 需要测试的字符串
     * @return 如果字符串为空（包括不为空但其中为空白字符串的情况）返回true，否则返回false
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * 测试传入的字符串是否为空
     *
     * @param string 需要测试的字符串
     * @return 如果字符串为空（包括不为空但其中为空白字符串的情况）返回false，否则返回true
     */
    public static boolean isNotEmpty(String string) {
        return string != null && string.trim().length() > 0;
    }

    /**
     * 传入的字符串是否相等
     *
     * @param a a字符串
     * @param b b字符串
     * @return 如果字符串相等（值比较）返回true，否则返回false
     */
    public static boolean equal(String a, String b) {
        return a == b || (a != null && b != null && a.length() == b.length() && a.equals(b));
    }

    /**
     * 将字符串用分隔符分割为long数组
     * <p><b>只支持10进制的数值转换</b></p>
     * <p><b>如果格式错误，将抛出NumberFormatException</b></p>
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的long数组.
     */
    public static long[] splitToLongArray(String string, String delimiter) {
        List<String> stringList = splitToStringList(string, delimiter);

        long[] longArray = new long[stringList.size()];
        int i = 0;
        for (String str : stringList) {
            longArray[i++] = Long.parseLong(str);
        }
        return longArray;
    }

    /**
     * 将字符串用分隔符分割为Long列表
     * <p><b>只支持10进制的数值转换</b></p>
     * <p><b>如果格式错误，将抛出NumberFormatException</b></p>
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的Long列表.
     */
    public static List<Long> splitToLongList(String string, String delimiter) {
        List<String> stringList = splitToStringList(string, delimiter);

        List<Long> longList = new ArrayList<Long>(stringList.size());
        for (String str : stringList) {
            longList.add(Long.parseLong(str));
        }
        return longList;
    }

    /**
     * 将字符串用分隔符分割为字符串数组
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的字符串数组.
     */
    public static String[] splitToStringArray(String string, String delimiter) {
        List<String> stringList = splitToStringList(string, delimiter);
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * 将字符串用分隔符分割为字符串列表
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的字符串数组.
     */
    public static List<String> splitToStringList(String string, String delimiter) {
        List<String> stringList = new ArrayList<String>();
        if (!isEmpty(string)) {
            int length = string.length();
            int pos = 0;

            do {
                int end = string.indexOf(delimiter, pos);
                if (end == -1) {
                    end = length;
                }
                stringList.add(string.substring(pos, end));
                pos = end + 1; // skip the delimiter
            } while (pos < length);
        }
        return stringList;
    }

    /**
     * InputSteam 转换到 String，会把输入流关闭
     *
     * @param inputStream 输入流
     * @return String 如果有异常则返回null
     */
    public static String stringFromInputStream(InputStream inputStream) {
        try {
            byte[] readBuffer = new byte[CACHE_SIZE];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int readLen = inputStream.read(readBuffer, 0, CACHE_SIZE);
                if (readLen <= 0) {
                    break;
                }

                byteArrayOutputStream.write(readBuffer, 0, readLen);
            }

            return byteArrayOutputStream.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 测试是否为有效的注册电子邮件格式
     *
     * @param string 内容
     * @return true if yes
     */
    public static boolean isEmailFormat(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        String emailFormat = "^([a-zA-Z0-9_\\.-]+)@([\\da-zA-Z\\.-]+)\\.([a-zA-Z\\.]{1,16})$";
        return Pattern.matches(emailFormat, string);
    }

    /**
     * 测试是否为有效的登录电子邮件格式
     *
     * @param string 内容
     * @return true if yes
     */
    public static boolean isLoginEmailFormat(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        String emailFormat = "^\\s*\\w+(?:\\.?[\\w-]+\\.?)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return Pattern.matches(emailFormat, string);
    }

    /**
     * 是否在长度范围之类
     *
     * @param string 内容
     * @param begin  最小长度（inclusive）
     * @param end    最大长度（inclusive）
     * @return 字符串长度在begin和end之内返回true，否则返回false。<p><b>输入字符串为null时，返回false</b></p>
     */
    public static boolean lengthInRange(String string, int begin, int end) {
        return string != null && string.length() >= begin && string.length() <= end;
    }

    /**
     * 去除文件名中的无效字符
     *
     * @param srcStr srcStr
     * @return 去除文件名后的字符
     */
    public static String validateFilePath(String srcStr) {
        return StringUtils.isEmpty(srcStr) ? srcStr : srcStr.replaceAll("[\\\\/:\"*?<>|]+", "_");
    }

    /**
     * 获取字串字符长度，规则如下：
     * 中文一个字长度为2，英文、字符长度为1
     * 如："我c"长度为3
     *
     * @param str str
     * @return 字符串长度
     */
    public static int getCharsLength(String str) {
        int length = 0;
        try {
            if (str == null) {
                return 0;
            }
            str = str.replaceAll("[^\\x00-\\xff]", "**");
            length += str.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取字串的文字个数，规则如下：
     * 汉字为1，英文、字符算0.5个字
     * 如："我"字数为1，"我k"字数为2，"我ko"字数也为2
     *
     * @param str str
     * @return 字符串长度
     */
    public static int getWordCount(String str) {
        int length = getCharsLength(str);
        return length % 2 + length / 2;
    }

    /**
     * 得到毫秒数对应的时间串, 格式 "2013-07-04 12:44:53.098" <br>
     * <br>
     * 这个方法使用Android的Time类实现，用以替代java.util.Calender<br>
     * 使用同余计算毫秒数，在某些ROM上，JDK被替换的算法不能计算毫秒数 (e.g. 华为荣耀)
     *
     * @param time 毫秒数
     * @return 时间字符串
     */
    public static String getTimeStr(long time) {
        long ms = time % 1000;

        Time timeObj = new Time();

        timeObj.set(time);

        StringBuilder builder = new StringBuilder();

        builder.append(timeObj.format("%Y-%m-%d %H:%M:%S")).append('.');

        if (ms < 10) {
            builder.append("00");
        } else if (ms < 100) {
            builder.append('0');
        }

        builder.append(ms);

        return builder.toString();
    }

    /**
     * 比较两个字符串，是否prefix字符串是source字符串的前缀，忽略大小写差别
     *
     * @param source 查找的字符串..
     * @param prefix 前缀.
     * @return {@code true} 如果指定字符串是这个字符串的前缀, 否则{@code false}
     */
    public static boolean startsWithIgnoreCase(String source, String prefix) {
        if (source == prefix) {
            return true;
        }
        if (source == null || prefix == null) {
            return false;
        }
        return source.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    /**
     * 比较两个字符串，是否prefix字符串是source字符串的后缀，忽略大小写差别
     *
     * @param source 查找的字符串..
     * @param suffix 后缀.
     * @return {@code true} 如果指定字符串是这个字符串的后缀, 否则{@code false}
     */
    public static boolean endsWithIgnoreCase(String source, String suffix) {
        if (source == suffix) {
            return true;
        }
        if (source == null || suffix == null) {
            return false;
        }
        int startIndex = source.length() - suffix.length();
        if (startIndex < 0) {
            return false;
        }
        return source.regionMatches(true, startIndex, suffix, 0, suffix.length());
    }

    /**
     * 使用数组中的键值对替换字符串. 算法将每个在字符串中找的的key替换成对应value.
     * 数组的构造类似 {key1, value1, key2, value2...}.
     *
     * @param source        source字符串.
     * @param keyValueArray 包含键值对的数组.
     * @return 被替换了的字符串，如果没有找到任意key则返回source.
     */
    public static String replaceWith(String source, Object... keyValueArray) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }
        if (keyValueArray.length % 2 != 0) {
            throw new IllegalArgumentException("key value array not valid");
        }
        StringBuilder sb = null;
        for (int i = 0; i < keyValueArray.length; i += 2) {
            String key = String.valueOf(keyValueArray[i]);
            String value = String.valueOf(keyValueArray[i + 1]);
            int index = sb != null ? sb.indexOf(key) : source.indexOf(key);
            while (index >= 0) {
                if (sb == null) {
                    sb = new StringBuilder(source);
                }
                sb.replace(index, index + key.length(), value);
                index = sb.indexOf(key, index + value.length());
            }
        }
        return sb != null ? sb.toString() : source;
    }

    /**
     * 使用数组中的键值对替换StringBuilder. 算法将每个在字符串中找的的key替换成对应value.
     * 数组的构造类似 {key1, value1, key2, value2...}.
     *
     * @param source        source StringBuilder.
     * @param keyValueArray 包含键值对的数组.
     * @return 被处理过的StringBuilder.
     */
    public static StringBuilder replaceWith(StringBuilder source, Object... keyValueArray) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }
        if (keyValueArray.length % 2 != 0) {
            throw new IllegalArgumentException("key value array not valid");
        }
        for (int i = 0; i < keyValueArray.length; i += 2) {
            String key = String.valueOf(keyValueArray[i]);
            String value = String.valueOf(keyValueArray[i + 1]);
            int index = source.indexOf(key);
            while (index >= 0) {
                source.replace(index, index + key.length(), value);
                index = source.indexOf(key, index + value.length());
            }
        }
        return source;
    }
}
