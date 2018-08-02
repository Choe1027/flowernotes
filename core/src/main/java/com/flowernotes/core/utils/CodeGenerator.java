package com.flowernotes.core.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author cyk
 * @date 2018/8/2/002 09:11
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class CodeGenerator {

    // 过滤字符，过滤掉难以辨认的字符
    private static final List<Character> filterChrs = Arrays.asList(new Character[]{'o', 'O', '0', '1', 'I', 'l', '9', 'g', 'G'});

    /**
     * 随机生成验证码
     * (缺省，数字+大写字母)
     * @param length 验证码的长度
     * @return 长度为length的验证码
     */
    public static String getCode(int length) {
        return getCode(length, CodeType.all_big);
    }
    /**
     * 随机生成验证码
     * @param length 验证码的长度
     * @param ct	验证码生成规则（{@link CodeType}）
     * @return 长度为length的验证码
     */
    public static String getCode(int length, CodeType ct) {
        String sb = "";
        Random random=new Random();
        char ch = '0';
        for (int i = 0; i < length; i++) {
            // 选择生成类型
            while(true) {
                switch (ct) {
                    case all_en: {
                        int index=random.nextInt(2);
                        if(index == 0) {
                            ch = (char)(random.nextInt(26)+65);
                        } else {
                            ch = (char)(random.nextInt(26)+97);
                        }
                        break;
                    }
                    case all_no: {
                        ch = (char)(random.nextInt(10)+48);
                        break;
                    }
                    case all: {
                        int index=random.nextInt(3);
                        if(index == 0) {
                            // 生成数字
                            ch = (char)(random.nextInt(10)+48);
                        } else if(index == 1) {
                            // 生成大写字母
                            ch = (char)(random.nextInt(26)+65);
                        } else {
                            // 生成小写字母
                            ch = (char)(random.nextInt(26)+97);
                        }
                        break;
                    }
                    case all_small: {
                        int index=random.nextInt(2);
                        if(index == 0) {
                            ch = (char)(random.nextInt(10)+48);
                        } else {
                            ch = (char)(random.nextInt(26)+97);
                        }
                        break;
                    }
                    case all_big: {
                        int index=random.nextInt(2);
                        if(index == 0) {
                            ch = (char)(random.nextInt(10)+48);
                        } else {
                            ch = (char)(random.nextInt(26)+65);
                        }
                        break;
                    }
                }
                if(filterChrs.contains(ch)) {
                    continue;
                }
                break;
            }
            sb += ch;
        }
        return sb;
    }
    /** 随机字符生成规则 */
    public enum CodeType {
        /** 全英文,有大小写 */
        all_en,
        /** 全数字 */
        all_no,
        /** 全字符,有数字英文大小写 */
        all,
        /** 全字符,有数字英文小写 */
        all_small,
        /** 全字符,有数字英文大写 */
        all_big;
    }

}
