package com.micro.curso.controller;

import com.micro.curso.entity.Curso;
import com.micro.curso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * @author : Bryan Quisaguano
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    //@CircuitBreaker(name = "CLIENTE", fallbackMethod = "errorEnPeticion")
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    //@CircuitBreaker(name = "CLIENTE", fallbackMethod = "errorEnPeticion")
    public ResponseEntity<?> detalle(@PathVariable Integer id){
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()){
            return  ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
   // @CircuitBreaker(name = "CLIENTE", fallbackMethod = "errorEnPeticion")
    public ResponseEntity<?> crear(@RequestBody Curso curso){
        Curso cursoBD = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoBD);
    }

    @PutMapping("/{id}")
    //@CircuitBreaker(name = "CLIENTE", fallbackMethod = "errorEnPeticion")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @RequestParam Integer id){
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()){
            Curso cursoBD = o.get();
            cursoBD.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoBD));

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
   // @CircuitBreaker(name = "CLIENTE", fallbackMethod = "errorEnPeticion")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()){
            cursoService.eliminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * se obtiene el puerto al cual se realiza la petición, para balanceador de carga
     */
    @Value("${server.port}")
    private int port;
    @GetMapping("/puertosalida")
    //@CircuitBreaker(name = "CLIENTE", fallbackMethod = "errorEnPeticion")
    public String verPuerto() {
        return "el puerto que recibe la peticion es : " + port;
    }

    private String errorEnPeticion(){
        return "control de erroes funcioando";
    }
}
