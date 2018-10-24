package com.xqx.monitor.bean;

import java.util.List;

/**
 * 所有sql监控返回字段
 */
public class DruidSqlBean {
	/** 返回结果状态 1：成功 */
	private int ResultCode;

	/** 当次请求ip:port */
	private String address;

	/** 当次请求地址 */
	private String addressFull;
	
	private List<SqlContent> Content;

	public void setResultCode(int ResultCode) {
		this.ResultCode = ResultCode;
	}

	public int getResultCode() {
		return ResultCode;
	}

	public void setContent(List<SqlContent> Content) {
		this.Content = Content;
	}

	public List<SqlContent> getContent() {
		return Content;
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
		return "DruidSqlBean [ResultCode=" + ResultCode + ", address=" + address + ", addressFull=" + addressFull
				+ ", Content=" + Content + "]";
	}

	/**
	 * 单句sql执行的详情情况
	 */
	public class SqlContent {
		private int ExecuteAndResultSetHoldTime;
		private List<Integer> EffectedRowCountHistogram;
		private String LastErrorMessage;
		private List<Integer> Histogram;
		private int InputStreamOpenCount;
		private int BatchSizeTotal;
		private int FetchRowCountMax;
		/** sql执行错误次数 */
		private int ErrorCount;
		private int BatchSizeMax;
		private String URL;
		private String Name;
		private String LastErrorTime;
		private int ReaderOpenCount;
		private int EffectedRowCountMax;
		private String LastErrorClass;
		private int InTransactionCount;
		private String LastErrorStackTrace;
		private int ResultSetHoldTime;
		/** 总共执行时间，每次执行该sql的和 */
		private int TotalTime;
		private int ID;
		private int ConcurrentMax;
		private int RunningCount;
		/** sql读取数据行数 */
		private int FetchRowCount;
		private String MaxTimespanOccurTime;
		private String LastSlowParameters;
		private int ReadBytesLength;
		private String DbType;
		private String DataSource;
		private String SQL;
		private long HASH;
		private String LastError;
		/** 该sql最慢执行时间 */
		private int MaxTimespan;
		private int BlobOpenCount;
		/** 该sql执行次数 */
		private int ExecuteCount;
		/** sql更新数据行数 */
		private int EffectedRowCount;
		private int ReadStringLength;
		private List<Integer> ExecuteAndResultHoldTimeHistogram;
		private String File;
		private int ClobOpenCount;
		private String LastTime;
		private List<Integer> FetchRowCountHistogram;

		public void setExecuteAndResultSetHoldTime(int ExecuteAndResultSetHoldTime) {
			this.ExecuteAndResultSetHoldTime = ExecuteAndResultSetHoldTime;
		}

		public int getExecuteAndResultSetHoldTime() {
			return ExecuteAndResultSetHoldTime;
		}

		public void setEffectedRowCountHistogram(List<Integer> EffectedRowCountHistogram) {
			this.EffectedRowCountHistogram = EffectedRowCountHistogram;
		}

		public List<Integer> getEffectedRowCountHistogram() {
			return EffectedRowCountHistogram;
		}

		public void setLastErrorMessage(String LastErrorMessage) {
			this.LastErrorMessage = LastErrorMessage;
		}

		public String getLastErrorMessage() {
			return LastErrorMessage;
		}

		public void setHistogram(List<Integer> Histogram) {
			this.Histogram = Histogram;
		}

		public List<Integer> getHistogram() {
			return Histogram;
		}

		public void setInputStreamOpenCount(int InputStreamOpenCount) {
			this.InputStreamOpenCount = InputStreamOpenCount;
		}

		public int getInputStreamOpenCount() {
			return InputStreamOpenCount;
		}

		public void setBatchSizeTotal(int BatchSizeTotal) {
			this.BatchSizeTotal = BatchSizeTotal;
		}

		public int getBatchSizeTotal() {
			return BatchSizeTotal;
		}

		public void setFetchRowCountMax(int FetchRowCountMax) {
			this.FetchRowCountMax = FetchRowCountMax;
		}

		public int getFetchRowCountMax() {
			return FetchRowCountMax;
		}

		public void setErrorCount(int ErrorCount) {
			this.ErrorCount = ErrorCount;
		}

