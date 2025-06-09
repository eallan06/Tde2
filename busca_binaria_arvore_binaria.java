// === Parte 1: Busca Binária com Lista Ordenada ===

// Produto.java
public class Produto implements Comparable<Produto> {
    private int id;
    private String nome;
    private double preco;

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }

    @Override
    public int compareTo(Produto outro) {
        return Integer.compare(this.id, outro.id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Preço: %.2f", id, nome, preco);
    }
}

// ProdutoService.java
import java.util.*;

public class ProdutoService {
    private final List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto novo) {
        produtos.add(novo);
        Collections.sort(produtos);
    }

    public Produto buscarPorId(int id) {
        int inicio = 0, fim = produtos.size() - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            Produto atual = produtos.get(meio);
            if (atual.getId() == id) return atual;
            else if (atual.getId() < id) inicio = meio + 1;
            else fim = meio - 1;
        }
        return null;
    }

    public void listarProdutos() {
        produtos.forEach(System.out::println);
    }
}

// Main.java (Busca Binária)
import java.util.*;

public class MainBuscaBinaria {
    public static void main(String[] args) {
        ProdutoService service = new ProdutoService();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("""
            \n=== Menu Produtos ===
            1 - Adicionar produto
            2 - Buscar por ID (binária)
            3 - Listar produtos
            0 - Sair
            """);
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();
                    service.adicionarProduto(new Produto(id, nome, preco));
                }
                case 2 -> {
                    System.out.print("ID para buscar: ");
                    int idBusca = sc.nextInt();
                    Produto encontrado = service.buscarPorId(idBusca);
                    if (encontrado != null) System.out.println("Produto encontrado: " + encontrado);
                    else System.out.println("Produto não encontrado.");
                }
                case 3 -> service.listarProdutos();
            }

        } while (opcao != 0);

        sc.close();
    }
}

// === Parte 2: Árvore Binária ===

// ArvoreProduto.java
class ArvoreProduto {
    private static class No {
        Produto produto;
        No esquerda, direita;
        No(Produto produto) { this.produto = produto; }
    }

    private No raiz;

    public void inserir(Produto produto) {
        raiz = inserirRec(raiz, produto);
    }

    private No inserirRec(No atual, Produto produto) {
        if (atual == null) return new No(produto);
        if (produto.getId() < atual.produto.getId())
            atual.esquerda = inserirRec(atual.esquerda, produto);
        else
            atual.direita = inserirRec(atual.direita, produto);
        return atual;
    }

    public Produto buscar(int id) {
        return buscarRec(raiz, id);
    }

    private Produto buscarRec(No atual, int id) {
        if (atual == null) return null;
        if (id == atual.produto.getId()) return atual.produto;
        return id < atual.produto.getId() ? buscarRec(atual.esquerda, id) : buscarRec(atual.direita, id);
    }

    public void listarEmOrdem() {
        listarRec(raiz);
    }

    private void listarRec(No atual) {
        if (atual != null) {
            listarRec(atual.esquerda);
            System.out.println(atual.produto);
            listarRec(atual.direita);
        }
    }
}

// Main.java (Árvore Binária)
import java.util.*;

public class MainArvoreBinaria {
    public static void main(String[] args) {
        ArvoreProduto arvore = new ArvoreProduto();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("""
            \n=== Menu Produtos (Árvore) ===
            1 - Adicionar Produto
            2 - Buscar Produto por ID
            3 - Listar Produtos em Ordem
            0 - Sair
            """);
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();
                    arvore.inserir(new Produto(id, nome, preco));
                }
                case 2 -> {
                    System.out.print("ID para buscar: ");
                    int idBusca = sc.nextInt();
                    Produto p = arvore.buscar(idBusca);
                    if (p != null) System.out.println("Encontrado: " + p);
                    else System.out.println("Produto não encontrado.");
                }
                case 3 -> arvore.listarEmOrdem();
            }

        } while (opcao != 0);

        sc.close();
    }
}
