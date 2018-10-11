package com.learning.base.util;

import java.util.regex.Pattern;

/**
 * 验证帮助类
 *
 */
public final class ValidUtil {

    private ValidUtil() {

    }

    /**
     * 手机号码:13333333333
     */
    public static final String MOBILE = "^((1)+\\d{10})$";

    /**
     * 手机号或者固话号码
     * **/

    public static final String MOBILEORTELL = "^(\\d{3,4}-?\\d{7,8})|(1\\d{10})$";

    /**
     * QQ
     * 必须是数字，且首位不能为0，最短4位，最长13位,可以为空
     */
    public static final String QQ = "([1-9][0-9]{4,14})?";

    /**
     * 邮箱:service@ofpay.com
     */
    public static final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * 邮箱前缀
     */
    public static final String EMAILPRE = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*";

    /**
     * 固定电话:025-88888888
     */
    public static final String TELL = "^(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";

    /**
     * 身份证号码:15 或 17为数字 + 字母
     */
    public static final String IDNO = "^(\\d{15}|\\d{17}[A-Za-z0-9])$";

    /**
     * 判断是手机号
     */
    public static final String PRICEMODULE = "5|6|7";

    /**
     * 是否是数字
     */
    public static final String NUMBER = "\\d*";

    /**
     * 是否是IP地址段
     */
    public static final String SHORTIP = "25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))";

    public static final String NUMWITHPOINT = "^\\d+[\\.]?\\d*$";

    /**
     * 最多两位小数的正实数
     */
    public static final String NUMWITH2POINT = "^[0-9]+(.[0-9]{1,2})?$";
    /**
     * 最多三位小数的正实数
     */
    public static final String NUMWITH3POINT = "^[0-9]+(.[0-9]{1,3})?$";
    /**
     * 人天(0.5的倍数或空)
     */
    public static final String MANDAY = "^([0-9]+(.5)?)|$";

    /**
     * 手机号码或者为空
     */
    public static final String MOBILEOREMPTY = "^((1)+\\d{10})+|$";

    /**
     * 邮箱:service@ofpay.com或者为空
     */
    public static final String EMAILOREMPTY = "^([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))+|$";

    public static final String PWDSTRING = "[A-Za-z]";

    public static final String PWDNUM = "[0-9]";

    public static final String SPECIALSTRING = "[`~!@#$%^&*()\\-_+=\\\\|{}':;'\",\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    public static final String SECURITYQ1 = "[1-5]{1}";
    public static final String SECURITYQ2 = "[6-9]{1}||10";
    public static final String SECURITYQ3 = "1[1-5]{1}";

    public static final String PWD_RULE = "^(?![0-9]+$)(?![a-z]+$)(?![0-9a-z]+$)(?![\\~\\)\\!@#\\$%^&\\*\\(\\)_\\+\\-=\\{\\}\\[\\]|:;<>\\?,\\./]+$)[0-9A-Za-z\\~\\)\\!@#\\$%^&\\*\\(\\)_\\+\\-=\\{\\}\\[\\]|:;<>\\?,\\./]{8,16}$";

    /**
     * 判断密码是否是字母和数字的组合
     *
     * @param str
     * @return
     */
    public static boolean isPwd(String str) {
        Pattern p1 = Pattern.compile(PWDNUM);
        Pattern p2 = Pattern.compile(PWDSTRING);
        return (p1.matcher(str).find() && p2.matcher(str).find());
    }

    /**
     * 判断密码是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isSpecialString(String str) {
        Pattern p = Pattern.compile(SPECIALSTRING);
        return p.matcher(str).find();
    }

    public static boolean matchNewPwdRule(String str) {
        Pattern p = Pattern.compile(PWD_RULE);
        return p.matcher(str).find();
    }

    /**
     * 短信验证码
     */
    public static final String MOBILECODE = "\\d{6}";

    /**
     * 邮箱验证码
     */
    public static final String EMAILCODE = "\\d{6}";

    /**
     * 金额
     */
    public static final String CREDIT = "^\\d+[\\.]?\\d*$||''";

    public static final String CREDIT2 = "^\\d+[\\.]?\\d*$||-\\d+[\\.]?\\d*$||''";

    /**
     * 日期格式 ：yyyy-MM-dd HH:mm:ss
     * */
    public static final String DATEFORMAT = "^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])\\s(0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})$";
    /**
     * 日期格式 不带时分秒：yyyy-MM-dd
     * */
    public static final String DATEFORMATSHORT = "^(\\d{4})-\\d{1,2}-\\d{1,2}$";

    /**
     * 日期格式 不带时分秒：yyyy-MM
     * */
    public static final String DATEFORMATMONTH = "^(\\d{4})-\\d{1,2}$";


    /**
     * 只是验证日期格式，不验证其正确性。
     * 基本格式：yyyy-MM-dd HH:mm:ss
     * */
    public static boolean isDateSimpleFormat(String dateTime) {
        Pattern pattern = Pattern.compile(DATEFORMAT);
        return pattern.matcher(dateTime).find();
    }

    /**
     * 是否是手机号码
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE);
        return pattern.matcher(mobile).find();
    }

    /**
     * 是否是邮箱地址
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL);
        return pattern.matcher(email).find();
    }
}