		public int getErrorCount() {
			return ErrorCount;
		}

		public void setBatchSizeMax(int BatchSizeMax) {
			this.BatchSizeMax = BatchSizeMax;
		}

		public int getBatchSizeMax() {
			return BatchSizeMax;
		}

		public void setURL(String URL) {
			this.URL = URL;
		}

		public String getURL() {
			return URL;
		}

		public void setName(String Name) {
			this.Name = Name;
		}

		public String getName() {
			return Name;
		}

		public void setLastErrorTime(String LastErrorTime) {
			this.LastErrorTime = LastErrorTime;
		}

		public String getLastErrorTime() {
			return LastErrorTime;
		}

		public void setReaderOpenCount(int ReaderOpenCount) {
			this.ReaderOpenCount = ReaderOpenCount;
		}

		public int getReaderOpenCount() {
			return ReaderOpenCount;
		}

		public void setEffectedRowCountMax(int EffectedRowCountMax) {
			this.EffectedRowCountMax = EffectedRowCountMax;
		}

		public int getEffectedRowCountMax() {
			return EffectedRowCountMax;
		}

		public void setLastErrorClass(String LastErrorClass) {
			this.LastErrorClass = LastErrorClass;
		}

		public String getLastErrorClass() {
			return LastErrorClass;
		}

		public void setInTransactionCount(int InTransactionCount) {
			this.InTransactionCount = InTransactionCount;
		}

		public int getInTransactionCount() {
			return InTransactionCount;
		}

		public void setLastErrorStackTrace(String LastErrorStackTrace) {
			this.LastErrorStackTrace = LastErrorStackTrace;
		}

		public String getLastErrorStackTrace() {
			return LastErrorStackTrace;
		}

		public void setResultSetHoldTime(int ResultSetHoldTime) {
			this.ResultSetHoldTime = ResultSetHoldTime;
		}

		public int getResultSetHoldTime() {
			return ResultSetHoldTime;
		}

		public void setTotalTime(int TotalTime) {
			this.TotalTime = TotalTime;
		}

		public int getTotalTime() {
			return TotalTime;
		}

		public void setID(int ID) {
			this.ID = ID;
		}

		public int getID() {
			return ID;
		}

		public void setConcurrentMax(int ConcurrentMax) {
			this.ConcurrentMax = ConcurrentMax;
		}

		public int getConcurrentMax() {
			return ConcurrentMax;
		}

		public void setRunningCount(int RunningCount) {
			this.RunningCount = RunningCount;
		}

		public int getRunningCount() {
			return RunningCount;
		}

		public void setFetchRowCount(int FetchRowCount) {
			this.FetchRowCount = FetchRowCount;
		}

		public int getFetchRowCount() {
			return FetchRowCount;
		}

		public void setMaxTimespanOccurTime(String MaxTimespanOccurTime) {
			this.MaxTimespanOccurTime = MaxTimespanOccurTime;
		}

		public String getMaxTimespanOccurTime() {
			return MaxTimespanOccurTime;
		}

		public void setLastSlowParameters(String LastSlowParameters) {
			this.LastSlowParameters = LastSlowParameters;
		}

		public String getLastSlowParameters() {
			return LastSlowParameters;
		}

		public void setReadBytesLength(int ReadBytesLength) {
			this.ReadBytesLength = ReadBytesLength;
		}

		public int getReadBytesLength() {
			return ReadBytesLength;
		}

		public void setDbType(String DbType) {
			this.DbType = DbType;
		}

		public String getDbType() {
			return DbType;
		}

		public void setDataSource(String DataSource) {
			this.DataSource = DataSource;
		}

		public String getDataSource() {
			return DataSource;
		}

		public void setSQL(String SQL) {
			this.SQL = SQL;
		}

		public String getSQL() {
			return SQL;
		}

		public void setHASH(int HASH) {
			this.HASH = HASH;
		}

		public long getHASH() {
			return HASH;
		}

		public void setLastError(String LastError) {
			this.LastError = LastError;
		}

		public String getLastError() {
			return LastError;
		}

		public void setMaxTimespan(int MaxTimespan) {
			this.MaxTimespan = MaxTimespan;
		}

