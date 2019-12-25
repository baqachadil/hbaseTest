package com.example.demo.services;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;
import com.example.demo.config.HbaseProperties;


@Service
public class TestService {
	@Autowired
    private HbaseTemplate hbaseTemplate;
	
	@Autowired
    private HbaseProperties hbaseProperties;
	
	private Table table1;
    private String tableName = "useryassine";
    private String family1 = "PersonalData";
    private String family2 = "ProfessionalData";

    public void createHbaseTable() throws IOException {           	 

    	    	org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
    	        configuration.set("hbase.zookeeper.quorum", this.hbaseProperties.getZkQuorum());
    	        configuration.set("hbase.rootdir", this.hbaseProperties.getRootDir());
    	        configuration.set("zookeeper.znode.parent", this.hbaseProperties.getZkBasePath());
    	        Connection connection = ConnectionFactory.createConnection(configuration);
    	        Admin admin = connection.getAdmin();

    	        HTableDescriptor ht = new HTableDescriptor(TableName.valueOf(tableName));
    	        ht.addFamily(new HColumnDescriptor(family1));
    	        ht.addFamily(new HColumnDescriptor(family2));
    	        System.out.println("connecting");

    	        System.out.println("Creating Table");
    	        createOrOverwrite(admin, ht);
    	        System.out.println("Done......");

    	        table1 = connection.getTable(TableName.valueOf(tableName));

    	        try {
    	            System.out.println("Adding user: user1");
    	            byte[] row1 = Bytes.toBytes("user1");
    	            Put p = new Put(row1);

    	            p.addColumn(family1.getBytes(), "name".getBytes(), Bytes.toBytes("ahmed"));
    	            p.addColumn(family1.getBytes(), "address".getBytes(), Bytes.toBytes("tunis"));
    	            p.addColumn(family2.getBytes(), "company".getBytes(), Bytes.toBytes("biat"));
    	            p.addColumn(family2.getBytes(), "salary".getBytes(), Bytes.toBytes("10000"));
    	            table1.put(p);

    	            System.out.println("Adding user: user2");
    	            byte[] row2 = Bytes.toBytes("user2");
    	            Put p2 = new Put(row2);
    	            p2.addColumn(family1.getBytes(), "name".getBytes(), Bytes.toBytes("imen"));
    	            p2.addColumn(family1.getBytes(), "tel".getBytes(), Bytes.toBytes("21212121"));
    	            p2.addColumn(family2.getBytes(), "profession".getBytes(), Bytes.toBytes("educator"));
    	            p2.addColumn(family2.getBytes(), "company".getBytes(), Bytes.toBytes("insat"));
    	            table1.put(p2);

    	            System.out.println("reading data...");
    	            Get g = new Get(row1);

    	            Result r = table1.get(g);
    	            System.out.println(Bytes.toString(r.getValue(family1.getBytes(), "name".getBytes())));

    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        } finally {
    	            table1.close();
    	            connection.close();
    	        }
    	    }

    	    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
    	        if (admin.tableExists(table.getTableName())) {
    	            admin.disableTable(table.getTableName());
    	            admin.deleteTable(table.getTableName());
    	        }
    	        admin.createTable(table);
    	    }
    
}
