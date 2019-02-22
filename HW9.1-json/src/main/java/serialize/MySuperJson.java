package serialize;

import helpers.ReflectionHelper;

import javax.json.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
                        ;
//                        continue;
                    else if (fieldValue.getClass().isArray() || fieldValue instanceof Collection)
                        jsonStructure.add(field.getName(), Json.createArrayBuilder(new ArrayList<>()).build());
                    else if (fieldValue instanceof Number)
                        jsonStructure.add(field.getName(), Integer.valueOf(fieldValue.toString()));
                    else
                        jsonStructure.add(field.getName(), fieldValue.toString());
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
}
