package SOLID;

import java.util.List;
import java.util.stream.Stream;

/**
 * As entidades de software devem estar abertas para extensão mas fechadas para modificação. (OCP)
 * @author Guilherme
 *
 */
public class OpenClosedPrinciple {

}

enum Color
{
  RED, GREEN, BLUE
}

enum Size
{
  SMALL, MEDIUM, LARGE, YUGE
}

class Product
{
  public String name;
  public Color color;
  public Size size;

  public Product(String name, Color color, Size size) {
    this.name = name;
    this.color = color;
    this.size = size;
  }
}

/**
 * ESTE FILTRO NÃO SEGUE O OPEN-CLOSED PRINCIPLE.
 */
class ProductFilter
{
  public Stream<Product> filterByColor(List<Product> products, Color color)
  {
    return products.stream().filter(p -> p.color == color);
  }

  public Stream<Product> filterBySize(List<Product> products, Size size)
  {
    return products.stream().filter(p -> p.size == size);
  }

  public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color)
  {
    return products.stream().filter(p -> p.size == size && p.color == color);
  }
  // Vai precisar de modificações constantemente para filtrar com novos possíveis campos.
  // 3 criteria = 7 methods
}

/**
 * ADICIONADO DUAS INTERFACES QUE SÃO ABERTAS PARA EXTENSÃO.
 */

interface Specification<T>
{
  boolean isSatisfied(T item);
}

interface Filter<T>
{
  Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product>
{
  private Color color;

  public ColorSpecification(Color color) {
    this.color = color;
  }

  @Override
  public boolean isSatisfied(Product p) {
    return p.color == color;
  }
}

class SizeSpecification implements Specification<Product>
{
  private Size size;

  public SizeSpecification(Size size) {
    this.size = size;
  }

  @Override
  public boolean isSatisfied(Product p) {
    return p.size == size;
  }
}

class AndSpecification<T> implements Specification<T>
{
  private Specification<T> first, second;

  public AndSpecification(Specification<T> first, Specification<T> second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean isSatisfied(T item) {
    return first.isSatisfied(item) && second.isSatisfied(item);
  }

}

/**
 * NOSSO FILTRO PASSOU A TER AGORA APENAS UM MÉTODO, QUE PODE FILTRAR POR QUALQUER COISA.
 */
class BetterFilter implements Filter<Product>
{
  @Override
  public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
    return items.stream().filter(p -> spec.isSatisfied(p));
  }
}

class OCPDemo
{
  public static void main(String[] args) {
    Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
    Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
    Product house = new Product("House", Color.BLUE, Size.LARGE);

    List<Product> products = List.of(apple, tree, house);

    ProductFilter pf = new ProductFilter();
    System.out.println("Green products (old):");
    pf.filterByColor(products, Color.GREEN)
      .forEach(p -> System.out.println(" - " + p.name + " is green"));

    // ^^ BEFORE

    // vv AFTER
    BetterFilter bf = new BetterFilter();
    System.out.println("Green products (new):");
    bf.filter(products, new ColorSpecification(Color.GREEN))
      .forEach(p -> System.out.println(" - " + p.name + " is green"));

    System.out.println("Large products:");
    bf.filter(products, new SizeSpecification(Size.LARGE))
      .forEach(p -> System.out.println(" - " + p.name + " is large"));

    System.out.println("Large blue items:");
    bf.filter(products,
      new AndSpecification<>(
        new ColorSpecification(Color.BLUE),
        new SizeSpecification(Size.LARGE)
      ))
      .forEach(p -> System.out.println(" - " + p.name + " is large and blue"));
  }
}