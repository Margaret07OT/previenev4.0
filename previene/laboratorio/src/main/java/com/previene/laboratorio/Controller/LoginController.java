package com.previene.laboratorio.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.previene.laboratorio.Model.Administrador;
import com.previene.laboratorio.Model.Tecnologo;
import com.previene.laboratorio.Model.Paciente;
import com.previene.laboratorio.Service.AdministradorService;
import com.previene.laboratorio.Service.TecnologoService;
import com.previene.laboratorio.Service.PacienteService;


@Controller
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private TecnologoService tecnologoService;

    @GetMapping("/login")
	public String login() {
		return "login/login";
	}

    @PostMapping("/acceder")
    public String acceder(String dni, String password,HttpSession session){

        Administrador admin=administradorService.findByDniAndPassword(dni, password);

        Paciente paciente=pacienteService.findByDniAndPassword(dni, password);

        Tecnologo tecnologo=tecnologoService.findByDniAndPassword(dni, password);

        if(admin!=null){
            if(admin.getRol().equalsIgnoreCase("Admin")){
                session.setAttribute("idusuario", admin.getDni());
                 return "redirect:/administrador/index";
            }
        }

        if(paciente!=null){
            if(paciente.getRol().equalsIgnoreCase("Paciente")){
                session.setAttribute("idusuario", paciente.getDni());
                return "redirect:/paciente/index";
            }
        }

        if(tecnologo!=null){
            if(tecnologo.getRol().equalsIgnoreCase("Tecnologo")){
                session.setAttribute("idusuario", tecnologo.getDni());
                return "redirect:/tecnologo/index";
            }
        }
        return "redirect:/login/login";
    }

    @GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
