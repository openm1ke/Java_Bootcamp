package classes;

public class Car {
    private String model;
    private String color;
    private int year;

    public Car() {
        this.model = "Default model";
        this.color = "Default color";
        this.year = 0;
    }
    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }

    public void changeColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                '}';
    }
}
