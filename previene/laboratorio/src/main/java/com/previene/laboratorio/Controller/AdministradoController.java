package com.previene.laboratorio.Controller;



import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;



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

import com.previene.laboratorio.Model.Administrador;
import com.previene.laboratorio.Model.Analisis;
import com.previene.laboratorio.Model.Area;
import com.previene.laboratorio.Model.Boleta;
import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.DetalleCita;
import com.previene.laboratorio.Model.Factura;
import com.previene.laboratorio.Model.Tecnologo;
import com.previene.laboratorio.Model.Paciente;
import com.previene.laboratorio.Model.Resultado;
import com.previene.laboratorio.Service.AdministradorService;
import com.previene.laboratorio.Service.AnalisisService;
import com.previene.laboratorio.Service.AreaService;
import com.previene.laboratorio.Service.BoletaService;
import com.previene.laboratorio.Service.CitaService;
import com.previene.laboratorio.Service.DetalleCitaService;
import com.previene.laboratorio.Service.FacturaService;
import com.previene.laboratorio.Service.TecnologoService;
import com.previene.laboratorio.Service.PacienteService;
import com.previene.laboratorio.Service.ResultadoService;

@Controller
@RequestMapping("/administrador")
public class AdministradoController {
    
	@Autowired
    private AdministradorService administradorService;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private TecnologoService tecnologoService;

	@Autowired
	private CitaService citaService;

	@Autowired
	private AnalisisService analisisService;

	@Autowired
	private ResultadoService resultadoService;

	@Autowired
	private BoletaService boletaService;

	@Autowired
	private FacturaService facturaService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private DetalleCitaService detalleCitaService;

