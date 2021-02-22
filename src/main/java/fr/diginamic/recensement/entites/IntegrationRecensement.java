/**
 * 
 */
package fr.diginamic.recensement.entites;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ness'ti
 *
 */
public class IntegrationRecensement {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2 => demander au DriverManager de fournir une co à une bdd
		ResourceBundle props = ResourceBundle.getBundle("database");
		String url = props.getString("database.url");
		String user = props.getString("database.user");
		String password = props.getString("database.password");

		try {

			Connection connection = DriverManager.getConnection(url, user, password);

			// recup fichier recensement pour lecture
			Path path = Paths.get("C:/workspaceJava/java-poo-j6/src/main/resources/recensement.csv");

			List<String> linesRecensement = Files.readAllLines(path, StandardCharsets.UTF_8);

			// 3 => Création du statement
			Statement statement = null;

			for (int i = 1; i < linesRecensement.size(); i++) {

				String[] arr = linesRecensement.get(i).split(";");
				String codeRegion = arr[0];
				String nomRegion = arr[1];
				String codeDepartement = arr[2];
				String codeVille = arr[5];
				String nom = arr[6];
				int population = Integer.parseInt(arr[9].replaceAll(" ", ""));

				// requête SQL pour insérer des éléments dans les colonnes qui sont représentés
				// par des ? en attendant
				PreparedStatement prepStatement = connection.prepareStatement(
						"INSERT INTO villes(codeRegion, nomRegion, codeDepartement, codeVille, nom, population) VALUES (?,?,?,?,?,?)");

				// méthode 2 param int/String

				prepStatement.setString(1, codeRegion);
				prepStatement.setString(2, nomRegion);
				prepStatement.setString(3, codeDepartement);
				prepStatement.setString(4, codeVille);
				prepStatement.setString(5, nom);
				prepStatement.setInt(6, population);

				prepStatement.executeUpdate();

			}

			// fermeture du statement et connection (dans l'ordre inverse
			// d'ouverture)
			statement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Impossible de se connecter à la base de données" + e.getMessage());
		}
	}
}
