package prototype;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/**
 * Em {@link CopyConstructor} toda vez que entrar um objeto mais complexo o construtor precisa ser atualizado,
 * aqui resolvemos este problema utilizando uma lib para Serializar e deserializar objetos, assim criando uma nova cópia.
 * @author Guilherme
 *
 */
public class CopyThroughSerialization {

}

class Foo implements Serializable {
	public int stuff;
	public String whatever;

	public Foo(int stuff, String whatever) {
		this.stuff = stuff;
		this.whatever = whatever;
	}

	@Override
	public String toString() {
		return "Foo{" + "stuff=" + stuff + ", whatever='" + whatever + '\'' + '}';
	}
}

class CopyThroughSerializationDemo {
	public static void main(String[] args) {
		Foo foo = new Foo(42, "life");
		// Utilizamos aqui o apache commons para fazer um deep clone do objeto.
		//OBS: Poder ser usado outra lib se preferir, o que importa é que foi feito o deep clone
		//e mesmo que sejam adicionados mais campos não é preciso mecher em nada.
		Foo foo2 = SerializationUtils.roundtrip(foo);

		foo2.whatever = "xyz";

		System.out.println(foo);
		System.out.println(foo2);
	}
}
