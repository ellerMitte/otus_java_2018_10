package serialize;

import helpers.ReflectionHelper;

import javax.json.*;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Igor on 21.02.19.
 */
public class MySuperJson {

    public String toJson(Object obj) {
        return getJsonValueFromObject(obj).toString();
    }

    private static JsonStructure createObjectBuilder(Object obj) {
        JsonObjectBuilder jsonStructure = Json.createObjectBuilder();
        Arrays.stream(ReflectionHelper.getFields(obj))
                .forEach(field -> {
                    Object fieldValue = ReflectionHelper.getFieldValue(obj, field);
                    if (Modifier.isTransient(field.getModifiers()))
                        return;
                    jsonStructure.add(field.getName(), getJsonValueFromObject(fieldValue));
                });

        return jsonStructure.build();
    }

    private static JsonStructure createArrayBuilder(List<Object> objArray) {
        JsonArrayBuilder jsonStructure = Json.createArrayBuilder();
        objArray.forEach(obj -> jsonStructure.add(getJsonValueFromObject(obj)));
        return jsonStructure.build();
    }

    private static JsonValue getJsonValueFromObject(Object obj) {
        if (obj == null)
            return JsonValue.NULL;
        else if (obj.getClass().isArray())
            return createArrayBuilder(ReflectionHelper.getListFromArray(obj));
        else if (obj instanceof Collection)
            return createArrayBuilder((List) obj);
        else if (obj instanceof Integer || obj instanceof Short || obj instanceof Byte)
            return Json.createValue(Integer.valueOf(obj.toString()));
        else if (obj instanceof Long)
            return Json.createValue((long) obj);
        else if (obj instanceof Double || obj instanceof Float)
            return Json.createValue(Double.valueOf(obj.toString()));
        else if (obj instanceof Boolean)
            if (obj.equals(true))
                return JsonValue.TRUE;
            else
                return JsonValue.FALSE;
        else if (obj instanceof String || obj instanceof Character)
            return Json.createValue(obj.toString());
        else
            return createObjectBuilder(obj);
    }
}
