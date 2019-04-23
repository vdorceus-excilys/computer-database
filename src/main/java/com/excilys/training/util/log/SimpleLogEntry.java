package com.excilys.training.util.log;

public class SimpleLogEntry implements Comparable<SimpleLogEntry> {
	static private Long currentId =0L;
	
	private Long id;
	private Exception exception;
	private String comments;
	
	public SimpleLogEntry(Exception exception, String comments) {
		this.exception = exception;
		this.comments =  comments;
		this.id = currentId++;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Override
	public int compareTo(SimpleLogEntry o) {
		return this.getId().compareTo(o.getId());
	}

}
