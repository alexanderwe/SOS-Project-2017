package dependencies;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Context {
	
	public static final String TYPE_STRING = "String";
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_INT = "int";
	public static final String TYPE_LONG = "long";
	public static final String TYPE_DOUBLE = "double";
	public static final String TYPE_LIST = "List";
	public static final String TYPE_UNKNOWN = "unknown";
	
	public static final String UNIT_NONE = "NONE";
	
	public static final String KEY_ATTR_NAME = "ATTRIBUTE_NAME";
	public static final String KEY_VALUE = "VALUE";
	public static final String KEY_UNIT = "UNIT";
	public static final String KEY_TYPE = "TYPE";
	
	/**
	 * All Information contained in the Context
	 * The Key of the outer HashMap corresponds to the managed resource ID, the value is a List of 4-tuples for each attribute
	 * Each 4-tuple contains information about the attribute name, its value, the unit and the type
	 */
	private HashMap<String, List<HashMap<String, String>>> allObjects;

	/**
	 * New Context model: Initializes the object structure
	 */
	public Context()
	{
		allObjects = new HashMap<String, List<HashMap<String, String>>>();
	}
	
	/**
	 * Adds an attribute to the Context or changes it
	 * @param objectId ID of the managed resource
	 * @param attrName Name of the attribute
	 * @param value Value the attribute should have
	 * @param unit Unit in which the attribute is measured to interpret numerical values - Use Constants of the Context class! (optional)
	 * @param type Type the value can be converted to - Use Constants of the Context class!
	 * @throws ContextException Invalid Parameters
	 */
	public void putEntry(String objectId, String attrName, String value, String unit, String type) throws ContextException
	{
		if(attrName == null || objectId == null || type == null)
			throw new ContextException("Attribute name, Object ID and Type may not be null!");
		
		if(unit == null)
			unit = UNIT_NONE;
		
		if(!allObjects.containsKey(objectId))
			allObjects.put(objectId, new LinkedList<HashMap<String, String>>());
		
		//Entry: One Robot or one Room
		List<HashMap<String, String>> entry = allObjects.get(objectId);
		
		HashMap<String, String> newAttribute = getAttributeByName(entry, attrName);
		
		if(newAttribute == null)
		{
			newAttribute = new HashMap<String, String>();
			entry.add(newAttribute);
		}
		
		newAttribute.put(KEY_ATTR_NAME, attrName);
		newAttribute.put(KEY_VALUE, value);
		newAttribute.put(KEY_UNIT, unit);
		newAttribute.put(KEY_TYPE, type);
	}
	
	/**
	 * Receives the corresponding 4-tuple for one attribute, specified by its name
	 * @param list List containing all 4-tuples
	 * @param attrName Name of the attribute
	 * @return HashMap structure of the 4-tuple
	 * @throws ContextException Context structure is invalid (should not happen)
	 */
	public HashMap<String, String> getAttributeByName(List<HashMap<String, String>> list, String attrName) throws ContextException
	{
		for (int i = 0; i < list.size(); i++)
		{
			HashMap<String, String> attribute = list.get(i);
			if(!attribute.containsKey(KEY_ATTR_NAME))
				throw new ContextException("Invalid Attribute is contained in the List!");
			if(attribute.get(KEY_ATTR_NAME).equals(attrName))
				return attribute;
		}
		
		return null;
	}
	
	public HashMap<String, List<HashMap<String, String>>> getAllObjects()
	{
		return allObjects;
	}
}
