import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import exceptions.*;

/**
 * @version 1.0 2018.04.10
 * @author Anton Shchesnovich 
 */
public class CiperGUI extends JFrame{
	
	/**
	 * text fields size
	 */
	private static final int TEXT_AREAS_HEIGHT = 20;
	private static final int TEXT_AREAS_WIDTH = 35;
	private static final int CODEWORD_FIELD_WIDTH = 15;
	
	/**
	 * need for choose work mode
	 * false - cipher
	 * true - decipher
	 */
	private boolean isEncrypted;
	
	/**
	 * contains cipher/decipher logic
	 */
	private VigenereCipher vCipher = new VigenereCipher();
	
	/**
	 * text fields for work
	 */
	private JTextField codewordField;
	private JTextArea inputTextArea;
	private JTextArea outputTextArea;
	
	/**
	 * create frame
	 * set settings for frame
	 * fill frame by neccessary elements
	 */
	public CiperGUI() {

		// set settings for frame
		setLocationByPlatform(true);
		setTitle("Vigenere ciper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		// add elements in frame
		GridBagConstraints c = new GridBagConstraints();
		c.gridy += 1;
		add(createRadioButtonsPanel(), c);
		c.gridy += 1;
		add(createCodewordPanel(), c);
		c.gridy += 1;
		add(createLabelsPanel(), c);
		c.gridy += 1;
		add(createTextAreasPanel(), c);
		c.gridy += 1;
		add(createStartButtonPanel(), c);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args){
		new CiperGUI();
	}
	
	/**
	 * two RadioButtons sets flag isEncrypted
	 */
	private JPanel createRadioButtonsPanel() {
		ButtonGroup buttonsForChangeMode = new ButtonGroup();
		JRadioButton cipherModeButton = new JRadioButton("cipher", true);
		JRadioButton decipherModeButton = new JRadioButton("decipher", false);
		ActionListener cipherListener = event -> isEncrypted = false;
		ActionListener decipherListener = event -> isEncrypted = true;
		cipherModeButton.addActionListener(cipherListener);
		decipherModeButton.addActionListener(decipherListener);
		buttonsForChangeMode.add(cipherModeButton);
		buttonsForChangeMode.add(decipherModeButton);
		JPanel panelForRadioButtons = new JPanel();
		panelForRadioButtons.add(cipherModeButton);
		panelForRadioButtons.add(decipherModeButton);	
		return panelForRadioButtons;
	}
	
	/**
	 * create field for input codeword
	 */
	private JPanel createCodewordPanel() {
		JPanel codewordPanel = new JPanel();
		codewordPanel.add(new JLabel("codeword:"));
		codewordField = new JTextField(CODEWORD_FIELD_WIDTH);
		codewordPanel.add(codewordField);
		return codewordPanel;
	}
	
	private JPanel createLabelsPanel() {
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new GridLayout(1, 2));
		labelsPanel.add(new JLabel("Write you text here:                                                                                          ", SwingConstants.LEFT));
		labelsPanel.add(new JLabel("   Result:", SwingConstants.LEFT));
		return labelsPanel;
	}
	
	/**
	 * create two text areas for input/output text
	 */
	private JPanel createTextAreasPanel() {
		JPanel panelForTextAreas = new JPanel();
		inputTextArea = new JTextArea(TEXT_AREAS_HEIGHT, TEXT_AREAS_WIDTH);
		inputTextArea.setLineWrap(true);
		JScrollPane inScroll = new JScrollPane(inputTextArea);
		outputTextArea = new JTextArea(TEXT_AREAS_HEIGHT, TEXT_AREAS_WIDTH);
		inputTextArea.setLineWrap(true);
		JScrollPane outScroll = new JScrollPane(outputTextArea);
		panelForTextAreas.add(inScroll);
		panelForTextAreas.add(outScroll);
		return panelForTextAreas;
	}
	
	/**
	 * button "Start" starts cipher/decipher process
	 * catched exceptions connected with illegal or empty fields
	 * showed warning window if it's nessessary
	 */
	private JPanel createStartButtonPanel() {
		JPanel panelForStartButton = new JPanel();
		JButton startButton = new JButton("Start");
		ActionListener startListener = event -> {
			vCipher.setCodeword(codewordField.getText());
			vCipher.setText(inputTextArea.getText());
			try {
				if(isEncrypted) {
					outputTextArea.setText(vCipher.decipher());
				}
				else {
					outputTextArea.setText(vCipher.cipher());
				}
			}catch(IllegalCodewordException ICE) {
				JOptionPane.showMessageDialog(null, "Please, use only Latin letters in codeword", "Illegal codeword", JOptionPane.WARNING_MESSAGE);
			}catch(IllegalTextException ITE) {
				JOptionPane.showMessageDialog(null, "Please, use only Latin letters in text", "Illegal text", JOptionPane.WARNING_MESSAGE);
			}catch(NullCodewordException NCE) {
				JOptionPane.showMessageDialog(null, "Please, write in codeword field", "Null codeword", JOptionPane.WARNING_MESSAGE);
			}catch(NullTextException NTE) {
				JOptionPane.showMessageDialog(null, "Please, write in text field", "Null text", JOptionPane.WARNING_MESSAGE);
			}
		};
		startButton.addActionListener(startListener);
		panelForStartButton.add(startButton);
		return panelForStartButton;
	}

}
