package jogo.reciclagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JogoReciclagemGUI extends JFrame {

    public JogoReciclagemGUI() {
        setTitle("Jogo da Reciclagem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
        setLocationRelativeTo(null);

        // Painel com layout absoluto para posicionar manualmente
        JPanel painel = new JPanel(null) {
            Image imagemFundo = new ImageIcon(getClass().getResource("/fundo_parque.jpeg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Redimensiona a imagem para o tamanho da janela
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Botão de sair
        JButton btnSair = new JButton("X");
        btnSair.setForeground(Color.WHITE);
        btnSair.setBackground(Color.RED);
        btnSair.setBounds( getWidth() - 100, 20, 60, 40);
        btnSair.setFont(new Font("Arial", Font.BOLD, 18));
        btnSair.addActionListener(e -> System.exit(0));
        painel.add(btnSair);

        // Título
        JLabel titulo = new JLabel("JOGO DO LIXO", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setBounds(0, 150, getWidth(), 60);
        painel.add(titulo);

        // Botão iniciar
        JButton btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 30));
        btnIniciar.setBackground(new Color(34, 139, 34));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setBounds(getWidth() / 2 - 150, 300, 300, 60);
        painel.add(btnIniciar);

        // Redimensionar elementos com o frame
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int largura = getWidth();
                int altura = getHeight();
                btnIniciar.setBounds(largura / 2 - 150, altura / 2, 300, 60);
                titulo.setBounds(0, altura / 2 - 120, largura, 60);
                btnSair.setBounds(largura - 80, 20, 60, 40);
            }
        });

        // Ação do botão Iniciar
        btnIniciar.addActionListener(e -> {
            dispose(); // Fecha a tela inicial
            new TelaJogo(); // Abre a tela do jogo (classe a ser criada)
        });

        setContentPane(painel);
        setVisible(true);
    }

    
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                AudioPlayer.tocarMusicaDeFundo();
                new JogoReciclagemGUI();
            });
        }

}
