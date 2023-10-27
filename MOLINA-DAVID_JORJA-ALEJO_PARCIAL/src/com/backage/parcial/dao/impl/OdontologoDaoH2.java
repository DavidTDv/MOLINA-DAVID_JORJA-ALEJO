import com.backage.parcial.dao.IDao;
import com.backage.parcial.model.Odontologo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private Connection connection;

    public OdontologoDaoH2() {
        // Inicializa la conexión a la base de datos H2
        connection = YourDatabaseConnectionFactory.getConnection();
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        try {
            // Crea la sentencia SQL para insertar un odontólogo en la base de datos
            String sql = "INSERT INTO ODONTOLOGOS (MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Establece los valores de los parámetros
            preparedStatement.setString(1, odontologo.getNumeroMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            // Ejecuta la sentencia SQL
            preparedStatement.executeUpdate();

            // Recupera la clave generada (si es necesario)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idGenerado = generatedKeys.getInt(1);
                odontologo.setId(idGenerado); // Actualiza el ID generado en el objeto Odontologo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return odontologo;
    }

    @Override
    public List<Odontologo> listar() {
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            // Crea la sentencia SQL para listar todos los odontólogos
            String sql = "SELECT * FROM ODONTOLOGOS";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Ejecuta la consulta SQL
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String numeroMatricula = resultSet.getString("MATRICULA");
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");

                Odontologo odontologo = new Odontologo(numeroMatricula,nombre,apellido);
                odontologo.setId(id);

                odontologos.add(odontologo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return odontologos;
    }
}
