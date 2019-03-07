package builder;

/**
 * Em alguns casos seu objeto e muito complexo e e precisa de mais de um Builder.
 * @author Guilherme
 *
 */
public class FacetedBuilder {

}

class Person2 {
	// address
	public String streetAddress, postcode, city;

	// employment
	public String companyName, position;
	public int annualIncome;

	@Override
	public String toString() {
		return "Person{" + "streetAddress='" + streetAddress + '\'' + ", postcode='" + postcode + '\'' + ", city='"
				+ city + '\'' + ", companyName='" + companyName + '\'' + ", position='" + position + '\''
				+ ", annualIncome=" + annualIncome + '}';
	}
}

// builder facade
class Person2Builder {
	// O objeto que nós vamos construir.
	protected Person2 person = new Person2(); // reference!

	/**
	 * Retorna {@link PersonJobBuilder} para termos acesso aos seus métodos.
	 * @return
	 */
	public PersonJobBuilder works() {
		return new PersonJobBuilder(person);
	}

	/**
	 * Retorna {@link PersonAddressBuilder} para termos acesso aos seus métodos.
	 * @return
	 */
	public PersonAddressBuilder lives() {
		return new PersonAddressBuilder(person);
	}

	public Person2 build() {
		return person;
	}
}

/**
 * Builder para informações de endereço.
 * @author Guilherme
 *
 */
class PersonAddressBuilder extends Person2Builder {
	public PersonAddressBuilder(Person2 person) {
		this.person = person;
	}

	public PersonAddressBuilder at(String streetAddress) {
		person.streetAddress = streetAddress;
		return this;
	}

	public PersonAddressBuilder withPostcode(String postcode) {
		person.postcode = postcode;
		return this;
	}

	public PersonAddressBuilder in(String city) {
		person.city = city;
		return this;
	}
}

/**
 * Builder para informações sobre o trabalho.
 * @author Guilherme
 *
 */
class PersonJobBuilder extends Person2Builder {
	public PersonJobBuilder(Person2 person) {
		this.person = person;
	}

	public PersonJobBuilder at(String companyName) {
		person.companyName = companyName;
		return this;
	}

	public PersonJobBuilder asA(String position) {
		person.position = position;
		return this;
	}

	public PersonJobBuilder earning(int annualIncome) {
		person.annualIncome = annualIncome;
		return this;
	}
}

class BuilderFacetsDemo {
	public static void main(String[] args) {
		Person2Builder pb = new Person2Builder();
		Person2 person = pb
				.lives()//Aqui temos acesso aos métodos de PersonAddressBuilder
					.at("123 London Road")
					.in("London")
					.withPostcode("SW12BC")
				.works()//Aqui temos acesso aos métodos de PersonJobBuilder
					.at("Fabrikam")
					.asA("Engineer")
					.earning(123000)
				.build();//Construimos nossa pessoa.
		System.out.println(person);
	}
}
