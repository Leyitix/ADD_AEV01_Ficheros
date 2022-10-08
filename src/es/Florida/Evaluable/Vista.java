package es.Florida.Evaluable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Vista {

	private JFrame frame;
	private JTextField textField_Buscar;
	private JTextField textField_Reemplazar;
	private JButton btnBuscar, btnReemplazar;
	private JTextArea textArea_Original, textArea_info;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBox_directorios, comboBox_ficheros, comboBox_opciones;
	private JTextArea textArea_Modificado;
	private JScrollPane scrollPane_1;
	JButton btnAceptar;
	private JTextArea textAreaEscibir;
	private JScrollPane scrollPane_3;

	/**
	 * Método constructor de la clase Vista Se inicializan todos los elementos de la
	 * interfaz gráfica
	 */
	public Vista() {
		initialize();
	}

	/**
	 * Método en el que se recogen todos los elementos de la interfaz
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 792, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField_Buscar = new JTextField();
		textField_Buscar.setBounds(25, 561, 226, 27);
		frame.getContentPane().add(textField_Buscar);
		textField_Buscar.setColumns(10);

		textField_Reemplazar = new JTextField();
		textField_Reemplazar.setColumns(10);
		textField_Reemplazar.setBounds(401, 561, 226, 27);
		frame.getContentPane().add(textField_Reemplazar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscar.setBounds(261, 560, 120, 27);
		frame.getContentPane().add(btnBuscar);

		btnReemplazar = new JButton("Reemplazar");
		btnReemplazar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnReemplazar.setBounds(637, 560, 120, 27);
		frame.getContentPane().add(btnReemplazar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(651, 281, 106, 23);
		frame.getContentPane().add(btnAceptar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 330, 356, 220);
		frame.getContentPane().add(scrollPane);

		textArea_Original = new JTextArea();
		scrollPane.setViewportView(textArea_Original);
		textArea_Original.setLineWrap(true);
		textArea_Original.setRows(12);

		comboBox_directorios = new JComboBox<String>();
		comboBox_directorios.setBounds(469, 44, 288, 22);
		frame.getContentPane().add(comboBox_directorios);

		comboBox_ficheros = new JComboBox<String>();
		comboBox_ficheros.setBounds(469, 92, 288, 22);
		frame.getContentPane().add(comboBox_ficheros);

		comboBox_opciones = new JComboBox<String>();
		comboBox_opciones.setBounds(651, 138, 106, 22);
		frame.getContentPane().add(comboBox_opciones);
		setComboBoxOpciones();

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(401, 330, 356, 220);
		frame.getContentPane().add(scrollPane_1);

		textArea_Modificado = new JTextArea();
		scrollPane_1.setViewportView(textArea_Modificado);
		textArea_Modificado.setRows(12);
		textArea_Modificado.setLineWrap(true);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(25, 42, 356, 224);
		frame.getContentPane().add(scrollPane_2);
		
				textArea_info = new JTextArea();
				scrollPane_2.setViewportView(textArea_info);
				textArea_info.setLineWrap(true);
				textArea_info.setRows(12);

		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(469, 177, 288, 89);
		frame.getContentPane().add(scrollPane_3);

		textAreaEscibir = new JTextArea();
		scrollPane_3.setViewportView(textAreaEscibir);
		
		JLabel lblNewLabel = new JLabel("Directorios");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(692, 29, 65, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblFicheros = new JLabel("Ficheros");
		lblFicheros.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFicheros.setBounds(701, 79, 56, 14);
		frame.getContentPane().add(lblFicheros);
		
		JLabel lblOpciones = new JLabel("Opciones");
		lblOpciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOpciones.setBounds(701, 125, 56, 14);
		frame.getContentPane().add(lblOpciones);

		this.frame.setVisible(true);
	}

	/**
	 * get del botón buscar
	 * @return retorna el botónBuscar
	 * */
	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	/**
	 * get del botón reemplazar
	 * @return retorna el botónReemplazar
	 * */
	public JButton getBtnReemplazar() {
		return btnReemplazar;
	}

	/**
	 * get del botón aceptar
	 * @return retorna el botónAceptar
	 * */
	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	/**
	 * get del textField buscar
	 * @return retorna el textFieldBuscar
	 * */
	public JTextField getTextFieldBuscar() {
		return textField_Buscar;
	}

	/**
	 * get del textField reemplazar
	 * @return retorna el textFielReemplazar
	 * */
	public JTextField getTextFieldReemplazar() {
		return textField_Reemplazar;
	}

	/**
	 * get del textArea original
	 * @return retorna el textAreaOriginal
	 * */
	public JTextArea getTextAreaOriginal() {
		return textArea_Original;
	}

	/**
	 * get del textArea info
	 * @return retorna el textAreaInfo
	 * */
	public JTextArea getTextAreaInfo() {
		return textArea_info;
	}

	/**
	 * get del textArea escribir
	 * @return retorna el textAreaEscribir
	 * */
	public JTextArea getTextAreaEscribir() {
		return textAreaEscibir;
	}

	/**
	 * get del textArea modificado
	 * @return retorna el textAreaModificado
	 * */
	public JTextArea getTextAreaModificado() {
		return textArea_Modificado;
	}

	/**
	 * get del combobox de los directorios
	 * @return retorna el combobox
	 * */
	public JComboBox<String> getComboBoxDirectorios() {
		return comboBox_directorios;
	}

	/**
	 * setea el contenido del combobox de los directorios
	 * */
	public void setComboBoxDirectorios(String[] directorios) {

		JComboBox<String> combo = getComboBoxDirectorios();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (String directorio : directorios) {
			model.addElement(directorio);
		}
		combo.setModel(model);
	}

	/**
	 * setea el contenido del combobox de los ficheros
	 * */
	public void setComboBoxFicheros(String[] ficheros) {

		JComboBox<String> combo = getComboBoxFicheros();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (String fichero : ficheros) {
			model.addElement(fichero);
		}
		combo.setModel(model);
	}

	/**
	 * get del combobox de los ficheros
	 * @return retorna el combobox
	 * */
	public JComboBox<String> getComboBoxFicheros() {
		return comboBox_ficheros;
	}

	/**
	 * setea el contenido del combobox de las opciones
	 * */
	public void setComboBoxOpciones() {

		JComboBox<String> combo = getComboBoxOpciones();

		combo.addItem("");
		combo.addItem("Crear");
		combo.addItem("Renombrar");
		combo.addItem("Copiar");
		combo.addItem("Suprimir");
		combo.addItem("Escribir");
		combo.addItem("Limpiar");

	}

	/**
	 * get del combobox de las opciones
	 * @return retorna el combobox
	 * */
	public JComboBox<String> getComboBoxOpciones() {
		return comboBox_opciones;
	}
}
