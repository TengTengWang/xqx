package com.xqx.monitor.pojo;

import java.util.List;

public class ActuatorBean {

	private String status;
	private Details details;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public Details getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "ActuatorBean [status=" + status + ", details=" + details + "]";
	}

	public static class Details {
		private DiskSpace diskSpace;

		public DiskSpace getDiskSpace() {
			return diskSpace;
		}

		public void setDiskSpace(DiskSpace diskSpace) {
			this.diskSpace = diskSpace;
		}

		@Override
		public String toString() {
			return "Details [diskSpace=" + diskSpace + "]";
		}

	}

	public static class DiskSpace {

		private String status;
		private DetailSub details;

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return status;
		}

		public void setDetails(DetailSub details) {
			this.details = details;
		}

		public DetailSub getDetails() {
			return details;
		}

		@Override
		public String toString() {
			return "DiskSpace [status=" + status + ", details=" + details + "]";
		}

	}

	public static class DetailSub {

		private long total;
		private long free;
		private long threshold;

		public void setTotal(long total) {
			this.total = total;
		}

		public long getTotal() {
			return total;
		}

		public void setFree(long free) {
			this.free = free;
		}

		public long getFree() {
			return free;
		}

		public void setThreshold(long threshold) {
			this.threshold = threshold;
		}

		public long getThreshold() {
			return threshold;
		}

		@Override
		public String toString() {
			return "DetailSub [total=" + total + ", free=" + free + ", threshold=" + threshold + "]";
		}

	}

	public static class MyMetrics {
		private List<Measurements> measurements;

		public List<Measurements> getMeasurements() {
			return measurements;
		}

		public void setMeasurements(List<Measurements> measurements) {
			this.measurements = measurements;
		}

		@Override
		public String toString() {
			return "Metrics [measurements=" + measurements + "]";
		}

	}

	public static class Measurements {
		private String value;
		private String statistic;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getStatistic() {
			return statistic;
		}

		public void setStatistic(String statistic) {
			this.statistic = statistic;
		}

		@Override
		public String toString() {
			return "Measurements [value=" + value + ", statistic=" + statistic + "]";
		}

	}
}