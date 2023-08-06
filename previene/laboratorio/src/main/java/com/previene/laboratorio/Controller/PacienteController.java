package com.previene.laboratorio.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Paciente;
import com.previene.laboratorio.Model.Resultado;
import com.previene.laboratorio.Service.CitaService;
import com.previene.laboratorio.Service.PacienteService;
import com.previene.laboratorio.Service.ResultadoService;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    
	@Autowired
    private PacienteService pacienteService;

	@Autowired
	private CitaService citaService;

	@Autowired
	private ResultadoService resultadoService;

    @GetMapping("/index")
	public String home(HttpSession session,Model model) {
		if(session.getAttribute("idusuario")!=null){
			Paciente pac=pacienteService.findByDni(session.getAttribute("idusuario").toString());
			if(pac.getRol().equalsIgnoreCase("Paciente")){
				model.addAttribute("pac",pac);
				return "paciente/index";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/perfil")
	public String perfil(HttpSession session,Model model) {
		if(session.getAttribute("idusuario")!=null){
			Paciente pac=pacienteService.findByDni(session.getAttribute("idusuario").toString());
			if(pac.getRol().equalsIgnoreCase("Paciente")){
				model.addAttribute("pac",pac);
				return "paciente/perfil";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/editPerfil")
	public String editperfil(HttpSession session,Map<String,Object> model) {
		if(session.getAttribute("idusuario")!=null){
			Paciente paciente=pacienteService.findByDni(session.getAttribute("idusuario").toString());
			if(paciente.getRol().equalsIgnoreCase("Paciente")){
				model.put("paciente",paciente);
				return "paciente/editPerfil";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@PostMapping("/editPerfil")
	public String updatePerfil(HttpSession session,@Validated Paciente paciente,BindingResult bindingResult,Map<String,Object> model) {
		if(session.getAttribute("idusuario")!=null){
			Paciente pac=pacienteService.findByDni(session.getAttribute("idusuario").toString());
			if(pac.getRol().equalsIgnoreCase("Paciente")){
				if(bindingResult.hasErrors()){
					model.put("paciente",paciente);
					return "paciente/editPerfil";
				}
				pacienteService.save(paciente);
				return "redirect:/paciente/perfil";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/listarCitas")
	public String listarCitas(HttpSession session,Map<String,Object> model) {
		if(session.getAttribute("idusuario")!=null){
			Paciente pac=pacienteService.findByDni(session.getAttribute("idusuario").toString());
			if(pac.getRol().equalsIgnoreCase("Paciente")){
				List<Cita> citas=citaService.findByPaciente(pac);
				model.put("citas", citas);
				model.put("pac",pac);
				return "paciente/listarCitas";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/verCita/{id_cita}")
	public String verCita(HttpSession session,@PathVariable Integer id_cita,Map<String,Object> model) {
		if(session.getAttribute("idusuario")!=null){
			Paciente pac=pacienteService.findByDni(session.getAttribute("idusuario").toString());
			if(pac.getRol().equalsIgnoreCase("Paciente")){
				if(id_cita>0){
					Cita cita=citaService.findById(id_cita);
					List<Resultado> resultados=resultadoService.findByCita(cita);
					model.put("pac",pac);
					model.put("cita", cita);
					model.put("resultados", resultados);
					return "paciente/verCita";
				}
				return "error/error";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
}
