package fiap.tds.repositories;

import fiap.tds.models.Endereco;
import fiap.tds.models.ConexaoOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class EnderecoRepositoryOracle extends ConexaoOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_endereco";
    private static final Map<String, String> TB_COLUMNS = Map.ofEntries(
        Map.entry("ID", "id_end"),
        Map.entry("CEP", "cep"),
        Map.entry("LOGRADOURO", "logradouro"),
        Map.entry("NUMERO", "numero"),
        Map.entry("BAIRRO", "bairro"),
        Map.entry("CIDADE", "cidade"),
        Map.entry("ESTADO", "estado"),
        Map.entry("PAIS", "pais"),
        Map.entry("INFOS_ADICIONAIS", "infos_adicionais"),
        Map.entry("TIPO_ENDERECO", "tipo_end"),
        Map.entry("FK_CLIENTE", "fk_clie")
        );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public EnderecoRepositoryOracle(){
    }

    public Endereco findById(int id){
        var endereco = new Endereco();
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
                var cep = rs.getInt(TB_COLUMNS.get("CEP"));
                var logradouro = rs.getString(TB_COLUMNS.get("LOGRADOURO"));
                var numero = rs.getInt(TB_COLUMNS.get("NUMERO"));
                var bairro = rs.getString(TB_COLUMNS.get("BAIRRO"));
                var cidade = rs.getString(TB_COLUMNS.get("CIDADE"));
                var estado = rs.getString(TB_COLUMNS.get("ESTADO"));
                var pais = rs.getString(TB_COLUMNS.get("PAIS"));
                var info_adicionais = rs.getString(TB_COLUMNS.get("INFOS_ADICIONAIS"));
                var tipo_end = rs.getString(TB_COLUMNS.get("TIPO_ENDERECO"));
                var fk_cliente = rs.getInt(TB_COLUMNS.get("FK_CLIENTE"));
                endereco = new Endereco(_id,cep,logradouro,numero,bairro,cidade,estado,pais,info_adicionais,tipo_end,fk_cliente);
                LOGGER.info("Endereço retornado com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar endereço: {0}", e.getMessage()));
        }

        return endereco;
    }

    public void create(Endereco endereco){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("CEP"),
                        TB_COLUMNS.get("LOGRADOURO"),
                        TB_COLUMNS.get("NUMERO"),
                        TB_COLUMNS.get("BAIRRO"),
                        TB_COLUMNS.get("CIDADE"),
                        TB_COLUMNS.get("ESTADO"),
                        TB_COLUMNS.get("PAIS"),
                        TB_COLUMNS.get("INFOS_ADICIONAIS"),
                        TB_COLUMNS.get("TIPO_ENDERECO"),
                        TB_COLUMNS.get("FK_CLIENTE")
                    )
            )
        )
        {
            stmt.setInt(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setInt(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getPais());
            stmt.setString(8, endereco.getInfo_adicionais());
            stmt.setString(9, endereco.getTipo_end());
            stmt.setInt(10, endereco.getFk_cliente());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Endereço criado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao criar endereço: {0}", e.getMessage()));
        }
    }

    public List<Endereco> readAll(){
        var lista = new ArrayList<Endereco>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new Endereco(
                        rs.getInt(TB_COLUMNS.get("ID")),
                        rs.getInt(TB_COLUMNS.get("CEP")),
                        rs.getString(TB_COLUMNS.get("LOGRADOURO")),
                        rs.getInt(TB_COLUMNS.get("NUMERO")),
                        rs.getString(TB_COLUMNS.get("BAIRRO")),
                        rs.getString(TB_COLUMNS.get("CIDADE")),
                        rs.getString(TB_COLUMNS.get("ESTADO")),
                        rs.getString(TB_COLUMNS.get("PAIS")),
                        rs.getString(TB_COLUMNS.get("INFOS_ADICIONAIS")),
                        rs.getString(TB_COLUMNS.get("TIPO_ENDERECO")),
                        rs.getInt(TB_COLUMNS.get("FK_CLIENTE"))
                    )
                );
            }
            LOGGER.info("Endereços retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar endereços: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Endereco endereco){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("CEP"),
                        TB_COLUMNS.get("LOGRADOURO"),
                        TB_COLUMNS.get("NUMERO"),
                        TB_COLUMNS.get("BAIRRO"),
                        TB_COLUMNS.get("CIDADE"),
                        TB_COLUMNS.get("ESTADO"),
                        TB_COLUMNS.get("PAIS"),
                        TB_COLUMNS.get("INFOS_ADICIONAIS"),
                        TB_COLUMNS.get("TIPO_ENDERECO"),
                        TB_COLUMNS.get("FK_CLIENTE"),
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setInt(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setInt(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getPais());
            stmt.setString(8, endereco.getInfo_adicionais());
            stmt.setString(9, endereco.getTipo_end());
            stmt.setInt(10, endereco.getFk_cliente());
            stmt.setInt(11, endereco.getId());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Endereço atualizado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao atualizar endereço: {0}", e.getMessage()));
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
                LOGGER.info("Endereço removido com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar endereço: {0}", e.getMessage()));
        }
    }
}
