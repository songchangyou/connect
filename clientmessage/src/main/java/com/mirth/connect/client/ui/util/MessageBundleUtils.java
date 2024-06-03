package com.mirth.connect.client.ui.util;

import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

public class MessageBundleUtils {

	/**
	 * 获取消息
	 */
	public static String getMessage(String key) {
		return getMessage(key,DEFAULT_LOCAL);
	}
	
	/**
	 * 获取消息
	 */
	public static String getMessage(String key,Locale locale) {
		return getResourceBundle(locale).get(key);
	}
	
	/**
	 * 获取消息
	 */
	public static String getMessageFormat(String key, Object... args) {
		return getMessageFormatWithLocale(key,DEFAULT_LOCAL,args);
	}
	
	/**
	 * 获取消息
	 */
	public static String getMessageFormatWithLocale(String key,Locale locale, Object... args) {
		String message = getResourceBundle(locale).get(key);
		if(StringUtils.isNotBlank(message)) {
			MessageFormat formatter = new MessageFormat(message);
			formatter.setLocale(locale); 
			message = formatter.format(args);
		}
		return message;
	}
	
	/**
	 * 字体
	 */
	public static String getFontFamily(String key) {
		return getFontFamily(key,DEFAULT_LOCAL);
	}
	
	/**
	 * 字体
	 */
	public static String getFontFamily(String key,Locale locale) {
		String font="宋体";
		String defaultFontKey = "font";
		HashMap<String,String> map = getResourceBundle(locale);
		if(map.containsKey(key)) {
			font = map.get(key);
		}else if(map.containsKey(defaultFontKey)){
			font = map.get(defaultFontKey);
		}
		return font;
	}
	
	
	/**
	 * 设置默认语言
	 */
	public static void setDefaultLocale(Locale locale) {
		DEFAULT_LOCAL = locale;
	}
	
	@SuppressWarnings("unused")
	private static HashMap<String,String> getResourceBundle() {
		return getResourceBundle(Locale.getDefault());
	}
	
	private static HashMap<String,String> getResourceBundle(Locale locale) {
		String baseName = "Messages";
		return getResourceBundle(baseName,locale);
	}
	
	 private static final Pattern PROPERTY_PATTERN = Pattern.compile("\\$\\{([^}]+)}");
	
	private static HashMap<String,String> getResourceBundle(String baseName,Locale locale) {
		if(!BUNDLES.containsKey(locale)) {
			String yamlFilePath = getMessageFile(baseName,locale);
			URL url = MessageBundleUtils.class.getClassLoader().getResource(yamlFilePath);
			if(url == null) {
				yamlFilePath = getDefaultMessageFile(baseName);
				url = MessageBundleUtils.class.getClassLoader().getResource(yamlFilePath);
			}
			if(url == null) {
				return null;
			}
			Yaml yaml = new Yaml();
	        InputStream inputStream = MessageBundleUtils.class.getClassLoader().getResourceAsStream(yamlFilePath);
	        HashMap<String,Object> resources = yaml.load(inputStream);
	        HashMap<String, String> srcMap = flattenMap(resources,"");
	        //替换属性
	        HashMap<String, String> replacedMap = replaceProperties(srcMap,srcMap);
	        BUNDLES.put(locale, replacedMap);
		}
		if(BUNDLES.containsKey(locale)){
			return BUNDLES.get(locale);
		}
		return null;
	}
	
	 /**
	  *  递归方法将多层Map转换为一层，key使用点分隔
	  */
    @SuppressWarnings("unchecked")
	private static HashMap<String, String> flattenMap(Map<String, Object> map, String parentKey) {
    	HashMap<String, String> flatMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = parentKey.isEmpty() ? entry.getKey() : parentKey + "." + entry.getKey();
            if (entry.getValue() instanceof Map) {
                flatMap.putAll(flattenMap((Map<String, Object>) entry.getValue(), key));
            } else {
                flatMap.put(key, entry.getValue().toString());
            }
        }
        return flatMap;
    }
    
    /**
     * 实现类似 Spring Boot 中属性替换（例如使用 ${property.name} 的方式）
     */
    private static HashMap<String, String> replaceProperties(HashMap<String, String> map, HashMap<String, String> properties) {
    	HashMap<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                result.put(entry.getKey(), replaceProperty(entry.getValue(), properties));
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
    
    /**
     * 实现类似 Spring Boot 中属性替换（例如使用 ${property.name} 的方式）
     */
    private static String replaceProperty(String value, HashMap<String, String> properties) {
        Matcher matcher = PROPERTY_PATTERN.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String propertyName = matcher.group(1);
            String propertyValue = properties.get(propertyName);
            if (propertyValue != null) {
                matcher.appendReplacement(sb, propertyValue);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
	
	/**
	 * 配置文件名称
	 */
	private static String getMessageFile(String baseName,Locale locale) {
		return String.format("%s_%s_%s.yml",baseName,locale.getLanguage(),locale.getCountry());
	}
	
	/**
	 * 配置文件名称
	 */
	private static String getDefaultMessageFile(String baseName) {
		return String.format("%s.yml",baseName);
	}
	
	private static Map<Locale,HashMap<String,String>> BUNDLES = new HashMap<>();
	
	/**
	 * 默认语言
	 */
	private static Locale DEFAULT_LOCAL = Locale.getDefault();
	
}
