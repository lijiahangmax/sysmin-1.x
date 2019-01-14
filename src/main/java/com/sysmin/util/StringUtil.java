package com.sysmin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author:Li
 * @time: 2018/11/27 11:04
 * @version: 1.0
 * String处理类
 */
public class StringUtil {

    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    public static final char DBC_CHAR_START = 33;

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    public static final char DBC_CHAR_END = 126;

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    public static final char SBC_CHAR_START = 65281;

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    public static final char SBC_CHAR_END = 65374;

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    public static final int CONVERT_STEP = 65248;

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    public static final char SBC_SPACE = 12288;

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    public static final char DBC_SPACE = ' ';

    /**
     * 将string字符串分割成int数组
     *
     * @param str   需要分割的字符串
     * @param split 分隔符
     * @return 处理好的int数组
     */
    public static Object[] parseStringToArray(String str, String split) {
        Object[] arr = Arrays.stream(str.split(split)).map(s -> Integer.valueOf(s)).collect(Collectors.toList()).toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        return arr;
    }

    /**
     * 隐藏信息 (手机号是 num,4,7,****)
     *
     * @param s       需处理的字符串
     * @param start   第几位开始隐藏
     * @param end     第几位隐藏结束
     * @param replace 替换的字符串
     * @return 处理好的字符串
     */
    public static String stringHide(String s, int start, int end, String replace) {
        try {
            return s.substring(0, start) + replace + s.substring(end);
        } catch (Exception e) {
            return s;
        }
    }

    /**
     * 判断输入的字符串参数是否为空
     *
     * @param s 需要判断的字符串
     * @return 空则返回true, 非空则false
     */
    public static boolean checkEmpty(String s) {
        return null == s || 0 == s.length() || "".equals(s) || 0 == s.replaceAll("\\s", "").length();
    }

    /**
     * 判断输入的字节数组是否为空
     *
     * @param bytes 需要判断的字节数组
     * @return 空则返回true, 非空则false
     */
    public static boolean checkEmpty(byte[] bytes) {
        return null == bytes || 0 == bytes.length;
    }

    /**
     * 字节数组转为字符串 默认以UTF-8转码
     * ISO-8859-1
     *
     * @param b 字节数组
     * @return 转换成的string
     */
    public static String bytesToString(byte[] b) {
        return bytesToString(b, "UTF-8");
    }

    /**
     * 字节数组转为字符串
     * 如果编码异常会以系统默认的编码
     *
     * @param b       字节数组
     * @param charset 转换的编码
     * @return 转换成的string
     */
    public static String bytesToString(byte[] b, String charset) {
        if (checkEmpty(b)) {
            return "";
        }
        if (checkEmpty(charset)) {
            return new String(b);
        }
        try {
            return new String(b, charset);
        } catch (UnsupportedEncodingException e) {
            System.out.println("将byte数组[" + b + "]转为String时发生异常:系统不支持该字符集[" + charset + "]");
            return new String(b);
        }
    }

    /**
     * 字符串转为字节数组 默认以UTF-8转码
     * ISO-8859-1
     *
     * @param s 需转换字符串
     * @return 字节数组
     */
    public static byte[] stringToBytes(String s) {
        return stringToBytes(s, "UTF-8");
    }

    /**
     * 字符串转为字节数组
     * 如果系统不支持所传入的charset字符集,则按照系统默认字符集进行转换
     *
     * @param s       需转换字符串
     * @param charset 转换的编码
     * @return 字节数组
     */
    public static byte[] stringToBytes(String s, String charset) {
        s = (s == null ? "" : s);
        if (checkEmpty(charset)) {
            return s.getBytes();
        }
        try {
            return s.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            System.out.println("将字符串[" + s + "]转为byte[]时发生异常:系统不支持该字符集[" + charset + "]");
            return s.getBytes();
        }
    }

    /**
     * 字符编码 该方法默认会以UTF-8编码字符串
     *
     * @param s 需要编码的字符串
     * @return 编码完毕的string
     */
    public static String encode(String s) {
        return encode(s, "UTF-8");
    }

    /**
     * 字符编码 该方法通常用于对中文进行编码 若系统不支持指定的编码字符集,则直接将s原样返回
     *
     * @param s       需要编码的字符串
     * @param charset 编码格式
     * @return 编码完毕的string
     */
    public static String encode(String s, String charset) {
        s = (s == null ? "" : s);
        try {
            return URLEncoder.encode(s, charset);
        } catch (UnsupportedEncodingException e) {
            System.out.println("编码字符串[" + s + "]时发生异常:系统不支持该字符集[" + charset + "]");
            return s;
        }
    }

