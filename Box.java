package HW_1;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    ArrayList<T> box = new ArrayList(); // fruit array

    public Box(T fruit) {
        // Constructor simply initializes T to the fruit type. Although the fruit object is created via new, it is not added to the list.
    }

    public float getWeight() {
        float totalWeight = 0.0f;
        for (int i = 0; i < box.size(); i++) {
            totalWeight = totalWeight + box.get(i).getWeight();
        }
        return totalWeight;

    }

    public int getCountFruits() {
        return box.size();
    }

    public boolean compare(Box<? extends Fruit> compareBox) {
        return Math.abs(getWeight() - compareBox.getWeight()) < 0.0001f;
    }

    public void addToBox(T fruit) {
        box.add(fruit);
    }

    void clearBox() {
        box.clear();
    }

    public void moveFruitsToAnotherBox(Box<T> anotherBox) {
        anotherBox.box.addAll(this.box);
        this.clearBox();
    }
}