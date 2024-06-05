package fiap.tds.repositories;

import fiap.tds.models.ConexaoOracle;
import fiap.tds.models.Loja;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class LojaRepositoryOracle extends ConexaoOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_loja";
    private static final Map<String, String> TB_COLUMNS = Map.ofEntries(
        Map.entry("ID", "id_loja"),
        Map.entry("NOME", "nm_loja"),
        Map.entry("FK_LOJISTA", "fk_lojista")
    );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public LojaRepositoryOracle(){
    }

    public Loja findById(int id){
        var loja = new Loja();
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                var _id = rs.getInt(TB_COLUMNS.get("ID"));
                var nm_lojista = rs.getString(TB_COLUMNS.get("NOME"));
                var fk_lojista = rs.getInt(TB_COLUMNS.get("FK_LOJISTA"));
                loja = new Loja(_id,nm_lojista,fk_lojista);
                LOGGER.info("Loja retornada com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar loja: {0}", e.getMessage()));
        }

        return loja;
    }

    public void create(Loja loja){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("NOME"),
                        TB_COLUMNS.get("FK_LOJISTA")
                    )
            )
        )
        {
            stmt.setString(1, loja.getNm_loja());
            stmt.setInt(2, loja.getFk_lojista());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Loja criada com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao criar loja: {0}", e.getMessage()));
        }
    }

    public List<Loja> readAll(){
        var lista = new ArrayList<Loja>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new Loja(
                        rs.getInt(TB_COLUMNS.get("ID")),
                        rs.getString(TB_COLUMNS.get("NOME")),
                        rs.getInt(TB_COLUMNS.get("FK_LOJISTA"))
                    )
                );
            }
            LOGGER.info("Lojas retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar lojas: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Loja loja){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("NOME"),
                        TB_COLUMNS.get("FK_LOJISTA"),
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setString(1, loja.getNm_loja());
            stmt.setInt(2, loja.getFk_lojista());
            stmt.setInt(3, loja.getId());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Loja atualizada com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao atualizar loja: {0}", e.getMessage()));
        }
        return 0;
    }

    public void delete(int id){
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "DELETE FROM %s WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setInt(1, id);
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Loja removida com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar loja: {0}", e.getMessage()));
        }
    }
}
