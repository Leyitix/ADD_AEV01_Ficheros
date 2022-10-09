package es.Florida.Evaluable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.Highlighter;

public class Controlador {

	private Vista vista;
	private Modelo modelo;
	private String ficheroEscritura;
	private String directorioSeleccionado, ficheroSeleccionado, itemSeleccionado;
	private JComboBox<String> comboDirectorios, comboFicheros, comboOpciones;
	private ActionListener actionListenerAceptar;
	private ActionListener actionListenerBuscar;
	private ActionListener actionListenerReemplazar;
	private String[] ficheros, directorios;

	/**
	 * Metodo constructor del Controlador
	 * 
	 * @param El modelo recibe los datos de los ficheros desde la clase modelo, la
	 *           vista recibe los elementos de la interfaz grafica, y el control()
	 *           ejecuta las funciones del controlador
	 */
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		control();
	}

	/**
	 * Metodo que recibe los eventos de los diferentes elementos de la interfaz y
	 * los textos introducidos en el textArea, he interactua determinados metodos
	 */
	private void control() {

		Highlighter highlighter = vista.getTextAreaOriginal().getHighlighter();

		directorios = modelo.mostrarDirectorios();
		vista.setComboBoxDirectorios(directorios);

		comboDirectorios = vista.getComboBoxDirectorios();
		comboDirectorios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				directorioSeleccionado = (String) comboDirectorios.getSelectedItem();
				setDirectorioSeleccionado(directorioSeleccionado);
				ficheros = modelo.mostrarFicheros(directorioSeleccionado);
				vista.setComboBoxFicheros(ficheros);
			}

		});

		comboFicheros = vista.getComboBoxFicheros();
		comboFicheros.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ficheroSeleccionado = (String) comboFicheros.getSelectedItem();
				if (ficheroSeleccionado != null) {
					mostrarFichero(ficheroSeleccionado, 1);
				}

			}

		});

		comboOpciones = vista.getComboBoxOpciones();
		comboOpciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				itemSeleccionado = (String) comboOpciones.getSelectedItem();
				setItemSeleccionado(itemSeleccionado);

			}
		});

		actionListenerAceptar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String textoTextAreaEscribir = vista.getTextAreaEscribir().getText();
				opcionesComboBox(textoTextAreaEscribir);
			}

		};

		actionListenerBuscar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String palabraBuscada = vista.getTextFieldBuscar().getText();
				modelo.buscarOcurrencias(directorioSeleccionado, ficheroSeleccionado, palabraBuscada, highlighter);
			}

		};

		actionListenerReemplazar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String textoReemplazar = vista.getTextFieldReemplazar().getText();
				String directorioSeleccionado = getDirectorioSeleccionado();
				String ficheroSeleccionado = getFicheroSeleccionado();
				modelo.reemplazarString(directorioSeleccionado, ficheroSeleccionado, textoReemplazar);
				String ficheroModificado = modelo.getFicheroEscritura();
				mostrarFichero(ficheroModificado, 2);
				resetComboBoxFicheros();
				vista.getTextFieldReemplazar().setText("");
				vista.getTextFieldBuscar().setText("");
			}
		};

		vista.getBtnAceptar().addActionListener(actionListenerAceptar);
		vista.getBtnBuscar().addActionListener(actionListenerBuscar);
		vista.getBtnReemplazar().addActionListener(actionListenerReemplazar);

	}

	/**
	 * Envia el fichero seleccionado y el directorio a dos metodos diferentes del
	 * modelo un para mostrat su contenido y otro para mostrar la informacion en
	 * diferentes textAreas
	 * 
	 * @param Recibe string ficheroSeleccionado, un int numeroTextArea
	 */
	protected void mostrarFichero(String ficheroSeleccionado, int numeroTextArea) {
		vista.getTextAreaOriginal().setText("");
		String directorioSeleccionado = getDirectorioSeleccionado();

		ArrayList<String> arrayLineas = modelo.contenidoFichero(directorioSeleccionado, ficheroSeleccionado);
		for (String linea : arrayLineas) {
			if (numeroTextArea == 1) {
				vista.getTextAreaOriginal().append(linea + "\n");
			} else if (numeroTextArea == 2) {
				vista.getTextAreaModificado().append(linea + "\n");
			}

		}

		ArrayList<String> info = modelo.infoFichero(directorioSeleccionado, ficheroSeleccionado);
		for (String linea : info) {
			vista.getTextAreaInfo().append(linea + "\n");
		}

	}

	/**
	 * Dependiendo de cual ha sido la opción seleccionada llamara a un determinado
	 * metodo
	 * 
	 * @param Recide String texto del textAreaAceptar
	 */
	protected void opcionesComboBox(String texto) {

		String itemSeleccionado = getItemSeleccionado();

		if (itemSeleccionado == "Limpiar") {
			vista.getTextAreaOriginal().setText("");
			vista.getTextAreaInfo().setText("");
			vista.getTextAreaModificado().setText("");

		} else if (itemSeleccionado == "Crear") {
			crearFichero(texto);

		} else if (itemSeleccionado == "Renombrar") {
			renombrarFichero(texto);

		} else if (itemSeleccionado == "Suprimir") {
			eliminarFichero();

		} else if (itemSeleccionado == "Copiar") {
			copiarFichero();

		} else if (itemSeleccionado == "Escribir") {
			escribirEnFichero(texto);

		}

	}

	/**
	 * Envía el directorio seleccionado y el fichero setea el fichero escritura con
	 * el string recibido del textArea Si no se ha seleccionado ningún directorio,
	 * saltará una adcertencia Comprueba que la extensión sea la adecuada para poder
	 * crear el fichero, si no lo es muestra un error
	 * 
	 * @param recibe el String textoTextAreaEscribir del textfield
	 */
	protected void crearFichero(String textoTextAreaEscribir) {

		String directorioSeleccionado = getDirectorioSeleccionado();
		setFicheroEscritura(textoTextAreaEscribir);
		ficheroEscritura = getFicheroEscritura();

		if (directorioSeleccionado != null && ficheroEscritura != "") {

			if (ficheroEscritura.contains(".txt")) {
				modelo.crearFichero(directorioSeleccionado, ficheroEscritura);
				resetComboBoxFicheros();
				vista.getTextAreaEscribir().setText(null);

			} else {
				JOptionPane.showMessageDialog(new JFrame(), "La extensión no es correcta", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (directorioSeleccionado == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Selecciona un directorio", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Envia el nombre del fichero original, el texto para renombrar el fichero y el
	 * directorio seleccionado al modelo, antes de enviar la informacion comprueba
	 * que el texto tenga la extension .txt y si no la encuentra lanza un mensaje de
	 * error, al terminar resetea el combobox de los ficheros
	 * 
	 * @param Recibe String textoTextAreaEscribir del textArea
	 */
	protected void renombrarFichero(String textoTextAreaEscribir) {
		String oldFile = getFicheroSeleccionado();
		String newFile = textoTextAreaEscribir;
		String directorioSeleccionado = getDirectorioSeleccionado();
		if (newFile.contains(".txt")) {
			modelo.renombrarFichero(oldFile, newFile, directorioSeleccionado);
			vista.getTextAreaEscribir().setText("");
			resetComboBoxFicheros();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "La extensión no es correcta", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Envia el fichero seleccionado a copiar y el directorio a la clase modelo, al
	 * terminar resetea el combobox de los ficheros
	 */
	protected void copiarFichero() {
		String ficheroACopiar = getFicheroSeleccionado();
		String directorioSeleccionado = getDirectorioSeleccionado();
		modelo.copiarFichero(directorioSeleccionado, ficheroACopiar);
		resetComboBoxFicheros();
	}

	/**
	 * Envia el fichero seleccionado a eliminar y el directorio a la clase modelo,
	 * al terminar resetea el combobox de los ficheros
	 */
	protected void eliminarFichero() {
		String ficheroSeleccionado = getFicheroSeleccionado();
		String directorioSeleccionado = getDirectorioSeleccionado();
		modelo.eliminarFichero(directorioSeleccionado, ficheroSeleccionado);
		resetComboBoxFicheros();
	}

	/**
	 * Envia el fichero seleccionado en el cual se quiere escribir, el directorio y
	 * el texto a insertar a la clase modelo, al terminar elimina el texto que se
	 * encuentre en la caja de texto escribir, al igual que vacia el resto de text
	 * area para mostrar la informacion actualizada
	 * 
	 * @param Recibe el String texto del textAreaEscribir
	 */
	protected void escribirEnFichero(String texto) {
		String directorioSeleccionado = getDirectorioSeleccionado();
		String ficheroSeleccionado = getFicheroSeleccionado();
		modelo.escribirEnFichero(directorioSeleccionado, ficheroSeleccionado, texto);
		vista.getTextAreaEscribir().setText("");
		vista.getTextAreaOriginal().setText("");
		vista.getTextAreaInfo().setText("");
		mostrarFichero(ficheroSeleccionado, 1);
	}

	/**
	 * Resetea el contenido del combobox de los ficheros para tener los elementos
	 * siempre acrualizados
	 */
	protected void resetComboBoxFicheros() {
		comboFicheros.removeAllItems();
		String[] ficherosActualizados = modelo.mostrarFicheros(directorioSeleccionado);
		vista.setComboBoxFicheros(ficherosActualizados);
	}

	/**
	 * Get de la clase vista
	 * 
	 * @return Retorna la clase vista para acceder a sus metodos
	 */
	public Vista getVista() {
		return vista;
	}

	/**
	 * Get de la clase modelo
	 * 
	 * @return Retorna la clase modelo para acceder a sus metodos
	 */
	public Modelo getModelo() {
		return modelo;
	}

	/**
	 * Get del fichero escritura
	 * 
	 * @return Retorna lel fichero escritura
	 */
	public String getFicheroEscritura() {
		return ficheroEscritura;
	}

	/**
	 * Set del fichero escritura *
	 */
	public void setFicheroEscritura(String ficheroEscritura) {
		this.ficheroEscritura = ficheroEscritura;
	}

	/**
	 * Get del directorio seleccionado del comboBox de los directorios
	 * 
	 * @return Retorna el directorio seleccionado
	 */
	public String getDirectorioSeleccionado() {
		return directorioSeleccionado;
	}

	/**
	 * Get del fichero seleccionado del comboBox de los ficheros
	 * 
	 * @return Retorna lel fichero escritura
	 */
	public String getFicheroSeleccionado() {
		return ficheroSeleccionado;
	}

	/**
	 * Set del directorio seleccionado del comboBox de los directorios
	 */
	public void setDirectorioSeleccionado(String directorio) {
		this.directorioSeleccionado = directorio;
	}

	/**
	 * Get del item seleccionado del comboBox de las opciones
	 * 
	 * @return Retorna item seleccionado
	 */
	public String getItemSeleccionado() {
		return itemSeleccionado;
	}

	/**
	 * Set del item seleccionado del comboBox de las opciones
	 */
	public void setItemSeleccionado(String itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

	/**
	 * Get de los ficheros
	 * 
	 * @return Retorna los ficheros del directorio seleccionado
	 */
	public String[] getFicheros() {
		return ficheros;
	}

	/**
	 * Set de los ficheros
	 */
	public void setFicheros(String[] ficheros) {
		this.ficheros = ficheros;
	}

}
