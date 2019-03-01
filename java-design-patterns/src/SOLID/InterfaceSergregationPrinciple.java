package SOLID;

/**
 * Recomendação para dividir interface em pequenas interfaces menores. (ISP)
 * 
 * @author Guilherme
 *
 */
public class InterfaceSergregationPrinciple {

}

class Document {
}

interface Machine {
	void print(Document d);

	void fax(Document d) throws Exception;

	void scan(Document d) throws Exception;
}

/**
 * Funcionaria se você tivesse apenas máquinas multifuncionais, pois para implementar esta interface
 * deve ser implementado todos os 3 métodos.
 */
class MultiFunctionPrinter implements Machine {
	public void print(Document d) {
		//
	}

	public void fax(Document d) {
		//
	}

	public void scan(Document d) {
		//
	}
}

/**
 * Uma impressora antiga por exemplo não passa fax nem escaneia, mas como a inteface
 * Machine define os 3 métodos, esta classe deverá implementar os 3.
 */
class OldFashionedPrinter implements Machine {
	public void print(Document d) {
		//OK
	}

	public void fax(Document d) throws Exception {
		throw new Exception();//Impressora antiga não passa fax.
	}

	public void scan(Document d) throws Exception {
		throw new Exception();//Impressora antiga não escaneia.
	}
}

/**
 * A solução seria dividir os métodos um em cada interface, para que a impressoa antiga acima
 * conseguisse ter apenas o método dela, que seria o print.
 */

interface Printer {
	void Print(Document d) throws Exception;
}

interface IScanner {
	void Scan(Document d) throws Exception;
}

/**
 * Agora a impressora antiga trabalha apenas com o que sabe, print().
 */
class JustAPrinter implements Printer {
	public void Print(Document d) {

	}
}

/**
 * Uma photocopiadora pode realizar as duas operações implementando as duas interfaces.
 */
class Photocopier implements Printer, IScanner {
	public void Print(Document d) throws Exception {
		//
	}

	public void Scan(Document d) throws Exception {
		//
	}
}

/**
 * Podemos melhorar ainda mais, ao inves de implementar X interfaces como no exemplo acima,
 * podemos criar uma interface que extende as outras e implementar apenas essa
 * como no exemplo MultiFunctionMachine.
 */
interface MultiFunctionDevice extends Printer, IScanner //
{

}

/**
 * Mais elegante. E ainda tem um pouco de Decorator.
 */
class MultiFunctionMachine implements MultiFunctionDevice {
	//Outras implementações podem compor esta implementação.
	private Printer printer;
	private IScanner scanner;

	public MultiFunctionMachine(Printer printer, IScanner scanner) {
		this.printer = printer;
		this.scanner = scanner;
	}

	public void Print(Document d) throws Exception {
		printer.Print(d);
	}

	public void Scan(Document d) throws Exception {
		scanner.Scan(d);
	}
}