package com.previene.laboratorio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Area;
import com.previene.laboratorio.Repository.AreaRepository;

@Service
public class AreaServicelmpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Area findById(int id_area) {
        return areaRepository.findById(id_area);
    }

    @Override
    @Transactional
    public Area save(Area area) {
        return areaRepository.save(area);
    }

    @Override
	public void update(Area area) {
		areaRepository.save(area);
	}

}
