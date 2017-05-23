package me.chaseking.advancedjava.finalproject.database;

/**
 * @author Chase King
 */
public class MySQLScript {
    private StringBuilder builder;

    public static MySQLScript createTable(String table){
        return new MySQLScript()
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(table)
                .append("(");
        //TODO A better way
    }

    private MySQLScript(){
        builder = new StringBuilder();
    }

    public MySQLScript append(String str){
        builder.append(str);
        return this;
    }

    public String build(){
        return builder.toString();
    }
}