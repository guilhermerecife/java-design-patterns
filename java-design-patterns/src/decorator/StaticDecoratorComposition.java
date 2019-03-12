package decorator;

import java.util.function.Supplier;

public class StaticDecoratorComposition {

}

interface Shape2 {
	String info();
}

class Circle2 implements Shape2 {
	private float radius;

	Circle2() {
	}

	public Circle2(float radius) {
		this.radius = radius;
	}

	void resize(float factor) {
		radius *= factor;
	}

	@Override
	public String info() {
		return "A circle of radius " + radius;
	}
}

class Square2 implements Shape2 {
	private float side;

	public Square2() {
	}

	public Square2(float side) {
		this.side = side;
	}

	@Override
	public String info() {
		return "A square with side " + side;
	}
}

/**
 * Mesma lógica de {@link ColoredShape}.
 * @author Guilherme
 *
 * @param <T>
 */
class ColoredShape2<T extends Shape2> implements Shape2 {
	private Shape2 shape;
	private String color;

	public ColoredShape2(Supplier<? extends T> ctor, String color) {
		shape = ctor.get();
		this.color = color;
	}

	@Override
	public String info() {
		return shape + " has the color " + color;
	}
}

class TransparentShape2<T extends Shape2> implements Shape2 {
	private Shape2 shape;
	private int transparency;

	public TransparentShape2(Supplier<? extends T> ctor, int transparency) {
		shape = ctor.get();
		this.transparency = transparency;
	}

	@Override
	public String info() {
		return shape + " has " + transparency + "% transparency";
	}
}

class StaticDecoratorDemo {
	public static void main(String[] args) {
		Circle2 circle = new Circle2(10);
		System.out.println(circle.info());

		ColoredShape2<Square2> blueSquare = new ColoredShape2<>(() -> new Square2(20), "blue");
		System.out.println(blueSquare.info());

		// ugly!
		TransparentShape2<ColoredShape2<Circle2>> myCircle = new TransparentShape2<>(
				() -> new ColoredShape2<>(() -> new Circle2(5), "green"), 50);
		System.out.println(myCircle.info());
	}
}