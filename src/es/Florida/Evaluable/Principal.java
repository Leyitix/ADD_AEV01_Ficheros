package es.Florida.Evaluable;

public class Principal {

	/**
	 * Clase principal desde la que se inicializan el resto de clases
	 * */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Vista vista = new Vista();
		Modelo modelo = new Modelo();
		Controlador controlador = new Controlador(modelo, vista);

	}

}
