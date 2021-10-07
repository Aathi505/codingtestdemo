package com.spacexdata.api.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.testng.Reporter;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;


public class APIUtil {

	public static String readJsonPathValue(String content, String filter) {
		Object result = JsonPath.parse(content, Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS))
				.read(filter);
		if(Objects.nonNull(result)){
			return result.toString().replaceAll("\\[\"(.*?)\"\\]", "$1");
		}
		return null;
	}
	
	public static String readJsonPath(String content, String filter) {
			
			return JsonPath.read(content, filter).toString();

	}
	
	public static List<String> streamJSONArrayAsList(String jsonArrayString) {
		JSONArray array = new JSONArray(jsonArrayString);
		return StreamSupport.stream(array.spliterator(), false).map(String.class::cast).collect(Collectors.toList());
	}
}
