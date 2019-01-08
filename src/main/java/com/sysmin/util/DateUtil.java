package com.sysmin.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间转换器
 *
 * @author:Li
 * @time: 2018/11/22 15:27
 * @version: 1.0
 * unix时间戳 *1000 以防出错
 * 计算时间差 两者时间段必须相同不能一个是yyyy-MM-dd HH:mm:ss 另一个是HH:mm:ss
 */
public class DateUtil {

    public static final Long UNIX_TIME = 1000L;
    private static final Long WEEK_STAMP = 604800000L;
    private static final Long DAY_STAMP = 86400000L;
    private static final Long HOUR_STAMP = 3600000L;
    private static final Long MINUTE_STAMP = 60000L;
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String WEEK = "EEEE";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 检查时间格式
     *
     * @param format 时间格式
     * @return SimpleDateFormat对象
     */
    private static SimpleDateFormat checkSimpleDateFormat(String format) {
        if (format != null && !"".equals(format)) {
            return getSimple(format);
        } else {
            return getSimple(DEFAULT_FORMAT);
        }
    }

    /**
     * 存放SimpleDateFormat的容器
     */
    private static Map<String, SimpleDateFormat> container = new HashMap<String, SimpleDateFormat>();

    /**
     * 从容器中获取simpleDateFormate
     *
     * @param format
     * @return
     */
    private static SimpleDateFormat getSimple(String format) {
        if (!container.containsKey(format)) {
            container.put(format, new SimpleDateFormat(format));
        }
        return container.get(format);
    }

