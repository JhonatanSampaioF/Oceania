package fiap.tds.repositories;

import fiap.tds.models.Lojista;
import fiap.tds.models.ConexaoOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class LojistaRepositoryOracle extends ConexaoOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_lojista";
    private static final Map<String, String> TB_COLUMNS = Map.of(
        "ID", "id_lojista",
        "NOME", "nm_lojista",
        "CNPJ", "cnpj",
        "EMAIL", "email",
        "SENHA", "senha",
        "DATA_CRIACAO", "dt_criacao"
    );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public LojistaRepositoryOracle(){
    }

    public Lojista findById(int id){
        var lojista = new Lojista();
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
                var nome = rs.getString(TB_COLUMNS.get("NOME"));
                var cnpj = rs.getString(TB_COLUMNS.get("CNPJ"));
                var email = rs.getString(TB_COLUMNS.get("EMAIL"));
                var senha = rs.getString(TB_COLUMNS.get("SENHA"));
                var dt_criacao = rs.getDate(TB_COLUMNS.get("DT_CRIACAO"));
                lojista = new Lojista(_id,nome,cnpj,email,senha,dt_criacao);
                LOGGER.info("Lojista retornado com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar lojista: {0}", e.getMessage()));
        }

        return lojista;
    }

    public void create(Lojista lojista){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("NOME"),
                        TB_COLUMNS.get("CNPJ"),
                        TB_COLUMNS.get("EMAIL"),
                        TB_COLUMNS.get("SENHA")
                    )
            )
        )
        {
            stmt.setString(1, lojista.getNm_lojista());
            stmt.setString(2, lojista.getCnpj());
            stmt.setString(3, lojista.getEmail());
            stmt.setString(4, lojista.getSenha());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Lojista criado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao criar lojista: {0}", e.getMessage()));
        }
    }

    public List<Lojista> readAll(){
        var lista = new ArrayList<Lojista>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new Lojista(
                        rs.getInt(TB_COLUMNS.get("ID")),
                        rs.getString(TB_COLUMNS.get("NOME")),
                        rs.getString(TB_COLUMNS.get("CNPJ")),
                        rs.getString(TB_COLUMNS.get("EMAIL")),
                        rs.getString(TB_COLUMNS.get("SENHA")),
                        rs.getDate(TB_COLUMNS.get("DT_CRIACAO"))
                    )
                );
            }
            LOGGER.info("Lojistas retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar lojistas: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Lojista lojista){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("NOME"),
                        TB_COLUMNS.get("CNPJ"),
                        TB_COLUMNS.get("EMAIL"),
                        TB_COLUMNS.get("SENHA"),
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setString(1, lojista.getNm_lojista());
            stmt.setString(2, lojista.getCnpj());
            stmt.setString(3, lojista.getEmail());
            stmt.setString(4, lojista.getSenha());
            stmt.setInt(5, lojista.getId());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Lojista atualizado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao atualizar lojista: {0}", e.getMessage()));
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
                LOGGER.info("Lojista removido com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar lojista: {0}", e.getMessage()));
        }
    }
}
