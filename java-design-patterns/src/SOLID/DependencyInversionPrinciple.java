package SOLID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.javatuples.Triplet;

public class DependencyInversionPrinciple {

}

// A. Um módulo de alto nível não pode depender de um de baixo nível
// Ambos devem depender de abstrações.

// B. Abstrações não devem depender de detalhes.
// Detalhes devem depender de abstrações.

enum Relationship {
	PARENT, CHILD, SIBLING
}

class Person {
	public String name;

	public Person(String name) {
		this.name = name;
	}
}

//Interface criada para resolver o problema apontado em Research.
interface RelationshipBrowser {
	List<Person> findAllChildrenOf(String name);
}

//Baixo nível, realiza apenas operaçõesm em relations.
class Relationships implements RelationshipBrowser {
	
	public List<Person> findAllChildrenOf(String name) {
		return relations.stream()
				.filter(x -> Objects.equals(x.getValue0().name, name) && x.getValue1() == Relationship.PARENT)
				.map(Triplet::getValue2).collect(Collectors.toList());
	}

	private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

	public List<Triplet<Person, Relationship, Person>> getRelations() {
		return relations;
	}

	public void addParentAndChild(Person parent, Person child) {
		relations.add(new Triplet<>(parent, Relationship.PARENT, child));
		relations.add(new Triplet<>(child, Relationship.CHILD, parent));
	}
}

/**
 * Alto nível, pode conter as regras de negócio.
 */
class Research {
	//Aqui há o problema do módulo de alto nível dependendo de detalhes do módulo de baixo nível.
	//EX: Se o tipo de relations em Relationships precisar mudar, este método não vai precisar ser alterado também.
	public Research(Relationships relationships) {
		List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
		relations.stream().filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
				.forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
	}

	//Aqui deixamos de depender de detalhes e passamos a depender da abstração RelationshipBrowser.
	//Agora mesmo com a mudança do tipo de relations em Relatioships, as alterações serão feitas apenas lá, e este trecho continuará funcionando
	//porque a interface RelationshipBrowser continuará nos devolvendo uma lista de Pessoas. 
	public Research(RelationshipBrowser browser) {
		List<Person> children = browser.findAllChildrenOf("John");
		for (Person child : children)
			System.out.println("John has a child called " + child.name);
	}
}

class DIPDemo {
	public static void main(String[] args) {
		Person parent = new Person("John");
		Person child1 = new Person("Chris");
		Person child2 = new Person("Matt");

		Relationships relationships = new Relationships();
		relationships.addParentAndChild(parent, child1);
		relationships.addParentAndChild(parent, child2);

		new Research(relationships);
	}
}
