package projeto_save;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class DetailsDialog extends JDialog {
    private Card card;
    private CardManager cardManager;
    private Main mainApp;
    private DefaultTableModel tableModel;
    private JTable table; // A JTable foi adicionada como um membro da classe
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton saveCardButton;
    private JButton editCardButton;
    private JButton addRowButton;
    private JButton deleteRowButton;

    public DetailsDialog(JFrame parent, Card card, CardManager manager, Main mainApp) {
        super(parent, "Detalhes do Card", true);
        this.card = card;
        this.cardManager = manager;
        this.mainApp = mainApp;
        setSize(1000, 700);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(parent);
        
        getRootPane().setBorder(new EmptyBorder(15, 15, 15, 15));

        // Painel de detalhes do card (Topo)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#BDC3C7")),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(card.getTitulo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLbl = new JLabel("Título:");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; infoPanel.add(titleLbl, gbc);
        
        titleField = new JTextField(card.getTitulo());
        titleField.setEditable(false);
        titleField.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7")));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; infoPanel.add(titleField, gbc);

        JLabel descLbl = new JLabel("Descrição:");
        descLbl.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST; infoPanel.add(descLbl, gbc);
        
        descriptionArea = new JTextArea(card.getDescricao());
        descriptionArea.setEditable(false);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new Dimension(300, 80));
        descScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7")));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weighty = 1.0; infoPanel.add(descScrollPane, gbc);
        
        JLabel dateLbl = new JLabel("Criado em:");
        dateLbl.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST; infoPanel.add(dateLbl, gbc);

        JLabel dateValueLbl = new JLabel(card.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateValueLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 2; infoPanel.add(dateValueLbl, gbc);
        
        topPanel.add(infoPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        editCardButton = new JButton("Editar Card");
        editCardButton.setFont(new Font("Arial", Font.BOLD, 12));
        editCardButton.setBackground(Color.decode("#3498DB"));
        editCardButton.setForeground(Color.BLACK);
        editCardButton.setFocusPainted(false);
        editCardButton.addActionListener(e -> toggleCardEditMode(true));
        buttonPanel.add(editCardButton);
        
        saveCardButton = new JButton("Salvar Alterações");
        saveCardButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveCardButton.setBackground(Color.decode("#2ECC71"));
        saveCardButton.setForeground(Color.BLACK);
        saveCardButton.setFocusPainted(false);
        saveCardButton.addActionListener(e -> saveCardChanges());
        saveCardButton.setVisible(false);
        buttonPanel.add(saveCardButton);
        
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);

        // Painel de tabela (Centro)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.decode("#BDC3C7")),
            "Dados Detalhados",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            Color.decode("#333333")
        ));
        tablePanel.setBackground(Color.WHITE);

        String[] columnNames = {"ID", "Tabela", "Cabeçalho", "Corpo", "Linha", "Célula", "Criado em"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel); // Inicializa o membro da classe
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel addRowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addRowPanel.setOpaque(false);
        addRowButton = new JButton("Adicionar Linha");
        addRowButton.setFont(new Font("Arial", Font.BOLD, 12));
        addRowButton.setBackground(Color.decode("#3498DB"));
        addRowButton.setForeground(Color.BLACK);
        addRowButton.setFocusPainted(false);
        addRowButton.addActionListener(e -> showAddRowDialog());
        addRowPanel.add(addRowButton);
        
        deleteRowButton = new JButton("Deletar Linha");
        deleteRowButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteRowButton.setBackground(Color.decode("#E74C3C"));
        deleteRowButton.setForeground(Color.RED);
        deleteRowButton.setFocusPainted(false);
        deleteRowButton.addActionListener(e -> deleteSelectedRow());
        addRowPanel.add(deleteRowButton);
        
        tablePanel.add(addRowPanel, BorderLayout.SOUTH);

        add(tablePanel, BorderLayout.CENTER);

        updateTabelaDisplay();
        setVisible(true);
    }

    private void toggleCardEditMode(boolean enable) {
        titleField.setEditable(enable);
        descriptionArea.setEditable(enable);
        editCardButton.setVisible(!enable);
        saveCardButton.setVisible(enable);
    }

    private void saveCardChanges() {
        cardManager.updateCard(card.getId(), titleField.getText(), descriptionArea.getText());
        mainApp.refreshCardList();
        JOptionPane.showMessageDialog(this, "Card atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        toggleCardEditMode(false);
    }

    private void deleteSelectedRow() {
        // Agora acessamos o método getSelectedRow() da instância da JTable
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int tabelaId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza de que deseja deletar esta linha?", 
                "Confirmar Deleção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cardManager.deleteTabela(tabelaId);
                card.getTabelas().removeIf(t -> t.getId() == tabelaId);
                updateTabelaDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma linha para deletar.", "Nenhuma Linha Selecionada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTabelaDisplay() {
        tableModel.setRowCount(0);
        for (Tabela tabela : card.getTabelas()) {
            tableModel.addRow(new Object[]{
                tabela.getId(),
                tabela.getTabela(),
                tabela.getThead(),
                tabela.getTbody(),
                tabela.getTr(),
                tabela.getTd(),
                tabela.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            });
        }
    }

    private void showAddRowDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Nova Linha", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        String[] labels = {"Tabela:", "Cabeçalho:", "Corpo:", "Linha:", "Célula:"};
        JComponent[] fields = {new JTextField(), new JTextField(), new JTextField(), new JTextArea(3, 20), new JTextArea(3, 20)};
        
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            gbc.gridx = 0; gbc.gridy = i * 2; gbc.anchor = GridBagConstraints.WEST; formPanel.add(lbl, gbc);
            
            JComponent field = fields[i];
            if (field instanceof JTextArea) {
                JScrollPane scrollPane = new JScrollPane(field);
                scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7")));
                gbc.gridx = 0; gbc.gridy = i * 2 + 1; gbc.weightx = 1.0; gbc.weighty = 1.0; formPanel.add(scrollPane, gbc);
            } else {
                ((JTextField) field).setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7")));
                gbc.gridx = 0; gbc.gridy = i * 2 + 1; gbc.weightx = 1.0; gbc.weighty = 0.0; formPanel.add(field, gbc);
            }
        }

        JButton saveButton = new JButton("Salvar");
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setBackground(Color.decode("#3498DB"));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        
        saveButton.addActionListener(e -> {
            Tabela newTabela = new Tabela(
                ((JTextField) fields[0]).getText(),
                ((JTextField) fields[1]).getText(),
                ((JTextField) fields[2]).getText(),
                ((JTextArea) fields[3]).getText(),
                ((JTextArea) fields[4]).getText()
            );
            cardManager.addTabelaToCard(card.getId(), newTabela);
            updateTabelaDisplay();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Linha adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(new JButton("Cancelar"));
        buttonPanel.add(saveButton);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}