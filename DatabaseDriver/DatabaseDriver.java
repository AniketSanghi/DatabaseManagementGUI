package DatabaseDriver;

import java.sql.*;

public class DatabaseDriver
{
	Statement request;
	Connection database;
	ResultSet result;

	public void open(String databasename) throws Exception
	{
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		database = DriverManager.getConnection("jdbc:derby:" + databasename);
	}

	public void create(String databasename) throws Exception
	{
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		database = DriverManager.getConnection("jdbc:derby:" + databasename + ";create=true");
	}

	public void close() throws Exception
	{
		
		database.close();
	}

	public Boolean execute(String query) throws Exception
	{
		request = database.createStatement();
		return request.execute(query);
	}

	public void executeQuery(String query) throws Exception
	{
		request = database.createStatement();
		result = request.executeQuery(query);
	}

	public int executeUpdate(String query) throws Exception
	{
		request = database.createStatement();
		return request.executeUpdate(query);
	}

	public int getColumnCount() throws Exception
	{
		ResultSetMetaData rsmd = result.getMetaData();
		return rsmd.getColumnCount();
	}

	public String [] getColumnNames(int range) throws Exception
	{
		String name[]= new String [getColumnCount()];
		ResultSetMetaData data = result.getMetaData();
		for(int i=1;i<=name.length;++i)
		{
			name[i-1]=data.getColumnLabel(i);
		}
		return name;
	}

	public String [][] getData() throws Exception
	{
		String data[][]=new String [65536][getColumnCount()];
		int j=0;
		if(!result.next())
			return null;
		do
		{
			for(int i=1;i<=getColumnCount();++i)
				data[j][i-1] = result.getString(i);
			j+=1;
		}while(result.next());

		String data1[][] = new String[j][getColumnCount()];
		for(int i=0;i<data1.length;++i)
			for(j=0;j<data[i].length;++j)
			{
				data1[i][j]=data[i][j];
			}

		return data1;
	}

	public String [] getTableNames() throws Exception
	{
		DatabaseMetaData md = database.getMetaData();
		ResultSet rs = md.getTables(null, "APP", "%", null);
		String names[] = new String[100000];
		int j=0;
		if(!result.next())
			return null;
		while (rs.next()) 
		{
		  names[j]=rs.getString(3);
		  j++;
		}
		String namesFinal[] = new String[j];
		for(int i=0;i<j;++i) namesFinal[i]=names[j];

		return names;
	}

	/*public static void main(String args[]) throws Exception{
		DatabaseDriver dd = new DatabaseDriver();
		dd.open("db");
		dd.execute("create table t1(name varchar(20),roll integer, age integer)");
		dd.executeQuery("select * from test");
		String names[]=dd.getColumnNames(10);
		for(int i=0;i<names.length;++i)
			System.out.println(names[i]);
	}*/
}