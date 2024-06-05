package fiap.tds.repositories;

import fiap.tds.models.DetalhesPedido;
import fiap.tds.models.ConexaoOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class DetalhesPedidoRepositoryOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_detalhes_pedido";
    private static final Map<String, String> TB_COLUMNS = Map.of(
        "QUANTIDADE", "quantidade",
        "SUBTOTAL", "subtotal",
        "FK_PEDIDO", "fk_pedido",
        "FK_PRODUTO", "fk_prod"
    );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public DetalhesPedidoRepositoryOracle(){
    }

    public DetalhesPedido findById(int fk_pedido, int fk_prod){
        var detalhesPedido = new DetalhesPedido();
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = ? AND %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("FK_PEDIDO"),
                        TB_COLUMNS.get("FK_PRODUTO")
                    )
            )
        )
        {
            stmt.setInt(1, fk_pedido);
            stmt.setInt(2, fk_prod);
            var rs = stmt.executeQuery();
            if(rs.next()){
                var quantidade = rs.getInt(TB_COLUMNS.get("QUANTIDADE"));
                var subtotal = rs.getDouble(TB_COLUMNS.get("SUBTOTAL"));
                var _fk_pedido = rs.getInt(TB_COLUMNS.get("FK_PEDIDO"));
                var _fk_prod = rs.getInt(TB_COLUMNS.get("FK_PRODUTO"));
                detalhesPedido = new DetalhesPedido(quantidade,subtotal,_fk_pedido,_fk_prod);
                LOGGER.info("Detalhes pedido retornado com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar detalhes pedido: {0}", e.getMessage()));
        }

        return detalhesPedido;
    }

    public void create(DetalhesPedido detalhesPedido){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("QUANTIDADE"),
                        TB_COLUMNS.get("SUBTOTAL"),
                        TB_COLUMNS.get("FK_PEDIDO"),
                        TB_COLUMNS.get("FK_PRODUTO")
                    )
            )
        )
        {
            stmt.setInt(1, detalhesPedido.getQuantidade());
            stmt.setDouble(2, detalhesPedido.getSubtotal());
            stmt.setInt(3, detalhesPedido.getFk_pedido());
            stmt.setInt(4, detalhesPedido.getFk_prod());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Detalhes pedido criado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao criar detalhes pedido: {0}", e.getMessage()));
        }
    }

    public List<DetalhesPedido> readAll(){
        var lista = new ArrayList<DetalhesPedido>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new DetalhesPedido(
                        rs.getInt(TB_COLUMNS.get("QUANTIDADE")),
                        rs.getDouble(TB_COLUMNS.get("SUBTOTAL")),
                        rs.getInt(TB_COLUMNS.get("FK_PEDIDO")),
                        rs.getInt(TB_COLUMNS.get("FK_PRODUTO"))
                    )
                );
            }
            LOGGER.info("Detalhes pedidos retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar detalhes pedidos: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(DetalhesPedido detalhesPedido){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("QUANTIDADE"),
                        TB_COLUMNS.get("SUBTOTAL"),
                        TB_COLUMNS.get("FK_PEDIDO"),
                        TB_COLUMNS.get("FK_PRODUTO"),
                        TB_COLUMNS.get("FK_PEDIDO"),
                        TB_COLUMNS.get("FK_PRODUTO")
                    )
            )
        )
        {
            stmt.setInt(1, detalhesPedido.getQuantidade());
            stmt.setDouble(2, detalhesPedido.getSubtotal());
            stmt.setInt(3, detalhesPedido.getFk_pedido());
            stmt.setInt(4, detalhesPedido.getFk_prod());
            stmt.setInt(5, detalhesPedido.getFk_pedido());
            stmt.setInt(6, detalhesPedido.getFk_prod());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Detalhes pedido atualizado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao atualizar detalhes pedido: {0}", e.getMessage()));
        }
        return 0;
    }

    public void delete(int fk_pedido, int fk_prod){
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "DELETE FROM %s WHERE %s = ? AND %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("FK_PEDIDO"),
                        TB_COLUMNS.get("FK_PRODUTO")
                    )
            )
        )
        {
            stmt.setInt(1, fk_pedido);
            stmt.setInt(2, fk_prod);
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Detalhes pedido removido com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar detalhes pedido: {0}", e.getMessage()));
        }
    }
}
