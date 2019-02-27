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
        if (obj == null) {
            return "null";
        } else if (obj instanceof String) {
            return Json.createValue((String) obj).toString();
        } else if (obj instanceof Character) {
            return Json.createValue(String.valueOf(obj)).toString();
        } else if (obj instanceof Boolean || obj instanceof Number) {
            return String.valueOf(obj);
        }

        return create(obj).toString();
    }

    private static JsonStructure create(Object obj) {
        if (obj.getClass().isArray())
            return createArrayBuilder(ReflectionHelper.getListFromArray(obj));
        else if (obj instanceof Collection)
            return createArrayBuilder((List) obj);
        else
            return createObjectBuilder(obj);
    }

    private static JsonStructure createObjectBuilder(Object obj) {
        JsonObjectBuilder jsonStructure = Json.createObjectBuilder();
        Arrays.stream(ReflectionHelper.getFields(obj))
                .forEach(field -> {
                    Object fieldValue = ReflectionHelper.getFieldValue(obj, field);
                    if (Modifier.isTransient(field.getModifiers()))
                        return;

                    if (fieldValue == null)
                        jsonStructure.addNull(null);
                    else if (fieldValue.getClass().isArray())
                        jsonStructure.add(field.getName(), createArrayBuilder(ReflectionHelper.getListFromArray(fieldValue)));
                    else if (fieldValue instanceof Collection)
                        jsonStructure.add(field.getName(), createArrayBuilder((List) fieldValue));
                    else if (fieldValue instanceof Integer || fieldValue instanceof Short || obj instanceof Byte)
                        jsonStructure.add(field.getName(), Integer.valueOf(fieldValue.toString()));
                    else if (fieldValue instanceof Long)
                        jsonStructure.add(field.getName(), (long) fieldValue);
                    else if (fieldValue instanceof Double || fieldValue instanceof Float)
                        jsonStructure.add(field.getName(), Double.valueOf(fieldValue.toString()));
                    else if (fieldValue instanceof Boolean)
                        jsonStructure.add(field.getName(), (boolean) fieldValue);
                    else if (fieldValue instanceof String || obj instanceof Character)
                        jsonStructure.add(field.getName(), fieldValue.toString());
                    else
                        jsonStructure.add(field.getName(), createObjectBuilder(fieldValue));
                });

        return jsonStructure.build();
    }

    private static JsonStructure createArrayBuilder(List<Object> objArray) {
        JsonArrayBuilder jsonStructure = Json.createArrayBuilder();
        objArray.forEach(obj -> {
            if (obj == null)
                jsonStructure.addNull();
            else if (obj.getClass().isArray())
                jsonStructure.add(createArrayBuilder(ReflectionHelper.getListFromArray(obj)));
            else if (obj instanceof Collection)
                jsonStructure.add(createArrayBuilder((List) obj));
            else if (obj instanceof Integer || obj instanceof Short || obj instanceof Byte)
                jsonStructure.add(Integer.valueOf(obj.toString()));
            else if (obj instanceof Long)
                jsonStructure.add((long) obj);
            else if (obj instanceof Double || obj instanceof Float)
                jsonStructure.add(Double.valueOf(obj.toString()));
            else if (obj instanceof Boolean)
                jsonStructure.add((boolean) obj);
            else if (obj instanceof String || obj instanceof Character)
                jsonStructure.add(obj.toString());
            else
                jsonStructure.add(createObjectBuilder(obj));
        });
        return jsonStructure.build();
    }
}
