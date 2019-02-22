package serialize;

import helpers.ReflectionHelper;

import javax.json.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.*;

/**
 * @author Igor on 21.02.19.
 */
public class MySuperJson {

    public String toJson(Object obj) {
        return create(obj).toString();
    }

    private static JsonStructure create(Object obj) {
        JsonObjectBuilder jsonStructure = Json.createObjectBuilder();
        Arrays.stream(ReflectionHelper.getFields(obj))
                .forEach(field -> {
                    Object fieldValue = ReflectionHelper.getFieldValue(obj, field);
                    if (fieldValue == null)
                        jsonStructure.addNull(null);
                    else if (fieldValue.getClass().isArray())
                        jsonStructure.add(field.getName(), Json.createArrayBuilder((JsonArray) fieldValue).build());
                    else if (fieldValue instanceof Collection)
                        jsonStructure.add(field.getName(), Json.createArrayBuilder((List) fieldValue).build());
                    else if (fieldValue instanceof Number || fieldValue instanceof String)
                        jsonStructure.add(field.getName(), fieldValue.toString());
                    else
                        jsonStructure.add(field.getName(), create(fieldValue));
                });

        return jsonStructure.build();
//        return Json.createObjectBuilder()
//                .add("firstName", "Duke")
//                .add("age", 28)
//                .add("streetAddress", "100 Internet Dr")
//                .add("phoneNumbers", Json.createArrayBuilder()
//                        .add(Json.createObjectBuilder()
//                                .add("type", "home")
//                                .add("number", "222-222-2222")))
//                .build();
    }

    private static JsonStructure ObjectBuilder(Object obj) {
        return Json.createObjectBuilder()
                .add(Object.class.getSimpleName(), obj.toString()).build();
    }

//    private static JsonStructure ArrayBuilder(Object[] obj) {
//
//        return Json.createArrayBuilder()
//                .add(Object.class.getSimpleName(), obj).build();
//    }
}
