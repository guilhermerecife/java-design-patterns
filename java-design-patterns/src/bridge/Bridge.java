package bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Conectar componentes atrav�s de abstra��es
 * 
 * @author Guilherme
 *
 */
public class Bridge {

}

interface Renderer {
	void renderCircle(float radius);
}

class VectorRenderer implements Renderer {
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing a circle of radius " + radius);
	}
}

class RasterRenderer implements Renderer {
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing pixels for a circle of radius " + radius);
	}
}

/**
 * Classe abstrata que ser� utilizada para todos os tipos de shapes.
 * Observe que recebe a interface {@link Renderer} no construtor, para que a regra de renderiza��o seja flexibilizada.
 * @author Guilherme
 *
 */
abstract class Shape {
	protected Renderer renderer;

	public Shape(Renderer renderer) {
		this.renderer = renderer;
	}

	public abstract void draw();

	public abstract void resize(float factor);
}

/**
 * Extende {@link Shape} e no m�todo draw, delega para {@link Renderer}.
 * @author Guilherme
 *
 */
class Circle extends Shape {
	public float radius;

	@Inject
	public Circle(Renderer renderer) {
		super(renderer);
	}

	public Circle(Renderer renderer, float radius) {
		super(renderer);
		this.radius = radius;
	}

	@Override
	public void draw() {
		renderer.renderCircle(radius);
	}

	@Override
	public void resize(float factor) {
		radius *= factor;
	}
}

/**
 * Configura��o de inje��o de depend�ncias.
 * OBS: Caso deseje ter um {@link Renderer} padr�o, a inje��o de depend�ncias ir� resolver este problema.
 * @author Guilherme
 *
 */
class ShapeModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Renderer.class).to(VectorRenderer.class);
	}
}

class BridgeDemo {
	public static void main(String[] args) {
		//Observe que agora passamos o Renderer desejado no construtor, e o Circle passa a trabalhar tanto com VectorRenderer quanto com RasterRenderer.
		RasterRenderer rasterRenderer = new RasterRenderer();
		VectorRenderer vectorRenderer = new VectorRenderer();
		Circle circle = new Circle(vectorRenderer, 5);
		circle.draw();
		circle.resize(2);
		circle.draw();

		//Com o injetor de depend�ncias.
		Injector injector = Guice.createInjector(new ShapeModule());
		Circle instance = injector.getInstance(Circle.class);
		instance.radius = 3;
		instance.draw();
		instance.resize(2);
		instance.draw();
	}
}