    /**
     * 将任何类型的时间转化为date (string时间, date时间, long时间戳)
     *
     * @param s      时间
     * @param format 如果是string时间需要传参, 其他为null
     * @return date时间
     */
    private static Date objToDate(Object s, String format) {
        Date d;
        if (s instanceof String) {
            d = stringToDate((String) s, format);
        } else if (s instanceof Date) {
            d = (Date) s;
        } else if (s instanceof Long) {
            d = stringToDate(stampToDate(s, DEFAULT_FORMAT), DEFAULT_FORMAT);
        } else {
            d = new Date();
            // throw new RuntimeException("类型错误");
        }
        return d;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s      时间戳
     * @param format 时间格式
     * @return 处理好的时间
     */
    public static String stampToDate(Object s, String format) {
        SimpleDateFormat simpleDateFormat = checkSimpleDateFormat(format);
        if (s instanceof String) {
            return simpleDateFormat.format(new Date(new Long((String) s)));
        } else if (s instanceof Date) {
            return simpleDateFormat.format((Date) s);
        } else if (s instanceof Long) {
            return simpleDateFormat.format(new Date((Long) s));
        } else {
            return null;
        }
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s      时间
     * @param format 时间格式
     * @param start  截取位数
     * @return 时间戳
     */
    public static long dateToStamp(String s, String format, int start) {
        SimpleDateFormat simpleDateFormat = checkSimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s, new ParsePosition(start));
        return date.getTime();
    }

    /**
     * 修改日期格式
     *
     * @param s      需处理的时间
     * @param format 输出格式
     * @return 处理好的时间
     */
    public static String dateToString(Date s, String format) {
        SimpleDateFormat simple = checkSimpleDateFormat(format);
        return simple.format(s);
    }

    /**
     * 修改日期格式
     *
     * @param s         需处理的时间
     * @param oldFormat 输出格式
     * @param newFormat 输出格式
     * @return 处理好的时间
     */
    public static String dateFormat(String s, String oldFormat, String newFormat) {
        SimpleDateFormat simpleDateFormat1 = checkSimpleDateFormat(oldFormat);
        SimpleDateFormat simpleDateFormat2 = checkSimpleDateFormat(newFormat);
        Date date = simpleDateFormat1.parse(s, new ParsePosition(0));
        return simpleDateFormat2.format(date);
    }

    /**
     * 获得当前时间
     *
     * @param format 时间格式
     * @return 时间
     */
    public static String getNowDate(String format) {
        SimpleDateFormat simpleDateFormat = checkSimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 将string的时间转换为date
     *
     * @param s      时间
     * @param format 当前时间格式
     * @return date的时间
     */
    public static Date stringToDate(String s, String format) {
        SimpleDateFormat simpleDateFormat = checkSimpleDateFormat(format);
        return simpleDateFormat.parse(s, new ParsePosition(0));
    }

    /**
     * 判断是否是闰年
     *
     * @param year 年份
     * @return true闰年  false平年
     */
    public static boolean checkYear(String year) {
        int y = Integer.valueOf(year);
        return y % 4 == 0 && y % 100 != 0;
    }

    /**
     * 获得月份的最后一天
     *
     * @param year  年份
     * @param month 月份
     * @return 最后一天
     */
    public static int getMonthLastDay(String year, String month) {
        int m = Integer.valueOf(month);
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            return 30;
        } else if (m == 2) {
            if (year == null || !"".equals(year)) {
                return 29;
            } else if (checkYear(year)) {
                return 28;
            } else {
                return 29;
            }
        } else {
            return 31;
        }
    }

    /**
     * 获得时间的前后
     *
     * @param s       需要判断的时间 (String,Date,Long)
     * @param format  时间的格式 (如果s为String类型需要传参,其他格式为null)
     * @param precies 是否使用精确时间 如: 23小时前/昨天
     * @return 结果
     */
    public static String getDateAgo(Object s, String format, boolean precies) {
        Date now = new Date(), d = objToDate(s, format);
        // 当前时间
        int nowYear = Integer.valueOf(getNowDate(YEAR)),
                nowMonth = Integer.valueOf(getNowDate(MONTH)),
                nowDay = Integer.valueOf(getNowDate(DAY)),
                nowHour = Integer.valueOf(getNowDate(HOUR)),
                nowMinute = Integer.valueOf(getNowDate(MINUTE));
        // 比较时间
        int dYear = Integer.valueOf(dateToString(d, YEAR)),
                dMonth = Integer.valueOf(dateToString(d, MONTH)),
                dDay = Integer.valueOf(dateToString(d, DAY)),
                dHour = Integer.valueOf(dateToString(d, HOUR)),
                dMinute = Integer.valueOf(dateToString(d, MINUTE));
        //  System.out.println(nowYear + "/" + nowMonth + "/" + nowDay + "/" + nowHour + "/" + nowMinute);
        //  System.out.println(dYear + "/" + dMonth + "/" + dDay + "/" + dHour + "/" + dMinute);
        // d.before(now) d是否在now之前
        // d.after(now) d是否在now之后
        if (d.before(now)) {
            if (nowYear > dYear) {
                if ((nowYear - dYear) > 10) {
                    return "很久以前";
                } else if (nowYear - dYear == 1) {
                    if (precies) {
                        if (nowMonth > dMonth) {
                            return "1年前";
                        } else if (nowMonth == dMonth) {
                            if (nowDay > dDay) {
                                return "1年前";
                            } else {
                                return "11月前";
                            }
                        } else {
                            return 12 - (dMonth - nowMonth) + "月前";
                        }
                    } else {
                        return "去年";
                    }
                } else {
                    return nowYear - dYear + "年前";
                }
            }
            if (nowMonth > dMonth) {
                if (nowMonth - dMonth == 1 && precies) {
                    if (nowDay >= dDay) {
                        return "1月前";
                    } else {
                        return getMonthLastDay(String.valueOf(nowYear), String.valueOf(nowMonth)) - (dDay - nowDay) + "天前";
                    }
                } else {
                    return nowMonth - dMonth + "月前";
                }
            }
            if (nowDay > dDay) {
                if (precies) {
                    if (nowDay - dDay == 1) {
                        if (nowHour >= dHour) {
                            return "昨天";
                        } else {
                            return 24 - (dHour - nowHour) + "小时前";
                        }
                    } else if (nowDay - dDay == 2) {
                        return "前天";
                    } else {
                        return nowDay - dDay + "天前";
                    }
                } else {
                    if (nowDay - dDay == 1) {
                        return "昨天";
                    } else if (nowDay - dDay == 2) {
                        return "前天";
                    } else {
                        return nowDay - dDay + "天前";
                    }
                }
            }
            if (nowHour > dHour) {
                if (nowHour - dHour == 1 && precies) {
                    if (nowMinute >= dMinute) {
                        return "1小时前";
                    } else {
                        return 60 - (dMinute - nowMinute) + "分钟前";
                    }
                } else {
                    return nowHour - dHour + "小时前";
                }
            }
            if (nowMinute > dMinute) {
                return nowMinute - dMinute + "分钟前";
            }
        } else if (d.after(now)) {
            if (nowYear < dYear) {
                if ((dYear - nowYear) > 10) {
                    return "很久以后";
                } else if (dYear - nowYear == 1) {
                    if (precies) {
                        if (nowMonth > dMonth) {
                            return 12 + dMonth - nowMonth + "月后";
                        } else if (nowMonth == dMonth) {
                            if (dDay > nowDay) {
                                return "1年后";
                            } else {
                                return "11月后";
                            }
                        } else {
                            return "1年后";
                        }
                    } else {
                        return "明年";
                    }
                } else {
                    return dYear - nowYear + "年后";
                }
            }
            if (nowMonth < dMonth) {
                if (dMonth - nowMonth == 1 && precies) {
                    if (nowDay >= dDay) {
                        return getMonthLastDay(String.valueOf(nowYear), String.valueOf(nowMonth)) + (dDay - nowDay) + "天后";
                    } else {
                        return "1月后";
                    }
                } else {
                    return dMonth - nowMonth + "月后";
                }
            }
            if (nowDay < dDay) {
                if (dDay - nowDay == 1 && precies) {
                    if (dMinute < nowMinute) {
                        return 24 - (nowMinute - dMinute) + "小时后";
                    } else {
                        return "明天";
                    }
                } else if (dDay - nowDay == 1) {
                    return "后天";
                } else if (dDay - nowDay == 2) {
                    return "后天";
                } else {
                    return dDay - nowDay + "天后";
                }
            }
            if (nowHour < dHour) {
                if (dHour - nowHour == 1 && precies) {
                    if (dMinute < nowMinute) {
                        return 60 - (nowMinute - dMinute) + "分钟后";
                    } else {
                        return "1小时后";
                    }
                } else {
                    return dHour - nowHour + "小时后";
                }
            }
            if (nowMinute < dMinute) {
                return dMinute - nowMinute + "分钟后";
            }
        }
        return "刚刚";
    }

    /**
     * 获得时间的前后
     * 没有多if判断的准确 具体体现在月份默认30天
     *
     * @param s       需要判断的时间 (String,Date,Long)
     * @param format  时间的格式 (如果s为String类型需要传参,其他格式为null)
     * @param vague   是否使用模糊时间 如: 昨天/23小时前
     * @param useWeek 是否使用周 如: 1周前/8天前
     * @return 结果
     */
    public static String getDateAgo(Object s, String format, boolean vague, boolean useWeek) {
        Date nowDate = new Date(),
                dDate = objToDate(s, format);
        long now = nowDate.getTime(),
                d = dDate.getTime();
        int nowYear = Integer.valueOf(dateToString(nowDate, YEAR)),
                dYear = Integer.valueOf(dateToString(dDate, YEAR)),
                nowDay = Integer.valueOf(dateToString(nowDate, DAY)),
                dDay = Integer.valueOf(dateToString(dDate, DAY));
        if (now - d > 0) {
            long before = now - d;
            if (nowYear - dYear > 10 && vague) {
                return "很久以前";
            }
            if (nowYear - dYear == 1 && vague) {
                return "去年";
            }
            if (before > (DAY_STAMP * 365)) {
                return (before / (DAY_STAMP * 365)) + "年前";
            }
            if (before > (DAY_STAMP * 30)) {
                return (before / (DAY_STAMP * 30)) != 12 ? (before / (DAY_STAMP * 30)) + "月前" : "1年前";
            }
            if (before > WEEK_STAMP && useWeek) {
                return (before / WEEK_STAMP) + "周前";
            }
            if (nowDay - dDay == 1 && vague) {
                return "昨天";
            }
            if (nowDay - dDay == 2 && vague) {
                return "前天";
            }
            if (before > DAY_STAMP) {
                return (before / DAY_STAMP) + "天前";
            }
            if (before > HOUR_STAMP) {
                return (before / HOUR_STAMP) + "小时前";
            }
            if (before > MINUTE_STAMP) {
                return (before / MINUTE_STAMP) + "分钟前";
            }
        } else if (d - now > 0) {
            long after = d - now;
            if (dYear - nowYear > 10 && vague) {
                return "很久以后";
            }
            if (dYear - nowYear == 1 && vague) {
                return "明年";
            }
            if (after > (DAY_STAMP * 365)) {
                return (after / (DAY_STAMP * 365)) + "年后";
            }
            if (after > (DAY_STAMP * 30)) {
                return (after / (DAY_STAMP * 30)) != 12 ? (after / (DAY_STAMP * 30)) + "月后" : "1年后";
            }
            if (after > WEEK_STAMP && useWeek) {
                return (after / WEEK_STAMP) + "周后";
            }
            if (dDay - nowDay == 1 && vague) {
                return "明天";
            }
            if (dDay - nowDay == 2 && vague) {
                return "后天";
            }
            if (after > DAY_STAMP) {
                return (after / DAY_STAMP) + "天后";
            }
            if (after > HOUR_STAMP) {
                return (after / HOUR_STAMP) + "小时后";
            }
            if (after > MINUTE_STAMP) {
                return (after / MINUTE_STAMP) + "分钟后";
            }
        }
        return "刚刚";
    }

    /**
     * 获得两个时间的时差
     *
     * @param date1       时间1
     * @param date1Format string时间类型, 可以为null
     * @param date2       时间2
     * @param date2Format string时间类型, 可以为null
     * @param type        true返回 0天0时5分 false 5分
     * @return 时差
     */
    public static String dateDistance(Object date1, String date1Format, Object date2, String date2Format, boolean type) {
        return dateDistance(date1, date1Format, date2, date2Format, type, "天", "时", "分");
    }

    /**
     * 获得两个时间的时差
     *
     * @param date1       时间1
     * @param date1Format string时间类型, 可以为null
     * @param date2       时间2
     * @param date2Format string时间类型, 可以为null
     * @param type        true返回 0天0时5分 false 5分
     * @param day         天显示的文字
     * @param hour        时显示的文字
     * @param minute      分显示的文字
     * @return 时差
     */
    public static String dateDistance(Object date1, String date1Format, Object date2, String date2Format, boolean type, String day, String hour, String minute) {
        long d1 = objToDate(date1, date1Format).getTime(), d2 = objToDate(date2, date2Format).getTime();
        long distance = (d1 - d2) > 0 ? d1 - d2 : d2 - d1;
        long distanceDay = distance / DAY_STAMP;
        long distanceHour = distance % DAY_STAMP / HOUR_STAMP;
        long distanceMinute = distance % DAY_STAMP % HOUR_STAMP / MINUTE_STAMP;
        String result = "";
        if (type) {
            if (!(distanceDay == 0 && distanceHour == 0 && distanceMinute == 0)) {
                result = distanceDay + (day == null ? "天" : day) + distanceHour + (hour == null ? "时" : hour) + distanceMinute + (minute == null ? "分" : minute);
            } else {
                result = "现在";
            }
        } else {
            if (distanceDay != 0) {
                result += distanceDay + (day == null ? "天" : day);
            }
            if (distanceHour != 0) {
                result += distanceHour + (hour == null ? "时" : hour);
            }
            if (distanceMinute != 0) {
                result += distanceMinute + (minute == null ? "分" : minute);
            }
        }
        return result;
    }

    /**
     * 将时间转化成12时制
     *
     * @param date         时间
     * @param dateFormat   string时间类型, 可以为null
     * @param beforeFormat 小时前面的时间格式 不包括小时(HH)
     * @param afterFormat  小时后面的时间格式 不包括小时(HH)
     * @return 12时制的时间
     */
    public static String to12Hour(Object date, String dateFormat, String beforeFormat, String afterFormat) {
        Date d = objToDate(date, dateFormat);
        return dateFormat(dateToString(d, null), DEFAULT_FORMAT, beforeFormat + hourType(Integer.valueOf(dateToString(d, HOUR)), true, false) + afterFormat);
    }

    /**
     * 将时间转化成12时制
     *
     * @param date       时间
     * @param dateFormat string时间类型, 可以为null
     * @return 12时制的时间
     */
    public static String to12Hour(Object date, String dateFormat) {
        return to12Hour(date, dateFormat, "yyyy-MM-dd ", ":mm:ss");
    }

    /**
     * 时间对应是时间段
     *
     * @param hour   小时
     * @param append 是否拼接时间
     * @param use24  拼接是否使用24小时制
     * @param temp   占位符
     * @return 时间类型
     */
    public static String hourType(int hour, boolean append, boolean use24, String temp) {
        String type = "";
        temp = (temp == null || "".equals(temp) ? "" : temp);
        if (hour >= 0 && hour < 6) {
            type = "凌晨";
        } else if (hour >= 6 && hour < 11) {
            type = "上午";
        } else if (hour >= 11 && hour < 13) {
            type = "中午";
        } else if (hour >= 13 && hour < 18) {
            type = "下午";
        } else if (hour >= 18 && hour < 20) {
            type = "傍晚";
        } else if (hour >= 20 && hour < 22) {
            type = "晚上";
        } else if (hour >= 22 && hour <= 23) {
            type = "深夜";
        }
        if (append) {
            if (!use24) {
                hour = hour > 12 ? hour - 12 : hour;
            }
            return type + temp + hour;
        }
        return type;
    }

    /**
     * 时间对应是时间段 默认无占位符
     *
     * @param hour   小时
     * @param append 是否拼接时间
     * @param use24  拼接是否使用24小时制
     * @return 时间类型
     */
    public static String hourType(int hour, boolean append, boolean use24) {
        return hourType(hour, append, use24, "");
    }

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        Date d1 = stringToDate(dateFormat("2018/11/28/15/39", "yyyy/MM/dd/HH/mm", DEFAULT_FORMAT), null);
        Date d2 = stringToDate(dateFormat("2018/10/12/3/22", "yyyy/MM/dd/HH/mm", DEFAULT_FORMAT), null);
        System.out.println(System.currentTimeMillis() - a);
    }

}