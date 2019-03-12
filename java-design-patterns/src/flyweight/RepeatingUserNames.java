package flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * O Flyweight tem mais a intenção de economizar memória.
 * Normalmente tenta evitar que um processamento já ocorrido tenha que ocorrer novamente.
 * @author Guilherme
 *
 */
public class RepeatingUserNames {

}

class User {
	private String fullName;

	public User(String fullName) {
		this.fullName = fullName;
	}
}

class User2 {
	static List<String> strings = new ArrayList<>();//Armazenar os nomes distintos
	private int[] names;

	public User2(String fullName) {
		Function<String, Integer> getOrAdd = (String s) -> {//Função para pegar ou adicionar nomes no array cache.
			int idx = strings.indexOf(s);
			if (idx != -1)
				return idx;
			else {
				strings.add(s);
				return strings.size() - 1;
			}
		};

		names = Arrays.stream(fullName.split(" ")).mapToInt(s -> getOrAdd.apply(s)).toArray();
	}

	public String getFullName() {
		return Arrays.stream(names).mapToObj(i -> strings.get(i)).collect(Collectors.joining(","));//Busca o nome compleno no cache.
	}
}

class UsersDemo {
	public static void main(String[] args) {
		User2 user = new User2("John Smith");
		User2 user1 = new User2("Jane Smith");
	}
}
