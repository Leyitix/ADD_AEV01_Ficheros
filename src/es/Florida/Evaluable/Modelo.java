package es.Florida.Evaluable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.awt.Color;

public class Modelo {

	private String ficheroLectura, ficheroEscritura, palabraBuscada;

	/**
	 * Metodo constructor del modelo que inicializa el ficheroLectura y el
	 * ficheroEscritura
	 */
	public Modelo() {
		ficheroLectura = "";
		ficheroEscritura = "";
	}

	/**
	 * Metodo que lista el contenido del directorio al que tiene acceso el usuario
	 * 
	 * @return Retorna el listado de directorios que se mostraran el en combobox de
	 *         los directorios
	 */
	public String[] mostrarDirectorios() {

		File directorios_de_trabajo = new File("./directorios_de_trabajo");
		String[] directorios = directorios_de_trabajo.list();
		return directorios;

	}

	/**
	 * Metodo que lista el contenido del directorio seleccionado del combobox
	 * 
	 * @param Recibe el String directorioSeleccionado del controlador
	 * @return Retorna el listado de ficheros que se encuentran en el directorio
	 *         seleccionado
	 */
	public String[] mostrarFicheros(String directorioSeleccionado) {

		File directorio_de_recetas = new File("./directorios_de_trabajo/" + directorioSeleccionado);
		String[] listarFicheros = directorio_de_recetas.list();
		return listarFicheros;

	}

