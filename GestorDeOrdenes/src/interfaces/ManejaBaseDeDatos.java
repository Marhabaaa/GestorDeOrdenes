package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface ManejaBaseDeDatos {

    void toDB(Connection connection) throws SQLException;
    void deleteFromDB(Connection connection) throws SQLException;
    void updateDB(Connection connection) throws SQLException;
}
