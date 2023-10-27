package com.backage.parcial.service;

import com.backage.parcial.dao.IDao;
import com.backage.parcial.model.Odontologo;

import java.util.ArrayList;
import java.util.List;

public class OdontologoLista implements IDao<Odontologo> {
    private List<Odontologo> odontologos = new ArrayList<>();

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologos.add(odontologo);
        return odontologo;
    }

    @Override
    public List<Odontologo> listar() {
        return new ArrayList<>(odontologos);
    }
}