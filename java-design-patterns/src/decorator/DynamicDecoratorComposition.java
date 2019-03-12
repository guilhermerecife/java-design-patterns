package decorator;

public class DynamicDecoratorComposition {

}

interface Shape {
	/**
	 * Informações adicionais do Shape.
	 * @return
	 */
	String info();
}

class Circle implements Shape {
	private float radius;

	Circle() {
	}

	public Circle(float radius) {
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

class Square implements Shape {
	private float side;

	public Square() {
	}

	public Square(float side) {
		this.side = side;
	}

	@Override
	public String info() {
		return "A square with side " + side;
	}
}

/*
 * E se precisarmos de um circulo colorido, ou um quadrado colorido.
 * Não devemos modificar Square nem Circle, nem mesmo criar ColoredSquare nem ColoredCircle.
 */


//Devemos criar Decorators, observe que nós não estamos alterando a classe base desses objetos.
class ColoredShape implements Shape {//Para formas coloridas
	private Shape shape;
	private String color;

	public ColoredShape(Shape shape, String color) {
		this.shape = shape;
		this.color = color;
	}

	@Override
	public String info() {
		return shape.info() + " has the color " + color;
	}
}

class TransparentShape implements Shape {//Aqui um adicional para formas com transparencia
	private Shape shape;
	private int transparency;

	public TransparentShape(Shape shape, int transparency) {
		this.shape = shape;
		this.transparency = transparency;
	}

	@Override
	public String info() {
		return shape.info() + " has " + transparency + "% transparency";
	}
}

class DynamicDecoratorDemo {
	public static void main(String[] args) {
		Circle circle = new Circle(10);
		System.out.println(circle.info());//Círculo

		ColoredShape blueSquare = new ColoredShape(new Square(20), "blue");//Quadrado colorido
		System.out.println(blueSquare.info());

		TransparentShape myCircle = new TransparentShape(new ColoredShape(new Circle(5), "green"), 50);//Circulo colorido com transparência.
		System.out.println(myCircle.info());
	}
}
