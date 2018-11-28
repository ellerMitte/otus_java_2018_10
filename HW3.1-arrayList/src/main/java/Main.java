
import java.util.Collections;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();
        MyArrayList<Integer> myArray1 = new MyArrayList<>();
        MyArrayList<Integer> myArray2 = new MyArrayList<>(50);

        Integer[] elements1 = new Integer[100];
        Integer[] elements2 = new Integer[100];

        for (int i=0; i<100; i++) {
            elements1[i] = random.nextInt(100);
            elements2[i] = random.nextInt(100);
        }

        Collections.addAll(myArray1, elements1);
        Collections.addAll(myArray2, elements2);
        System.out.println(myArray1);
        System.out.println(myArray2);

        Collections.copy(myArray1, myArray2);
        System.out.println(myArray1);
        System.out.println(myArray2);

        Collections.sort(myArray1);
        Collections.sort(myArray2, (o1, o2) -> (o1 < o2) ? 1 : ((o1 == o2) ? 0 : -1));
        System.out.println(myArray1);
        System.out.println(myArray2);


    }
}
