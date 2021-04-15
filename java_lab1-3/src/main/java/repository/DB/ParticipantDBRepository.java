package repository.DB;

import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.Interface.IParticipantRepository;
import repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantDBRepository implements IParticipantRepository {

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public ParticipantDBRepository(Properties props) {
        logger.info("Initializing ParticipantDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Participant entity) {
        logger.traceEntry("saving participant{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participanti (nume,echipa,capacMotor) values (?,?,?)")){
            //preStmt.setInt(1,entity.getId());
            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getEchipa());
            preStmt.setInt(3,entity.getCapacMotor());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting participant{}",integer);
        Connection con=dbUtils.getConnection();
        if (integer <0)
            throw new IllegalArgumentException("argument is negative");
        Participant participant = findOne(integer);
        try {
            PreparedStatement preStmt = con.prepareStatement("delete from Participanti where id=?");
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
    public void update(Integer integer, Participant entity) {
        logger.traceEntry("updating participant {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Participanti set echipa=?, capacMotor=? where id=?")){

            preStmt.setString(1,entity.getNume());
            preStmt.setInt(2,entity.getCapacMotor());
            preStmt.setInt(3,entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);

        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Participant findOne(Integer integer) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        Participant participant=new Participant();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti where id like ?")) {
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String nume = result.getString("nume");
                    String echipa = result.getString("echipa");
                    int capacMotor = result.getInt("capacMotor");
                    participant.setNume(nume);
                    participant.setEchipa(echipa);
                    participant.setCapacMotor(capacMotor);
                    participant.setId(integer);


                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(participant);
    }

//int participantId, String nume, String echipa, int capacMotor
    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id=result.getInt("id");
                    String nume=result.getString("nume");
                    String echipa=result.getString("echipa");
                    int capacMotor=result.getInt("capacMotor");
                    Participant participant=new Participant(id,nume,echipa,capacMotor);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error db "+e);

        }logger.traceExit();
        return participants;
    }

    @Override
    public List<Participant> findByEchipa(String echipa) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti where echipa like ?")) {
            preStmt.setString(1,"%"+echipa+"%");
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    int capacMotor = result.getInt("capacMotor");


                    Participant participant=new Participant(id,nume,echipa,capacMotor);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(participants);
    }
}
