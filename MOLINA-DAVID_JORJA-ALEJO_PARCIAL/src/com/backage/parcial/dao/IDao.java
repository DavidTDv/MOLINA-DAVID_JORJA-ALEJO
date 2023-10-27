package com.backage.parcial.dao;

import com.backage.parcial.model.Odontologo;
import java.util.List;

public interface IDao<T> {
    Odontologo guardar(T objeto);
    List<T> listar();
}
