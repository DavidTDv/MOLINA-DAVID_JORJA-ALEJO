package com.backage.parcial.test;

import com.backage.parcial.model.Odontologo;
import com.backage.parcial.service.OdontologoLista;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoListaTest {
    private static OdontologoLista odontologoLista;
    @BeforeAll
    static void doBefore() {
        try {
            odontologoLista = new OdontologoLista();
        }catch (Exception e){
            fail("Error durante la configuracion previa: "+ e.getMessage());
        }
    }
    @Test
    void agregarOdontologoALista(){
        Odontologo odontologo = new Odontologo(23498,"Alejo", "Jorja");
        Odontologo odontologo1 = odontologoLista.guardar(odontologo);


        assertTrue(odontologoLista.listar().contains(odontologo));
    }

}