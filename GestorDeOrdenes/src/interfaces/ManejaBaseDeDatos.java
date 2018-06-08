package interfaces;

import java.sql.Connection;

public interface ManejaBaseDeDatos {

    void toDB(Connection connection);
    void updateDB(Connection connection);
    void deleteFromDB(Connection connection);
}
