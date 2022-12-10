package com.micro.curso.service;


import com.micro.curso.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();

    Optional<Curso> porId(Integer id);

    Curso guardar(Curso curso);

    void eliminar(Integer id);
}
