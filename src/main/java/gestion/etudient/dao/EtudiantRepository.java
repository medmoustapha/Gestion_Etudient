package gestion.etudient.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gestion.etudient.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{
	
	List<Etudiant> findByNom(String nom);

	Page<Etudiant> findByNom(String nom,Pageable pageable);
	@Query("select e from Etudiant e where e.nom like :x")
	Page<Etudiant> chercherEtudiants(@Param("x")String mc,Pageable pageable);
	@Query("select e from Etudiant e where e.dateNaissance>:x and e.dateNaissance<:y")
	List<Etudiant> chercherEtudiants(@Param("x")Date d1,@Param("y")Date d2);

}
