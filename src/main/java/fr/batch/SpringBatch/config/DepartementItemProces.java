package fr.batch.SpringBatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import fr.batch.SpringBatch.model.Departement;

public class DepartementItemProces implements ItemProcessor<Departement, Departement>{

	private static final Logger log = LoggerFactory.getLogger(DepartementItemProces.class);
	@Override
	public Departement process(Departement departement) throws Exception {
		final String code = departement.getCode().toUpperCase();
		final String nom = departement.getNom().toUpperCase();
		final Departement transfo = new Departement(code,nom);
		log.info(" Ca donne " + departement + " ceci "+ transfo);
		return transfo;
	}

}
