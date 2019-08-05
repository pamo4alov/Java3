package HW_1;

/*
Task 3:
Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта,
поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
Для хранения фруктов внутри коробки можно использовать ArrayList;
Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта
(вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах);
Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
которую подадут в compare в качестве параметра, true – если она равны по весу, false – в противном случае
(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую
(помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами).
Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
Не забываем про метод добавления фрукта в коробку.
 */

public class Fruits {
    public static void main(String[] args) {

        Box<Orange> boxWithOranges = new Box<>(new Orange());
        Box<Apple> boxWithApples = new Box<>(new Apple());
        Box<Apple> anotherBoxWithApples = new Box<>(new Apple());

        boxWithOranges.addToBox(new Orange());
        boxWithOranges.addToBox(new Orange());
        boxWithApples.addToBox(new Apple());
        boxWithApples.addToBox(new Apple());
        boxWithApples.addToBox(new Apple());

        System.out.println("In the box with oranges " + boxWithOranges.getCountFruits() + " an oranges, weighing "
                + boxWithOranges.getWeight());
        System.out.println("In the box with apples  " + boxWithApples.getCountFruits() + " an apples, weighing "
                + boxWithApples.getWeight());
        System.out.println("In another box with apples " + anotherBoxWithApples.getCountFruits() + " an apples, weighing "
                + anotherBoxWithApples.getWeight());

        System.out.println("----------------------------------------------");
        // Compare the weight of the boxes
        if (boxWithApples.compare(boxWithOranges)) {
            System.out.println("Weight of the boxes is the same");
        } else System.out.println("Weight of the boxes is different");
        System.out.println("----------------------------------------------");

        // move apples to another box
        boxWithApples.moveFruitsToAnotherBox(anotherBoxWithApples);

        System.out.println("In the box with apples " + boxWithApples.getCountFruits() + " an apples, weighing "
                + boxWithApples.getWeight());
        System.out.println("In another box with apples " + anotherBoxWithApples.getCountFruits() + " an apples, weighing "
                + anotherBoxWithApples.getWeight());
    }
}

