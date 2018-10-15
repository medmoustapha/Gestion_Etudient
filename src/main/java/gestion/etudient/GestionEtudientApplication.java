package gestion.etudient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ch.qos.logback.core.net.SyslogOutputStream;
import gestion.etudient.dao.EtudiantRepository;
import gestion.etudient.entities.Etudiant;

@SpringBootApplication
public class GestionEtudientApplication {

	public static void main(String[] args) throws ParseException {
		org.springframework.context.ApplicationContext ctx = SpringApplication.run(GestionEtudientApplication.class, args);
		EtudiantRepository etudiantRepository=ctx.getBean(EtudiantRepository.class);
		/*DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		etudiantRepository.save(new Etudiant("Mohamed", df.parse("1991-12-31"), "medmostapha@gmail.com", "mohamed.jpg"));
		etudiantRepository.save(new Etudiant("Ahmed", df.parse("1991-12-31"), "medmostapha@gmail.com", "ahmed.jpg"));
		etudiantRepository.save(new Etudiant("Moustapha", df.parse("1991-12-31"), "medmostapha@gmail.com", "moustapha.jpg"));
		
		Page<Etudiant> etds=etudiantRepository.chercherEtudiants("%p%",new PageRequest(0, 5));
		etds.forEach(e->System.out.println(e.getNom()));*/
	}
}
