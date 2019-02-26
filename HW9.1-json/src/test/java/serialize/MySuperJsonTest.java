package serialize;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 25.02.19.
 */
public class MySuperJsonTest {

    private Gson gson;
    private MySuperJson myJson;

    @Before
    public void BeforeTest() {
        gson = new Gson();
        myJson = new MySuperJson();
    }

    @Test
    public void toJsonObject() {
        Person person = new Person(22, "test");
        Assert.assertEquals("person", person, gson.fromJson(myJson.toJson(person), Person.class));
    }

    @Test
    public void toJsonArray() {
        Person[] personArray = {new Person(43, "person1"), new Person(25, "person2"), new Person(36, "person3")};
        Assert.assertArrayEquals("personArray", personArray, gson.fromJson(myJson.toJson(personArray), Person[].class));
    }

    @Test
    public void toJsonList() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(43, "person1"));
        personList.add(new Person(24, "person2"));
        TypeToken<List<Person>> token = new TypeToken<List<Person>>() {};
        Assert.assertEquals(personList, gson.fromJson(myJson.toJson(personList), token.getType()));
    }

    @Test
    public void customTest() {
        Assert.assertEquals(gson.toJson(null), myJson.toJson(null));
        Assert.assertEquals(gson.toJson(1), myJson.toJson(1));
        Assert.assertEquals(gson.toJson(1d), myJson.toJson(1d));
        Assert.assertEquals(gson.toJson(false), myJson.toJson(false));
        Assert.assertEquals(gson.toJson(new byte[] {1, 2, 3}), myJson.toJson(new byte[] {1, 2, 3}));
        Assert.assertEquals(gson.toJson(new char[] {1, 2, 3}), myJson.toJson(new char[] {1, 2, 3}));
    }
}