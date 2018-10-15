package gestion.etudient.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import gestion.etudient.dao.EtudiantRepository;
import gestion.etudient.entities.Etudiant;

@Controller
@RequestMapping(value="/Etudiant")
public class EtudiantController {
	@Autowired
	EtudiantRepository etudiantRepository;
	@Value("${dir.images}")
	private String imageDir;
	
	@RequestMapping(value="/index")
    public String index(Model model, @RequestParam(name="page",defaultValue="0")int p,
    		                         @RequestParam(name="motCle",defaultValue="")String mc){
		Page<Etudiant> etudiants=etudiantRepository.chercherEtudiants("%"+mc+"%",new PageRequest(p, 5));
		int pagesCount=etudiants.getTotalPages();
		int [] pages=new int[pagesCount];
		for(int i=0;i<pagesCount;i++) pages[i]=i;
		model.addAttribute("motCle", mc);
		model.addAttribute("pages", pages);
		model.addAttribute("pageEtudiants", etudiants);
		model.addAttribute("pageCourante", p);
    	return "etudiants";
    }
   @RequestMapping(value="/form", method=RequestMethod.GET)
   public String formEtudiant(Model model){
	   model.addAttribute("etudiant", new Etudiant());
	   return "FormEtudiant";
	 
   }
   @RequestMapping(value="/SaveEtudiant", method=RequestMethod.POST)
   public String save(@Valid Etudiant e,BindingResult bindingResult,@RequestParam(name="picture")MultipartFile file) throws Exception{
	   if(bindingResult.hasErrors())
		   return "FormEtudiant";
	   if(!(file.isEmpty())){
		   e.setPhoto(file.getOriginalFilename());}
	   etudiantRepository.save(e);
	   if(!(file.isEmpty())){
		   e.setPhoto(file.getOriginalFilename());
		   file.transferTo(new File(imageDir+e.getId()));
	   }
	   
	   return "redirect:index";
	 
   }
   @RequestMapping(value="/UpdateEtudiant", method=RequestMethod.POST)
   public String update(@Valid Etudiant e,BindingResult bindingResult,@RequestParam(name="picture")MultipartFile file) throws Exception{
	   if(bindingResult.hasErrors())
		   return "EditEtudiant";
	   if(!(file.isEmpty())){
		   e.setPhoto(file.getOriginalFilename());}
	   etudiantRepository.save(e);
	   if(!(file.isEmpty())){
		   e.setPhoto(file.getOriginalFilename());
		   file.transferTo(new File(imageDir+e.getId()));
	   }
	   
	   return "redirect:index";
	 
   }
   @RequestMapping(value="/getPhoto", produces=MediaType.IMAGE_JPEG_VALUE)
   @ResponseBody
   public byte[] getPhoto(Long id) throws Exception{
	   File f=new File(imageDir+id);
	   return org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(f));
   }
   @RequestMapping(value="/delete" ,method=RequestMethod.GET)
   public String supprimer(Long id){
	   etudiantRepository.deleteById(id);
	   return "redirect:index";
   }
   @RequestMapping(value="/edit" ,method=RequestMethod.GET)
   public String editer(Long id,Model model){
	 Etudiant e=etudiantRepository.getOne(id);
	 model.addAttribute("etudiant",e);
	   return "EditEtudiant";
   }
}
