package projeto_save;

//src/CardManager.java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardManager {
 private final List<Card> cards;
 private int nextCardId = 1;
 private int nextTabelaId = 1;

 public CardManager() {
     this.cards = new ArrayList<>();
 }

 public List<Card> getAllCards() {
     return new ArrayList<>(cards);
 }

 public Card getCardById(int id) {
     return cards.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
 }

 public void addCard(Card card) {
     card.setId(nextCardId++);
     cards.add(card);
 }

 public void updateCard(int id, String titulo, String descricao) {
     Card card = getCardById(id);
     if (card != null) {
         card.setTitulo(titulo);
         card.setDescricao(descricao);
     }
 }

 public void addTabelaToCard(int cardId, Tabela tabela) {
     Card card = getCardById(cardId);
     if (card != null) {
         tabela.setId(nextTabelaId++);
         card.getTabelas().add(tabela);
     }
 }

 public void updateTabela(int tabelaId, String tabela, String thead, String tbody, String tr, String td) {
     for (Card card : cards) {
         for (Tabela tab : card.getTabelas()) {
             if (tab.getId() == tabelaId) {
                 tab.setTabela(tabela);
                 tab.setThead(thead);
                 tab.setTbody(tbody);
                 tab.setTr(tr);
                 tab.setTd(td);
                 return;
             }
         }
     }
 }

 public void deleteTabela(int tabelaId) {
     for (Card card : cards) {
         card.getTabelas().removeIf(t -> t.getId() == tabelaId);
     }
 }

 public List<Card> searchCards(String query) {
     if (query == null || query.trim().isEmpty()) {
         return getAllCards();
     }
     String lowerCaseQuery = query.toLowerCase();
     return cards.stream()
             .filter(c -> c.getTitulo().toLowerCase().contains(lowerCaseQuery) ||
                          c.getDescricao().toLowerCase().contains(lowerCaseQuery))
             .collect(Collectors.toList());
 }
}
