package com.xqx.job.user.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "receive")
public class ReceiveDO {
	@Id
	@GeneratedValue
	private Long id;
	/** 状态：0完成，非0未完成。例：3表示还有3步未做 */
	private String status;
	/** json格式数据 */
	private String data;

	public ReceiveDO() {
		super();
	}

	public ReceiveDO(String data) {
		this.data = data;
	}

	public ReceiveDO(String status, String data) {
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
