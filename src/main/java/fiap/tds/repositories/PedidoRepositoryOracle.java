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
    private static final String TB_NAME = "tb_pedido";

    private static final Map<String, String> TB_COLUMNS = Map.ofEntries(
        Map.entry("ID", "id_pedido"),
        Map.entry("DT_PEDIDO", "dt_pedido"),
        Map.entry("STATUS", "status_pedido"),
        Map.entry("VALOR_TOTAL", "valor_total"),
        Map.entry("METODO_PAGAMENTO", "metodo_pgto"),
        Map.entry("FK_CLIENTE", "fk_cliente")
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
                var dt_pedido = rs.getDate(TB_COLUMNS.get("DT_PEDIDO"));
                var status_pedido = rs.getString(TB_COLUMNS.get("STATUS"));
                var valor_total = rs.getDouble(TB_COLUMNS.get("VALOR_TOTAL"));
                var metodo_pagamento = rs.getString(TB_COLUMNS.get("METODO_PAGAMENTO"));
                var fk_cliente = rs.getInt(TB_COLUMNS.get("FK_CLIENTE"));
                pedido = new Pedido(_id,dt_pedido,status_pedido,valor_total,metodo_pagamento,fk_cliente);
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
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("STATUS"),
                        TB_COLUMNS.get("VALOR_TOTAL"),
                        TB_COLUMNS.get("METODO_PAGAMENTO"),
                        TB_COLUMNS.get("FK_CLIENTE")
                    )
            )
        )
        {
            stmt.setString(1, pedido.getStatus_pedido());
            stmt.setDouble(2, pedido.getValor_total());
            stmt.setString(3, pedido.getMetodo_pgto());
            stmt.setInt(4, pedido.getFk_cliente());
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
                        rs.getDate(TB_COLUMNS.get("DT_PEDIDO")),
                        rs.getString(TB_COLUMNS.get("STATUS")),
                        rs.getDouble(TB_COLUMNS.get("VALOR_TOTAL")),
                        rs.getString(TB_COLUMNS.get("METODO_PAGAMENTO")),
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
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("STATUS"),
                        TB_COLUMNS.get("VALOR_TOTAL"),
                        TB_COLUMNS.get("METODO_PAGAMENTO"),
                        TB_COLUMNS.get("FK_CLIENTE"),
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setString(1, pedido.getStatus_pedido());
            stmt.setDouble(2, pedido.getValor_total());
            stmt.setString(3, pedido.getMetodo_pgto());
            stmt.setInt(4, pedido.getFk_cliente());
            stmt.setInt(5, pedido.getId());
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
