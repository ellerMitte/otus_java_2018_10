package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author sergey
 * created on 27.01.19.
 */
public class Phone {
    private final int id;
    private final String number;


    // Обратите внимание на то, сколько раз вызывается констурктор и сколько объектов создается
    public Phone(int id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id == phone.id &&
                Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "number: " + number + " id: " + id;
    }
}