package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

/**
 * The Abstract class that contains all the reflection-using functions that query and manage the database
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    private String createInsertQuery(T t) {
        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("INSERT INTO orders_management.");
        finalQuery.append(type.getSimpleName()).append(" (");
        StringBuilder valuesQuery = new StringBuilder(" VALUES( ");
        boolean first = true;
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                if (first){
                    first = false;
                }
                else{
                    valuesQuery.append(", ");
                    finalQuery.append(", ");
                }
                finalQuery.append(field.getName());
                value = field.get(t);
                if (field.getGenericType().toString().equals("class java.lang.String")) {
                    valuesQuery.append("'").append(value).append("'");
                } else
                    valuesQuery.append(value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        finalQuery.append(") ").append(valuesQuery).append(")");
        System.out.println(finalQuery);
        return finalQuery.toString();
    }

    private String createUpdateQuery(T oldT, T newT){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName()).append(" SET ");
        boolean first = true;
        int id = 0;
        for (Field field : oldT.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                if (first) first = false;  else sb.append(", ");
                value = field.get(newT);
                if (field.getName().equals("id")) {
                    id = (int) field.get(oldT);
                    value = id;
                }
                sb.append(field.getName()).append(" = ");
                if (field.getGenericType().toString().equals("class java.lang.String")) {
                    sb.append("'").append(value).append("'");
                } else
                    sb.append(value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append(" WHERE id = ").append(id);
        System.out.println(sb);
        return sb.toString();
    }

    private String createDeleteQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        int id = 0;
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                if (field.getName().equals("id")){
                    id = (int) field.get(t);break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        sb.append(" WHERE id = ").append(id);
        System.out.println(sb);
        return sb.toString();
    }

    public List<T> getAll() {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query;
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
        queryBuilder.append(type.getSimpleName());
        query = queryBuilder.toString();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:getAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public List<T> findAll(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + "orders_management.order " +
                " WHERE id" + type.getSimpleName() + " = " + id;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IntrospectionException | SQLException | InvocationTargetException | IllegalArgumentException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(T t) {
        // TODO:
        Connection connection = null;
        PreparedStatement insertStatement = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            insertStatement = connection.prepareStatement(query);
            insertStatement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
    }

    public T update(T oldT, T newT) {
        // TODO:
        Connection connection = null;
        PreparedStatement updateStatement = null;
        String query = createUpdateQuery(oldT, newT);
        try {
            connection = ConnectionFactory.getConnection();
            updateStatement = connection.prepareStatement(query);
            updateStatement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
        return newT;
    }

    public void delete(T t){
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        String query = createDeleteQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            deleteStatement = connection.prepareStatement(query);
            deleteStatement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(connection);
        }
    }

    public void deleteAllOrders(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String deleteQuery = "DELETE FROM " + "orders_management.order " +
                " WHERE id" + type.getSimpleName() + " = " + id;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(deleteQuery);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}
