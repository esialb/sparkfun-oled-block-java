package org.esialb.edison.sfo;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OledSwing extends JFrame {
	private OledImage image = SFOled.createImage();
	private byte[] buffer = new byte[SFOled.BUFFER_SIZE];
	
	private volatile boolean up, down, left, right, select, a, b;
	
	public OledSwing() {
		super("SparkFun OLED");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(256, 192);
		final JPanel p = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		add(p);
		p.setFocusable(true);
		p.requestFocus();
		p.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				p.requestFocusInWindow();
			}
		});
		p.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A: a = true; break;
				case KeyEvent.VK_Z: b = true; break;
				case KeyEvent.VK_UP: up = true; break;
				case KeyEvent.VK_DOWN: down = true; break;
				case KeyEvent.VK_LEFT: left = true; break;
				case KeyEvent.VK_RIGHT: right = true; break;
				case KeyEvent.VK_ENTER: select = true; break;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A: a = false; break;
				case KeyEvent.VK_Z: b = false; break;
				case KeyEvent.VK_UP: up = false; break;
				case KeyEvent.VK_DOWN: down = false; break;
				case KeyEvent.VK_LEFT: left = false; break;
				case KeyEvent.VK_RIGHT: right = false; break;
				case KeyEvent.VK_ENTER: select = false; break;
				}
			}
		});
	}
	
	public void display0() {
		byte[] ibuf = ((OledDataBuffer) image.getRaster().getDataBuffer()).getBuffer();
		System.arraycopy(buffer, 0, ibuf, 0, SFOled.BUFFER_SIZE);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}
	
	public void read0(byte[] b) {
		System.arraycopy(buffer, 0, b, 0, SFOled.BUFFER_SIZE);
	}
	
	public void write0(byte[] b) {
		System.arraycopy(b, 0, buffer, 0, SFOled.BUFFER_SIZE);
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isSelect() {
		return select;
	}

	public boolean isA() {
		return a;
	}

	public boolean isB() {
		return b;
	}
	
}
