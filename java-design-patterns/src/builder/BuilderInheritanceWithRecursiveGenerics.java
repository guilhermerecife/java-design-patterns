package builder;

/**
 * Herança de Builder com generics recursivas.
 * @author Guilherme
 *
 */
public class BuilderInheritanceWithRecursiveGenerics {
	
}

/**
 * Elemento.
 * @author Guilherme
 *
 */
class Person {
	public String name;

	public String position;

	@Override
	public String toString() {
		return "Person{" + "name='" + name + '\'' + ", position='" + position + '\'' + '}';
	}
}

/**
 * Builder de Pessoa.
 * @author Guilherme
 *
 * @param <SELF>
 */
class PersonBuilder<SELF extends PersonBuilder<SELF>> {
	protected Person person = new Person();

	public SELF withName(String name) {
		person.name = name;
		return self();
	}

	protected SELF self() {
		// unchecked cast, Mas seguro. Tente passar uma classe que não é um PersonBuilder, não vai funcionar.
		return (SELF) this;
	}

	public Person build() {
		return person;
	}
}

/**
 * Aqui temos um construtor para Funcionários, ele extende do de Pessoa, adicionando a possibilidade de setar position.
 * @author Guilherme
 *
 */
class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
	public EmployeeBuilder worksAs(String position) {
		person.position = position;
		return self();
	}

	@Override
	protected EmployeeBuilder self() {
		return this;
	}
}

class RecursiveGenericsDemo {
	public static void main(String[] args) {
		EmployeeBuilder eb = new EmployeeBuilder()
				.withName("Dmitri")
				.worksAs("Quantitative Analyst");
		System.out.println(eb.build());
	}
}