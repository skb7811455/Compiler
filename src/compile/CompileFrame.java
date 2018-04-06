package compile;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class CompileFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	private String s;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompileFrame frame = new CompileFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public String sendString(){
		return  s;
	}
	public CompileFrame() {
		Shiyan1 compiler=new Shiyan1();
		Shiyan2 compiler1=new Shiyan2();
		Shiyan3 compiler2=new Shiyan3();
		Shiyan4 compiler3=new Shiyan4();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1350, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLocation(201, 9);
		panel.setSize(810, 56);
		contentPane.add(panel, null);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("词法分析");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton btnNewButton1 = new JButton("LL语法分析");
		btnNewButton1.setBackground(Color.LIGHT_GRAY);
		JButton btnNewButton2 = new JButton("算符分析");
		btnNewButton2.setBackground(Color.LIGHT_GRAY);
		JButton btnNewButton3 = new JButton("LR语法分析");
		btnNewButton3.setBackground(Color.LIGHT_GRAY);
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		panel.add(btnNewButton);
		panel.add(btnNewButton1);
		panel.add(btnNewButton2);
		panel.add(btnNewButton3);
		
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 17));
		textArea_1.setBackground(Color.WHITE);
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 17));
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setFont(new Font("Monospaced", Font.PLAIN, 12));
	
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setFont(new Font("Monospaced", Font.PLAIN, 12));
	
		JScrollPane scrollPane = new JScrollPane(textArea);
		JScrollPane scrollPane_1 = new JScrollPane(textArea_1);
		JScrollPane scrollPane_2 = new JScrollPane(textArea_2);
		JScrollPane scrollPane_3 = new JScrollPane(textArea_3);
		JScrollPane scrollPane_4 = new JScrollPane(textArea_4);
		scrollPane.setBounds(20, 100, 300, 650);
		scrollPane_1.setBounds(362, 100, 325, 650);
		scrollPane_2.setBounds(722, 100, 238, 650);
		scrollPane_3.setBounds(986, 100, 158, 648);
		scrollPane_4.setBounds(1166, 100, 158, 648);
		contentPane.add(scrollPane, null);
		contentPane.add(scrollPane_1, null);
		contentPane.add(scrollPane_2, null);
		contentPane.add(scrollPane_3, null);
		contentPane.add(scrollPane_4, null);
		
		JLabel label = new JLabel("\u8F93\u5165\u533A");
		label.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 17));
		label.setBounds(133, 75, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u7B26\u53F7\u6808");
		label_1.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 17));
		label_1.setBounds(1031, 75, 83, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u5206\u6790\u52A8\u4F5C");
		label_2.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 17));
		label_2.setBounds(1172, 75, 100, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u72B6\u6001\u6808");
		label_3.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 17));
		label_3.setBounds(853, 75, 54, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u8F93\u51FA\u533A");
		label_4.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 17));
		label_4.setBounds(527, 75, 54, 15);
		contentPane.add(label_4);
		
		
		
		
		
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value=textArea.getText();
				//System.out.println(value);
				String output=compiler.Compile(value);	
				
				//System.out.println(output);
				textArea_1.setText(output);			
			}
		});
		
		btnNewButton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value=textArea.getText();
				//System.out.println(value);
				String output=compiler.Compile(value);
				String output1;
				output1=compiler1.Compile(compiler.SendTokens());
				//System.out.println(output1);
				textArea_1.setText(output1);			
			}
		});
		
		btnNewButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value=textArea.getText();
				//System.out.println(value);
				String output=compiler.Compile(value);
				String output2;
				output2=compiler2.analyze(compiler.SendTokens(),compiler.SendNumbers());
				//System.out.println(output2);
				textArea_1.setText(output2);			
			}
		});
		
		btnNewButton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value=textArea.getText();
				//System.out.println(value);
				String output=compiler.Compile(value);
				String []output3;
				output3=compiler3.analyze(compiler.SendTokens());
				textArea_2.setText(output3[0]);
				textArea_3.setText(output3[1]);
				textArea_4.setText(output3[2]);
			}
		});
	}
}
