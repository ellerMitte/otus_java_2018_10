package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 27.01.19.
 */
//implements Serializable - обязательное условие для сериализации
public class Phone implements Serializable {
    public final int id;
    public final String number;


    // Обратите внимание на то, сколько раз вызывается констурктор и сколько объектов создается
    public Phone(int id, String number) {
//        System.out.println("new Person");
        this.id = id;
        this.number = number;
    }

    @Override
    public String toString() {
        return "number: " + number + " id: " + id;
    }
}