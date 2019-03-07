package builder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Separa a construção de um objeto de sua representação.
 * OBS: (Exemplos de Builder Fluente e Não fluente)
 * @author Guilherme
 *
 */
public class Builder {

}

/**
 * Elemento HTML.
 * @author Guilherme
 *
 */
class HtmlElement {
	public String name, text;
	public ArrayList<HtmlElement> elements = new ArrayList<HtmlElement>();
	private final int indentSize = 2;
	private final String newLine = System.lineSeparator();

	public HtmlElement() {
	}

	public HtmlElement(String name, String text) {
		this.name = name;
		this.text = text;
	}

	private String toStringImpl(int indent) {
		StringBuilder sb = new StringBuilder();
		String i = String.join("", Collections.nCopies(indent * indentSize, " "));
		sb.append(String.format("%s<%s>%s", i, name, newLine));
		if (text != null && !text.isEmpty()) {
			sb.append(String.join("", Collections.nCopies(indentSize * (indent + 1), " "))).append(text)
					.append(newLine);
		}

		for (HtmlElement e : elements)
			sb.append(e.toStringImpl(indent + 1));

		sb.append(String.format("%s</%s>%s", i, name, newLine));
		return sb.toString();
	}

	@Override
	public String toString() {
		return toStringImpl(0);
	}
}

/**
 * Builder de elemento HTML.
 * @author Guilherme
 *
 */
class HtmlBuilder {
	private String rootName;
	private HtmlElement root = new HtmlElement();

	/**
	 * O contrutor do builder recebe o nome do elemento raíz.
	 * @param rootName
	 */
	public HtmlBuilder(String rootName) {
		this.rootName = rootName;
		root.name = rootName;
	}

	/**
	 * Método não fluente que adiciona outros elementos na construção.
	 * @param childName
	 * @param childText
	 */
	public void addChild(String childName, String childText) {
		HtmlElement e = new HtmlElement(childName, childText);
		root.elements.add(e);
	}

	/**
	 * Método fluente que adiciona outros elementos na contrução.
	 * Observe que retorna o próprio construtor para que as chamadas possam ser sequenciais.
	 * @param childName
	 * @param childText
	 * @return
	 */
	public HtmlBuilder addChildFluent(String childName, String childText) {
		HtmlElement e = new HtmlElement(childName, childText);
		root.elements.add(e);
		return this;
	}

	public void clear() {
		root = new HtmlElement();
		root.name = rootName;
	}

	@Override
	public String toString() {
		return root.toString();
	}
}

class BuilderDemo {
	public static void main(String[] args) {
		//Não fluente, repare que toda a lógica de criação de HtmlElement está no builder
		HtmlBuilder builder = new HtmlBuilder("ul");
		builder.addChild("li", "hello");
		builder.addChild("li", "world");
		System.out.println(builder);

		//Fluente, repare que as chamadas de addChildFluent não sequenciais, isso é ser fluente.
		builder.clear();
		builder.addChildFluent("li", "hello").addChildFluent("li", "world");
		System.out.println(builder);
	}
}