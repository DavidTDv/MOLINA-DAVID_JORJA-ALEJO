package com.backage.parcial.dao.impl;

import com.backage.parcial.dao.H2Connection;
import com.backage.parcial.dao.IDao;
import com.backage.parcial.model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoRegistrado = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, odontologo.getNumeroMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();

            odontologoRegistrado = new Odontologo(odontologo.getNumeroMatricula(), odontologo.getNombre(), odontologo.getApellido());

            connection.commit();
            LOGGER.info("Se ha registrado el odontólogo: " + odontologoRegistrado.getNumeroMatricula());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexión: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> listar() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Odontologo odontologo = crearObjetoOdontologo(resultSet);
                odontologos.add(odontologo);
            }

            LOGGER.info("Listado de todos los odontólogos: " + odontologos);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la base de datos. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return odontologos;
    }

    private Odontologo crearObjetoOdontologo(ResultSet resultSet) throws SQLException {
        int numeroMatricula = resultSet.getInt("NUMERO_MATRICULA");
        String nombre = resultSet.getString("NOMBRE");
        String apellido = resultSet.getString("APELLIDO");

        return new Odontologo(numeroMatricula, nombre, apellido);
    }
}
