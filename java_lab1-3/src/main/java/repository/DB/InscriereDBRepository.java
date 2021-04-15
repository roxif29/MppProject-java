package repository.DB;

import model.Cursa;
import model.Inscriere;
import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.Interface.IInscriereRepository;
import repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InscriereDBRepository implements IInscriereRepository{

    private JdbcUtils dbUtils;
    private CursaDBRepository cursaDBRepository;
    private ParticipantDBRepository participantDBRepository;


    private static final Logger logger= LogManager.getLogger();

    public InscriereDBRepository(Properties props) {
        logger.info("Initializing InscriereDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        return 0;
    }
//int id,  Cursa c, Inscriere p
    @Override
    public void save(Inscriere entity) {
        logger.traceEntry("saving inscriere{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Inscrieri (cursaId,participantId) values (?,?)")){
           // preStmt.setInt(1,entity.getId());
            preStmt.setInt(1,entity.getC().getId());
            preStmt.setInt(2,entity.getP().getId());

            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting inscriere{}",integer);
        Connection con=dbUtils.getConnection();
        if (integer <0)
            throw new IllegalArgumentException("argument is negative");
        Inscriere inscriere = findOne(integer);
        try {
            PreparedStatement preStmt = con.prepareStatement("delete from Inscrieri where id=?");
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
    public void update(Integer integer, Inscriere entity) {
        logger.traceEntry("updating request {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Inscrieri set participantId=? where id=?")){

            preStmt.setInt(1,entity.getP().getId());
            preStmt.setInt(2,entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);

        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }
    //int id, , Cursa c, Inscriere p
    @Override
    public Inscriere findOne(Integer integer) {

        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        Inscriere inscriere=new Inscriere();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Inscrieri where id like ?")) {
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idCursa = result.getInt("idCursa");
                    int idPart = result.getInt("idPart");
                    Cursa c=cursaDBRepository.findOne(idCursa);
                    Participant p=participantDBRepository.findOne(idPart);
                    inscriere.setId(integer);
                    inscriere.setC(c);
                    inscriere.setP(p);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(inscriere);
    }
    //int id, , Cursa c, Inscriere p
    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Inscriere> inscrieri=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Inscrieri")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id=result.getInt("id");
                    int idCursa = result.getInt("idCursa");
                    int idPart = result.getInt("idPart");
                    Inscriere i=findOne(id);
                    Inscriere inscriere=new Inscriere(id,i.getC(),i.getP());
                    inscriere.setId(id);
                    inscrieri.add(inscriere);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
        return inscrieri;

    }
}
