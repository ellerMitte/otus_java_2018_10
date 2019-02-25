package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author sergey
 * created on 27.01.19.
 */
public class Person {
    private final int age;
    private final short s;
    private long l;
    private final String name;
    private final double d;
    private final float f;
    private final boolean bool;
    private final Phone phone = new Phone(12, "322-22-33");
    private int[] array = new int[5];
    private List<Phone> list = new ArrayList<>();
    private transient final String hidden = "hiddenField"; //transient - поле будет пропущено при сериализации

    // Обратите внимание на то, сколько раз вызывается констурктор и сколько объектов создается
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
        this.d = 45.1;
        this.bool = false;
        this.s = 12;
        this.l = 100L;
        this.f = 33.2f;
        this.array[0] = 23;
        this.array[1] = 52;
        this.array[2] = 252;
        list.add(phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                s == person.s &&
                l == person.l &&
                Double.compare(person.d, d) == 0 &&
                Float.compare(person.f, f) == 0 &&
                bool == person.bool &&
                Objects.equals(name, person.name) &&
                Objects.equals(phone, person.phone) &&
                Arrays.equals(array, person.array) &&
                Objects.equals(list, person.list) &&
                Objects.equals(hidden, person.hidden);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(age, s, l, name, d, f, bool, phone, list, hidden);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return "Name: " + name + " age: " + age + " hidden:" + hidden;
    }
}