    @GetMapping("/index")
	public String home(HttpSession session,Model model) {
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				model.addAttribute("admin",admin);
				return "administrador/index";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	
	//Paciente: Create, list, edit , save paciente, cambiar de estado
	@GetMapping("/listarPaciente")
	public String listarPaciente(HttpSession session,Model model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				List<Paciente> pacientes = pacienteService.findAll();
				model.addAttribute("pacientes", pacientes);
				return "administrador/listarPaciente";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/createPaciente")
	public String createPaciente(HttpSession session, Map<String,Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				Paciente paciente = new Paciente();
				model.put("paciente", paciente);
				model.put("mes", "2022-12-31");
				return "administrador/createPaciente";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	//Refinar unas cositas: creo que ya esta xd
	@PostMapping("/createPaciente")
	public String savePaciente(HttpSession session,@Validated Paciente paciente,BindingResult bindingResult,Date menstruacion,String med,String enf,Map<String,Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(bindingResult.hasErrors() || (paciente.getMedicamento().equals("Si") && med.trim().length()==0) || (paciente.getEnfermedad().equals("Si") && enf.trim().length()==0)){	
					if(paciente.getSexo()!=null){
						if(paciente.getSexo().equals("F")){
							model.put("mes", paciente.getMenstruacion());
						}else{
							model.put("mes", "2022-12-31");
						}
					}else{
						model.put("mes", "2022-12-31");
					}
					if(paciente.getMedicamento()!=null){
						if(paciente.getMedicamento().equals("Si") && med.trim().length()==0){
							model.put("siMedVacio", "No debe dejar el campo vacio si esta medicado");
						}
					}
					if(paciente.getEnfermedad()!=null){
						if(paciente.getEnfermedad().equals("Si") && enf.trim().length()==0){
							model.put("siEnfVacio", "No debe dejar el campo vacio si tiene alguna enfermedad");
						}
					}
						model.put("paciente", paciente);
						model.put("med", med);
						model.put("enf", enf);
						return "administrador/createPaciente";
				}

				if(paciente.getMedicamento().equals("Si")){
					paciente.setMedicamento(paciente.getMedicamento()+","+med);
				}

				if(paciente.getEnfermedad().equals("Si")){
					paciente.setEnfermedad(paciente.getEnfermedad()+","+enf);
				}

				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				if(paciente.getSexo().equals("M")){
					paciente.setMenstruacion("No");
				}else{
					paciente.setMenstruacion(formato.format( menstruacion));
				}	
				
				pacienteService.save(paciente);

				return "redirect:/administrador/listarPaciente";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/editPaciente/{dni}")
	public String editPaciente(HttpSession session,@PathVariable String dni,Map<String, Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(dni.length()==8){
					Paciente paciente=pacienteService.findByDni(dni);
					String med="",enf="";
					if(paciente.getSexo().equals("F")){
						model.put("mes", paciente.getMenstruacion());
					}else{
						model.put("mes", "2022-12-31");
					}
					Pattern pattern = Pattern.compile("Si,.*");
					if(pattern.matcher(paciente.getMedicamento()).matches()){
						med=paciente.getMedicamento().substring(3);
					
					}
					if(pattern.matcher(paciente.getEnfermedad()).matches()){
						enf=paciente.getEnfermedad().substring(3);
						
					}
					model.put("paciente", paciente);
					model.put("med", med);
					model.put("enf", enf);
					return "administrador/editPaciente";
				}else{
					return "redirect:/administrador/listarPaciente";
				}
				
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	
	@PostMapping("/editPaciente")
	public String updatePaciente(HttpSession session,@Validated Paciente paciente,BindingResult bindingResult,Date menstruacion,String med,String enf,Map<String, Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				med=med.trim();
				enf=enf.trim();
				if (bindingResult.hasErrors() || (paciente.getMedicamento().equals("Si,") && med.length()==0) || (paciente.getEnfermedad().equals("Si,") && enf.length()==0)) {
					if(paciente.getSexo().equals("F")){
						model.put("mes", paciente.getMenstruacion());
					}else{
						model.put("mes", "2022-12-31");
					}
					Pattern pattern = Pattern.compile("Si,.*");
					if(pattern.matcher(paciente.getMedicamento()).matches()){
						paciente.setMedicamento("Si,"+med);
					
					}
					if(pattern.matcher(paciente.getEnfermedad()).matches()){
						paciente.setEnfermedad("Si,"+enf);
					}
					if(paciente.getMedicamento().equals("Si,") && med.trim().length()==0){
						model.put("siMedVacio", "No debe dejar el campo vacio si esta medicado");
					}
					if(paciente.getEnfermedad().equals("Si,") && enf.trim().length()==0){
						model.put("siEnfVacio", "No debe dejar el campo vacio si tiene alguna enfermedad");
					}
					model.put("paciente",paciente);
					model.put("med",med);
					model.put("enf",enf);
					return "administrador/editPaciente";
				}
				Pattern pattern = Pattern.compile("Si,.*");
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				if(paciente.getSexo().equals("M")){
					paciente.setMenstruacion("No");
				}else{
					paciente.setMenstruacion(formato.format(menstruacion));
				}
				if(pattern.matcher(paciente.getMedicamento()).matches()){
					paciente.setMedicamento("Si,"+med);	
				}
				if(pattern.matcher(paciente.getEnfermedad()).matches()){
					paciente.setEnfermedad("Si,"+enf);
				}
				pacienteService.save(paciente);
				return "redirect:/administrador/listarPaciente";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/desactivarPaciente/{dni}")
	public String desactivarPaciente(HttpSession session,@PathVariable String dni){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(dni.length()==8){
					Paciente paciente=pacienteService.findByDni(dni);
					paciente.setEstado("Inactivo");
					pacienteService.update(paciente);
				}
				return "redirect:/administrador/listarPaciente";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/activarPaciente/{dni}")
	public String activarPaciente(HttpSession session,@PathVariable String dni){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(dni.length()==8){
					Paciente paciente=pacienteService.findByDni(dni);
					paciente.setEstado("Activo");
					pacienteService.update(paciente);
				}
				return "redirect:/administrador/listarPaciente";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	// AREAS: listar,create,save , update y cambio de estado
	@GetMapping("/listarArea")
	public String listarArea(HttpSession session, Model model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				List<Area> areas = areaService.findAll();
				model.addAttribute("areas", areas);
				return "administrador/listarArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	@GetMapping("/createArea")
	public String createArea(HttpSession session, Map<String, Object> model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				Area area = new Area();
				model.put("area", area);
				return "administrador/createArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	@PostMapping("/saveArea")
	public String saveArea(HttpSession session, @Validated Area area, BindingResult bindingResult) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if (bindingResult.hasErrors()) {
					return "administrador/createArea";
				}
				area.setEstado("Activo");

				areaService.save(area);
				return "redirect:/administrador/listarArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	@GetMapping("/editArea/{id_area}")
	public String editArea(HttpSession session, @PathVariable int id_area, Map<String, Object> model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				Area area = areaService.findById(id_area);
				model.put("area", area);
				return "administrador/editArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	@PostMapping("/updateArea")
	public String updateArea(HttpSession session, @Validated Area area, BindingResult bindingResult,Map<String, Object> model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if (bindingResult.hasErrors()) {
					return "administrador/editArea";
				}
				areaService.update(area);
				return "redirect:/administrador/listarArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	@GetMapping("/desactivarArea/{id_area}")
	public String desactivarArea(HttpSession session, @PathVariable int id_area) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if(id_area>0){
					Area area = areaService.findById(id_area);
					area.setEstado("Inactivo");
					areaService.update(area);
				}
				return "redirect:/administrador/listarArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}
	@GetMapping("/activarArea/{id_area}")
	public String activarTecnologo(HttpSession session, @PathVariable int id_area) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if(id_area>0){
					Area area = areaService.findById(id_area);
					area.setEstado("Activo");
					areaService.update(area);
				}
				return "redirect:/administrador/listarArea";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	// ANALISI: listar,create,save , update y cambio de estado
	@GetMapping("/listarAnalisis")
	public String listarAnalisis(HttpSession session, Model model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				List<Analisis> analisis = analisisService.findAll();
				model.addAttribute("analisis", analisis);
				return "administrador/listarAnalisis";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}
	@GetMapping("/createAnalisis")
	public String createAnalisis(HttpSession session, Map<String, Object> model, Model model1) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				Analisis analisis = new Analisis();
				List<Area> areas = areaService.findAll();
				model.put("analisis", analisis);
				model.put("areas", areas);
				return "administrador/createAnalisis";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}
	@PostMapping("/saveAnalisis")
	public String saveAnalisis(HttpSession session, @Validated Analisis analisis, BindingResult bindingResult) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if (bindingResult.hasErrors()) {
					return "redirect:/administrador/createAnalisis";
				}
				analisis.setEstado("Activo");
				analisisService.save(analisis);
				return "redirect:/administrador/listarAnalisis";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}
	@GetMapping("/editAnalisis/{id_analisis}")
	public String editAnalisis(HttpSession session, @PathVariable Integer id_analisis, Map<String, Object> model,Model model1) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				Analisis analisis = analisisService.findById(id_analisis);
				List<Area> areas = areaService.findAll();
				model.put("areas", areas);
				model.put("analisis", analisis);
				return "administrador/editAnalisis";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	@PostMapping("/updateAnalisis")
	public String updateAnalisis(HttpSession session, @Validated Analisis analisis, BindingResult bindingResult,Map<String, Object> model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if (bindingResult.hasErrors()) {
					String cad="redirect:/administrador/editAnalisis/"+analisis.getId_analisis();
					return cad;
				}
				analisisService.update(analisis);
				return "redirect:/administrador/listarAnalisis";
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}

	//Tecnologo: listar,create,save , update y cambio de estado
	@GetMapping("/listarTecnologo")
	public String listarTecnologo(HttpSession session,Model model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				List<Tecnologo> tecnologos = tecnologoService.findAll();
				model.addAttribute("tecnologos", tecnologos);
				return "administrador/listarTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/createTecnologo")
	public String createTecnologo(HttpSession session,Map<String, Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				Tecnologo tecnologo = new Tecnologo();
				model.put("tecnologo", tecnologo);
				return "administrador/createTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	//Listo
	@PostMapping("/createTecnologo")
	public String saveTecnologo(HttpSession session,@Validated Tecnologo tecnologo,BindingResult bindingResult,Map<String, Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(bindingResult.hasErrors()){
					model.put("tecnologo", tecnologo);
					return "administrador/createTecnologo";
				}
				tecnologoService.save(tecnologo);
				return "redirect:/administrador/listarTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/editTecnologo/{dni}")
	public String editTecnologo(HttpSession session,@PathVariable String dni,Map<String, Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				Tecnologo tecnologo;
				if(dni.length()==8){
					tecnologo=tecnologoService.findByDni(dni);
				}else{
					return "redirect:/administrador/listarTecnologo";
				}
				model.put("tecnologo", tecnologo);
				return "administrador/editTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/updateTecnologo")
	public String updateTecnologo(HttpSession session,@Validated Tecnologo tecnologo,BindingResult bindingResult, Map<String, Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if (bindingResult.hasErrors()) {
					model.put("tecnologo", tecnologo);
					return "administrador/editTecnologo";
				}
				tecnologoService.update(tecnologo);
				return "redirect:/adminstrador/listarTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/desactivarTecnologo/{dni}")
	public String desactivarTecnologo(HttpSession session,@PathVariable String dni){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(dni.length()==8){
					Tecnologo tecnologo=tecnologoService.findByDni(dni);
					tecnologo.setEstado("Inactivo");
					tecnologoService.update(tecnologo);
				}
				return "redirect:/administrador/listarTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/activarTecnologo/{dni}")
	public String activarTecnologo(HttpSession session,@PathVariable String dni){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(dni.length()==8){
					Tecnologo tecnologo=tecnologoService.findByDni(dni);
					tecnologo.setEstado("Activo");
					tecnologoService.update(tecnologo);
				}
				return "redirect:/administrador/listarTecnologo";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	//Citas: create , save , eliminar , editar
	@GetMapping("/listarCita")
	public String listarCita(HttpSession session,Model model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				List<Cita> citas = citaService.findAll();
				model.addAttribute("citas", citas);
				return "administrador/listarCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/createCita")
	public String createCita(HttpSession session, Map<String,Object> model){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				Cita cita = new Cita();
				List<Paciente> pacientes = pacienteService.findByEstado("Activo");
				List<Tecnologo> tecnologos =tecnologoService.findByEstado("Activo");
				List<Analisis> analisis = analisisService.findAll();
				model.put("cita", cita);
				model.put("pacientes", pacientes);
				model.put("tecnologos", tecnologos);
				model.put("analisisList", analisis);
				return "administrador/createCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@PostMapping("/createCita")
	public String saveCita(HttpSession session,@Validated Cita cita,BindingResult bindingResult,Map<String,Object> model,String selectAnalisis,String selectPaciente,String selectTecnologo){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				List<Integer> l=new ArrayList<>();
				cita.setPaciente(pacienteService.findByDni(selectPaciente));
				cita.setTecnologo(tecnologoService.findByDni(selectTecnologo));
				if(selectAnalisis.length()>0){
					String[]selectAnalisisV=selectAnalisis.split(",");
					for(int i=0;i<selectAnalisisV.length;i++){
						l.add(Integer.parseInt(selectAnalisisV[i]));
					}
					cita.setAnalisis(l);
				}
				
				if(bindingResult.hasErrors()|| cita.getTecnologo()==null ||cita.getPaciente()==null || cita.getAnalisis().size()==0 || ((cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0) ||(cita.getTipoCita().equals("Domicilio") && cita.getDistrito().trim().length()==0) || (cita.getTipoCita().equals("Domicilio") && cita.getReferencia().trim().length()==0)||(cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0 && cita.getDistrito().trim().length()==0 )||(cita.getTipoCita().equals("Domicilio") && cita.getDistrito().trim().length()==0 && cita.getReferencia().trim().length()==0 )||(cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0 && cita.getReferencia().trim().length()==0 )|| (cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0 && cita.getDistrito().trim().length()==0 && cita.getReferencia().trim().length()==0 ))){
					List<Paciente> pacientes = pacienteService.findByEstado("Activo");
					List<Tecnologo> tecnologos =tecnologoService.findByEstado("Activo");
					List<Analisis> analisis = analisisService.findAll();
					model.put("cita", cita);
					model.put("pacientes", pacientes);
					model.put("tecnologos", tecnologos);
					model.put("analisisList", analisis);
					if(cita.getPaciente()==null){
						model.put("errorPaciente", "Debe elegir al paciente");
					}
					if(cita.getTecnologo()==null){
						model.put("errorTecnologo", "Debe elegir al tecnologo");
					}
					if(cita.getAnalisis().size()==0){
						model.put("errorAnalisis", "Debe elegir al menos un analisis");
					}
					if(cita.getTipoCita()!=null && cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0){
						model.put("errorDireccion", "No debe dejar el campo vacia");
					}
					if(cita.getTipoCita()!=null && cita.getTipoCita().equals("Domicilio") && cita.getDistrito().trim().length()==0){
						model.put("errorDistrito", "No debe dejar el campo vacio");
					}
					if(cita.getTipoCita()!=null && cita.getTipoCita().equals("Domicilio") && cita.getReferencia().trim().length()==0){
						model.put("errorReferencia", "No debe dejar el campo vacio");
					}
					return "administrador/createCita";
				}
	
				if(cita.getTipoCita().equals("Domicilio")){
					cita.setTipoCita("Domicilio: "+cita.getDireccion()+", "+cita.getDistrito()+", Referencia: "+cita.getReferencia());
				}
				double precioT=0;

				List<Analisis> analisisL=new ArrayList<>();

				List<Area> areas=new ArrayList<>();

				for(int i=0;i<cita.getAnalisis().size();i++){
					analisisL.add(analisisService.findById(cita.getAnalisis().get(i)));
				}
				
				for(Analisis a:analisisL){
					precioT=precioT+a.getPrecio();
				}

				cita.setAdmin(admin);
				cita.setTotal(precioT);
				
				citaService.save(cita);

				for(Analisis a:analisisL){
					areas.add(a.getArea());
				}
				for(int i=0;i<areas.size();i++){
            		for(int j=i+1;j<areas.size();j++){
                		if(areas.get(i).getDescripcion().equals(areas.get(j).getDescripcion())){
                    		areas.remove(j);
                    		i--;
                    		break;
                		}
            		}
        		}
				for(Area a:areas){
					double subTotal=0;
					Resultado resultado=new Resultado();
					for(int i=0;i<analisisL.size();i++){
						if(analisisL.get(i).getArea().getDescripcion()==a.getDescripcion()){
							subTotal=subTotal+analisisL.get(i).getPrecio();
						}
					}
					resultado.setCita(cita);
					resultado.setEstado("Pendiente");
					resultado.setArea(a);
					resultado.setSubTotal(subTotal);
					resultadoService.save(resultado);
				}
				for(Analisis a:analisisL){
					DetalleCita detalleCita=new DetalleCita();
					detalleCita.setCita(cita);
					detalleCita.setAnalisis(a);
					detalleCitaService.save(detalleCita);
				}
				return "redirect:/administrador/listarCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/editCita/{id_cita}")
	public String editCita(HttpSession session, @PathVariable Integer id_cita, Map<String, Object> model) {
		if (session.getAttribute("idusuario") != null) {
			Administrador admin = administradorService.findByDni(session.getAttribute("idusuario").toString());
			if (admin.getRol().equalsIgnoreCase("Admin")) {
				if(citaService.findById(id_cita).getEstado().equals("Pendiente")){
					if(id_cita>0){
						Cita cita=citaService.findById(id_cita);
						List<DetalleCita> dCList=detalleCitaService.findByCita(cita);
						List<Tecnologo> tecnologos =tecnologoService.findByEstado("Activo");
						List<Analisis> analisis = analisisService.findAll();
						List<Integer> a=new ArrayList<>();
						for(int i=0;i<dCList.size();i++){
							a.add(dCList.get(i).getAnalisis().getId_analisis());
						}
						cita.setAnalisis(a);
						Pattern pattern = Pattern.compile("Domicilio:.*");
						if(pattern.matcher(cita.getTipoCita()).matches()){
							String[]subcad=cita.getTipoCita().split(",");
							cita.setDireccion(subcad[0].substring(11));
							cita.setDistrito(subcad[1].substring(1));
							cita.setReferencia(subcad[2].substring(13));
							cita.setTipoCita("Domicilio");
						}
						Paciente paciente=pacienteService.findByDni(cita.getPaciente().getDni());
						model.put("cita", cita);
						model.put("tecnologos", tecnologos);
						model.put("analisisList", analisis);
						model.put("pac", paciente);
						return "administrador/editCita";
					}else{
						return "redirect:/administrador/listarCita";
					}
				}else{
					return "error/error";
				}	
			} else {
				return "error/error";
			}
		} else {
			return "error/error";
		}
	}
	@PostMapping("/editCita")
	public String updateCita(HttpSession session,@Validated Cita cita,BindingResult bindingResult,Map<String,Object> model,String selectAnalisis,String selectTecnologo){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			List<Integer> l=new ArrayList<>();
			cita.setTecnologo(tecnologoService.findByDni(selectTecnologo));
			if(selectAnalisis.length()>0){
				String[]selectAnalisisV=selectAnalisis.split(",");
				for(int i=0;i<selectAnalisisV.length;i++){
					l.add(Integer.parseInt(selectAnalisisV[i]));
				}
				cita.setAnalisis(l);
			}
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(bindingResult.hasErrors() || cita.getTecnologo()==null ||cita.getAnalisis().size()==0 || ((cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0) ||(cita.getTipoCita().equals("Domicilio") && cita.getDistrito().trim().length()==0) || (cita.getTipoCita().equals("Domicilio") && cita.getReferencia().trim().length()==0)||(cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0 && cita.getDistrito().trim().length()==0 )||(cita.getTipoCita().equals("Domicilio") && cita.getDistrito().trim().length()==0 && cita.getReferencia().trim().length()==0 )||(cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0 && cita.getReferencia().trim().length()==0 )|| (cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0 && cita.getDistrito().trim().length()==0 && cita.getReferencia().trim().length()==0 ))){
					Paciente paciente=pacienteService.findByDni(cita.getPaciente().getDni());
					List<Tecnologo> tecnologos =tecnologoService.findByEstado("Activo");
					List<Analisis> analisis = analisisService.findAll();
					model.put("cita", cita);
					model.put("tecnologos", tecnologos);
					model.put("analisisList", analisis);
					model.put("pac", paciente);
					if(cita.getTecnologo()==null){
						model.put("errorTecnologo", "Debe elegir al tecnologo");
					}
					if(cita.getAnalisis().size()==0){
						model.put("errorAnalisis", "Debe elegir al menos un analisis");
					}
					if(cita.getTipoCita()!=null && cita.getTipoCita().equals("Domicilio") && cita.getDireccion().trim().length()==0){
						model.put("errorDireccion", "No debe dejar el campo vacia");
					}
					if(cita.getTipoCita()!=null && cita.getTipoCita().equals("Domicilio") && cita.getDistrito().trim().length()==0){
						model.put("errorDistrito", "No debe dejar el campo vacio");
					}
					if(cita.getTipoCita()!=null && cita.getTipoCita().equals("Domicilio") && cita.getReferencia().trim().length()==0){
						model.put("errorReferencia", "No debe dejar el campo vacio");
					}
					return "administrador/editCita";
				}
				resultadoService.deleteByCita(cita);
				detalleCitaService.deleteByCita(cita);
				if(cita.getTipoCita().equals("Domicilio")){
					cita.setTipoCita("Domicilio: "+cita.getDireccion()+", "+cita.getDistrito()+", Referencia: "+cita.getReferencia());
				}
				double precioT=0;

				List<Analisis> analisisL=new ArrayList<>();

				List<Area> areas=new ArrayList<>();

				for(int i=0;i<cita.getAnalisis().size();i++){
					analisisL.add(analisisService.findById(cita.getAnalisis().get(i)));
				}
				
				for(Analisis a:analisisL){
					precioT=precioT+a.getPrecio();
				}


				cita.setAdmin(admin);
				cita.setTotal(precioT);
				
				citaService.save(cita);

				for(Analisis a:analisisL){
					areas.add(a.getArea());
				}
				for(int i=0;i<areas.size();i++){
            		for(int j=i+1;j<areas.size();j++){
                		if(areas.get(i).getDescripcion().equals(areas.get(j).getDescripcion())){
                    		areas.remove(j);
                    		i--;
                    		break;
                		}
            		}
        		}
				for(Area a:areas){
					double subTotal=0;
					Resultado resultado=new Resultado();
					for(int i=0;i<analisisL.size();i++){
						if(analisisL.get(i).getArea().getDescripcion()==a.getDescripcion()){
							subTotal=subTotal+analisisL.get(i).getPrecio();
						}
					}
					resultado.setCita(cita);
					resultado.setEstado("Pendiente");
					resultado.setArea(a);
					resultado.setSubTotal(subTotal);
					resultadoService.save(resultado);
				}
				for(Analisis a:analisisL){
					DetalleCita detalleCita=new DetalleCita();
					detalleCita.setCita(cita);
					detalleCita.setAnalisis(a);
					detalleCitaService.save(detalleCita);
				}
				return "redirect:/administrador/listarCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/deleteCita/{id}")
	public String deleteCita(HttpSession session,Model model,@PathVariable Integer id) {
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(id>0){
					Cita cita=citaService.findById(id);
					resultadoService.deleteByCita(cita);
					detalleCitaService.deleteByCita(cita);
					citaService.deleteById(id);
				}
				return "redirect:/administrador/listarCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@PostMapping("/generarComprobanteCita")
	public String generarComprobanteCita(HttpSession session,String comprobante,String ruc,int id_cita){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				if(id_cita>0){
					Cita cita=new Cita();
					cita=citaService.findById(id_cita);
					if(comprobante.equals("Boleta")){
						Boleta boleta=new Boleta();
						boleta.setTotal(cita.getTotal());
						boleta.setCita(cita);
						boletaService.save(boleta);
						cita.setEstado("Pagado-Boleta");
						citaService.update(cita);
						
					}else if(comprobante.equals("Factura") && ruc.length()==11){
						Factura factura=new Factura();
						factura.setTotal(cita.getTotal());
						factura.setCita(cita);
						factura.setRuc(ruc);
						facturaService.save(factura);
						cita.setEstado("Pagado-Factura");
						citaService.update(cita);
					}
						
				}
				
				return "redirect:/administrador/listarCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	//Visualizar boleta y factura
	@GetMapping("/verBoleta/{id_cita}")
	public String verBoleta(HttpSession session,Model model,@PathVariable int id_cita){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				Cita cita=citaService.findById(id_cita);
				List<Resultado> resultados=new ArrayList<>();
				List<DetalleCita> detalleCitas= new ArrayList<>();
				Boleta boleta=new Boleta();
				if(cita.getEstado().equals("Pagado-Boleta")){
					 resultados=resultadoService.findByCita(cita);
					 boleta=boletaService.findByCita(cita);
					 detalleCitas=detalleCitaService.findByCita(cita);	
				}else{
					return "redirect:/administrador/listarCita";
				}
				
				model.addAttribute("resultados", resultados);
				model.addAttribute("boleta", boleta);
				model.addAttribute("detalleCitas", detalleCitas);
				model.addAttribute("cita", cita);
				
				return "administrador/verBoleta";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}

	@GetMapping("/verFactura/{id_cita}")
	public String verFactura(HttpSession session,Model model,@PathVariable int id_cita){
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				Cita cita=citaService.findById(id_cita);
				List<Resultado> resultados=new ArrayList<>();
				List<DetalleCita> detalleCitas= new ArrayList<>();
				Factura factura=new Factura();
				if(cita.getEstado().equals("Pagado-Factura")){
					 resultados=resultadoService.findByCita(cita);
					 factura=facturaService.findByCita(cita);
					 detalleCitas=detalleCitaService.findByCita(cita);	
				}else{
					return "redirect:/administrador/listarCita";
				}
				
				model.addAttribute("resultados", resultados);
				model.addAttribute("factura", factura);
				model.addAttribute("detalleCitas", detalleCitas);
				model.addAttribute("cita", cita);
				
				return "administrador/verFactura";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	//Reportes: Paciente, Citas, ingresos
	@GetMapping("/reportePaciente")
	public String reportePaciente(HttpSession session,Model model) {
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				model.addAttribute("admin",admin);
				return "administrador/reportePac";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@PostMapping("/reportePaciente")
	public String reportePacienteMes(HttpSession session,Map<String,Object> model,String mes) {
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM");
				if(mes.length()==0){
					model.put("errorMes", "No debe dejar el mes vacio");
					model.put("mes", mes);
					return "administrador/reportePac";
				}
				if(LocalDate.now().format(formato).compareToIgnoreCase(mes)<0){
					model.put("errorMes", "El mes debe estar en pasado o presente");
					model.put("mes", mes);
					return "administrador/reportePac";
				}
				List<Paciente> pacientes=pacienteService.bucarPorMes(mes);
				model.put("pacientes", pacientes);
				model.put("mes", mes);
				model.put("cantidadPac",pacientes.size());
				return "administrador/reportePac";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
	@GetMapping("/reporteCita")
	public String reporteCita(HttpSession session,Map<String,Object> model) {
		if(session.getAttribute("idusuario")!=null){
			Administrador admin=administradorService.findByDni(session.getAttribute("idusuario").toString());
			if(admin.getRol().equalsIgnoreCase("Admin")){
				model.put("admin",admin);
				Date fechaActual = new Date();
				List<Cita> citas=citaService.findByFecha(fechaActual);
				model.put("citas", citas);
				model.put("cantidadCita",citas.size());
				return "administrador/reporteCita";
			}else{
				return "error/error";
			}
		}else{
			return "error/error";
		}
	}
}
