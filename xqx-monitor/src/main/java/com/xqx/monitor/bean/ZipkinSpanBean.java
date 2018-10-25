package com.xqx.monitor.bean;

import java.util.List;
import java.util.Map;

import zipkin2.Annotation;
import zipkin2.Endpoint;
import zipkin2.Span.Kind;

public class ZipkinSpanBean {

	// Custom impl to reduce GC churn and Kryo which cannot handle AutoValue
	// subclass
	// See https://github.com/openzipkin/zipkin/issues/1879
	private String traceId, parentId, id;
	private Kind kind;
	private String name;
	private long timestamp, duration; // zero means null, saving 2 object references
	private Endpoint localEndpoint, remoteEndpoint;
	private List<Annotation> annotations;
	private Map<String, String> tags;
	private int flags; // bit field for timestamp and duration, saving 2 object references

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Endpoint getLocalEndpoint() {
		return localEndpoint;
	}

	public void setLocalEndpoint(Endpoint localEndpoint) {
		this.localEndpoint = localEndpoint;
	}

	public Endpoint getRemoteEndpoint() {
		return remoteEndpoint;
	}

	public void setRemoteEndpoint(Endpoint remoteEndpoint) {
		this.remoteEndpoint = remoteEndpoint;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	@Override
	public String toString() {
		return "ZipkinSpanBean [traceId=" + traceId + ", parentId=" + parentId + ", id=" + id + ", kind=" + kind
				+ ", name=" + name + ", timestamp=" + timestamp + ", duration=" + duration + ", localEndpoint="
				+ localEndpoint + ", remoteEndpoint=" + remoteEndpoint + ", annotations=" + annotations + ", tags="
				+ tags + ", flags=" + flags + "]";
	}

}
