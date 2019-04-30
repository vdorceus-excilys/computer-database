package com.excilys.training.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigurationFile {
	
	private String file;
	
	public ConfigurationFile(String file) {
		this.file = file;
	}

	public String readAsString() throws URISyntaxException, IOException {
		Path path = Paths.get(getClass().getClassLoader().getResource(file).toURI());
		Stream<String> stream = Files.lines(path);
		String content = stream.collect(Collectors.joining("\n"));
		stream.close();
		return content.toString();			
	}
}
