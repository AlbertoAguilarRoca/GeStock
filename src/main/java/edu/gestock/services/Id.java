package edu.gestock.services;

/**
 * 
 * @author Alberto Aguilar & Juan Villegas
 * Generador de identificadores aleatorios para la base de datos.
 */
public class Id {

	/**
	 * Genera identidicadores aleatorios para cada pila
	 * @param 
	 * @return Retorna un identificador aleatorio
	 */
	public static String generator(int min, int max) {
		int nCaracteres = numeroAleatorio(min, max); //total de caracteres del id
		String id = "";
		for(int i = 1; i <= nCaracteres; i++) {
			int caracter = numeroAleatorio(48, 122);
			//excluimos los caracteres que no nos interesan
			while(caracter >= 58 && caracter <= 64 || caracter >= 91 && caracter <= 96) {
				caracter = numeroAleatorio(48, 122);
			}			
			char letra  = (char) caracter;
			id = id + letra;
		}
		return id;
	}
	
	/*Genera un numero aleatorio entre dos número*/
	private static int numeroAleatorio(int min, int max) {
		double numero = Math.floor(Math.random() * (max-min)) +min;
		return (int) numero;
	}
	
}
