
package jogo.reciclagem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class TelaFinal extends JFrame {

    private int pontos;
    private int recorde;

    private final Path arquivoRecorde = Paths.get(
        System.getProperty("user.home"),
        ".jogo-reciclagem",
        "recorde.txt"
    );

    public TelaFinal(int pontos) {
        this.pontos = pontos;

        // Carrega o recorde anterior e salva automaticamente se houver um novo.
        recorde = carregarRecorde();

        if (pontos > recorde) {
            recorde = pontos;
            salvarRecorde();
        }

        setTitle("Jogo da Reciclagem - Resultado Final");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel painel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(34, 139, 34));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        setContentPane(painel);

        // Componentes
        JLabel labelPontuacao = new JLabel("Pontuação: " + pontos);
        labelPontuacao.setFont(new Font("Arial", Font.BOLD, 50));
        labelPontuacao.setForeground(Color.WHITE);

        JLabel labelRecorde = new JLabel("Recorde: " + recorde);
        labelRecorde.setFont(new Font("Arial", Font.BOLD, 30));
        labelRecorde.setForeground(Color.WHITE);

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 30));
        btnReiniciar.setBackground(new Color(0, 128, 0));
        btnReiniciar.setForeground(Color.WHITE);

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 30));
        btnSair.setBackground(Color.RED);
        btnSair.setForeground(Color.WHITE);

        // Adiciona ao painel
        painel.add(labelPontuacao);
        painel.add(labelRecorde);
        painel.add(btnReiniciar);
        painel.add(btnSair);

        // Mantém o posicionamento original dos componentes
        SwingUtilities.invokeLater(() -> {
            int w = getWidth();
            int h = getHeight();

            labelPontuacao.setBounds(w / 2 - 200, h / 2 - 150, 400, 60);
            labelRecorde.setBounds(w / 2 - 100, h / 2 - 70, 200, 40);
            btnReiniciar.setBounds(w / 2 - 150, h / 2, 300, 60);
            btnSair.setBounds(w / 2 - 75, h / 2 + 80, 150, 50);
        });

        // Ações
        btnReiniciar.addActionListener(e -> {
            dispose();
            new JogoReciclagemGUI();
        });

        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private int carregarRecorde() {
        try {
            if (Files.exists(arquivoRecorde)) {
                String conteudo = Files.readString(
                    arquivoRecorde,
                    StandardCharsets.UTF_8
                ).trim();

                return Math.max(0, Integer.parseInt(conteudo));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Não foi possível carregar o recorde: "
                + e.getMessage());
        }

        return 0;
    }

    private void salvarRecorde() {
        try {
            Files.createDirectories(arquivoRecorde.getParent());

            Files.write(
                arquivoRecorde,
                Collections.singletonList(String.valueOf(recorde)),
                StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.err.println("Não foi possível salvar o recorde: "
                + e.getMessage());
        }
    }
}
