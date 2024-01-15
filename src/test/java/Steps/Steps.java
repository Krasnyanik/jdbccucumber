package Steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class Steps {
private Connection connectDB;
private ResultSet result;
    @И("выполнено подключение к {string} c логином {string} и паролем {string}")
    public void выполнено_подключение_к_с_логином_и_паролем(String arg1, String arg2, String arg3) throws SQLException {
        this.connectDB = DriverManager.getConnection(arg1,
                arg2, arg3);


    }
    @И("добавляем товар под номером {int} с названием {string} типом {string} и экзотичностью {int}")
    public void добавляем_товар_под_номером_с_названием_типом_и_экзотичностью(int arg1, String arg2, String arg3, int arg4) throws SQLException {
        String insert = "insert into FOOD values (?,?,?,?);";
        PreparedStatement addProductInDB = connectDB.prepareStatement(insert);
        addProductInDB.setInt(1,arg1);
        addProductInDB.setString(2,arg2);
        addProductInDB.setString(3, arg3);
        addProductInDB.setInt(4,arg4);
        addProductInDB.executeUpdate();
    }

    @И("товар {string} добавлен")
    public void товар_добавлен(String arg1) throws SQLException {
        String checkProductInTable = "select FOOD_ID, FOOD_NAME from FOOD order by FOOD_ID desc LIMIT 1";
        Statement statement = connectDB.createStatement();
        this.result = statement.executeQuery(checkProductInTable);

        while(result.next()){
            String food_name = result.getString(2);
            Assertions.assertEquals(arg1,food_name);
        }
    }

    @И("удаляем товар {string}")
    public void удаляем_товар_под_номером(String arg1) throws SQLException {
        String delete = "delete from FOOD where FOOD_NAME = ?;";
        PreparedStatement del = connectDB.prepareStatement(delete);
        del.setString(1,arg1);
        del.executeUpdate();
    }

    @И("товар {string} удален")
    public void товар_удален(String arg1) throws SQLException {
        String checkProductInTable = "select * from FOOD";
        Statement statement = connectDB.createStatement();
        this.result = statement.executeQuery(checkProductInTable);
        while(result.next()){
            String food_name = result.getString(2);
            Assertions.assertNotEquals(arg1,food_name);
        }
    }
}
