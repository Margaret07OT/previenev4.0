package com.previene.laboratorio.Service;

import java.util.List;

import com.previene.laboratorio.Model.Area;

public interface AreaService {

    public List<Area> findAll();

    public Area findById(int id_area);

    public Area save(Area area);

    public void update(Area Area);

}
