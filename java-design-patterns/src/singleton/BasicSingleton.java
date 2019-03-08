package singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Basicamente uma classe que só precisa ser instanciada uma única vez.
 * OBS: Aqui não é demonstrado o Lazy Inicialization e nem como torná-lo Thread-safe.
 * 
 * @author Guilherme
 *
 */
public class BasicSingleton {
	// Previne a chamada do construtor de fora da classe, porém
	// * uma instancia pode ser criada deliberadamente através de reflection
	// * uma instancia pode ser criada acidentalmente através de serialização
	private BasicSingleton() {
		System.out.println("Singleton is initializing");
	}

	private static final BasicSingleton INSTANCE = new BasicSingleton();

	private int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	//Necessário para a correta serialização\deserialização.
	// readResolve é usado internamente para substituir o objeto lido do stream.
	//Ver em https://stackoverflow.com/questions/1168348/java-serialization-readobject-vs-readresolve
	protected Object readResolve() {
		return INSTANCE;
	}

	public static BasicSingleton getInstance() {
		return INSTANCE;
	}
}

class BasicSingletonDemo {
	/**
	 * Salva o objeto em um arquivo.
	 * @param singleton
	 * @param filename
	 * @throws Exception
	 */
	static void saveToFile(BasicSingleton singleton, String filename) throws Exception {
		try (FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(singleton);
		}
	}

	/**
	 * Ler o objet de um arquivo.
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	static BasicSingleton readFromFile(String filename) throws Exception {
		try (FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (BasicSingleton) in.readObject();
		}
	}

	public static void main(String[] args) throws Exception {
		BasicSingleton singleton = BasicSingleton.getInstance();
		singleton.setValue(111);

		String filename = "singleton.bin";
		saveToFile(singleton, filename);

		singleton.setValue(222);

		BasicSingleton singleton2 = readFromFile(filename);

		System.out.println(singleton == singleton2);
		System.out.println(singleton.getValue());
		System.out.println(singleton2.getValue());
	}
}
