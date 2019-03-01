package SOLID;

/**
 * Recomenda��o para dividir interface em pequenas interfaces menores. (ISP)
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
 * Funcionaria se voc� tivesse apenas m�quinas multifuncionais, pois para implementar esta interface
 * deve ser implementado todos os 3 m�todos.
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
 * Uma impressora antiga por exemplo n�o passa fax nem escaneia, mas como a inteface
 * Machine define os 3 m�todos, esta classe dever� implementar os 3.
 */
class OldFashionedPrinter implements Machine {
	public void print(Document d) {
		//OK
	}

	public void fax(Document d) throws Exception {
		throw new Exception();//Impressora antiga n�o passa fax.
	}

	public void scan(Document d) throws Exception {
		throw new Exception();//Impressora antiga n�o escaneia.
	}
}

/**
 * A solu��o seria dividir os m�todos um em cada interface, para que a impressoa antiga acima
 * conseguisse ter apenas o m�todo dela, que seria o print.
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
 * Uma photocopiadora pode realizar as duas opera��es implementando as duas interfaces.
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
	//Outras implementa��es podem compor esta implementa��o.
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