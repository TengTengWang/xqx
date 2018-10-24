package com.xqx.monitor.bean;

import java.util.List;

/**
 * 所有uri监控返回字段
 */
public class DruidUriBean {

	/** 返回结果状态 1：成功 */
	private int ResultCode;

	/** 当次请求ip:port */
	private String address;

	/** 当次请求地址 */
	private String addressFull;

	private List<UriContent> UriContent;

	public void setResultCode(Integer ResultCode) {
		this.ResultCode = ResultCode;
	}

	public Integer getResultCode() {
		return ResultCode;
	}

	public void setUriContent(List<UriContent> UriContent) {
		this.UriContent = UriContent;
	}

	public List<UriContent> getUriContent() {
		return UriContent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressFull() {
		return addressFull;
	}

	public void setAddressFull(String addressFull) {
		this.addressFull = addressFull;
	}

	@Override
	public String toString() {
		return "DruidUrlBean [ResultCode=" + ResultCode + ", address=" + address + ", addressFull=" + addressFull
				+ ", UriContent=" + UriContent + "]";
	}

	public class UriContent {

		/** 方法 */
		private String URI;
		/** 执行中 */
		private Integer RunningCount;
		/** 最大并发 */
		private Integer ConcurrentMax;
		/** 请求次数 */
		private Integer RequestCount;
		/** 请求时间（和） */
		private Integer RequestTimeMillis;
		private Integer ErrorCount;
		private String LastAccessTime;
		private Integer JdbcCommitCount;
		private Integer JdbcRollbackCount;
		/** Jdbc执行数 */
		private Integer JdbcExecuteCount;
		private Integer JdbcExecuteErrorCount;
		private Integer JdbcExecutePeak;
		/** Jdbc时间（和） */
		private Integer JdbcExecuteTimeMillis;
		/** 读取行数 */
		private Integer JdbcFetchRowCount;
		private Integer JdbcFetchRowPeak;
		/** 更新行数 */
		private Integer JdbcUpdateCount;
		private Integer JdbcUpdatePeak;
		private Integer JdbcPoolConnectionOpenCount;
		private Integer JdbcPoolConnectionCloseCount;
		private Integer JdbcResultSetOpenCount;
		private Integer JdbcResultSetCloseCount;
		private List<Integer> Histogram;
		private List<String> Profiles;
		/** 请求最慢（单次） */
		private Integer RequestTimeMillisMax;
		/** 请求最慢（单次）时的时间 */
		private String RequestTimeMillisMaxOccurTime;

		public void setURI(String URI) {
			this.URI = URI;
		}

		public String getURI() {
			return URI;
		}

		public void setRunningCount(Integer RunningCount) {
			this.RunningCount = RunningCount;
		}

		public Integer getRunningCount() {
			return RunningCount;
		}

		public void setConcurrentMax(Integer ConcurrentMax) {
			this.ConcurrentMax = ConcurrentMax;
		}

		public Integer getConcurrentMax() {
			return ConcurrentMax;
		}

		public void setRequestCount(Integer RequestCount) {
			this.RequestCount = RequestCount;
		}

		public Integer getRequestCount() {
			return RequestCount;
		}

		public void setRequestTimeMillis(Integer RequestTimeMillis) {
			this.RequestTimeMillis = RequestTimeMillis;
		}

		public Integer getRequestTimeMillis() {
			return RequestTimeMillis;
		}

		public void setErrorCount(Integer ErrorCount) {
			this.ErrorCount = ErrorCount;
		}

		public Integer getErrorCount() {
			return ErrorCount;
		}

		public void setLastAccessTime(String LastAccessTime) {
			this.LastAccessTime = LastAccessTime;
		}

		public String getLastAccessTime() {
			return LastAccessTime;
		}

		public void setJdbcCommitCount(Integer JdbcCommitCount) {
			this.JdbcCommitCount = JdbcCommitCount;
		}

		public Integer getJdbcCommitCount() {
			return JdbcCommitCount;
		}

		public void setJdbcRollbackCount(Integer JdbcRollbackCount) {
			this.JdbcRollbackCount = JdbcRollbackCount;
		}

		public Integer getJdbcRollbackCount() {
			return JdbcRollbackCount;
		}

		public void setJdbcExecuteCount(Integer JdbcExecuteCount) {
			this.JdbcExecuteCount = JdbcExecuteCount;
		}

		public Integer getJdbcExecuteCount() {
			return JdbcExecuteCount;
		}

		public void setJdbcExecuteErrorCount(Integer JdbcExecuteErrorCount) {
			this.JdbcExecuteErrorCount = JdbcExecuteErrorCount;
		}

		public Integer getJdbcExecuteErrorCount() {
			return JdbcExecuteErrorCount;
		}

		public void setJdbcExecutePeak(Integer JdbcExecutePeak) {
			this.JdbcExecutePeak = JdbcExecutePeak;
		}

		public Integer getJdbcExecutePeak() {
			return JdbcExecutePeak;
		}

		public void setJdbcExecuteTimeMillis(Integer JdbcExecuteTimeMillis) {
			this.JdbcExecuteTimeMillis = JdbcExecuteTimeMillis;
		}

		public Integer getJdbcExecuteTimeMillis() {
			return JdbcExecuteTimeMillis;
		}

		public void setJdbcFetchRowCount(Integer JdbcFetchRowCount) {
			this.JdbcFetchRowCount = JdbcFetchRowCount;
		}

		public Integer getJdbcFetchRowCount() {
			return JdbcFetchRowCount;
		}

		public void setJdbcFetchRowPeak(Integer JdbcFetchRowPeak) {
			this.JdbcFetchRowPeak = JdbcFetchRowPeak;
		}

		public Integer getJdbcFetchRowPeak() {
			return JdbcFetchRowPeak;
		}

		public Integer getJdbcUpdateCount() {
			return JdbcUpdateCount;
		}

		public void setJdbcUpdateCount(Integer jdbcUpdateCount) {
			JdbcUpdateCount = jdbcUpdateCount;
		}

		public Integer getJdbcUpdatePeak() {
			return JdbcUpdatePeak;
		}

		public void setJdbcUpdatePeak(Integer jdbcUpdatePeak) {
			JdbcUpdatePeak = jdbcUpdatePeak;
		}

		public void setJdbcPoolConnectionOpenCount(Integer JdbcPoolConnectionOpenCount) {
			this.JdbcPoolConnectionOpenCount = JdbcPoolConnectionOpenCount;
		}

		public Integer getJdbcPoolConnectionOpenCount() {
			return JdbcPoolConnectionOpenCount;
		}

		public void setJdbcPoolConnectionCloseCount(Integer JdbcPoolConnectionCloseCount) {
			this.JdbcPoolConnectionCloseCount = JdbcPoolConnectionCloseCount;
		}

		public Integer getJdbcPoolConnectionCloseCount() {
			return JdbcPoolConnectionCloseCount;
		}

		public void setJdbcResultSetOpenCount(Integer JdbcResultSetOpenCount) {
			this.JdbcResultSetOpenCount = JdbcResultSetOpenCount;
		}

		public Integer getJdbcResultSetOpenCount() {
			return JdbcResultSetOpenCount;
		}

		public void setJdbcResultSetCloseCount(Integer JdbcResultSetCloseCount) {
			this.JdbcResultSetCloseCount = JdbcResultSetCloseCount;
		}

		public Integer getJdbcResultSetCloseCount() {
			return JdbcResultSetCloseCount;
		}

		public void setHistogram(List<Integer> Histogram) {
			this.Histogram = Histogram;
		}

		public List<Integer> getHistogram() {
			return Histogram;
		}

		public void setProfiles(List<String> Profiles) {
			this.Profiles = Profiles;
		}

		public List<String> getProfiles() {
			return Profiles;
		}

		public void setRequestTimeMillisMax(Integer RequestTimeMillisMax) {
			this.RequestTimeMillisMax = RequestTimeMillisMax;
		}

		public Integer getRequestTimeMillisMax() {
			return RequestTimeMillisMax;
		}

		public void setRequestTimeMillisMaxOccurTime(String RequestTimeMillisMaxOccurTime) {
			this.RequestTimeMillisMaxOccurTime = RequestTimeMillisMaxOccurTime;
		}

		public String getRequestTimeMillisMaxOccurTime() {
			return RequestTimeMillisMaxOccurTime;
		}

		@Override
		public String toString() {
			return "UriContent [URI=" + URI + ", RunningCount=" + RunningCount + ", ConcurrentMax=" + ConcurrentMax
					+ ", RequestCount=" + RequestCount + ", RequestTimeMillis=" + RequestTimeMillis + ", ErrorCount="
					+ ErrorCount + ", LastAccessTime=" + LastAccessTime + ", JdbcCommitCount=" + JdbcCommitCount
					+ ", JdbcRollbackCount=" + JdbcRollbackCount + ", JdbcExecuteCount=" + JdbcExecuteCount
					+ ", JdbcExecuteErrorCount=" + JdbcExecuteErrorCount + ", JdbcExecutePeak=" + JdbcExecutePeak
					+ ", JdbcExecuteTimeMillis=" + JdbcExecuteTimeMillis + ", JdbcFetchRowCount=" + JdbcFetchRowCount
					+ ", JdbcFetchRowPeak=" + JdbcFetchRowPeak + ", JdbcUpdateCount=" + JdbcUpdateCount
					+ ", JdbcUpdatePeak=" + JdbcUpdatePeak + ", JdbcPoolConnectionOpenCount="
					+ JdbcPoolConnectionOpenCount + ", JdbcPoolConnectionCloseCount=" + JdbcPoolConnectionCloseCount
					+ ", JdbcResultSetOpenCount=" + JdbcResultSetOpenCount + ", JdbcResultSetCloseCount="
					+ JdbcResultSetCloseCount + ", Histogram=" + Histogram + ", Profiles=" + Profiles
					+ ", RequestTimeMillisMax=" + RequestTimeMillisMax + ", RequestTimeMillisMaxOccurTime="
					+ RequestTimeMillisMaxOccurTime + "]";
		}

	}
}