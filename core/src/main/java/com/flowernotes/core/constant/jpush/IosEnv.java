package com.flowernotes.core.constant.jpush;

/**
 * @author cyk
 * @date 2018/8/1/001 10:08
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum  IosEnv {
    /**
     * 开发环境
     */
    dev(1,"dev"),
    /**
     * 生产环境
     */
    prod(2,"prod")
            ;
    private Integer env;
    private String value;

    private IosEnv(Integer env, String value) {
        this.env = env;
        this.value = value;
    }

    public Integer getEnv() {
        return env;
    }

    public void setEnv(Integer env) {
        this.env = env;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据环境的值获取环境类型
     * @param value
     * @return
     */
    public static IosEnv getEnvByValue(String value){
        IosEnv[] values = IosEnv.values();
        for (IosEnv env : values){
            if (env.getValue().equals(value)){
                return env;
            }
        }
        return null;
    }
}
