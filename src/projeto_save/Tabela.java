package projeto_save;

//src/Tabela.java
import java.time.LocalDateTime;

public class Tabela {
 private int id;
 private int cardId;
 private String tabela;
 private String thead;
 private String tbody;
 private String tr;
 private String td;
 private LocalDateTime createdAt;
 
 // Construtor para novas linhas de tabela em memória
 public Tabela(String tabela, String thead, String tbody, String tr, String td) {
     this.tabela = tabela;
     this.thead = thead;
     this.tbody = tbody;
     this.tr = tr;
     this.td = td;
     this.createdAt = LocalDateTime.now();
 }
 
 // Construtor usado pelo CardManager para inicialização
 public Tabela(int id, int cardId, String tabela, String thead, String tbody, String tr, String td) {
     this.id = id;
     this.cardId = cardId;
     this.tabela = tabela;
     this.thead = thead;
     this.tbody = tbody;
     this.tr = tr;
     this.td = td;
     this.createdAt = LocalDateTime.now();
 }

 // Getters e Setters
 public void setId(int id) { this.id = id; }
 public int getId() { return id; }
 public int getCardId() { return cardId; }
 public void setCardId(int cardId) { this.cardId = cardId; }
 public String getTabela() { return tabela; }
 public void setTabela(String tabela) { this.tabela = tabela; }
 public String getThead() { return thead; }
 public void setThead(String thead) { this.thead = thead; }
 public String getTbody() { return tbody; }
 public void setTbody(String tbody) { this.tbody = tbody; }
 public String getTr() { return tr; }
 public void setTr(String tr) { this.tr = tr; }
 public String getTd() { return td; }
 public void setTd(String td) { this.td = td; }
 public LocalDateTime getCreatedAt() { return createdAt; }
}
