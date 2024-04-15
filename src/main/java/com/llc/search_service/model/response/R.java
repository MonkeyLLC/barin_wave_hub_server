package com.llc.search_service.model.response;

import com.llc.search_service.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;


//@ApiModel
public class R<T> {

    //@ApiModelProperty(value = "状态码，1000为成功状态")
    @Schema(description = "状态码，1000为成功状态")
    private Integer status;

  //  @ApiModelProperty(value = "数据内容")
    private T data;

   // @ApiModelProperty(value = "消息")
    private String msg;

   // @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    public R() {
        super();
    }

    public R(Integer status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public R(Integer status, String msg, T data) {
        super();
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> R<T> ok() {
        return new R<T>(Constants.DEFAULT_SUCCESS_STATUS, Constants.DEFAULT_SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(Constants.DEFAULT_SUCCESS_STATUS, Constants.DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> R<T> ok(T data, String msg) {
        return new R<T>(Constants.DEFAULT_SUCCESS_STATUS, msg, data);
    }

    public static <T> R<T> fail(String msg) {
        return new R<T>(Constants.DEFAULT_ERROR_STATUS, msg, null);
    }

    public static <T> R<T> fail(Integer status, String msg) {
        return new R<T>(status, msg);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
