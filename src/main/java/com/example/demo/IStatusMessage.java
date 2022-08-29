package com.example.demo;

public interface IStatusMessage {
    String getCode();
    String getMessage();

    enum SystemStatus implements IStatusMessage {

        SUCCESS("1001", "操作成功"), //请求成功
        ERROR("400", "网络异常，请稍后重试~"),
        FILE_UPLOAD_NULL("402","上传图片为空文件"), ;      //请求失败

        private String code;
        private String message;

        SystemStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return this.code;
        }
        public String getMessage() {
            return this.message;
        }
    }
}