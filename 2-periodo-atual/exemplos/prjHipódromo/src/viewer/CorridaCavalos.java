package viewer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CorridaCavalos extends JFrame {
	private static final int DISTANCIA_TOTAL = 400;
	private static final int NUM_CAVALOS = 5;
	private static final int LARGURA_PISTA = 800;
	private static final int ALTURA_RAIA = 100;
	private static final int MARGEM_ESQUERDA = 50;
	private static final int MARGEM_SUPERIOR = 50;

	private Cavalo[] cavalos;
	private Thread[] threads;
	private JPanel pistaPanel;
	private JLabel statusLabel;
	private volatile String vencedor = null;
	private final Object lock = new Object();
	private AtomicBoolean corridaTerminada = new AtomicBoolean(false);

	public CorridaCavalos() {
		setTitle("Simulador de Corrida de Cavalos");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Painel da pista
		pistaPanel = new PistaPanel();
		pistaPanel.setPreferredSize(new Dimension(850, 550));
		pistaPanel.setBackground(new Color(34, 139, 34)); // Verde grama

		// Label de status
		statusLabel = new JLabel("Corrida em andamento...", SwingConstants.CENTER);
		statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setBackground(Color.BLACK);
		statusLabel.setOpaque(true);

		add(pistaPanel, BorderLayout.CENTER);
		add(statusLabel, BorderLayout.SOUTH);

		// Inicializar cavalos
		String[] nomes = { "Relâmpago", "Trovão", "Ventania", "Foguete", "Tempestade" };
		Color[] cores = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA };

		cavalos = new Cavalo[NUM_CAVALOS];
		threads = new Thread[NUM_CAVALOS];

		for (int i = 0; i < NUM_CAVALOS; i++) {
			cavalos[i] = new Cavalo(nomes[i], cores[i], i);
		}

		// Botão iniciar
		JButton iniciarButton = new JButton("Iniciar Corrida");
		iniciarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarCorrida();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(iniciarButton);
		add(buttonPanel, BorderLayout.NORTH);
	}

	private void iniciarCorrida() {
		if (vencedor != null) {
			// Resetar corrida
			vencedor = null;
			corridaTerminada.set(false);
			statusLabel.setText("Corrida em andamento...");
			for (Cavalo cavalo : cavalos) {
				cavalo.posicao = 0;
			}
			pistaPanel.repaint();
		}

		// Iniciar threads
		for (int i = 0; i < NUM_CAVALOS; i++) {
			threads[i] = new Thread(cavalos[i]);
			threads[i].start();
		}
	}

	private class Cavalo implements Runnable {
		private String nome;
		private Color cor;
		private int posicao;
		private int raia;
		private Random random;

		public Cavalo(String nome, Color cor, int raia) {
			this.nome = nome;
			this.cor = cor;
			this.raia = raia;
			this.posicao = 0;
			this.random = new Random();
		}

		@Override
		public void run() {
			while (!corridaTerminada.get() && posicao < DISTANCIA_TOTAL) {
				try {
					// Sorteia velocidade entre 1 e 10
					int velocidade = random.nextInt(10) + 1;
					posicao += velocidade;

					if (posicao > DISTANCIA_TOTAL) {
						posicao = DISTANCIA_TOTAL;
					}

					// Atualiza interface gráfica
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							pistaPanel.repaint();
							statusLabel
									.setText(String.format("%s avançou %dm. Posição: %dm", nome, velocidade, posicao));
						}
					});

					// Verifica se venceu
					if (posicao >= DISTANCIA_TOTAL && !corridaTerminada.get()) {
						synchronized (lock) {
							if (vencedor == null) {
								vencedor = nome;
								corridaTerminada.set(true);
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										mostrarVencedor();
									}
								});
							}
						}
					}

					// Pequena pausa para simular movimento
					Thread.sleep(500);

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}
	}

	private void mostrarVencedor() {
		JOptionPane.showMessageDialog(this, "🏆 O vencedor é: " + vencedor + "! 🏆", "Vencedor da Corrida",
				JOptionPane.INFORMATION_MESSAGE);
		statusLabel.setText("🏆 Vencedor: " + vencedor + " 🏆");
	}

	private class PistaPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Desenhar pista
			Graphics2D g2d = (Graphics2D) g;

			// Desenhar raias
			for (int i = 0; i < NUM_CAVALOS; i++) {
				int y = MARGEM_SUPERIOR + (i * ALTURA_RAIA);

				// Raia
				g2d.setColor(Color.WHITE);
				g2d.drawRect(MARGEM_ESQUERDA - 5, y, getWidth() - MARGEM_ESQUERDA - 50, ALTURA_RAIA - 10);

				// Número da raia
				g2d.setColor(Color.WHITE);
				g2d.setFont(new Font("Arial", Font.PLAIN, 14));
				g2d.drawString("" + (i + 1), MARGEM_ESQUERDA - 30, y + ALTURA_RAIA / 2 + 5);
			}

			// Desenhar linha de chegada
			int chegadaX = MARGEM_ESQUERDA
					+ (int) ((getWidth() - MARGEM_ESQUERDA - 50) * ((double) DISTANCIA_TOTAL / (DISTANCIA_TOTAL + 50)));

			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawLine(chegadaX, MARGEM_SUPERIOR - 10, chegadaX, MARGEM_SUPERIOR + NUM_CAVALOS * ALTURA_RAIA);

			// Desenhar texto "Chegada"
			g2d.setColor(Color.RED);
			g2d.setFont(new Font("Arial", Font.BOLD, 14));
			g2d.drawString("CHEGADA", chegadaX - 30, MARGEM_SUPERIOR - 15);

			// Desenhar cavalos
			for (int i = 0; i < NUM_CAVALOS; i++) {
				int y = MARGEM_SUPERIOR + (i * ALTURA_RAIA) + 10;
				int x = MARGEM_ESQUERDA + (int) ((getWidth() - MARGEM_ESQUERDA - 50)
						* ((double) cavalos[i].posicao / (DISTANCIA_TOTAL + 50)));

				// Desenhar cavalo (círculo com características)
				g2d.setColor(cavalos[i].cor);
				g2d.fillOval(x, y, 40, 50);

				// Desenhar cabeça do cavalo
				g2d.fillOval(x + 30, y + 5, 25, 25);

				// Desenhar pescoço
				g2d.fillRect(x + 20, y + 15, 15, 15);

				// Desenhar crina
				g2d.setColor(Color.BLACK);
				g2d.fillArc(x + 35, y - 10, 20, 25, 180, 180);

				// Nome do cavalo
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font("Arial", Font.BOLD, 12));
				g2d.drawString(cavalos[i].nome, x, y + 70);

				// Posição
				g2d.setFont(new Font("Arial", Font.PLAIN, 10));
				g2d.drawString(cavalos[i].posicao + "m", x, y + 85);
			}
		}
	}
}
