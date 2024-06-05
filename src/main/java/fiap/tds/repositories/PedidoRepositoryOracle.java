package fiap.tds.repositories;

import fiap.tds.models.ConexaoOracle;
import fiap.tds.models.Pedido;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class PedidoRepositoryOracle extends ConexaoOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final Map<String, String> TB_NAME = Map.ofEntries(
        Map.entry("PEDIDO","tb_pedido"),
        Map.entry("DET_PEDIDO","tb_detpedido"),
        Map.entry("PRODUTO","tb_produto"),
        Map.entry("CLIENTE","tb_cliente")
    );

    private static final Map<String, String> TB_COLUMNS = Map.ofEntries(
        Map.entry("ID", "id_pedido"),
        Map.entry("DT_PEDIDO", "dt_pedido"),
        Map.entry("STATUS", "status_pedido"),
        Map.entry("VALOR_TOTAL", "valor_total"),
        Map.entry("METODO_PAGAMENTO", "metodo_pgto"),
        Map.entry("FK_CLIENTE", "fk_cliente"),
        Map.entry("QUANTIDADE", "quantidade"),
        Map.entry("SUBTOTAL", "subtotal"),
        Map.entry("FK_PEDIDO", "fk_pedido"),
        Map.entry("FK_PRODUTO", "fk_prod")
    );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public PedidoRepositoryOracle(){
    }

    public Pedido findById(int id){
        var pedido = new Pedido();
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = ? INNER JOIN %s on %s = %s"
                    .formatted(
                        TB_NAME.get("PEDIDO"),
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
                pedido = new Pedido(_id,cep,logradouro,numero,bairro,cidade,estado,pais,info_adicionais,tipo_end,fk_cliente);
                LOGGER.info("Pedido retornado com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar pedido: {0}", e.getMessage()));
        }

        return pedido;
    }

    public void create(Pedido pedido){
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
            stmt.setInt(1, pedido.getCep());
            stmt.setString(2, pedido.getLogradouro());
            stmt.setInt(3, pedido.getNumero());
            stmt.setString(4, pedido.getBairro());
            stmt.setString(5, pedido.getCidade());
            stmt.setString(6, pedido.getEstado());
            stmt.setString(7, pedido.getPais());
            stmt.setString(8, pedido.getInfo_adicionais());
            stmt.setString(9, pedido.getTipo_end());
            stmt.setInt(10, pedido.getFk_cliente());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Pedido criado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao criar pedido: {0}", e.getMessage()));
        }
    }

    public List<Pedido> readAll(){
        var lista = new ArrayList<Pedido>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new Pedido(
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
            LOGGER.info("Pedidos retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar pedidos: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Pedido pedido){
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
            stmt.setInt(1, pedido.getCep());
            stmt.setString(2, pedido.getLogradouro());
            stmt.setInt(3, pedido.getNumero());
            stmt.setString(4, pedido.getBairro());
            stmt.setString(5, pedido.getCidade());
            stmt.setString(6, pedido.getEstado());
            stmt.setString(7, pedido.getPais());
            stmt.setString(8, pedido.getInfo_adicionais());
            stmt.setString(9, pedido.getTipo_end());
            stmt.setInt(10, pedido.getFk_cliente());
            stmt.setInt(11, pedido.getId());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Pedido atualizado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao atualizar pedido: {0}", e.getMessage()));
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
                LOGGER.info("Pedido removido com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar pedido: {0}", e.getMessage()));
        }
    }
}
