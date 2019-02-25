import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Person;
import serialize.MySuperJson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 21.02.19.
 */
public class JsonDemo {
    public static void main(String[] args) {
        Person obj = new Person(22, "test");
        System.out.println(obj);
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        System.out.println(json);

        MySuperJson myJson = new MySuperJson();
        String json2 = myJson.toJson(obj);
        System.out.println(json2);


        Person obj2 = gson.fromJson(json, Person.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person(22, "test"));
        persons.add(new Person(45, "more test"));
        gson = new Gson();
        json = gson.toJson(persons);
        System.out.println(json);
        TypeToken<List<Person>> token = new TypeToken<List<Person>>() {};
        myJson = new MySuperJson();
        json2 = myJson.toJson(persons);
        System.out.println(json2);
    }


}
