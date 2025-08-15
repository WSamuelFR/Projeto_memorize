package projeto_save;

//src/Card.java
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Card {
 private int id;
 private String titulo;
 private String descricao;
 private LocalDateTime createdAt;
 private List<Tabela> tabelas;

 // Construtor para novos cartões em memória
 public Card(String titulo, String descricao) {
     this.titulo = titulo;
     this.descricao = descricao;
     this.createdAt = LocalDateTime.now();
     this.tabelas = new ArrayList<>();
 }
 
 // Construtor usado pelo CardManager para inicialização
 public Card(int id, String titulo, String descricao) {
     this.id = id;
     this.titulo = titulo;
     this.descricao = descricao;
     this.createdAt = LocalDateTime.now();
     this.tabelas = new ArrayList<>();
 }

 // Getters e Setters
 public void setId(int id) { this.id = id; }
 public int getId() { return id; }
 public String getTitulo() { return titulo; }
 public void setTitulo(String titulo) { this.titulo = titulo; }
 public String getDescricao() { return descricao; }
 public void setDescricao(String descricao) { this.descricao = descricao; }
 public LocalDateTime getCreatedAt() { return createdAt; }
 public List<Tabela> getTabelas() { return tabelas; }
}