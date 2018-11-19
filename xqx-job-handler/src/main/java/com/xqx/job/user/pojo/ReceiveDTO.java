package com.xqx.job.user.pojo;

public class ReceiveDTO {
	/** 状态：0完成，非0未完成。例：3表示还有3步未做 */
	private String status;
	/** json格式数据 */
	private String data;

	public ReceiveDTO() {
		super();
	}

	public ReceiveDTO(String data) {
		this.data = data;
		this.status = "0";
	}

	public ReceiveDTO(String status, String data) {
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReceiveDTO [status=" + status + ", data=" + data + "]";
	}

}
