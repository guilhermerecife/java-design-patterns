package prototype;

/**
 * Criar cópia através dos construtores.
 * 
 * @author Guilherme
 *
 */
public class CopyConstructor {

}

class Address {
	public String streetAddress, city, country;

	public Address(String streetAddress, String city, String country) {
		this.streetAddress = streetAddress;
		this.city = city;
		this.country = country;
	}

	/**
	 * Cria uma cópia de Adrress com base em other.
	 * @param other
	 */
	public Address(Address other) {
		this(other.streetAddress, other.city, other.country);
	}

	@Override
	public String toString() {
		return "Address{" + "streetAddress='" + streetAddress + '\'' + ", city='" + city + '\'' + ", country='"
				+ country + '\'' + '}';
	}
}

class Employee {
	public String name;
	public Address address;

	public Employee(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	/**
	 * Cria uma cópia de Employee com base em other.
	 * @param other
	 */
	public Employee(Employee other) {
		name = other.name;
		address = new Address(other.address);
	}

	@Override
	public String toString() {
		return "Employee{" + "name='" + name + '\'' + ", address=" + address + '}';
	}
}

class CopyConstructorDemo {
	public static void main(String[] args) {
		Employee john = new Employee("John", new Address("123 London Road", "London", "UK"));
		
		Employee chris = new Employee(john);

		chris.name = "Chris";
		System.out.println(john);
		System.out.println(chris);
	}
}
