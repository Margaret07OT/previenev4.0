package com.previene.laboratorio.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.DetalleCita;
import com.previene.laboratorio.Model.Resultado;
import com.previene.laboratorio.Model.Tecnologo;
import com.previene.laboratorio.Service.CitaService;
import com.previene.laboratorio.Service.DetalleCitaService;
import com.previene.laboratorio.Service.ResultadoService;
import com.previene.laboratorio.Service.TecnologoService;


@Controller
@RequestMapping("/tecnologo")
public class TecnologoController {

	@Autowired
    private TecnologoService tecnologoService;

	@Autowired
	private CitaService citaService;

	@Autowired
	private ResultadoService resultadoService;

	@Autowired
	private DetalleCitaService detalleCitaService;
    
    @GetMapping("/index")
	public String home(HttpSession session,Model model) {
		if(session.getAttribute("idusuario")!=null){
			Tecnologo tecnologo=tecnologoService.findByDni(session.getAttribute("idusuario").toString());
			if(tecnologo.getRol().equalsIgnoreCase("Tecnologo")){
				model.addAttribute("tecnologo",tecnologo);
				return "tecnologo/index";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/listarCitasP")
	public String listarCitasP(HttpSession session,Map<String,Object> model){
		if(session.getAttribute("idusuario")!=null){
			Tecnologo tecnologo=tecnologoService.findByDni(session.getAttribute("idusuario").toString());
			if(tecnologo.getRol().equalsIgnoreCase("Tecnologo")){
				List<Cita> citas=new ArrayList<>();
				citas=citaService.findAll();
				for(int i=0;i<citas.size();i++){
					if(citas.get(i).getEstado().equals("Pendiente") || citas.get(i).getTecnologo()!=tecnologo){
						citas.remove(i);
					}
				}
				model.put("citas", citas);
				return "tecnologo/listarCitasP";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/verCita/{id_cita}")
	public String verCita(HttpSession session,@PathVariable Integer id_cita,Map<String,Object> model){
		if(session.getAttribute("idusuario")!=null){
			Tecnologo tecnologo=tecnologoService.findByDni(session.getAttribute("idusuario").toString());
			if(tecnologo.getRol().equalsIgnoreCase("Tecnologo")){
				Cita cita=citaService.findById(id_cita);
				List<Resultado> resultados=resultadoService.findByCita(cita);
				model.put("cita", cita);
				model.put("resultados", resultados);
				return "tecnologo/verCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/ingresarResultado/{id_resultado}")
	public String ingresarResultado(HttpSession session,@PathVariable Integer id_resultado,Map<String,Object> model){
		if(session.getAttribute("idusuario")!=null){
			Tecnologo tecnologo=tecnologoService.findByDni(session.getAttribute("idusuario").toString());
				if(tecnologo.getRol().equalsIgnoreCase("Tecnologo")){
					if(resultadoService.findById(id_resultado).getEstado().equals("Pendiente")){
						Resultado resultado= resultadoService.findById(id_resultado);
						List<DetalleCita> detalleCita=detalleCitaService.findByCita(resultado.getCita());
						model.put("resultado", resultado);
						model.put("detalleCita", detalleCita);
						return "tecnologo/ingresarResultado";
					}else{
						return "error/error";
					}
				}else{
					return "error/error";
				}
			}else{
				return "error/error";
			}	
	}
	@PostMapping("/ingresarResultado")
	public String ingresarResultado(HttpSession session,Resultado resultado,Map<String,Object> model,MultipartFile archivo){
		if(session.getAttribute("idusuario")!=null){
			Tecnologo tecnologo=tecnologoService.findByDni(session.getAttribute("idusuario").toString());
			if(tecnologo.getRol().equalsIgnoreCase("Tecnologo")){
				if(archivo.isEmpty() || !archivo.getContentType().equalsIgnoreCase("application/pdf")){
					Resultado resul= resultadoService.findById(resultado.getId());
					List<DetalleCita> detalleCita=detalleCitaService.findByCita(resul.getCita());
					model.put("resultado", resultado);
					model.put("detalleCita", detalleCita);
					return "tecnologo/ingresarResultado";
				}
				try {
					resultado.setResultado(archivo.getBytes());
					resultado.setNombreResultado(archivo.getOriginalFilename());
					resultado.setTipo(archivo.getContentType());
					resultado.setEstado("Terminado");
					resultado.setFechaR(LocalDateTime.now());
					resultadoService.save(resultado);
			
					return "redirect:/tecnologo/verCita/"+resultado.getCita().getId();
				} catch (IOException e) {
					e.printStackTrace();
					// Manejar el error adecuadamente
				}
				return "error/error";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/descargarResultado/{id}")
	public ResponseEntity<byte[]> descargarResultado(@PathVariable Integer id) {
	Resultado resultado=resultadoService.findById(id);
    if (resultado!= null) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(resultado.getTipo()));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(resultado.getNombreResultado()).build());
        return new ResponseEntity<>(resultado.getResultado(), headers, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
}
