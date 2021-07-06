package HW_1;

/*
Задание:
1. Написать метод, который меняет два элемента массива местами. (массив может быть любого ссылочного типа);
2. Написать метод, который преобразует массив в ArrayList;
3. Есть классы Fruit -> Apple, Orange; (больше фруктов не надо)
Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
Для хранения фруктов внутри коробки можете использовать ArrayList;
Сделать метод getWeight() который высчитывает вес коробки, зная кол-во фруктов и вес одного фрукта
(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
которую подадут в compare в качестве параметра, true - если их веса равны, false в противной случае(коробки с яблоками
мы можем сравнивать с коробками с апельсинами);
Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку (помним про сортировку фруктов,
нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую
перекидываются объекты, которые были в этой коробке;Ну и не забываем про метод добавления фрукта в коробку;
*/

import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {
    public static void main(String[] args) {

        // Task 1
        System.out.println("---------- Task 1 ----------");
        String[] strArray = {"a", "b", "c", "d", "e"};
        Integer[] intArray = {1, 2, 3, 4, 5};
        Double[] dblArray = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        swapElements(strArray, 0,3);
        System.out.println("Swapped array elements strArray: " + Arrays.toString(strArray));
        swapElements(intArray, 1,2);
        System.out.println("Swapped array elements intArray: " + Arrays.toString(intArray));
        swapElements(dblArray, 3,5);
        System.out.println("Swapped array elements dblArray: " + Arrays.toString(dblArray));

        // Task 2
        System.out.println("---------- Task 2 ----------");
        System.out.println("Get strArray as ArrayList: " + asList(strArray).toString());
        System.out.println("Get intArray as ArrayList: " + asList(intArray).toString());
        System.out.println("Get dblArray as ArrayList: " + asList(dblArray).toString());
    }

    // Method returns an ArrayList from the passed array
    public static <E> ArrayList<E> asList(E[] o) {
        ArrayList<E> res = new ArrayList<>();
        if (o.length > 0) {
            for (E t : o) {
                res.add(t);
            }
        }
        return res;
    }

    // Method changes the elements with the specified positions in the passed array
    public static <E> void swapElements(E[] o, int pos1, int pos2) {
        if (pos1 >= 0 && pos2 >= 0 && pos1 < o.length && pos2 < o.length) {
            E tempEl = o[pos1];
            o[pos1] = o[pos2];
            o[pos2] = tempEl;
        } else
            throw new RuntimeException("Invalid array boundaries");
    }
}
