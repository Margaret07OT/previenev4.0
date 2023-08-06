package com.previene.laboratorio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    List<Area> findAll();

    Area findById(int id_area);
}
