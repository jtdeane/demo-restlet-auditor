package ws.cogito.auditing.service;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Services for conversion between JSON and POJOs
 * @author jeremydeane
 */
public final class JSONTransreptor {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Convert POJO to JSON
	 * @param object
	 * @return String
	 * @throws Exception
	 */
	public static String toJSON (Object object) throws Exception {
		return objectMapper.writeValueAsString(object);
	}
	
	/**
	 * Convert JSON to POJO
	 * @param json
	 * @param valueType
	 * @return Object
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object toPOJO (String json, Class valueType) throws Exception {
		return objectMapper.readValue(json, valueType);
	}

}