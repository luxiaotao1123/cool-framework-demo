package com.cool.demo.common.model.enums;

public enum HtmlNavIconType {

    INDEX("index", "layui-icon-home"),
    SYSTEM("system", "layui-icon-component"),
    SET("set", "layui-icon-set"),
    MERCHANT("merchant", "layui-icon-user"),
    DEVELOP("develop", "layui-icon-util"),
    STOCK("stock", ""),
    LOG_REPORT("logReport", ""),
    IO_WORK("ioWork", ""),
    WORK_FLOW("workFlow", ""),
    BASE("base", ""),
    ;


    private String code;
    private String icon;
    HtmlNavIconType(String code, String icon){
        this.code = code;
        this.icon = icon;
    }

    public static String get(String code) {
        for (HtmlNavIconType type : HtmlNavIconType.values()){
            if (type.code.equals(code)){
                return type.icon;
            }
        }
        return "layui-icon-file-b";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
