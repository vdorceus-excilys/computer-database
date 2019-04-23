package com.excilys.training.util.log;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class SimpleLog {
	
	private final static TreeSet<SimpleLogEntry> log;
	//auto initialized singleton
	private final static SimpleLog self;
	private PrintStream out=System.err;
	private Boolean debug=false;
	
	static {
		log = new TreeSet<SimpleLogEntry>();
		self = new SimpleLog();
	}

	private SimpleLog() {
	}
	
	static public SimpleLog getInstance() {
		return self;
	}
	
	public void log(SimpleLogEntry entry) {
		log.add(entry);
		if(out!=null) {
			out.println(entry.getComments());
			if(debug)
				out.println(entry.getException().getMessage());
		}
			
	}
	public TreeSet<SimpleLogEntry> logMap(){
		return log;
	}
	public List<SimpleLogEntry> logList(){
		List<SimpleLogEntry> l = new ArrayList<SimpleLogEntry>();
		for(SimpleLogEntry s : log) {
			l.add(s);
		}
		return l;
	}
	public void setOutputStream(PrintStream out) {
		this.out = out;
	}
	public void debug(Boolean debug) {
		this.debug= debug;
	}
}
