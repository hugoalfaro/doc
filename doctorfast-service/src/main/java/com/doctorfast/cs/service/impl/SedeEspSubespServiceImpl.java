/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.SedeEspSubespBean;
import com.doctorfast.cs.data.mapper.SedeEspSubespMapper;
import com.doctorfast.cs.data.model.SedeEspSubesp;
import com.doctorfast.cs.data.model.SedeEspSubespId;
import com.doctorfast.cs.data.repository.SedeEspSubespRepository;
import com.doctorfast.cs.service.SedeEspSubespService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class SedeEspSubespServiceImpl implements SedeEspSubespService {

    @Autowired
    private SedeEspSubespRepository sedeEspSubespdRepository;

    @Override
    public void delete(Integer idSede, Integer idEspecialidad, Integer idSubespecialidad) {
        SedeEspSubespId id = new SedeEspSubespId(idSede, idEspecialidad, idSubespecialidad);
        sedeEspSubespdRepository.delete(id);
    }

    @Override
    public List<SedeEspSubespBean> findByIdIdSedeAndIdIdEspecialidad(Integer idSede, Integer idEspecialidad) {
        return SedeEspSubespMapper.INSTANCE.toBean(sedeEspSubespdRepository.findByIdIdSedeAndIdIdEspecialidad(idSede, idEspecialidad));
    }

    @Override
    public SedeEspSubespBean save(SedeEspSubespBean bean) {
        SedeEspSubesp model = SedeEspSubespMapper.INSTANCE.toModel(bean);
        return SedeEspSubespMapper.INSTANCE.toBean(sedeEspSubespdRepository.save(model));
    }

}
