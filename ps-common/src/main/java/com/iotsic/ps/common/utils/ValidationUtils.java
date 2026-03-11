package com.iotsic.ps.common.utils;

import cn.hutool.core.util.StrUtil;
import com.iotsic.ps.common.constant.BusinessConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class ValidationUtils {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern ID_CARD_PATTERN = Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,20}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");

    private ValidationUtils() {
    }

    public static boolean isPhone(String phone) {
        if (StrUtil.isBlank(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isEmail(String email) {
        if (StrUtil.isBlank(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isIdCard(String idCard) {
        if (StrUtil.isBlank(idCard)) {
            return false;
        }
        return ID_CARD_PATTERN.matcher(idCard).matches();
    }

    public static boolean isUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isUrl(String url) {
        if (StrUtil.isBlank(url)) {
            return false;
        }
        return URL_PATTERN.matcher(url).matches();
    }

    public static boolean isValidPassword(String password) {
        if (StrUtil.isBlank(password)) {
            return false;
        }
        return password.length() >= BusinessConstant.MIN_PASSWORD_LENGTH
                && password.length() <= BusinessConstant.MAX_PASSWORD_LENGTH;
    }

    public static boolean isValidNickname(String nickname) {
        if (StrUtil.isBlank(nickname)) {
            return false;
        }
        return nickname.length() <= BusinessConstant.MAX_NICKNAME_LENGTH;
    }

    public static boolean isValidUsernameLength(String username) {
        if (StrUtil.isBlank(username)) {
            return false;
        }
        return username.length() <= BusinessConstant.MAX_USERNAME_LENGTH;
    }

    public static boolean isNumeric(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return str.matches("\\d+");
    }

    public static boolean isAlpha(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return str.matches("[a-zA-Z]+");
    }

    public static boolean isAlphanumeric(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return str.matches("[a-zA-Z0-9]+");
    }

    public static boolean isPositiveInteger(Integer num) {
        if (num == null) {
            return false;
        }
        return num > 0;
    }

    public static boolean isPositiveLong(Long num) {
        if (num == null) {
            return false;
        }
        return num > 0;
    }

    public static boolean isInRange(Integer num, int min, int max) {
        if (num == null) {
            return false;
        }
        return num >= min && num <= max;
    }

    public static boolean isInRange(Long num, long min, long max) {
        if (num == null) {
            return false;
        }
        return num >= min && num <= max;
    }

    public static boolean isBlank(String str) {
        return StrUtil.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return StrUtil.isNotBlank(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return StrUtil.isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence str) {
        return StrUtil.isNotEmpty(str);
    }
}
