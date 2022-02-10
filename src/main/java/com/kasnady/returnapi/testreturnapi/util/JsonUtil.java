package com.kasnady.returnapi.testreturnapi.util;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonUtil {

	private JsonUtil() {
		throw new IllegalStateException("JsonUtil class");
	}

	public static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDateTime.class, new JsonUtil.LocalDateTimeSerializer()).create();

	public static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
		@Override
		public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}
}