    /**
     * 字符解码 该方法默认会以UTF-8解码字符串
     *
     * @param s 需要解码的字符串
     * @return 解码完毕的字符串
     */
    public static String decode(String s) {
        return decode(s, "UTF-8");
    }

    /**
     * 字符解码  该方法通常用于对中文进行解码 若系统不支持指定的解码字符集,则直接将s原样返回
     *
     * @param s       需要解码的字符串
     * @param charset 解码格式
     * @return 解码完毕的字符串
     */
    public static String decode(String s, String charset) {
        s = (s == null ? "" : s);
        try {
            return URLDecoder.decode(s, charset);
        } catch (UnsupportedEncodingException e) {
            System.out.println("解码字符串[" + s + "]时发生异常:系统不支持该字符集[" + charset + "]");
            return s;
        }
    }

    /**
     * 字符串右补空格 该方法默认采用空格(其ASCII码为32)来右补字符
     *
     * @param str  需要补空格的字符串
     * @param size 字符串所对应补全空格对应的长度
     * @return 处理完毕的字符串
     */
    public static String rightPadForByte(String str, int size) {
        return rightPadForByte(str, size, 32);
    }

    /**
     * 字符串右补字符
     *
     * @param str           需要补空格的字符串
     * @param size          字符串所对应补全空格对应的长度
     * @param padStrByASCII 该值为所补字符的ASCII码,如32表示空格,48表示0,64表示@等
     * @return 处理完毕的字符串
     * 若str对应的byte[]长度不小于size,则按照size截取str对应的byte[],而非原样返回str
     * 所以size参数很关键.事实上之所以这么处理,是由于支付处理系统接口文档规定了字段的最大长度
     * 若对普通字符串进行右补字符,建议org.apache.commons.lang.StringUtils.rightPad(...)
     */
    public static String rightPadForByte(String str, int size, int padStrByASCII) {
        byte[] srcByte = str.getBytes();
        byte[] destByte = null;
        if (srcByte.length >= size) {
            //由于size不大于原数组长度,故该方法此时会按照size自动截取,它会在数组右侧填充'(byte)0'以使其具有指定的长度
            destByte = Arrays.copyOf(srcByte, size);
        } else {
            destByte = Arrays.copyOf(srcByte, size);
            Arrays.fill(destByte, srcByte.length, size, (byte) padStrByASCII);
        }
        return new String(destByte);
    }

    /**
     * 字符串左补空格 该方法默认采用空格(其ASCII码为32)来右补字符
     *
     * @param str  需要补空格的字符串
     * @param size 字符串所对应补全空格对应的长度
     * @return 处理完毕的字符串
     */
    public static String leftPadForByte(String str, int size) {
        return leftPadForByte(str, size, 32);
    }

    /**
     * 字符串左补空格 该方法默认采用空格(其ASCII码为32)来右补字符
     *
     * @param str           需要补空格的字符串
     * @param size          字符串所对应补全空格对应的长度
     * @param padStrByASCII 该值为所补字符的ASCII码,如32表示空格,48表示0,64表示@等
     * @return 处理完毕的字符串
     * 若str对应的byte[]长度不小于size,则按照size截取str对应的byte[],而非原样返回str
     * 所以size参数很关键.事实上之所以这么处理,是由于支付处理系统接口文档规定了字段的最大长度
     */
    public static String leftPadForByte(String str, int size, int padStrByASCII) {
        byte[] srcByte = str.getBytes();
        byte[] destByte = new byte[size];
        Arrays.fill(destByte, (byte) padStrByASCII);
        if (srcByte.length >= size) {
            System.arraycopy(srcByte, 0, destByte, 0, size);
        } else {
            System.arraycopy(srcByte, 0, destByte, size - srcByte.length, srcByte.length);
        }
        return new String(destByte);
    }

