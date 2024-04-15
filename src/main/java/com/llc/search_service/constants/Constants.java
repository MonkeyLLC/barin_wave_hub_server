package com.llc.search_service.constants;


public final class Constants {

    /**
     * 默认成功状态码
     */
    public static final Integer DEFAULT_SUCCESS_STATUS = 1000;

    /**
     * 默认失败状态码
     */
    public static final Integer DEFAULT_ERROR_STATUS = 1001;

    public static final Integer DEFAULT_NOTFOUND = 1004;

    public static final Integer DEFAULT_NOT_ALLOWED = 1005;

    public static final Integer DEFAULT_SERVER_ERROR = 1010;


    /**
     * token 过期
     */
    public static final Integer TOKEN_OVERDUE = 200004;

    /**
     * tonekn 不匹配
     */
    public static final Integer TOKEN_MISMATCHING = 200005;

    /**
     * token 构建错误
     */
    public static final Integer TOKEN_BUILD_ERROR = 200006;

    /**
     * token 验证签名失败
     */
    public static final Integer TOKEN_SIGNATURE_FAILED = 200007;

    /**
     * token 非法
     */
    public static final Integer TOKEN_ILLEGALITY = 200008;


    /**
     * 默认成功消息
     */
    public static final String DEFAULT_SUCCESS_MSG = "操作成功";

    /**
     * 服务异常
     */
    public static final String DEFAULT_FAIL_MSG = "服务异常";

    /**
     * api版本
     */
    public static final String API_VERSION = "v1";


    /**
     * 通用字符集编码
     */
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 中文字符集编码
     */
    public static final String CHARSET_CHINESE = "GBK";

    /**
     * 英文字符集编码
     */
    public static final String CHARSET_LATIN = "ISO-8859-1";

    /**
     * NULL字符串
     */
    public static final String NULL = "null";

    /**
     * 日期格式
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 简单日期时间格式
     */
    public static final String FORMAT_DATETIME_SIMPLE = "yyyyMMddHHmmss";

    /**
     * 日期时间格式
     */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳格式
     */
    public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 当前登录用户的默认属性
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 限制属性
     */
    public static final String SECRET_KEY = "secretKey";

    /**
     * 过期时间
     */
    public static final long EXPIRATION = 300;
    public static final long USER_LOCK = 900;
    public static final long TWO_HOURS = 2;

    /**
     * 过期前1分钟
     */
    public static final long LASTTIME = 60;

    /**
     * 状态数值0
     */
    public static final Integer ZEOR = 0;

    /**
     * 状态数值1
     */
    public static final Integer SINGLE = 1;

    /**
     * 状态数值2
     */
    public static final Integer TWIN = 2;

    /**
     * jwt 配置
     */
    public static final String JWTHEADER = "Authorization";
    public static final String JWTISSUER = "com.zx";
    public static final String JWTSECTET = "mySecret";
    public static final Integer JWTEXPIRATION = 7200;
    public static final String JWTPREFIX = "ZxStoreUser";

    /**
     * jwt 配置
     */
    public static final Integer REDIS_CACHE_TIME = 24;

    public static final String DEFAULT_LANG = "lang";
    /**
     * 密码ENCORDER
     */
    public static int PW_ENCORDER_SALT = 12;

    /**
     * 发送短信通道
     */
    public static final String SMS_MESSAGE = "st_smsMessage";

    public static final String HW_SMS_MESSAGE = "st_hwSMSMessage";

    public static final String HTTP_HEADER_USER_ID = "Sr-User-Id";

    public static final String HTTP_HEADER_FROM = "Sr-From";

    /**
     * API V3密钥
     */
    public static final String API_V3_KEY = "3dd0b280ea506adae1eacc3bc5301e56";

    public static final Integer VIP_USER_MONEY = 36000;

    public static final Integer MAIOR_USER_MONEY = 180000;

    /**
     * 微信公众号
     */
    public static final String ENCODINGAESKEY = "Bar0mQ9IivhBjHfmjDtwrXeGwpOhQvCjTjXwftSRyUP";

}
