package fr.diginamic.recensement.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;

/**
 * Recherche et affichage de la population d'une ville
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationVilleService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) {

		System.out.println("Quel est le nom de la ville recherchée ? ");
		String choix = scanner.nextLine();

		// 2 => demander au DriverManager de fournir une co à une bdd
		ResourceBundle props = ResourceBundle.getBundle("database");
		String url = props.getString("database.url");
		String user = props.getString("database.user");
		String password = props.getString("database.password");

		try {

			Connection connection;
			connection = DriverManager.getConnection(url, user, password);

			Statement statement = connection.createStatement();

			// récupération du nombre d'habitants suite à l'entrée du nom de la ville par
			// l'utilisateur
			ResultSet curseur = statement.executeQuery("SELECT * FROM villes WHERE nom='" + choix + "'");
			if (curseur.next()) {
				int population = curseur.getInt("population");
				System.out.println("La population de la ville " + choix + " est égale à " + population);
			}
			curseur.close();
			statement.close();
			connection.close();

		} catch (SQLException e1) {
			System.err.println("Une erreur s'est produite lors de la recherche de la ville " + choix);
		}
	}
}