    /**
     * HTML字符转义
     *
     * @return 过滤后的字符串
     * 对输入参数中的敏感字符进行过滤替换,防止用户利用JavaScript等方式输入恶意代码
     * String input = <img src='http://t1.baidu.com/it/fm=0&gp=0.jpg'/>
     * HtmlUtils.htmlEscape(input);         // from spring.jar
     * StringEscapeUtils.escapeHtml(input); // from commons-lang.jar
     * Apache的StringEscapeUtils功能要更强大一些
     * StringEscapeUtils提供了对HTML,Java,JavaScript,SQL,XML等字符的转义和反转义
     * 但二者在转义HTML字符时,都不会对单引号和空格进行转义,而本方法则提供了对它们的转义
     * 空格	 &nbsp;
     * <	小于号	&lt;
     * >	大于号	&gt;
     * &	和号	 &amp;
     * "	引号	&quot;
     * '	撇号 	&apos;
     * ￠	分	 &cent;
     * £	镑	 &pound;
     * ¥	日圆	&yen;
     * €	欧元	&euro;
     * §	小节	&sect;
     * ©	版权	&copy;
     * ®	注册商标	&reg;
     * ™	商标	&trade;
     * ×	乘号	&times;
     * ÷	除号	&divide;
     */
    public static String htmlEncode(String s) {
        if (checkEmpty(s)) {
            return s;
        }
        s = s.replaceAll("&", "&amp;");
        s = s.replaceAll("<", "&lt;");
        s = s.replaceAll(">", "&gt;");
        s = s.replaceAll(" ", "&nbsp;");
        // IE暂不支持单引号的实体名称,而支持单引号的实体编号,故单引号转义成实体编号,其它字符转义成实体名称
        s = s.replaceAll("'", "&#39;");
        // s = s.replaceAll("'", "&apos;");
        // 双引号也需要转义，所以加一个斜线对其进行转义
        s = s.replaceAll("\"", "&quot;");
        // 不能把\n的过滤放在前面，因为还要对<和>过滤，这样就会导致<br/>失效了
        s = s.replaceAll("\n", "<br/>");
        // 替换tab
        s = s.replaceAll("\t", "&nbsp;&nbsp;");
        return s;
    }

    /**
     * HTML字符反转义
     */
    public static String htmlDecode(String s) {
        if (checkEmpty(s)) {
            return s;
        }
        s = s.replaceAll("&amp;", "&");
        s = s.replaceAll("&lt;", "<");
        s = s.replaceAll("&gt;", "v");
        s = s.replaceAll("&nbsp;", " ");
        s = s.replaceAll("&#39;", "'");
        s = s.replaceAll("&quot;", "\"");
        // s = s.replaceAll("&apos;", "\"");
        s = s.replaceAll("<br/>", "\n");
        return s;
    }

