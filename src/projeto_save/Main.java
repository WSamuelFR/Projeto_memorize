package projeto_save;

//src/Main.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
 private JFrame mainFrame;
 private JPanel cardsPanel;
 private CardManager cardManager;
 private JTextField searchField;

 public Main() {
     cardManager = new CardManager();
     // Dados de exemplo, como a persistência não é utilizada
     // estes dados serão carregados a cada execução da aplicação.
     Card card1 = new Card(1, "Planejamento de Projeto", "Organização e controle de tarefas para o projeto de sistema de cartões.");
     card1.getTabelas().add(new Tabela(1, 1, "Tarefas", "Tarefa", "Descrição", "Implementar login", "Login de usuário"));
     cardManager.addCard(card1);

     Card card2 = new Card(2, "Reunião com a Equipe", "Anotações da reunião para discutir o cronograma e os novos requisitos.");
     cardManager.addCard(card2);

     initializeGUI();
 }

 private void initializeGUI() {
     mainFrame = new JFrame("Memorizador Beta");
     mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     mainFrame.setSize(1000, 700);
     mainFrame.setLocationRelativeTo(null);
     mainFrame.setLayout(new BorderLayout());

     // Configuração do painel superior (Header)
     JPanel headerPanel = new JPanel(new BorderLayout());
     headerPanel.setBackground(Color.WHITE);
     headerPanel.setBorder(BorderFactory.createCompoundBorder(
         new MatteBorder(0, 0, 1, 0, Color.decode("#E0E0E0")),
         new EmptyBorder(15, 20, 15, 20)
     ));

     JLabel titleLabel = new JLabel("Memorizador Beta");
     titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
     titleLabel.setForeground(Color.decode("#333333"));
     headerPanel.add(titleLabel, BorderLayout.WEST);
     
     JPanel searchAndAddPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
     searchAndAddPanel.setOpaque(false);
     
     searchField = new JTextField("Pesquisar por titulo ou descrição...");
     searchField.setPreferredSize(new Dimension(300, 35));
     searchField.setForeground(Color.GRAY);
     searchField.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7")));
     searchField.setFont(new Font("Arial", Font.PLAIN, 12));
     searchField.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent e) {
             if (searchField.getText().equals("Pesquisar por titulo ou descrição...")) {
                 searchField.setText("");
                 searchField.setForeground(Color.BLACK);
             }
         }
         public void focusLost(java.awt.event.FocusEvent e) {
             if (searchField.getText().isEmpty()) {
                 searchField.setText("Pesquisar por titulo ou descrição...");
                 searchField.setForeground(Color.GRAY);
             }
         }
     });
     searchField.addActionListener(e -> updateCardList(searchField.getText().equals("Pesquisar por titulo ou descrição...") ? "" : searchField.getText()));

     JButton addButton = new JButton("Adicionar Novo Card");
     addButton.setFont(new Font("Arial", Font.BOLD, 12));
     addButton.setBackground(Color.decode("#3498DB"));
     addButton.setForeground(Color.BLACK);
     addButton.setFocusPainted(false);
     addButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
     addButton.addActionListener(e -> showAddCardDialog());
     
     searchAndAddPanel.add(searchField);
     searchAndAddPanel.add(addButton);
     headerPanel.add(searchAndAddPanel, BorderLayout.EAST);
     
     mainFrame.add(headerPanel, BorderLayout.NORTH);

     // Painel principal para os cards
     JPanel contentPanel = new JPanel(new BorderLayout());
     contentPanel.setBackground(Color.decode("#ECF0F1"));
     contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

     cardsPanel = new JPanel();
     cardsPanel.setBackground(Color.decode("#ECF0F1"));
     cardsPanel.setLayout(new GridBagLayout());
     
     JScrollPane scrollPane = new JScrollPane(cardsPanel);
     scrollPane.setBorder(BorderFactory.createEmptyBorder());
     scrollPane.getVerticalScrollBar().setUnitIncrement(16);
     contentPanel.add(scrollPane, BorderLayout.CENTER);
     
     mainFrame.add(contentPanel, BorderLayout.CENTER);

     updateCardList("");

     mainFrame.setVisible(true);
 }

 private JPanel createCardPanel(Card card) {
     JPanel cardPanel = new JPanel(new BorderLayout());
     cardPanel.setBackground(Color.WHITE);
     cardPanel.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createLineBorder(Color.decode("#BDC3C7"), 1),
         new EmptyBorder(15, 15, 15, 15)
     ));
     cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
     
     cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override
         public void mouseClicked(java.awt.event.MouseEvent evt) {
             new DetailsDialog(mainFrame, card, cardManager, Main.this);
         }

         @Override
         public void mouseEntered(java.awt.event.MouseEvent e) {
             cardPanel.setBackground(Color.decode("#F5F5F5"));
         }

         @Override
         public void mouseExited(java.awt.event.MouseEvent e) {
             cardPanel.setBackground(Color.WHITE);
         }
     });
     
     JLabel titleLabel = new JLabel(card.getTitulo());
     titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
     titleLabel.setForeground(Color.decode("#333333"));
     
     JTextArea descriptionArea = new JTextArea(card.getDescricao());
     descriptionArea.setWrapStyleWord(true);
     descriptionArea.setLineWrap(true);
     descriptionArea.setEditable(false);
     descriptionArea.setFocusable(false);
     descriptionArea.setBackground(cardPanel.getBackground());
     descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
     descriptionArea.setForeground(Color.GRAY);
     
     JLabel dateLabel = new JLabel("Criado em: " + card.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
     dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
     dateLabel.setForeground(Color.decode("#95A5A6"));
     
     JPanel textPanel = new JPanel(new BorderLayout(0, 5));
     textPanel.setOpaque(false);
     textPanel.add(titleLabel, BorderLayout.NORTH);
     textPanel.add(descriptionArea, BorderLayout.CENTER);
     
     cardPanel.add(textPanel, BorderLayout.CENTER);
     cardPanel.add(dateLabel, BorderLayout.SOUTH);
     
     return cardPanel;
 }

 private void updateCardList(String query) {
     cardsPanel.removeAll();
     cardsPanel.revalidate();
     cardsPanel.repaint();
     List<Card> cards = cardManager.searchCards(query);
     
     if (cards.isEmpty()) {
         JPanel emptyState = new JPanel();
         emptyState.setLayout(new BoxLayout(emptyState, BoxLayout.Y_AXIS));
         emptyState.setOpaque(false);
         
         JLabel emptyTitle = new JLabel("Nenhum cartão encontrado");
         emptyTitle.setFont(new Font("Arial", Font.BOLD, 18));
         emptyTitle.setForeground(Color.GRAY);
         emptyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
         
         JLabel emptyMessage = new JLabel("Adicione um novo para começar ou ajuste sua pesquisa.");
         emptyMessage.setFont(new Font("Arial", Font.PLAIN, 14));
         emptyMessage.setForeground(Color.GRAY);
         emptyMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
         
         emptyState.add(Box.createVerticalStrut(50));
         emptyState.add(emptyTitle);
         emptyState.add(Box.createVerticalStrut(10));
         emptyState.add(emptyMessage);
         
         cardsPanel.add(emptyState);
     } else {
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gbc.weightx = 1.0;
         gbc.gridx = 0;
         gbc.gridy = 0;
         
         int cardsPerRow = 3;
         for (int i = 0; i < cards.size(); i++) {
             Card card = cards.get(i);
             JPanel cardPanel = createCardPanel(card);
             gbc.gridx = i % cardsPerRow;
             gbc.gridy = i / cardsPerRow;
             cardsPanel.add(cardPanel, gbc);
         }
     }
 }

 private void showAddCardDialog() {
     JDialog dialog = new JDialog(mainFrame, "Adicionar Novo Card", true);
     dialog.setSize(450, 350);
     dialog.setLocationRelativeTo(mainFrame);
     dialog.setLayout(new BorderLayout());

     JPanel formPanel = new JPanel(new GridBagLayout());
     formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(5, 5, 5, 5);
     gbc.fill = GridBagConstraints.HORIZONTAL;

     JLabel titleLabel = new JLabel("Título:");
     titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
     gbc.gridx = 0;
     gbc.gridy = 0;
     gbc.anchor = GridBagConstraints.WEST;
     formPanel.add(titleLabel, gbc);

     JTextField titleField = new JTextField();
     gbc.gridx = 0;
     gbc.gridy = 1;
     gbc.weightx = 1.0;
     formPanel.add(titleField, gbc);

     JLabel descriptionLabel = new JLabel("Descrição:");
     descriptionLabel.setFont(new Font("Arial", Font.BOLD, 12));
     gbc.gridx = 0;
     gbc.gridy = 2;
     formPanel.add(descriptionLabel, gbc);

     JTextArea descriptionArea = new JTextArea(5, 20);
     descriptionArea.setWrapStyleWord(true);
     descriptionArea.setLineWrap(true);
     JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
     gbc.gridx = 0;
     gbc.gridy = 3;
     gbc.weighty = 1.0;
     formPanel.add(descriptionScrollPane, gbc);

     JButton saveButton = new JButton("Salvar");
     saveButton.setFont(new Font("Arial", Font.BOLD, 12));
     saveButton.setBackground(Color.decode("#3498DB"));
     saveButton.setForeground(Color.BLACK);
     saveButton.setFocusPainted(false);
     
     saveButton.addActionListener(e -> {
         String titulo = titleField.getText();
         String descricao = descriptionArea.getText();
         if (!titulo.isEmpty() && !descricao.isEmpty()) {
             cardManager.addCard(new Card(titulo, descricao));
             updateCardList("");
             dialog.dispose();
         } else {
             JOptionPane.showMessageDialog(dialog, "Título e descrição são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
         }
     });
     
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
     buttonPanel.add(new JButton("Cancelar"));
     buttonPanel.add(saveButton);

     dialog.add(formPanel, BorderLayout.CENTER);
     dialog.add(buttonPanel, BorderLayout.SOUTH);
     dialog.setVisible(true);
 }

 public void refreshCardList() {
     updateCardList("");
 }

 public static void main(String[] args) {
     try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     } catch (Exception e) {
         e.printStackTrace();
     }
     SwingUtilities.invokeLater(Main::new);
 }
}