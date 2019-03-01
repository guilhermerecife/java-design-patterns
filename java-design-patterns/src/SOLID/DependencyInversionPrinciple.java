package SOLID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.javatuples.Triplet;

public class DependencyInversionPrinciple {

}

// A. Um m�dulo de alto n�vel n�o pode depender de um de baixo n�vel
// Ambos devem depender de abstra��es.

// B. Abstra��es n�o devem depender de detalhes.
// Detalhes devem depender de abstra��es.

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

//Baixo n�vel, realiza apenas opera��esm em relations.
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
 * Alto n�vel, pode conter as regras de neg�cio.
 */
class Research {
	//Aqui h� o problema do m�dulo de alto n�vel dependendo de detalhes do m�dulo de baixo n�vel.
	//EX: Se o tipo de relations em Relationships precisar mudar, este m�todo n�o vai precisar ser alterado tamb�m.
	public Research(Relationships relationships) {
		List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
		relations.stream().filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
				.forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
	}

	//Aqui deixamos de depender de detalhes e passamos a depender da abstra��o RelationshipBrowser.
	//Agora mesmo com a mudan�a do tipo de relations em Relatioships, as altera��es ser�o feitas apenas l�, e este trecho continuar� funcionando
	//porque a interface RelationshipBrowser continuar� nos devolvendo uma lista de Pessoas. 
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
