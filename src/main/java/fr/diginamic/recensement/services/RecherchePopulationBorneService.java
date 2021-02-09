package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws ExceptionMinimum {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		if (!NumberUtils.isCreatable(saisieMin) || saisieMin.contains(".")) {
			throw new ExceptionMinimum(
					"ATTENTION => Veuillez entrer un chiffre svp ! (le chiffre doit être supérieur à 0)");
		}

		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();
		if (!NumberUtils.isDigits(saisieMax)) {
			throw new ExceptionMinimum(
					"ATTENTION => Veuillez entrer un chiffre svp ! (le chiffre doit être supérieur à 0");
		}

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;

		if (min < 0) {
			throw new ExceptionMinimum("Vous devez saisir un chiffre positif svp !");
		}

		Boolean trouverUnDepartement = false;

		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				trouverUnDepartement = true;
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
		if (!trouverUnDepartement) {
			throw new ExceptionMinimum("Attention, le code département saisi n'est pas correct ! ");
		}
	}
}
