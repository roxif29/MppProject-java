package repository.DB;

import model.Cursa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.Interface.ICursaRepository;
import repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaDBRepository implements ICursaRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CursaDBRepository(Properties props) throws SQLException {
        logger.info("Initializing CursaDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    //int id, int nrPers, int capacMotor
    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Cursa entity) {
        logger.traceEntry("saving cursa{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Curse (nrPers,capacMotor) values (?,?)")){
           // preStmt.setInt(1,entity.getId());
            preStmt.setInt(1,entity.getNrPers());
            preStmt.setInt(2,entity.getCapacMotor());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting cursa{}",integer);
        Connection con=dbUtils.getConnection();
        if (integer <0)
            throw new IllegalArgumentException("argument is negative");
        Cursa cursa = findOne(integer);
        try {
            PreparedStatement preStmt = con.prepareStatement("delete from Curse where id=?");
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
    //int id, int nrPers, int capacMotor
    @Override
    public void update(Integer integer, Cursa entity) {
        logger.traceEntry("updating cursa {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Curse set nrPers=? where id=?")){
            preStmt.setInt(1,entity.getNrPers());
            preStmt.setInt(2,entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);

        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }
    //int id, int nrPers, int capacMotor
    @Override
    public Cursa findOne(Integer integer){
        logger.traceEntry();
    Connection con=dbUtils.getConnection();
    Cursa cursa=new Cursa();
        try(
    PreparedStatement preStmt=con.prepareStatement("select * from Curse where id like ?")) {
        preStmt.setInt(1,integer);
        try(ResultSet result=preStmt.executeQuery()) {
            while (result.next()) {
                int nrPers = result.getInt("nrPers");
                int capacMotor = result.getInt("capacMotor");
                cursa.setCapacMotor(capacMotor);
                cursa.setNrPers(nrPers);
                cursa.setId(integer);


            }
        }
    } catch (
    SQLException e) {
        logger.error(e);
        System.err.println("Error DB "+e);
    }
        return logger.traceExit(cursa);
    }
    //int id, int nrPers, int capacMotor
    @Override
    public Iterable<Cursa> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Cursa> curse=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Curse")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id=result.getInt("id");
                    int nrPers=result.getInt("nrPers");
                    int capacMotor=result.getInt("capacMotor");
                    Cursa cursa=new Cursa(id,nrPers,capacMotor);
                    cursa.setId(id);
                    curse.add(cursa);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
        return curse;
    }

    @Override
    public List<Cursa> findByCapacMotor(int capacMotor) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Cursa> curse=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Curse where capacMotor like ?")) {
            preStmt.setInt(1,capacMotor);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int nrPers = result.getInt("nrPers");
                    Cursa cursa=new Cursa(id,nrPers,capacMotor);
                    cursa.setId(id);
                    curse.add(cursa);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(curse);
    }
}