		public int getMaxTimespan() {
			return MaxTimespan;
		}

		public void setBlobOpenCount(int BlobOpenCount) {
			this.BlobOpenCount = BlobOpenCount;
		}

		public int getBlobOpenCount() {
			return BlobOpenCount;
		}

		public void setExecuteCount(int ExecuteCount) {
			this.ExecuteCount = ExecuteCount;
		}

		public int getExecuteCount() {
			return ExecuteCount;
		}

		public void setEffectedRowCount(int EffectedRowCount) {
			this.EffectedRowCount = EffectedRowCount;
		}

		public int getEffectedRowCount() {
			return EffectedRowCount;
		}

		public void setReadStringLength(int ReadStringLength) {
			this.ReadStringLength = ReadStringLength;
		}

		public int getReadStringLength() {
			return ReadStringLength;
		}

		public void setExecuteAndResultHoldTimeHistogram(List<Integer> ExecuteAndResultHoldTimeHistogram) {
			this.ExecuteAndResultHoldTimeHistogram = ExecuteAndResultHoldTimeHistogram;
		}

		public List<Integer> getExecuteAndResultHoldTimeHistogram() {
			return ExecuteAndResultHoldTimeHistogram;
		}

		public void setFile(String File) {
			this.File = File;
		}

		public String getFile() {
			return File;
		}

		public void setClobOpenCount(int ClobOpenCount) {
			this.ClobOpenCount = ClobOpenCount;
		}

		public int getClobOpenCount() {
			return ClobOpenCount;
		}

		public void setLastTime(String LastTime) {
			this.LastTime = LastTime;
		}

		public String getLastTime() {
			return LastTime;
		}

		public void setFetchRowCountHistogram(List<Integer> FetchRowCountHistogram) {
			this.FetchRowCountHistogram = FetchRowCountHistogram;
		}

		public List<Integer> getFetchRowCountHistogram() {
			return FetchRowCountHistogram;
		}

		@Override
		public String toString() {
			return "Content [ExecuteAndResultSetHoldTime=" + ExecuteAndResultSetHoldTime
					+ ", EffectedRowCountHistogram=" + EffectedRowCountHistogram + ", LastErrorMessage="
					+ LastErrorMessage + ", Histogram=" + Histogram + ", InputStreamOpenCount=" + InputStreamOpenCount
					+ ", BatchSizeTotal=" + BatchSizeTotal + ", FetchRowCountMax=" + FetchRowCountMax + ", ErrorCount="
					+ ErrorCount + ", BatchSizeMax=" + BatchSizeMax + ", URL=" + URL + ", Name=" + Name
					+ ", LastErrorTime=" + LastErrorTime + ", ReaderOpenCount=" + ReaderOpenCount
					+ ", EffectedRowCountMax=" + EffectedRowCountMax + ", LastErrorClass=" + LastErrorClass
					+ ", InTransactionCount=" + InTransactionCount + ", LastErrorStackTrace=" + LastErrorStackTrace
					+ ", ResultSetHoldTime=" + ResultSetHoldTime + ", TotalTime=" + TotalTime + ", ID=" + ID
					+ ", ConcurrentMax=" + ConcurrentMax + ", RunningCount=" + RunningCount + ", FetchRowCount="
					+ FetchRowCount + ", MaxTimespanOccurTime=" + MaxTimespanOccurTime + ", LastSlowParameters="
					+ LastSlowParameters + ", ReadBytesLength=" + ReadBytesLength + ", DbType=" + DbType
					+ ", DataSource=" + DataSource + ", SQL=" + SQL + ", HASH=" + HASH + ", LastError=" + LastError
					+ ", MaxTimespan=" + MaxTimespan + ", BlobOpenCount=" + BlobOpenCount + ", ExecuteCount="
					+ ExecuteCount + ", EffectedRowCount=" + EffectedRowCount + ", ReadStringLength=" + ReadStringLength
					+ ", ExecuteAndResultHoldTimeHistogram=" + ExecuteAndResultHoldTimeHistogram + ", File=" + File
					+ ", ClobOpenCount=" + ClobOpenCount + ", LastTime=" + LastTime + ", FetchRowCountHistogram="
					+ FetchRowCountHistogram + "]";
		}
	}

}