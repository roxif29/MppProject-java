package repository.DB;

import model.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.Interface.IAngajatRepository;
import repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AngajatDBRepository implements IAngajatRepository {
    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public AngajatDBRepository(Properties props) {
        logger.info("Initializing AngajatDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public int size() {
        return 0;
    }
//id user pass   int id, String username, String pass
    @Override
    public void save(Angajat entity) {
        logger.traceEntry("saving angajat{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Angajati (username,pass) values (?,?)")){
           // preStmt.setInt(1,entity.getId());
            preStmt.setString(1,entity.getUsername());
            preStmt.setString(2,entity.getPass());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting angajat{}",integer);
        Connection con=dbUtils.getConnection();
        if (integer <0)
            throw new IllegalArgumentException("argument is negative");
        Angajat angajat = findOne(integer);
        try {
            PreparedStatement preStmt = con.prepareStatement("delete from Angajati where id=?");
            preStmt.setInt(1, integer);
            int result=preStmt.executeUpdate();
            if (result> 0) {
                logger.trace("Deleted {} instances",result);

            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
    }

    @Override
    public void update(Integer integer, Angajat entity) {
        logger.traceEntry("updating request {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Angajati set pass=? where id=?")){

            preStmt.setString(1,entity.getPass());
            preStmt.setInt(2,entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);

        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Angajat findOne(Integer integer) {
         logger.traceEntry();
        Connection con=dbUtils.getConnection();
        Angajat angajat=new Angajat();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati where id like ?")) {
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String username = result.getString("username");
                    String pass = result.getString("pass");
                    angajat.setUsername(username);
                    angajat.setPass(pass);
                    angajat.setId(integer);


                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(angajat);
    }

    @Override
    public Iterable<Angajat> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Angajat> angajati=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id=result.getInt("id");
                    String username=result.getString("username");
                    String pass=result.getString("pass");
                    Angajat angajat=new Angajat(id,username,pass);
                    angajat.setId(id);
                    angajati.add(angajat);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
        return angajati;
    }

    @Override
    public Angajat findByUserPass(String user, String pass) {
        Iterable<Angajat> angajati=findAll();
        Angajat result=new Angajat();
        for(Angajat a:angajati){
            if(a.getPass().equals(pass)&&a.getUsername().equals(user))
            {
             result.setUsername(user);
             result.setPass(pass);
             result.setId(a.getId());
             return result;
            }
        }
        return null;
    }
}