    /**
     * 把list用给定的符号symbol连接成一个字符串
     *
     * @param list   需要处理的列表
     * @param symbol 链接的符号 只能为一位,不然结果有误
     * @return 处理后的字符串
     */
    public static String joinString(List list, String symbol) {
        String result = "";
        if (list != null) {
            for (Object o : list) {
                String temp = o.toString();
                if (temp.trim().length() > 0) {
                    result += (temp + symbol);
                }
            }
            if (result.length() > 1) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    /**
     * 判定第一个字符串是否等于的第二个字符串中的某一个值
     *
     * @param str1 测试的字符串
     * @param str2 字符串数组(用,分割)
     * @return 是否包含
     */
    public static boolean requals(String str1, String str2) {
        if (str1 != null && str2 != null) {
            str2 = str2.replaceAll("\\s*", "");
            String[] arr = str2.split(",");
            for (String t : arr) {
                if (t.equals(str1.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判定第一个字符串是否等于的第二个字符串中的某一个值
     *
     * @param str1  测试的字符串
     * @param str2  字符串数组
     * @param split str2字符串的分隔符
     * @return 是否包含
     */
    public static boolean requals(String str1, String str2, String split) {
        if (str1 != null && str2 != null) {
            str2 = str2.replaceAll("\\s*", "");
            String[] arr = str2.split(split);
            for (String t : arr) {
                if (t.equals(str1.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 字符串省略截取
     * 字符串截取到指定长度size+...的形式
     *
     * @param s    需要处理的字符串
     * @param size 截取的长度
     * @return 处理后的字符串
     */
    public static String subStringLimit(String s, int size) {
        return subStringLimit(s, size, "...");
    }

    /**
     * 截取字符串　超出的字符用symbol代替
     *
     * @param s      需要处理的字符串
     * @param size   字符串长度
     * @param symbol 最后拼接的字符串
     * @return 测试后的字符串
     */
    public static String subStringLimit(String s, int size, String symbol) {
        if (s != null && s.length() > size) {
            s = s.substring(0, size) + symbol;
        }
        return s;
    }


    /**
     * 隐藏邮件地址前缀。
     *
     * @param email - EMail邮箱地址 例如: ssss@koubei.com 等等...
     * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
     */
    public static String hideEmailPrefix(String email) {
        if (null != email) {
            int index = email.lastIndexOf('@');
            if (index > 0) {
                email = repeat("*", index).concat(email.substring(index));
            }
        }
        return email;
    }

    /**
     * 通过源字符串重复生成N次组成新的字符串。
     *
     * @param src 需要重复的字符串
     * @param num 重复生成次数
     * @return 生成的重复字符串
     */
    public static String repeat(String src, int num) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            s.append(src);
        }
        return s.toString();
    }

    /**
     * 截取字符串左侧的Num位截取到末尾
     *
     * @param s   处理的字符串
     * @param num 开始位置
     * @return 截取后的字符串
     */
    public static String ltrim(String s, int num) {
        String t = "";
        if (!checkEmpty(s) && s.length() >= num) {
            t = s.substring(num, s.length());
        }
        return t;

    }

    /**
     * 截取字符串右侧第0位到第Num位
     *
     * @param s   处理的字符串
     * @param num 截取的位置
     * @return 截取后的字符串
     */
    public static String rtrim(String s, int num) {
        if (!checkEmpty(s) && s.length() > num) {
            s = s.substring(0, s.length() - num);
        }
        return s;
    }

    /**
     * 根据指定的字符把源字符串分割成一个list
     *
     * @param s     处理的字符串
     * @param split 分割字符串
     * @return 处理后的list
     */
    public static List<String> parseStringToList(String s, String split) {
        List<String> list = new ArrayList<>();
        if (s != null) {
            String[] tt = s.split(split);
            list.addAll(Arrays.asList(tt));
        }
        return list;
    }

    /**
     * 格式化一个double
     *
     * @param format 要格式化成的格式 #.00, #.#
     * @return 格式化后的字符串
     */
    public static String formatDouble(double d, String format) {
        try {
            DecimalFormat df = new DecimalFormat(format);
            return df.format(d);
        } catch (Exception e) {
            return String.valueOf(d);
        }
    }

    /**
     * 截取字符串左侧指定长度的字符串
     *
     * @param s     输入字符串
     * @param count 截取长度
     * @return 截取字符串
     */
    public static String parseLeft(String s, int count) {
        if (checkEmpty(s)) {
            return "";
        }
        count = (count > s.length()) ? s.length() : count;
        return s.substring(0, count);
    }

    /**
     * 截取字符串右侧指定长度的字符串
     *
     * @param s     输入字符串
     * @param count 截取长度
     * @return 截取字符串
     */
    public static String parseRight(String s, int count) {
        if (checkEmpty(s)) {
            return "";
        }
        count = (count > s.length()) ? s.length() : count;
        return s.substring(s.length() - count, s.length());
    }

    /**
     * 全角字符变半角字符
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     *
     * @param s 需要处理的字符串
     * @return 处理后的字符串
     */
    public static String fullToHalf(String s) {
        if (checkEmpty(s)) {
            return "";
        } else {
            StringBuilder buf = new StringBuilder(s.length());
            char[] ca = s.toCharArray();
            for (int i = 0; i < s.length(); i++) {
                if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) {
                    // 如果位于全角！到全角～区间内
                    buf.append((char) (ca[i] - CONVERT_STEP));
                } else if (ca[i] == SBC_SPACE) {
                    // 如果是全角空格
                    buf.append(DBC_SPACE);
                } else {
                    // 不处理全角空格，全角！到全角～区间外的字符
                    buf.append(ca[i]);
                }
            }
            return buf.toString();
        }
    }

    /**
     * 半角字符变全角字符
     * 只处理空格，!到˜之间的字符，忽略其他
     *
     * @param s 需要处理的字符串
     * @return 处理后的字符串
     */
    public static String halfToFull(String s) {
        if (checkEmpty(s)) {
            return "";
        } else {
            StringBuilder buf = new StringBuilder(s.length());
            char[] ca = s.toCharArray();
            for (char t : ca) {
                if (t == DBC_SPACE) {
                    // 如果是半角空格，直接用全角空格替代
                    buf.append(SBC_SPACE);
                } else if ((t >= DBC_CHAR_START) && (t <= DBC_CHAR_END)) {
                    // 字符是!到~之间的可见字符
                    buf.append((char) (t + CONVERT_STEP));
                } else {
                    // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                    buf.append(t);
                }
            }
            return buf.toString();
        }
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param s 需要处理的字符串
     */
    public static String replaceBlank(String s) {
        if (s != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(s);
            s = m.replaceAll("");
        }
        return s;
    }

    /**
     * 替换\n
     *
     * @param s 需要处理的字符串
     * @return
     */
    public static String replaceLine(String s) {
        if (checkEmpty(s)) {
            return s;
        } else {
            return s.replaceAll("\n", "");
        }
    }

    /**
     * 页面中去除字符串中的空格 替换为其他的  原字符串^不能有
     *
     * @param s       需要处理的字符串
     * @param replace 替换为的字符串
     */
    public static String replaceBlank(String s, String replace) {
        String newStr = null;
        if (s != null) {
            newStr = s.replace(" ", "^").replaceAll("\\^+", replace);
        } else {
            newStr = s;
        }
        return newStr;
    }

    /**
     * 字符串转换unicode
     *
     * @param s 需要处理的字符串
     */
    public static String stringToUnicode(String s) {
        StringBuilder uni = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            String temp = "\\u" + String.valueOf(Integer.toHexString(s.charAt(i)));
            uni.append(temp);
        }
        return uni.toString();
    }

    /**
     * 转字符串 实现native2ascii.exe类似的功能
     *
     * @param s 需要处理的字符串
     */
    public static String unicodeToString(String s) {
        StringBuilder str = new StringBuilder();
        String[] hex = s.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            str.append((char) data);
        }
        return str.toString();
    }

    /**
     * 删除所有的标点符号
     *
     * @param s 处理的字符串
     */
    public static String trimPunct(String s) {
        if (checkEmpty(s)) {
            return "";
        }
        return s.replaceAll("[\\pP\\p{Punct}]", "");
    }

    /**
     * 获取字符串str在String中出现的次数
     *
     * @param string 处理的字符串
     * @param str    子字符串
     * @return 出现的次数
     */
    public static int countToString(String string, String str) {
        if ((str == null) || (str.length() == 0) || (string == null) || (string.length() == 0)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = string.indexOf(str, index)) != -1) {
            count++;
            index += str.length();
        }
        return count;
    }

    /**
     * 替换一个出现的字符串
     *
     * @param s    需要处理的字符串
     * @param sub  需要替换的字符
     * @param with 替换为..
     * @return 新的字符串
     */
    public static String replaceFirst(String s, String sub, String with) {
        int i = s.indexOf(sub);
        if (i == -1) {
            return s;
        }
        return s.substring(0, i) + with + s.substring(i + sub.length());
    }

    /**
     * 匹配字符出现次数
     *
     * @param s    源数据
     * @param find 匹配数据
     * @return 次数
     */
    public static int findNum(String s, String find) {
        int count = 0;
        Pattern p = Pattern.compile(find);
        Matcher m = p.matcher(s);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 提取字数串里的数字
     *
     * @param s 字符串
     * @return
     */
    public static int getStringNum(String s) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(s);
        return Integer.valueOf(m.replaceAll("").trim());
    }

    /**
     * 替换最后一次出现的字串
     *
     * @param s    需要处理的字符串
     * @param sub  需要替换的字符
     * @param with 替换为..
     * @return 新的字符串
     */
    public static String replaceLast(String s, String sub, String with) {
        int i = s.lastIndexOf(sub);
        if (i == -1) {
            return s;
        }
        return s.substring(0, i) + with + s.substring(i + sub.length());
    }

    /**
     * 删除所有匹配的字符串
     *
     * @param s   需要处理的字符串
     * @param sub 需要删除的字符串
     * @return 新的字符串
     */
    public static String removeStr(String s, String sub) {
        int c = 0;
        int sublen = sub.length();
        if (sublen == 0) {
            return s;
        }
        int i = s.indexOf(sub, c);
        if (i == -1) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        do {
            sb.append(s.substring(c, i));
            c = i + sublen;
        } while ((i = s.indexOf(sub, c)) != -1);
        if (c < s.length()) {
            sb.append(s.substring(c, s.length()));
        }
        return sb.toString();
    }

    /**
     * 将字符串首字母转大写
     *
     * @param str 需要处理的字符串
     * @return 新的字符串
     */
    public static String upperFirstChar(String str) {
        if (checkEmpty(str)) {
            return "";
        }
        char[] cs = str.toCharArray();
        if ((cs[0] >= 'a') && (cs[0] <= 'z')) {
            cs[0] -= (char) 0x20;
        }
        return String.valueOf(cs);
    }

    /**
     * 将字符串首字母转小写
     *
     * @param str 需要处理的字符串
     * @return 新的字符串
     */
    public static String lowerFirstChar(String str) {
        if (checkEmpty(str)) {
            return "";
        }
        char[] cs = str.toCharArray();
        if ((cs[0] >= 'A') && (cs[0] <= 'Z')) {
            cs[0] += (char) 0x20;
        }
        return String.valueOf(cs);
    }

    /**
     * 判读俩个字符串右侧的length个字符串是否一样
     *
     * @param str1   字符串1
     * @param str2   字符串2
     * @param length 右侧长度
     * @return 是否匹配
     */
    public static boolean rigthEquals(String str1, String str2, int length) {
        return parseRight(str1, length).equals(parseRight(str2, length));
    }

    /**
     * 判读俩个字符串左侧的length个字符串是否一样
     *
     * @param str1   字符串1
     * @param str2   字符串2
     * @param length 左侧长度
     * @return 是否匹配
     */
    public static boolean leftEquals(String str1, String str2, int length) {
        return parseLeft(str1, length).equals(parseLeft(str2, length));
    }

    /**
     * 字符串相似度比较(速度较慢)
     * 较长的字符串放到前面有助于提交效率
     *
     * @param str1
     * @param str2
     * @return 相似度 0 - 1
     */
    public static double SimilarDegree(String str1, String str2) {
        str1 = trimPunct(str1);
        str2 = trimPunct(str2);
        if (str1.length() > str2.length()) {
            return SimilarDegreeImpl(str1, str2);
        } else {
            return SimilarDegreeImpl(str2, str1);
        }
    }

    /**
     * 字符串相似度比较(速度较快)
     *
     * @param str1
     * @param str2
     * @return 相似度 0 - 1
     */
    public static double SimilarityRatio(String str1, String str2) {
        str1 = trimPunct(str1);
        str2 = trimPunct(str2);
        if (str1.length() > str2.length()) {
            return 1 - (double) compare(str1, str2) / Math.max(str1.length(), str2.length());
        } else {
            return 1 - (double) compare(str2, str1) / Math.max(str2.length(), str1.length());
        }
    }

    /**
     * SimilarDegree的辅助实现类
     */
    private static double SimilarDegreeImpl(String str1, String str2) {
        String newStrA = SimilarDegreeImplSup(str1);
        String newStrB = SimilarDegreeImplSup(str2);
        int temp1 = Math.max(newStrA.length(), newStrB.length());
        int temp2 = longestCommonSubstring(newStrA, newStrB).length();
        return temp2 * 1.0 / temp1;
    }

    /**
     * 第二种实现对比俩个字符串的相似度
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相似度0-1
     */
    private static String longestCommonSubstring(String str1, String str2) {
        char[] chars_strA = str1.toCharArray();
        char[] chars_strB = str2.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }
            }
        }
        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[n - 1]) {
                n--;
            } else if (matrix[m][n] == matrix[m - 1][n]) {
                m--;
            } else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);
    }

    /**
     * SimilarDegreeImpl的辅助类
     */
    private static String SimilarDegreeImplSup(String str) {
        StringBuffer sb = new StringBuffer();
        for (char item : str.toCharArray()) {
            if ((item >= 0x4E00 && item <= 0X9FA5) || (item >= 'a' && item <= 'z') || (item >= 'A' && item <= 'Z') || (item >= '0' && item <= '9')) {
                sb.append(item);
            }
        }
        return sb.toString();
    }

    /**
     * 第二种实现对比俩个字符串的相似度
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相似度0-1
     */
    private static int compare(String str1, String str2) {
        int d[][]; // 矩阵
        int n = str1.length();
        int m = str2.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];

        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // 遍历str
        for (i = 1; i <= n; i++) {
            ch1 = str1.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = compareSup(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    /**
     * compare的辅助实现类
     */
    private static int compareSup(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

}