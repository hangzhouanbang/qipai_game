package com.anbang.qipai.game.msg.msjobj;

public class CommonMO {

	private String msg;

	private Object data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

    public CommonMO() {
    }

    public CommonMO(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }
}