	/**
	 * Metodo lee el contenido del fichero seleccionado
	 * 
	 * @param Reecibe el String directorio y el String fichero dede el controlador
	 * @return Retorna ArrayList<String> contenidoFichero para poder mostratlo en el
	 *         textArea
	 */
	public ArrayList<String> contenidoFichero(String directorio, String fichero) {

		ArrayList<String> contenidoFichero = new ArrayList<String>();
		File f = new File("./directorios_de_trabajo/" + directorio + "/" + fichero);

		try {

			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String linea = br.readLine();
			while (linea != null) {
				contenidoFichero.add(linea);
				linea = br.readLine();
			}

			br.close();
			fr.close();

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contenidoFichero;

	}

	/**
	 * Lista una serie de caracteristicas del fichero recibido desde el controlador
	 * 
	 * @param Recibe el String directorio y el String fichero dede el controlador
	 * @return Retorna ArrayList<String> info para mostrar las caracteristicas del
	 *         fichero en un textArea
	 */
	public ArrayList<String> infoFichero(String directorio, String fichero) {

		File f = new File("./directorios_de_trabajo/" + directorio + "/" + fichero);
		ArrayList<String> info = new ArrayList<String>();

		info.add("  Permiso de ejecución: " + f.canExecute());
		info.add("  Permiso de lectura: " + f.canRead());
		info.add("  Permiso de escritura; " + f.canWrite());
		info.add("  Ruta absoluta: " + f.getAbsolutePath());
		info.add("  Espacio libre: " + f.getFreeSpace() / 1024 / 1024 / 1024 + " bytes");
		info.add("  Espacio total: " + f.getTotalSpace() / 1024 / 1024 / 1024 + " bytes");
		info.add("  Padre: " + f.getParent());
		info.add("  Nombre: " + f.getName());
		info.add("  Absoluto: " + f.isAbsolute());
		info.add("  Directorio: " + f.isDirectory());
		info.add("  Fichero: " + f.isFile());
		info.add("  Ultima modificación: " + new Date(f.lastModified()));

		return info;
	}

	/**
	 * Lista una serie de caracteriasticas del fichero recibido desde el controlador
	 * Si el fichero existe lanzara un error, en caso contrario lo creara
	 * 
	 * @param recibe el String directorio y el String ficheroEscritura
	 */
	public void crearFichero(String directorio, String ficheroEscritura) {

		File fichero = new File("./directorios_de_trabajo/" + directorio + "/" + ficheroEscritura);

		if (fichero.exists()) {
			JOptionPane.showMessageDialog(new JFrame(), "El fichero ya existe", "ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			try {

				fichero.createNewFile();
				JOptionPane.showMessageDialog(new JFrame(), "Fichero creado con éxito", "",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "No se puede crear el fichero", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	/**
	 * Renombra el nombre de un determinado fichero llama a un metodo ventana de
	 * confirmacion que genera un popup, si el usuario acepta se renombra el
	 * fichero, en caso contrario se cancela la accion
	 * 
	 * @param Recibe el String oldFile que contiene el nombre del fichero original,
	 *               el String newFile con el nuevo nombre del .txt y el directorio
	 *               seleccionado
	 * @return Retorna un null
	 */
	public String renombrarFichero(String oldFile, String newFile, String directorio) {

		String confirmacion = ventanaConfirmacion("renombrarFichero");

		if (confirmacion == "si") {
			File oldfile = new File("./directorios_de_trabajo/" + directorio + "/" + oldFile);
			File newfile = new File("./directorios_de_trabajo/" + directorio + "/" + newFile);

			if (oldfile.renameTo(newfile)) {
				JOptionPane.showMessageDialog(new JFrame(), "Fichero renombrado con éxito", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "No se puede renombrar el fichero", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

			return newFile;

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No se ha renombrado el fichero", "",
					JOptionPane.INFORMATION_MESSAGE);
		}

		return null;

	}

	/**
	 * Metodo que se utiliza para generar la copia de un fichero Añade la extension
	 * _copia.txt a un nuevo fichero en el que copia el contienido del original si
	 * el resultado es exitoso lanza un popup informativo, en caso contrario muestra
	 * un error
	 * 
	 * @param Recibe String directorio y el String fichero
	 */
	public void copiarFichero(String directorio, String fichero) {

		try {

			if (fichero.contains(".txt")) {
				String nombreCopia = fichero.replace(".txt", "_copia.txt");
				String copiaDesde = "./directorios_de_trabajo/" + directorio + "/" + fichero;
				String copiaHasta = "./directorios_de_trabajo/" + directorio + "/" + nombreCopia;
				File origen = new File(copiaDesde);
				File destino = new File(copiaHasta);
				InputStream in = new FileInputStream(origen);
				OutputStream out = new FileOutputStream(destino);

				byte[] buf = new byte[1024];
				int len;

				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				JOptionPane.showMessageDialog(new JFrame(), "Fichero copiado con éxito", "",
						JOptionPane.INFORMATION_MESSAGE);

				in.close();
				out.close();

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se puede copiar el fichero", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Metodo que elimina un determinado fichero, mostrara un mensaje dependiendo
	 * del resultado
	 * 
	 * @param recibe String directorio y el String fichero
	 */
	public void eliminarFichero(String directorio, String fichero) {

		String confirmacion = ventanaConfirmacion("suprimirFichero");

		if (confirmacion == "si") {
			File f = new File("./directorios_de_trabajo/" + directorio + "/" + fichero);
			if (f.delete()) {
				JOptionPane.showMessageDialog(new JFrame(), "Fichero eliminado con éxito", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "No se puede eliminar el fichero", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No se ha eliminado el fichero", "",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Metodo que inserta un texto en un fichero seleccionado, mostrara un mensaje
	 * dependiendo del resultado
	 * 
	 * @param Recibe String directorio, el String fichero y el texto del TextArea
	 */
	public void escribirEnFichero(String directorio, String fichero, String textoTextArea) {

		try {
			String ruta = ("./directorios_de_trabajo/" + directorio + "/" + fichero);
			String anyadirTexto = null;

			FileReader fr = new FileReader(ruta);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(ruta, true);

			String linea = br.readLine();
			if (linea != null) {
				anyadirTexto = "\r\n" + textoTextArea;
				fw.write(anyadirTexto);
			} else {
				anyadirTexto = textoTextArea;
				fw.write(anyadirTexto);
			}

			JOptionPane.showMessageDialog(new JFrame(), "Texto insertado con éxito", "",
					JOptionPane.INFORMATION_MESSAGE);
			fw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se puede escribir en el fichero", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Metodo que busca ocurrencias dentro de un determinado fichero mediante el
	 * metodo indexOf, cuenta el numero de veces que la palabra aparece en el texto,
	 * mediante un if controlo e mensaje que se muestra en el popup dependiendo del
	 * numero de ocurrencias, mostrara un mensaje dependiendo del resultado
	 * 
	 * @param Recibe String directorio, el String fichero, el String texto a buscar
	 *               la clase Highlighter para subrayar las palabras encontradas
	 */
	public void buscarOcurrencias(String directorio, String fichero, String texto, Highlighter highlighter) {

		File f = new File("./directorios_de_trabajo/" + directorio + "/" + fichero);

		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);

		int contador = 0;
		setPalabraBuscada(texto);

		try {

			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String linea = br.readLine();

			while (linea != null) {

				while (linea.indexOf(palabraBuscada) > -1) {

					int primerIndex = linea.indexOf(palabraBuscada);
					int ultimoIndex = primerIndex + palabraBuscada.length();

					linea = linea.substring(linea.indexOf(palabraBuscada) + palabraBuscada.length(), linea.length());

					highlighter.addHighlight(primerIndex, ultimoIndex, painter);

					contador++;

				}

				linea = br.readLine();

			}

			fr.close();
			br.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se pueden encontrar ocurrencias", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

		String resultado;
		if (contador == 0) {
			resultado = "No se encuentran coincidencias con " + "'" + texto + "'";
		} else if (contador == 1) {
			resultado = "'" + texto + "'" + " aparece " + contador + " vez";
		} else {
			resultado = "'" + texto + "'" + " aparece " + contador + " veces";
		}

		JOptionPane.showMessageDialog(new JFrame(), resultado, "", JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * Metodo con el que se reemplazara el nombre del fichero seleccionado, llamara
	 * al metodo ventana de confirmacion en el que se controlara si se quiere
	 * sobreescribir el fichero o crear una copia, mostrara un mensaje dependiendo
	 * del resultado
	 * 
	 * @param Recibe como parametro el String del directorio, del fichero
	 *               seleccionado y del texto a reemplazar
	 */
	public void reemplazarString(String directorio, String fichero, String textoReemplazar) {

		String palabraBuscada = getPalabraBuscada();
		String confirmacion = ventanaConfirmacion("reemplazarString");
		String ficheroLectura = "./directorios_de_trabajo/" + directorio + "/" + fichero;
		String nombreFicheroEscritura = "";
		File fL = new File(ficheroLectura);
		File ficheroEscritura;

		if (ficheroLectura.contains(".txt")) {
			nombreFicheroEscritura = fichero.replace(".txt", "_copia.txt");
		}

		String ruta = "./directorios_de_trabajo/" + directorio + "/" + nombreFicheroEscritura;
		ficheroEscritura = new File(ruta);

		try {
			FileWriter fw = new FileWriter(ficheroEscritura);
			BufferedWriter bw = new BufferedWriter(fw);
			FileReader fr = new FileReader(ficheroLectura);
			BufferedReader br = new BufferedReader(fr);

			String linea = br.readLine();
			String newString = "";

			while (linea != null) {

				if (linea.contains(palabraBuscada)) {
					String oldString = linea;
					newString = oldString.replace(palabraBuscada, textoReemplazar);
					bw.write(newString);
					bw.newLine();
					linea = br.readLine();

				} else {

					bw.write(linea);
					bw.newLine();
					linea = br.readLine();

				}

			}

			br.close();
			fr.close();
			bw.close();
			fw.close();

			if (confirmacion == "si") {

				fL.delete();
				if (nombreFicheroEscritura.contains("_copia.txt")) {
					File oldFile = new File("./directorios_de_trabajo/" + directorio + "/" + nombreFicheroEscritura);
					nombreFicheroEscritura = fichero.replace("_copia.", ".");

					File newFile = new File("./directorios_de_trabajo/" + directorio + "/" + nombreFicheroEscritura);
					oldFile.renameTo(newFile);

					setFicheroEscritura(nombreFicheroEscritura);
				}

			} else if (confirmacion == "no") {
				setFicheroEscritura(nombreFicheroEscritura);
			}

			JOptionPane.showMessageDialog(new JFrame(), "Palabra reemplazada con éxito", "",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se puede reemplazar la palabra", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Con este metodo se genera una ventana de confirmacion con opciones si o no,
	 * el si retornara un 0 y el no un 1
	 * 
	 * @param Recibe el nombre del metodo que hace la llamada a la ventana de
	 *               confirmacion
	 * @return Retorna la informacion de si el usuario a pulsado en si o en no
	 */
	public String ventanaConfirmacion(String metodo) {

		String confirmacion = null;
		int option = 0;

		if (metodo == "reemplazarString") {
			option = JOptionPane.showConfirmDialog(null, "Deseas reescribir el fichero?", "Confirmar salida",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		} else if (metodo == "renombrarFichero") {
			option = JOptionPane.showConfirmDialog(null, "Deseas renombrar el fichero?", "Confirmar salida",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		} else if (metodo == "suprimirFichero") {
			option = JOptionPane.showConfirmDialog(null, "Deseas suprimir el fichero??", "Confirmar salida",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}

		if (option == 0) {
			confirmacion = "si";

		} else if (option == 1) {
			confirmacion = "no";

		}

		return confirmacion;
	}

	/**
	 * Get del ficheroLectura
	 * 
	 * @return Del fichero lectura
	 */
	public String getFicheroLectura() {
		return ficheroLectura;
	}

	/**
	 * Set del ficheroLectura
	 */
	public void setFicheroLectura(String ficheroLectura) {
		this.ficheroLectura = ficheroLectura;
	}

	/**
	 * Get del ficheroEscritura
	 * 
	 * @return Del fichero escritura
	 */
	public String getFicheroEscritura() {
		return ficheroEscritura;
	}

	/**
	 * Set del ficheroEscritura
	 */
	public void setFicheroEscritura(String ficheroEscritura) {
		this.ficheroEscritura = ficheroEscritura;
	}

	/**
	 * Get de la palabra buscada
	 * 
	 * @return De la palabra buscada
	 */
	public String getPalabraBuscada() {
		return palabraBuscada;
	}

	/**
	 * Set de la palabra buscada
	 */
	public void setPalabraBuscada(String palabraBuscada) {
		this.palabraBuscada = palabraBuscada;
	}
}
