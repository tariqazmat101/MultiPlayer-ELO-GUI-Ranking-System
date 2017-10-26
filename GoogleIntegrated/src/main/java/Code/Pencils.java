package Code;

/**
 * Created by tariq on 10/26/2017.
 */
public class Pencils {
    String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    int weight;
    String model;

    public Pencils(String color, int weight, String model) {
        this.color = color;
        this.weight = weight;
        this.model = model;
    }
}
